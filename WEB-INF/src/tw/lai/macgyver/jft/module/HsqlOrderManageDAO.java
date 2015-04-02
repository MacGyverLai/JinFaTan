package tw.lai.macgyver.jft.module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderClash;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;

@Service("hsqlOrderManageDAO")
@Transactional(readOnly = true)
public class HsqlOrderManageDAO extends BaseDAO implements IOrderManageDAO {

	@Override
	public boolean deleteOrder(int orderYear, int familyId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Delete From \"Order\" Where \"Order_Year\"=? And \"Family\"=?";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { orderYear, familyId };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean insertOrder(Order order) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Insert Into \"Order\" (\"Order_Year\", \"Family\", \"Order_Date\", " +
				"\"Order_Period\", \"Checkout\", \"Memo\", \"Insert_Time\") Values " +
				"(?, ?, ?, ?, ?, ?,?)";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { order.getOrderYear(), order.getFamilyId(), 
				order.getOrderDate(), order.getOrderPeriod(), order.isCheckout(), 
				order.getMemo(), new Date() };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public List<Integer> queryOrderYears() {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select \"Order_Year\" From \"Order\" Group By \"Order_Year\" " +
				"Order By \"Order_Year\" Desc";
		macLog.debug("execute SQL : " + sql);
		
		List<Integer> result = this.jdbcTemplate.queryForList(sql, Integer.class);
		
		return result;
	}

	@Override
	public List<Order> queryOrderByRange(int start, int length, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Limit ? ? B.\"Name\", C.* From \"Family\" A, \"Member\" B, " +
				"\"Order\" C Where A.\"Householder\" = B.\"Id\" And A.\"Id\" = C.\"Family\" " +
				"And C.\"Order_Date\" Between ? And ? Order By \"Order_Date\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { start, length, startDate, endDate };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Order order = new Order();
				order.setHouseHolder(rs.getString("Name"));
				order.setOrderYear(rs.getInt("Order_Year"));
				order.setFamilyId(rs.getInt("Family"));
				order.setOrderDate(rs.getDate("Order_Date"));
				order.setOrderPeriod(rs.getString("Order_Period"));
				order.setCheckout(rs.getBoolean("Checkout"));
				order.setMemo(rs.getString("Memo"));
				order.setInsertTime(rs.getTimestamp("Insert_Time"));
				order.setUpdateTime(rs.getTimestamp("Update_Time"));
				
				return order;
			}
		};
		List<Order> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}

	@Override
	public List<Order> queryOrderByHouseholder(String householder, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select B.\"Name\", C.* from \"Family\" A, \"Member\" B, \"Order\" C " +
				"Where A.\"Householder\" = B.\"Id\" And A.\"Id\" = C.\"Family\" And " +
				"B.\"Name\" Like ? And C.\"Order_Date\" Between ? And ? " +
				"Order By \"Order_Date\"";
		macLog.debug("execute SQL : " + sql);
		
		householder = "%" + householder + "%";
		Object[] args = new Object[] { householder, startDate, endDate };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Order order = new Order();
				order.setHouseHolder(rs.getString("Name"));
				order.setOrderYear(rs.getInt("Order_Year"));
				order.setFamilyId(rs.getInt("Family"));
				order.setOrderDate(rs.getDate("Order_Date"));
				order.setOrderPeriod(rs.getString("Order_Period"));
				order.setCheckout(rs.getBoolean("Checkout"));
				order.setMemo(rs.getString("Memo"));
				order.setInsertTime(rs.getTimestamp("Insert_Time"));
				order.setUpdateTime(rs.getTimestamp("Update_Time"));
				
				return order;
			}
		};
		List<Order> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}

	@Override
	public List<Order> queryOrderByIndex(int start, int length) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Limit ? ? * From \"Order\" Order By \"Order_Date\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { start, length };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Order order = new Order();
				order.setOrderYear(rs.getInt("Order_Year"));
				order.setFamilyId(rs.getInt("Family"));
				
				Date tmpDate = null;
				if (rs.getDate("Order_Date") != null) {
					tmpDate = new Date(rs.getDate("Order_Date").getTime());
					order.setOrderDate(tmpDate);
				}
				order.setOrderPeriod(rs.getString("Order_Period"));
				order.setCheckout(rs.getBoolean("Checkout"));
				order.setMemo(rs.getString("Memo"));
				if (rs.getDate("Insert_Time") != null) {
					tmpDate = new Date(rs.getTimestamp("Insert_Time").getTime());
					order.setInsertTime(tmpDate);
				}
				if (rs.getDate("Update_Time") != null) {
					tmpDate = new Date(rs.getTimestamp("Update_Time").getTime());
					order.setUpdateTime(tmpDate);
				}
				
				return order;
			}
		};
		List<Order> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}
	

	@Override
	public int queryOrderAmount(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		int result = 0;
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Count(*) From \"Order\" Where \"Order_Date\" Between ? And ?";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { startDate, endDate };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		result = this.jdbcTemplate.queryForInt(sql, args);
		
		return result;
	}

	@Override
	public boolean updateOrder(Order order) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Update \"Order\" Set \"Order_Date\"=?, \"Order_Period\"=?, " +
				"\"Checkout\"=?, \"Memo\"=?, \"Update_Time\"=? " +
				"Where \"Order_Year\"=? And \"Family\"=?";
		macLog.debug("SQL Command is " + sql);
		Object[] args = new Object[] { order.getOrderDate(), order.getOrderPeriod(), 
				order.isCheckout(), order.getMemo(), new Date(), order.getOrderYear(), 
				order.getFamilyId() };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public List<String> queryConfiguration(String property) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select b.\"Value\" From \"Configuration_Property\" a, " +
				"\"Configuration_Value\" b Where a.\"Name\" = ? and " +
				"a.\"Property_Id\" = b.\"Property\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { property };
		macLog.debug("Args = " + property);
		
		List<String> result = this.jdbcTemplate.queryForList(sql, args, String.class);
		
		return result;
	}

	@Override
	public List<OrderStatisticsInfo> queryOrderStatisticsInfo(Date[] orderStatisticsDate) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sqlArgs = null;
		for (int i = 0; i < orderStatisticsDate.length; i++) {
			if (i == 0)
				sqlArgs = "?";
			else
				sqlArgs += ", ?";
		}
		
		String sql = "Select \"Order_Date\", \"Order_Period\", Count(\"Order_Date\") Amount" +
				" From \"Order\" Where \"Order_Date\" In (" +  sqlArgs + ") Group By " +
				"\"Order_Date\", \"Order_Period\" Order By \"Order_Date\"";
		macLog.debug("execute SQL : " + sql);
		macLog.debug("Args = " + orderStatisticsDate);
		
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				OrderStatisticsInfo orderStatisticsInfo = new OrderStatisticsInfo();
				
				Date tmpDate = null;
				if (rs.getDate("Order_Date") != null) {
					tmpDate = new Date(rs.getDate("Order_Date").getTime());
					orderStatisticsInfo.setOrderDate(tmpDate);
				}
				orderStatisticsInfo.setOrderPeriod(rs.getString("Order_Period"));
				orderStatisticsInfo.setAmount(rs.getInt("Amount"));
				
				return orderStatisticsInfo;
			}
		};
		List<OrderStatisticsInfo> result = this.jdbcTemplate.query(sql, orderStatisticsDate, 
				rm);
		
		return result;
	}

	@Override
	public List<OrderClash> queryOrderClash(int orderYear) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select * From \"Order_Clash\" Where \"Order_Year\" = ?";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { orderYear };
		macLog.debug("Args = " + orderYear);
		
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				OrderClash orderClash = new OrderClash();
				
				Date tmpDate = null;
				orderClash.setOrderYear(rs.getInt("Order_Year"));
				if (rs.getDate("Clash_Date") != null) {
					tmpDate = new Date(rs.getDate("Clash_Date").getTime());
					orderClash.setClashDate(tmpDate);
				}
				orderClash.setClashBirthYear(rs.getInt("Clash_Birth_Year"));
				
				return orderClash;
			}
		};
		List<OrderClash> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}
	
}
