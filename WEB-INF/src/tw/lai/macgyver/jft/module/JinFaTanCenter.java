package tw.lai.macgyver.jft.module;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.stereotype.Service;

import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.util.VoToString;
import tw.lai.macgyver.jft.vo.Base;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderClash;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;

@Service("jftCenter")
public class JinFaTanCenter implements IJinFaTanCenter {
	
	private static Date[] orderStatisticsDate = null;
	
	private static OrderClash[] orderClash = null;
	
	private ICustomerManageDAO hsqlDAO = null;
	
	private IOrderManageDAO hsqlOrderManageDAO = null;
	
	private int rowsOfPage = 20;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	private JsonConfig jsonDateConfig = null;
	
	@Resource(name = "hsqlDAO")
	public void setHsqlDAO(ICustomerManageDAO hsqlDAO) {
		this.hsqlDAO = hsqlDAO;
		
	}
	
	@Resource(name = "hsqlOrderManageDAO")
	public void setHsqlOrderManageDAO(IOrderManageDAO hsqlOrderManageDAO) {
		this.hsqlOrderManageDAO = hsqlOrderManageDAO;
	}
	
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public DateFormat getDateFormat() {
		return dateFormat;
	}
	
	/* (non-Javadoc)
	 * @see tw.lai.macgyver.jft.modle.IJinFaTanCenter#checkUserAuthority(java.lang.String, java.lang.String)
	 */
	public int checkUserAuthority(String userName, String password) {
		int result = AUTHORITY_ERROR;
		
		if ("86352811".equals(userName) && "0935254621".equals(password))
			result = AUTHORITY_NORMAL;
		else if ("MacGyver".equals(userName) && "gyvermac".equals(password))
			result = AUTHORITY_ADMIN;
		
		return result;
	}

	@Override
	public Family retvFamilyInfo(int familyId) {
		// TODO Auto-generated method stub
		Family result = null;
		
		result = this.hsqlDAO.queryFamilyById(familyId);
		
		return result;
	}

	@Override
	public Family[] retvFamilyInfoByPage(int page) {
		// TODO Auto-generated method stub
		Family[] result = null;
		
		// 第一筆從0開始算
		int start = (page - 1) * this.rowsOfPage;
		List<Family> familyList = this.hsqlDAO.queryFamilyByIndex(start, this.rowsOfPage);
		result = familyList.toArray(new Family[0]);
		
		return result;
	}

	@Override
	public int retvFamilyPageAmount() {
		// TODO Auto-generated method stub
		int result = 0;
		
		int amount = this.hsqlDAO.queryFamilyAmount();
		result = amount / this.rowsOfPage;
		if (amount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}

	@Override
	public Member[] retvMemberByFamilyId(int familyId) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		int thisYear = calendar.get(Calendar.YEAR);
		
		return this.retvMemberByFamilyId(familyId, thisYear);
	}
	
	@Override
	public Member[] retvMemberByFamilyId(int familyId, int thisYear) {
		// TODO Auto-generated method stub
		Member[] result = null;
		MacLogger macLog = new MacLogger();
		
		List<Member> memberList = this.hsqlDAO.queryMemberByFamilyId(familyId);
		result = memberList.toArray(new Member[0]);
		// calculate
		for (int i = 0; i < result.length; i++) {
			int birthYear = result[i].getBirthYear();
			result[i].setSixtyYear(this.calculateJiaZi(birthYear));
			result[i].setAnimal(this.calculateAnimal(birthYear));
			result[i].setTaiSui(this.calculateTaiSui(birthYear));
			result[i].setYears(this.convertToChinese(this.calculateYears(birthYear, thisYear), false));
			
			macLog.log("Jia Zi = " + result[i].getSixtyYear() + " | Animal = " + 
					result[i].getAnimal() + " | Tai Sui = " + result[i].isTaiSui());
		}
		
		return result;
	}
	
