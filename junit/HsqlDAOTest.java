import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import tw.lai.macgyver.jft.module.HsqlCustomerManageDAO;
import tw.lai.macgyver.jft.module.HsqlOrderManageDAO;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.tools.PowerVoDisplay;

import junit.framework.TestCase;


public class HsqlDAOTest extends TestCase {
	
	private HsqlCustomerManageDAO hsqlDao = null;
	
	private HsqlOrderManageDAO hsqlOrderManageDao = null;
	
	private PowerVoDisplay pvd = new PowerVoDisplay();
	
	protected void setUp() throws Exception {
		String filePath = "C:/JinFaTan/JinFaTan";
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:" + filePath + ";close_result=true;shutdown=true");
		dataSource.setUsername("SA");
		dataSource.setPassword("");
		
		this.hsqlDao = new HsqlCustomerManageDAO();
		this.hsqlDao.setDataSource(dataSource);
		
		this.hsqlOrderManageDao = new HsqlOrderManageDAO();
		this.hsqlOrderManageDao.setDataSource(dataSource);
	}
	
	public void testAlter() {
//		String sql = "Alter Table \"Member\" Alter Column \"Birth_Month\" VARCHAR(10) Not Null";
		String sql = "Alter Table \"Member\" Add Column \"Asset\" VARCHAR(30) Default ''";
		
		this.hsqlDao.jdbcTemplate.execute(sql);
	}

	public void testQueryFamilyByName() {
		String name = "雲";// 陳家駱, 書培
		
		List<Family> hsqlRtn = this.hsqlDao.queryFamilyByName(name, 0, 20);
		for (int i = 0; i < hsqlRtn.size(); i++)
			System.out.println(this.pvd.diplayToString(hsqlRtn.get(i)));
	}
	
	public void testQueryFamilyByPhone() {
		String phone = "0935";
		
		List<Family> hsqlRtn = this.hsqlDao.queryFamilyByPhone(phone, 1, 20);
		for (int i = 0; i < hsqlRtn.size(); i++)
			System.out.println(this.pvd.diplayToString(hsqlRtn.get(i)));
	}
	
	public void testQueryFamilyByAddress() {
		String address = "三和路四段197巷59號4樓";
		
		List<Family> hsqlRtn = this.hsqlDao.queryFamilyByAddress(address, 1, 20);
		for (int i = 0; i < hsqlRtn.size(); i++)
			System.out.println(this.pvd.diplayToString(hsqlRtn.get(i)));
	}
	
