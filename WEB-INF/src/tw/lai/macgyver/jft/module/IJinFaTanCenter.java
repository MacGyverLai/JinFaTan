package tw.lai.macgyver.jft.module;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import net.sf.json.JsonConfig;

import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderClash;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;

public interface IJinFaTanCenter {

	static public final int AUTHORITY_ERROR = 15;
	
	static public final int AUTHORITY_NORMAL = 10;
	
	static public final int AUTHORITY_MANAGER = 5;
	
	static public final int AUTHORITY_ADMIN = 1;
	
	static public final String SEARCH_BY_NAME = "name";
	
	static public final String SEARCH_BY_PHONE = "phone";
	
	static public final String SEARCH_BY_ADDRES = "address";
	
	public void setDateFormat(DateFormat dateFormat);
	
	public DateFormat getDateFormat();

	public int checkUserAuthority(String userName, String password);
	
	public Family retvFamilyInfo(int familyId);
	
	public Family[] retvFamilyInfoByPage(int page);
	
	public int retvFamilyPageAmount();
	
	public Member[] retvMemberByFamilyId(int familyId);
	
	public Member[] retvMemberByFamilyId(int familyId, int thisYear);
	
	public Family[] searchFamilyByName(String name, int page);
	
	public Family[] searchFamilyByPhone(String phone, int page);
	
	public Family[] searchFamilyByAddress(String address, int page);
	
	public int retvFamilyPageAmountByName(String name);
	
	public int retvFamilyPageAmountByPhonePage(String phone);
	
	public int retvFamilyPageAmountByAddress(String address);
	
	public int getTotalPage(int rowCount);
	
	public Family[] getListFromPage(int page, Family[] familys);
	
	public boolean addCustomer(Family family, Member[] members, int householderIndex);
	
	public boolean modifyCustomer(Family family, Member[] members, int householderIndex);
	
	public boolean deleteCustomer(int familyId);
	
	public boolean deleteMember(int memberId);
	
	/**
	 * �p��ͦ~�Ҥl
	 * @param birthYear
	 * @return
	 */
	public String calculateJiaZi(int birthYear);
	
	/**
	 * �p��ͨv
	 * @param birthYear
	 * @return
	 */
	public String calculateAnimal(int birthYear);
	
	/**
	 * 計算出生年的沖煞
	 * @param birthYear
	 * @return
	 */
	public String calculateClash(int birthYear, int thisYear);
	
	/**
	 * �p��ӷ�
	 * @param birthYear
	 * @return
	 */
	public boolean calculateTaiSui(int birthYear);
	
	public int calculateYears(int birthYear, int thisYear);
	
	public String replaceNumberToChinese(String input);
	
	public String fullSpace(int length, String target, boolean isHead);
	
	public int convertJiaZiToYear(String tianGan, String diZhi);
	
	public int convertAnimalToYear(int aboutAge, String animal);
	
	public int convertToNumber(String chineseDate);
	
	public String convertSmallToBig(String input);
	
	public String convertNameFormat(String name);
	
	public String convertToChinese(int number, boolean isDateFormat);
	
	public int[] retvOrderYears();
	
	public Order[] retvOrderByRange(int page, int year) throws ParseException;
	
	public Order[] retvOrderByPage(int page);
	
	public Order[] retvOrderByHouseholder(String householder, int year) throws ParseException;
	
	public int retvOrderPageAmount(Date startDate, Date endDate);
	
	public int retvOrderPageAmount(int orderCount);
	
	public boolean addOrder(Order order);
	
	public boolean modifyOrder(Order order);
	
	public boolean deleteOrder(int orderYear, int familyId);
	
	public String[] retvAllCity();
	
	public String[] retvAreaByCity(String city);
	
	public Date[] retvOrderStatisticsDate();
	
	public boolean refreshOrderStatisticsDate();
	
	public OrderStatisticsInfo[] retvOrderStatisticsInfo();
	
	public JsonConfig getJsonConfig();
	
	/**
	 * 取得單戶中會冲到的人名與日期
	 * @param familyId
	 * @return
	 */
	public OrderClash[] retvOrderClash(int familyId, int orderYear);
}