	@Override
	public String calculateJiaZi(int birthYear) {
		String result = null;
		
		int tempYear = (birthYear + 1911 - 1804) % 60;
		int tianGan = tempYear % 10;
		int diZhi = tempYear % 12;
		
		switch (tianGan) {
		case 0:
			result = Base.TIAN_GAN_JIA;
			break;
		case 1:
			result = Base.TIAN_GAN_YI;
			break;
		case 2:
			result = Base.TIAN_GAN_BING;
			break;
		case 3:
			result = Base.TIAN_GAN_DING;
			break;
		case 4:
			result = Base.TIAN_GAN_WU;
			break;
		case 5:
			result = Base.TIAN_GAN_JI;
			break;
		case 6:
			result = Base.TIAN_GAN_GENG;
			break;
		case 7:
			result = Base.TIAN_GAN_XIN;
			break;
		case 8:
			result = Base.TIAN_GAN_REN;
			break;
		case 9:
			result = Base.TIAN_GAN_GUI;
			break;
		}
		
		switch (diZhi) {
		case 0:
			result += Base.DI_ZHI_ZI;
			break;
		case 1:
			result += Base.DI_ZHI_CHOU;
			break;
		case 2:
			result += Base.DI_ZHI_YIN;
			break;
		case 3:
			result += Base.DI_ZHI_MAO;
			break;
		case 4:
			result += Base.DI_ZHI_CHEN;
			break;
		case 5:
			result += Base.DI_ZHI_SI;
			break;
		case 6:
			result += Base.DI_ZHI_WU;
			break;
		case 7:
			result += Base.DI_ZHI_WEI;
			break;
		case 8:
			result += Base.DI_ZHI_SHEN;
			break;
		case 9:
			result += Base.DI_ZHI_YOU;
			break;
		case 10:
			result += Base.DI_ZHI_XU;
			break;
		case 11:
			result += Base.DI_ZHI_HAI;
			break;
		}
		
		return result;
	}
	
	@Override
	public String calculateAnimal(int birthYear) {
		String result = null;
		
		int tempYear = (birthYear + 1911 - 1804) % 60;
		int diZhi = tempYear % 12;
		
		switch (diZhi) {
		case 0:
			result = Base.ANIMAL_SHU;
			break;
		case 1:
			result = Base.ANIMAL_NIU;
			break;
		case 2:
			result = Base.ANIMAL_HU;
			break;
		case 3:
			result = Base.ANIMAL_TU;
			break;
		case 4:
			result = Base.ANIMAL_LONG;
			break;
		case 5:
			result = Base.ANIMAL_SHE;
			break;
		case 6:
			result = Base.ANIMAL_MA;
			break;
		case 7:
			result = Base.ANIMAL_YANG;
			break;
		case 8:
			result = Base.ANIMAL_HOU;
			break;
		case 9:
			result = Base.ANIMAL_JI;
			break;
		case 10:
			result = Base.ANIMAL_GOU;
			break;
		case 11:
			result = Base.ANIMAL_ZHU;
			break;
		}
		
		return result;
	}
	
	@Override
	public String calculateClash(int birthYear, int thisYear) {
		// TODO Auto-generated method stub
		int offset = (thisYear - 1804) % 12;
		int diZhi = (birthYear + 1911 - 1804) % 12;
		
		int clash = diZhi - offset;
		if (clash < 0)
			clash += 12;
		
		return Base.CLASH[clash];
	}

	@Override
	public boolean calculateTaiSui(int birthYear) {
		Calendar calendar = Calendar.getInstance();
		int thisYear = calendar.get(Calendar.YEAR);
		
		int zeroYear = (thisYear - 1804) % 12;
		int tempYear = (birthYear + 1911 - 1804) % 12;
		
		if ((tempYear == zeroYear) || 
				((tempYear + 6) % 12) == zeroYear)
			return true;
		else
			return false;
	}

	@Override
	public int calculateYears(int birthYear, int thisYear) {
		// TODO Auto-generated method stub
//		Calendar calendar = Calendar.getInstance();
//		int thisYear = calendar.get(Calendar.YEAR) - 1911;
		
		thisYear = thisYear - 1911;
		
		return thisYear - birthYear + 1;
	}