	public void testQueryFamilyById() {
		int familyId = 5;
		
		try {
			Family family = this.hsqlDao.queryFamilyById(familyId);
			System.out.println(this.pvd.diplayToString(family));
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testQueryMemberByFamilyId() {
		int familyId = 60;
		
		try {
			List hsqlRtn = this.hsqlDao.queryMemberByFamilyId(familyId);
			for (int i = 0; i < hsqlRtn.size(); i++)
				System.out.println(this.pvd.diplayToString(hsqlRtn.get(i)));
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testInsertFamily() {
		Family family = new Family();
		family.setAddress("三和路四段197巷59號4樓");
		family.setCity("新北市三重區");
		family.setHousePhone("(02)2998-5945");
		family.setPhone_1("0954-793227");
		
		try {
			boolean hsqlRtn = this.hsqlDao.insertFamily(family);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testInsertMember() {
		int familyId = 5;
		Member member = new Member();
		member.setName("陳家駱");
		member.setBirthYear(74);
		member.setBirthMonth("六");
		member.setBirthDay("二十九");
		member.setBirthTime("晨");
		
		try {
			boolean hsqlRtn = this.hsqlDao.insertMember(member, familyId);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testUpdateFamily() {
		int familyId = 5;
		int memberId = 9;
		
		try {
			Family family = this.hsqlDao.queryFamilyById(familyId);
			family.getHouseholder().setId(memberId);
			System.out.println(this.pvd.diplayToString(family));
			
			boolean hsqlRtn = this.hsqlDao.updateFamily(family);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testAddCustomer() {
		Family family = new Family();
		family.setAddress("三和路四段197巷59號4樓");
		family.setCity("新北市土城區");
		family.setHousePhone("(02)2992-9292");
		family.setPhone_1("0954-333753");
		
		Member[] members = new Member[2];
		members[0] = new Member();
		members[0].setName("張無忌");
		members[0].setBirthYear(72);
		members[0].setBirthMonth("五");
		members[0].setBirthDay("十八");
		members[0].setBirthTime("午");
		
		members[1] = new Member();
		members[1].setName("趙敏");
		members[1].setBirthYear(74);
		members[1].setBirthMonth("六");
		members[1].setBirthDay("二十九");
		members[1].setBirthTime("晨");
		
		try {
			boolean hsqlRtn = this.hsqlDao.addCustomer(family, members, 1);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testDeleteFamilyById() {
		int familyId = 7;
		
		try {
			boolean hsqlRtn = this.hsqlDao.deleteFamilyById(familyId);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testDeleteMemberById() {
		int memberId = 11;
		
		try {
			boolean hsqlRtn = this.hsqlDao.deleteMemberById(memberId);
			System.out.println(hsqlRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testQueryFamilyByIndex() {
		List<Family> hsqlRtn = this.hsqlDao.queryFamilyByIndex(0, 10);
		
		JSONArray jsonArray = JSONArray.fromObject(hsqlRtn);
		System.out.println(jsonArray);
		
		for (int i = 0; i < hsqlRtn.size(); i++)
			System.out.println(this.pvd.diplayToString(hsqlRtn.get(i)));
	}
	
	public void testCountFamilyByAddress() {
		String address = "三芝";
		int amount = this.hsqlDao.countFamilyByAddress(address);
		System.out.println(amount);
	}
	
	public void testQueryOrderDate() {
		Object[] args = new Object[] { new Date(0), new Date() };
		String sql = "Select \"Order_Date\" From \"Order\" Where \"Order_Date\" between ? and ?";
		Date rtnDate = (Date) this.hsqlDao.jdbcTemplate.queryForObject(sql, args, Date.class);
		System.out.println(rtnDate);
	}
	
	public void testQueryOrderByIndex() {
		List<Order> orders = this.hsqlOrderManageDao.queryOrderByIndex(0, 20);
		
//		for (int i = 0; i < orders.size(); i++) {
//			System.out.println(this.pvd.diplayToString(orders.get(i)));
//			((Order) orders.get(i)).setInsertTime(new Date());
//			((Order) orders.get(i)).setOrderDate(new Date());
//		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(
				Date.class, new JsonValueProcessor() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				System.out.println("into processArrayValue...");
				return null;
			}

			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				// TODO Auto-generated method stub
				String result = null;
				System.out.println("into processObjectValue...");
				if (arg1 instanceof Date)
					result = this.dateFormat.format(arg1);
				else if (arg1 != null)
					result = arg1.toString();
				
				return result;
			}
		});
		
		Order order = new Order();
		order.setOrderYear(2012);
		order.setFamilyId(100);
		order.setOrderDate(new Date());
		System.out.println(JSONObject.fromObject(order, jsonConfig));
		
		// 未試完, 應該用在JSON String to Bean
		String[] dateFormats = new String[] {"yyyy/MM/dd"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		
		
		JSONArray jsonArray = JSONArray.fromObject(orders, jsonConfig);
		System.out.println(jsonArray);
	}
	
	public void testInsertOrder() {
		Order order = new Order();
		order.setFamilyId(150);
		order.setOrderYear(2012);
		order.setOrderDate(new Date());
		order.setCheckout(true);
		order.setMemo("for Mac test...");
		
		boolean hsqlRtn = this.hsqlOrderManageDao.insertOrder(order);
		System.out.println(hsqlRtn);
	}
	
	public void testQueryAllArea() {
		List hsqlRtn = this.hsqlDao.queryAllCity();
		System.out.println(this.pvd.diplayToString(hsqlRtn));
		
		hsqlRtn = this.hsqlDao.queryAreaByCity("新北市");
		System.out.println(this.pvd.diplayToString(hsqlRtn));
	}
	
	public void testQueryConfiguration() {
		List hsqlRtn = this.hsqlOrderManageDao.queryConfiguration("OrderStatisticsDate");
		System.out.println(this.pvd.diplayToString(hsqlRtn));
	}
	
	public void testQueryOrderStatisticsInfo() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date tmpDate1 = dateFormat.parse("2013/03/02");
			Date tmpDate2 = dateFormat.parse("2013/03/03");
			Date tmpDate3 = dateFormat.parse("2013/03/16");
			
			Date[] orderStatisticsDate = new Date[] { tmpDate1, tmpDate2, tmpDate3 };
			List hsqlRtn = this.hsqlOrderManageDao.queryOrderStatisticsInfo(orderStatisticsDate);
			
			System.out.println(this.pvd.diplayToString(hsqlRtn));
		} catch (ParseException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