	@Override
	public int convertJiaZiToYear(String tianGan, String diZhi) {
		// TODO Auto-generated method stub
		int result = 0;
		
		String[] tianGanArray = new String[10];
		tianGanArray[0] = Base.TIAN_GAN_JIA;
		tianGanArray[1] = Base.TIAN_GAN_YI;
		tianGanArray[2] = Base.TIAN_GAN_BING;
		tianGanArray[3] = Base.TIAN_GAN_DING;
		tianGanArray[4] = Base.TIAN_GAN_WU;
		tianGanArray[5] = Base.TIAN_GAN_JI;
		tianGanArray[6] = Base.TIAN_GAN_GENG;
		tianGanArray[7] = Base.TIAN_GAN_XIN;
		tianGanArray[8] = Base.TIAN_GAN_REN;
		tianGanArray[9] = Base.TIAN_GAN_GUI;
		
		String[] diZhiArray = new String[12];
		diZhiArray[0] = Base.DI_ZHI_ZI;
		diZhiArray[1] = Base.DI_ZHI_CHOU;
		diZhiArray[2] = Base.DI_ZHI_YIN;
		diZhiArray[3] = Base.DI_ZHI_MAO;
		diZhiArray[4] = Base.DI_ZHI_CHEN;
		diZhiArray[5] = Base.DI_ZHI_SI;
		diZhiArray[6] = Base.DI_ZHI_WU;
		diZhiArray[7] = Base.DI_ZHI_WEI;
		diZhiArray[8] = Base.DI_ZHI_SHEN;
		diZhiArray[9] = Base.DI_ZHI_YOU;
		diZhiArray[10] = Base.DI_ZHI_XU;
		diZhiArray[11] = Base.DI_ZHI_HAI;
		
		int tianGanCount = 0;
		int diZhiCount = 0;
		for (int i = 0; i < 60; i++) {
			if (tianGanArray[tianGanCount].equals(tianGan) && 
					diZhiArray[diZhiCount].equals(diZhi)) {
				result = i;
				break;
			}
			
			tianGanCount++;
			if (tianGanCount >= tianGanArray.length)
				tianGanCount = 0;
			diZhiCount++;
			if (diZhiCount >= diZhiArray.length)
				diZhiCount = 0;
		}
		
		return result + 1924;
	}

	@Override
	public int convertAnimalToYear(int aboutAge, String animal) {
		// TODO Auto-generated method stub
		int result = 0;
		
		String[] shengXiao = new String[12];
		shengXiao[0] = Base.ANIMAL_SHU;
		shengXiao[1] = Base.ANIMAL_NIU;
		shengXiao[2] = Base.ANIMAL_HU;
		shengXiao[3] = Base.ANIMAL_TU;
		shengXiao[4] = Base.ANIMAL_LONG;
		shengXiao[5] = Base.ANIMAL_SHE;
		shengXiao[6] = Base.ANIMAL_MA;
		shengXiao[7] = Base.ANIMAL_YANG;
		shengXiao[8] = Base.ANIMAL_HOU;
		shengXiao[9] = Base.ANIMAL_JI;
		shengXiao[10] = Base.ANIMAL_GOU;
		shengXiao[11] = Base.ANIMAL_ZHU;
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		int rangeStartYear = thisYear - aboutAge - 4;
		int rangeEndYear = thisYear - aboutAge + 5;
		
		int animalOrder = 0;
		for (int i = 0; i < shengXiao.length; i++) {
			if (shengXiao[i].equals(animal)) {
				result = i;
				break;
			}
		}
		result += 1924;
		
		for (int i = result; i < thisYear; i += 12) {
			if (i >= rangeStartYear) {
				result = i;
				break;
			}
		}
		
		return result;
	}

	@Override
	public String replaceNumberToChinese(String input) {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		
		int startConvertChar = 0;
		int endConvertChar = 0;
		for (int i = 0; i < input.length(); i++) {
			char tmpChar = input.charAt(i);
			int tmpNumber = 0;
			
			if (Character.isDigit(tmpChar)) {
				if (i == input.length() - 1)
					result.append(this.convertToChinese(Integer.parseInt(String.valueOf(
							tmpChar)), false));
				
				startConvertChar = i;
				for (int j = startConvertChar + 1; j < input.length(); j++) {
					if (!Character.isDigit(input.charAt(j))) {
						endConvertChar = j;
						tmpNumber = Integer.parseInt(input.substring(
								startConvertChar, endConvertChar));
						result.append(this.convertToChinese(tmpNumber, false));
						result.append(input.charAt(j));
						i = j;
						break;
					} else if (j == input.length() - 1) {
						endConvertChar = input.length();
						tmpNumber = Integer.parseInt(input.substring(
								startConvertChar, endConvertChar));
						result.append(this.convertToChinese(tmpNumber, false));
						i = j;
						break;
					}
				}
			} else {
				result.append(tmpChar);
			}
		}
		
		return result.toString();
	}

	@Override
	public int convertToNumber(String chineseDate) {
		// TODO Auto-generated method stub
		String result = null;
		
		if (chineseDate.length() < 3) {
			if ("初十".equals(chineseDate))
				result = "10";
			else if ('初' == chineseDate.charAt(0))
				result = String.valueOf(this.converNumber(chineseDate.charAt(1)));
			else if ('十' == chineseDate.charAt(0))
				result = "1" + this.converNumber(chineseDate.charAt(1));
			else
				result = this.converNumber(chineseDate.charAt(0)) + "0";
		} else {
			result = String.valueOf(this.converNumber(chineseDate.charAt(0))) + 
					String.valueOf(this.converNumber(chineseDate.charAt(2)));
		}
		
		return Integer.valueOf(result);
	}
	
	private char converNumber(char chineseNumber) {
		char result = '0';
		
		switch (chineseNumber) {
		case '一':
			result = '1';
			break;
		case '二':
			result = '2';
			break;
		case '三':
			result = '3';
			break;
		case '四':
			result = '4';
			break;
		case '五':
			result = '5';
			break;
		case '六':
			result = '6';
			break;
		case '七':
			result = '7';
			break;
		case '八':
			result = '8';
			break;
		case '九':
			result = '9';
			break;
		}
		
		return result;
	}

	@Override
	public String convertSmallToBig(String input) {
		// TODO Auto-generated method stub
		char[] output = input.toCharArray();
		for (int i = 0; i < output.length; i++)
			output[i] += 65248;
		
		return String.valueOf(output).replace('－', '∣');
	}

	@Override
	public String fullSpace(int length, String target, boolean isHead) {
		// TODO Auto-generated method stub
		int fullSize = length - target.length();
		
		if (fullSize > 0) {
			for (int i = 0; i < fullSize; i++) {
				if (isHead)
					target += "　";
				else
					target = "　" + target;				
			}
		}
		return target;
	}

	@Override
	public String convertNameFormat(String name) {
		// TODO Auto-generated method stub
		if (name.length() == 2)
			return name.substring(0, 1) + "　" + name.substring(1, 2);
		else
			return name;
	}

	@Override
	public Family[] searchFamilyByName(String name, int page) {
		// TODO Auto-generated method stub
		Family[] result = null;
		
		int start = (page - 1) * this.rowsOfPage;
		List<Family> familys = this.hsqlDAO.queryFamilyByName(name, start, this.rowsOfPage);
		result = (Family[]) familys.toArray(new Family[0]);
		
		return result;
	}

	@Override
	public Family[] searchFamilyByPhone(String phone, int page) {
		// TODO Auto-generated method stub
		Family[] result = null;
		
		int start = (page - 1) * this.rowsOfPage;
		List<Family> familys = this.hsqlDAO.queryFamilyByPhone(phone, start, this.rowsOfPage);
		result = (Family[]) familys.toArray(new Family[0]);
		
		return result;
	}
	
	@Override
	public Family[] searchFamilyByAddress(String address, int page) {
		// TODO Auto-generated method stub
		Family[] result = null;
		
		int start = (page - 1) * this.rowsOfPage;
		List<Family> familys = this.hsqlDAO.queryFamilyByAddress(address, start, this.rowsOfPage);
		result = (Family[]) familys.toArray(new Family[0]);
		
		return result;
	}
	
	@Override
	public int retvFamilyPageAmountByAddress(String address) {
		// TODO Auto-generated method stub
		int result = 0;
		
		int amount = this.hsqlDAO.countFamilyByAddress(address);
		result = amount / this.rowsOfPage;
		if (amount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}

	@Override
	public int retvFamilyPageAmountByName(String name) {
		// TODO Auto-generated method stub
		int result = 0;
		
		int amount = this.hsqlDAO.countFamilyByName(name);
		result = amount / this.rowsOfPage;
		if (amount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}

	@Override
	public int retvFamilyPageAmountByPhonePage(String phone) {
		// TODO Auto-generated method stub
		int result = 0;
		
		int amount = this.hsqlDAO.countFamilyByPhone(phone);
		result = amount / this.rowsOfPage;
		if (amount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}

	public Family[] getListFromPage(int page, Family[] familys) {
		Family[] result = null;
		
		int pageAmount = this.getTotalPage(familys.length);
		if (page < 1)
			page = 1;
		else if (page > pageAmount)
			page = pageAmount;
		
		int start = (page - 1) * this.rowsOfPage;
		int end = page * this.rowsOfPage;
		end = Math.min(end, familys.length);
		
		System.out.println("start:" + start + " | end:" + end);
		
		result = new Family[end];
		for (int i = 0; i < result.length; i++)
			result[i] = familys[i + start];
		
		return result;
	}

	@Override
	public int getTotalPage(int rowCount) {
		// TODO Auto-generated method stub
		int result = rowCount / this.rowsOfPage;
		if (rowCount % this.rowsOfPage > 0)
			result++;
		if (result < 1)
			result = 1;
		
		return result;
	}

	@Override
	public boolean addCustomer(Family family, Member[] members, int householderIndex) {
		// TODO Auto-generated method stub
		family.setAddress(this.replaceNumberToChinese(family.getAddress()));
		
		// added for seq number on 20120211
		for (int i = 0; i < members.length; i++)
			members[i].setSeqNo(i);
		
		return this.hsqlDAO.addCustomer(family, members, householderIndex);
	}

	@Override
	public boolean modifyCustomer(Family family, Member[] members, int householderIndex) {
		// TODO Auto-generated method stub
		family.setAddress(this.replaceNumberToChinese(family.getAddress()));
		
		// added for seq number on 20120211
		for (int i = 0; i < members.length; i++)
			members[i].setSeqNo(i);
		
		return this.hsqlDAO.modifyCustomer(family, members, householderIndex);
	}

	@Override
	public boolean deleteCustomer(int familyId) {
		// TODO Auto-generated method stub
		return this.hsqlDAO.deleteCustomer(familyId);
	}

	@Override
	public boolean deleteMember(int memberId) {
		// TODO Auto-generated method stub
		return this.hsqlDAO.deleteMemberById(memberId);
	}

	@Override
	public int[] retvOrderYears() {
		// TODO Auto-generated method stub
		int [] result = null;
		
		List<Integer> orderYearList = this.hsqlOrderManageDAO.queryOrderYears();
		result = new int[orderYearList.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = orderYearList.get(i).intValue();
		
		return result;
	}

	@Override
	public Order[] retvOrderByRange(int page, int year) throws ParseException {
		// TODO Auto-generated method stub
		Order[] result = null;
		
		// 第一筆從0開始算
		int start = (page - 1) * this.rowsOfPage;
		
		Date startDate = this.dateFormat.parse(year + "/01/01");
		Date endDate = this.dateFormat.parse(year + "/12/31");
		List<Order> orderList = this.hsqlOrderManageDAO.queryOrderByRange(start, this.rowsOfPage, 
				startDate, endDate);
		result = orderList.toArray(new Order[0]);
		
		return result;
	}

	@Override
	public Order[] retvOrderByPage(int page) {
		// TODO Auto-generated method stub
		Order[] result = null;
		
		// 第一筆從0開始算
		int start = (page - 1) * this.rowsOfPage;
		List<Order> orderList = this.hsqlOrderManageDAO.queryOrderByIndex(start, this.rowsOfPage);
		result = orderList.toArray(new Order[0]);
		
		return result;
	}

	@Override
	public Order[] retvOrderByHouseholder(String householder, int year) throws ParseException {
		// TODO Auto-generated method stub
		Order[] result = null;
		
		Date startDate = this.dateFormat.parse(year + "/01/01");
		Date endDate = this.dateFormat.parse(year + "/12/31");
		List<Order> orderList = this.hsqlOrderManageDAO.queryOrderByHouseholder(householder, 
				startDate, endDate);
		result = orderList.toArray(new Order[0]);
		
		return result;
	}

	@Override
	public int retvOrderPageAmount(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		int result = 0;
		
		int amount = this.hsqlOrderManageDAO.queryOrderAmount(startDate, endDate);
		result = amount / this.rowsOfPage;
		if (amount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}
	
	@Override
	public int retvOrderPageAmount(int orderCount) {
		// TODO Auto-generated method stub
		int result = 0;
		
		result = orderCount / this.rowsOfPage;
		if (orderCount % this.rowsOfPage > 0)
			result++;
		
		return result;
	}

	@Override
	public boolean addOrder(Order order) {
		// TODO Auto-generated method stub
		return this.hsqlOrderManageDAO.insertOrder(order);
	}
	
	@Override
	public boolean modifyOrder(Order order) {
		// TODO Auto-generated method stub
		return this.hsqlOrderManageDAO.updateOrder(order);
	}
	
	@Override
	public boolean deleteOrder(int orderYear, int familyId) {
		// TODO Auto-generated method stub
		return this.hsqlOrderManageDAO.deleteOrder(orderYear, familyId);
	}

	@Override
	public String[] retvAllCity() {
		// TODO Auto-generated method stub
		String[] result = null;
		
		List<String> cityList = this.hsqlDAO.queryAllCity();
		result = cityList.toArray(new String[0]);
		
		return result;
	}

	@Override
	public String[] retvAreaByCity(String city) {
		// TODO Auto-generated method stub
		String[] result = null;
		
		List<String> areaList = this.hsqlDAO.queryAreaByCity(city);
		result = areaList.toArray(new String[0]);
		
		return result;
	}
	
	@Override
	public Date[] retvOrderStatisticsDate() {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		try {
			if (orderStatisticsDate == null) {
				List<String> configurationList = this.hsqlOrderManageDAO.queryConfiguration(
						"OrderStatisticsDate");
				
				Date[] tmpDates = new Date[configurationList.size()];
				for (int i = 0; i < tmpDates.length; i++)
					tmpDates[i] = this.dateFormat.parse(configurationList.get(i));
				
				orderStatisticsDate = tmpDates;
			}
		} catch (ParseException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			macLog.log(ex);
		}
		
		return orderStatisticsDate;
	}

	@Override
	public boolean refreshOrderStatisticsDate() {
		// TODO Auto-generated method stub
		orderStatisticsDate = null;
		return true;
	}

	@Override
	public OrderStatisticsInfo[] retvOrderStatisticsInfo() {
		// TODO Auto-generated method stub
		OrderStatisticsInfo[] result = null;
		
		List<OrderStatisticsInfo> orderStatisticsInfoList = 
				this.hsqlOrderManageDAO.queryOrderStatisticsInfo(
				this.retvOrderStatisticsDate());
		result = orderStatisticsInfoList.toArray(new OrderStatisticsInfo[0]);
		
		return result;
	}
	
	@Override
	public OrderClash[] retvOrderClash(int familyId, int orderYear) {
		// TODO Auto-generated method stub
		OrderClash[] result = null;
		MacLogger macLog = new MacLogger();
		
		OrderClash[] orderClash = getOrderClash(orderYear);
		macLog.debug("Config Order Clash size = " + orderClash.length);
		
		/* change clash date to auto calculation	
		// retrieve order clash info
		if (orderClash == null) {
			List<OrderClash> tmpList = this.hsqlOrderManageDAO.queryOrderClash(orderYear);
			orderClash = tmpList.toArray(new OrderClash[0]);
		}
		 */
		
		// retrieve householder
		Family family = this.hsqlDAO.queryFamilyById(familyId);
		
		// retrieve Family info
		List<Member> members = this.hsqlDAO.queryMemberByFamilyId(familyId);
		
		List orderClashList = new ArrayList();
		OrderClash tmpOrderClash = null;
		Member tmpMember = null;
		for (int i = 0; i < members.size(); i++) {
			for (int j = 0; j< orderClash.length; j++) {
				tmpMember = members.get(i);
				if ((tmpMember.getBirthYear() % 60) == orderClash[j].getClashBirthYear()) {
					tmpOrderClash = new OrderClash();
					tmpOrderClash.setOrderYear(orderYear);
					tmpOrderClash.setClashBirthYear(tmpMember.getBirthYear());
					tmpOrderClash.setClashDate(orderClash[j].getClashDate());
					tmpOrderClash.setName(tmpMember.getName());
					if (family.getHouseholder().getId() == tmpMember.getId())
						tmpOrderClash.setHouseholder(true);
					
					orderClashList.add(tmpOrderClash);
				}
			}
		}
		macLog.debug("Order Clash size = " + orderClashList.size());
		
		result = (OrderClash[]) orderClashList.toArray(new OrderClash[0]);
		return result;
	}

	private OrderClash[] getOrderClash(int orderYear) {
		MacLogger macLog = new MacLogger(); 
		
		if (orderClash == null) {
			try {
				// first, retrieve 2012's clash date and JiaZi
				Date firstDate = this.dateFormat.parse(Base.FIRST_DATE_2012);
				
				// retrieve OrderStatisticsDate data
				Date[] orderStatisticsDates = this.retvOrderStatisticsDate();
				
				// calculate order clash info
				long firstDateAmount = (firstDate.getTime() / 60000 / 60 / 24);
				int firstDateClashBirthYear = this.convertJiaZiToYear(
						Base.FIRST_DATE_CLASH[0], Base.FIRST_DATE_CLASH[1]) - 1911;
				
				// calculate clash dates in the OrderStatisticsDate range
				orderClash = new OrderClash[orderStatisticsDates.length];
				for (int i = 0; i < orderStatisticsDates.length; i++) {
					long tmpAmount = (orderStatisticsDates[i].getTime() / 60000 / 60 /24);
					long clashBirthYear = (tmpAmount - firstDateAmount + firstDateClashBirthYear) % 60;
					
					orderClash[i] = new OrderClash();
					orderClash[i].setClashDate(orderStatisticsDates[i]);
					orderClash[i].setClashBirthYear((int) clashBirthYear);
					orderClash[i].setOrderYear(orderYear);
				}
			} catch (ParseException ex) {
				// TODO Auto-generated catch block
				macLog.log(ex);
			}
		}
		
		return orderClash;
	}

	@Override
	public JsonConfig getJsonConfig() {
		// TODO Auto-generated method stub
		if (this.jsonDateConfig == null) {
			this.jsonDateConfig = new JsonConfig();
			
			this.jsonDateConfig.registerJsonValueProcessor(
					Date.class, new JsonValueProcessor() {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					return null;
				}
	
				@Override
				public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
					// TODO Auto-generated method stub
					String result = null;
					if (arg1 instanceof Date)
						result = this.dateFormat.format(arg1);
					else if (arg1 != null)
						result = arg1.toString();
					
					return result;
				}
			});
			
			this.jsonDateConfig.registerJsonValueProcessor(Boolean.class, new JsonValueProcessor() {

				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
					// TODO Auto-generated method stub
					String result = null;
					if (arg1 instanceof Boolean) {
						boolean tmpBoolean = ((Boolean) arg1).booleanValue();
						if (tmpBoolean)
							result = "是";
						else
							result = "否";
					} else if (arg1 != null) {
						result = arg1.toString();
					}
						
					return result;
				}
			});
		}
		
		return this.jsonDateConfig;
	}
	
	public String convertToChinese(int number, boolean isDateFormat) {
		String result = null;
		
		int bit = number % 10;
		int ten = number / 10 % 10;
		int hundred = number / 100 % 10;
		
		if (number >= 100 ) {
			result = this.converNumber(hundred);
			if (ten == 0)
				result += "Ｏ";
			else
				result += this.converNumber(ten);
			if (bit == 0)
				result += "Ｏ";
			else
				result += this.converNumber(bit);
		} else if (number >= 20) {
			result = this.converNumber(ten) + "十" + this.converNumber(bit);
		} else if (number > 10) {
			result = "十" + this.converNumber(bit);
		} else if (number == 10) {
			if (isDateFormat)
				result = "初十";
			else
				result = "十";
		} else if (number == 0) {
			result = "吉";
		} else {
			if (isDateFormat)
				result = "初" + this.converNumber(bit);
			else
				result = this.converNumber(bit);
		}
		
		return result;
	}
	
	private String converNumber(int number) {
		String result = null;
		switch (number) {
		case 0:
			result = "";
			break;
		case 1:
			result = "一";
			break;
		case 2:
			result = "二";
			break;
		case 3:
			result = "三";
			break;
		case 4:
			result = "四";
			break;
		case 5:
			result = "五";
			break;
		case 6:
			result = "六";
			break;
		case 7:
			result = "七";
			break;
		case 8:
			result = "八";
			break;
		case 9:
			result = "九";
			break;
		}
		return result;
	}
	
}
