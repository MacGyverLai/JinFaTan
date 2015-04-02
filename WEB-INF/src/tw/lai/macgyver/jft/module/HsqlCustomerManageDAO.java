package tw.lai.macgyver.jft.module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;

@Service("hsqlDAO")
@Transactional(readOnly = true)
public class HsqlCustomerManageDAO extends BaseDAO implements ICustomerManageDAO {
	
	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException ex) {
			// TODO Auto-generated catch block
			new MacLogger().sysError(ex);
		}
	}
	
	/* mark by MacGyver at 20110425
	private String dbFileNamePrefix = "";
	
	private Connection connect = null;
	
	public void initConnection() {
		MacLogger macLog = new MacLogger();
		try {
			if (this.connect == null || this.connect.isClosed()) {
				this.connect = DriverManager.getConnection("jdbc:hsqldb:file:" + 
						dbFileNamePrefix, "sa", "");
				macLog.debug("Create DB connection success!");
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			macLog.debug(ex);
		}
	}
	
	public void releaseConnection() {
		
	}
	*/

	@Override
	public List<Family> queryFamilyByIndex(int start, int length) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Limit ? ? * From \"Family\" Order By \"Id\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { start, length };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Family family = new Family();
				family.setId(rs.getInt("Id"));
				family.setAddress(rs.getString("Address"));
				family.setHousePhone(rs.getString("House_Phone"));
				family.setPhone_1(rs.getString("Contact_Phone_1"));
				family.setPhone_2(rs.getString("Contact_Phone_2"));
				family.setCity(rs.getString("City"));
				family.setArea(rs.getString("Area"));
				
				// query householder
				int memberId = rs.getInt("Householder");
				family.setHouseholder(queryMemberById(memberId));
				
				return family;
			}
		};
		List<Family> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}
	
	public List<Family> queryFamilyByName(String name, int start, int length) {
		MacLogger macLog = new MacLogger();
		
		/* the SQL only search house holder
		String sql = "Select Limit ? ? * From \"Family\" a, \"Member\" b Where " +
				"a.\"Householder\" = b.\"Id\" And b.\"Name\" Like ?";
		*/
		String sql = "Select Limit ? ? * From \"Family\" Where \"Id\" in (" +
				"Select a.\"Id\" From \"Family\" a, \"Member\" b Where " +
				"a.\"Id\" = b.\"Family\" And b.\"Name\" Like ? Group By a.\"Id\")";
		
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + name + "%";
		Object[] args = new Object[] { start, length, condition };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Family family = new Family();
				family.setId(rs.getInt("Id"));
				family.setAddress(rs.getString("Address"));
				family.setHousePhone(rs.getString("House_Phone"));
				family.setPhone_1(rs.getString("Contact_Phone_1"));
				family.setPhone_2(rs.getString("Contact_Phone_2"));
				family.setCity(rs.getString("City"));
				family.setArea(rs.getString("Area"));
				
				// query householder
				int memberId = rs.getInt("Householder");
				family.setHouseholder(queryMemberById(memberId));
				
				return family;
			}
		};
		List<Family> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}
	
	@Override
	public int countFamilyByName(String name) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		int result = 0;
		
		String sql = "Select Count(Distinct a.\"Id\") From \"Family\" a, \"Member\" b Where " +
				"a.\"Id\" = b.\"Family\" And b.\"Name\" Like ?";
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + name + "%";
		Object[] args = new Object[] { condition };
		macLog.debug("Args = " + name);
		
		result = this.jdbcTemplate.queryForInt(sql, args);
		return result;
	}

	public Family queryFamilyById(int familyId) {
		final Family result = new Family();
		MacLogger macLog = new MacLogger();
		
		String sql = "Select * From \"Family\" Where \"Id\" = ?";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { familyId };
		macLog.debug("Args = " + familyId);
		RowCallbackHandler rch = new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				result.setId(rs.getInt("Id"));
				result.setAddress(rs.getString("Address"));
				result.setHousePhone(rs.getString("House_Phone"));
				result.setPhone_1(rs.getString("Contact_Phone_1"));
				result.setPhone_2(rs.getString("Contact_Phone_2"));
				result.setCity(rs.getString("City"));
				result.setArea(rs.getString("Area"));
				
				// query householder
				int memberId = rs.getInt("Householder");
				result.setHouseholder(queryMemberById(memberId));
			}
		};
		this.jdbcTemplate.query(sql, args, rch);
		
		return result;
	}
	
	public Member queryMemberById(int memberId) {
		final Member result = new Member();
		MacLogger macLog = new MacLogger();
		
		String sql = "Select * From \"Member\" Where \"Id\" = ?";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { memberId };
		macLog.debug("Args = " + memberId);
		RowCallbackHandler rch = new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				result.setId(rs.getInt("Id"));
				result.setName(rs.getString("Name"));
				result.setOrder(rs.getString("Order"));
				result.setBirthYear(rs.getInt("Birth_Year"));
				
				/* change for type modify on 20111218
				result.setBirthDay(rs.getInt("Birth_Day"));
				*/
				result.setBirthMonth(rs.getString("Birth_Month"));
				result.setBirthDay(jftCenter.convertToChinese(rs.getInt("Birth_Day"), true));
				
				result.setBirthTime(rs.getString("Birth_Time"));
				result.setLight(rs.getBoolean("Light"));
				
				// added for asset on 20120102
				result.setAsset(rs.getString("Asset"));
				
				// added for seq number on 20120211
				result.setSeqNo(rs.getInt("Seq_No"));
			}
		};
		this.jdbcTemplate.query(sql, args, rch);
		
		return result;
	}

	@Override
	public int queryFamilyAmount() {
		// TODO Auto-generated method stub
		int result = 0;
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Count(*) From \"Family\"";
		macLog.debug("execute SQL : " + sql);
		
		result = this.jdbcTemplate.queryForInt(sql);
		
		return result;
	}

	@Override
	public List<Member> queryMemberByFamilyId(int familyId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select * From \"Member\" Where \"Family\" = ? Order By \"Seq_No\", \"Id\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { familyId };
		macLog.debug("Args = " + familyId);
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Member member = new Member();
				member.setId(rs.getInt("Id"));
				member.setName(rs.getString("Name"));
				member.setOrder(rs.getString("Order"));
				member.setBirthYear(rs.getInt("Birth_Year"));
				
				/* change for type modify on 20111218
				member.setBirthDay(rs.getInt("Birth_Day"));
				*/
				member.setBirthMonth(rs.getString("Birth_Month"));
				member.setBirthDay(jftCenter.convertToChinese(rs.getInt("Birth_Day"), true));
				
				member.setBirthTime(rs.getString("Birth_Time"));
				member.setLight(rs.getBoolean("Light"));
				
				// added for asset on 20120102
				member.setAsset(rs.getString("Asset"));
				
				// added for seq number on 20120211
				member.setSeqNo(rs.getInt("Seq_No"));
				
				return member;
			}
		};
		List<Member> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}

	@Override
	public List<Family> queryFamilyByAddress(String address, int start, int length) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Limit ? ? * From \"Family\" Where \"City\" like ? Or \"Address\" " +
				"like ? Order By \"City\"";
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + address + "%";
		Object[] args = new Object[] { start, length, condition, condition };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Family family = new Family();
				family.setId(rs.getInt("Id"));
				family.setAddress(rs.getString("Address"));
				family.setHousePhone(rs.getString("House_Phone"));
				family.setPhone_1(rs.getString("Contact_Phone_1"));
				family.setPhone_2(rs.getString("Contact_Phone_2"));
				family.setCity(rs.getString("City"));
				family.setArea(rs.getString("Area"));
				
				// query householder
				int memberId = rs.getInt("Householder");
				family.setHouseholder(queryMemberById(memberId));
				
				return family;
			}
		};
		List<Family> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}

	@Override
	public int countFamilyByAddress(String address) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		int result = 0;
		
		String sql = "Select Count(*) From \"Family\" Where \"City\" like ? Or \"Address\" " +
				"like ?";
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + address + "%";
		Object[] args = new Object[] { condition, condition };
		macLog.debug("Args = " + address);
		
		result = this.jdbcTemplate.queryForInt(sql, args);
		return result;
	}

	@Override
	public Family queryNewFamily(String address) {
		// TODO Auto-generated method stub
		final Family result = new Family();
		MacLogger macLog = new MacLogger();
		
		String sql = "Select * From \"Family\" Where \"Address\" = ? " +
				"And \"Householder\" = '-1' Order By \"City\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { address };
		macLog.debug("Args = " + address);
		RowCallbackHandler rch = new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				result.setId(rs.getInt("Id"));
				result.setAddress(rs.getString("Address"));
				result.setHousePhone(rs.getString("House_Phone"));
				result.setPhone_1(rs.getString("Contact_Phone_1"));
				result.setPhone_2(rs.getString("Contact_Phone_2"));
				result.setCity(rs.getString("City"));
				result.setArea(rs.getString("Area"));
				
				// query householder
				/* member Id always -1
				int memberId = rs.getInt("Householder");
				result.setHouseholder(queryMemberById(memberId));
				*/
			}
		};
		this.jdbcTemplate.query(sql, args, rch);
		
		return result;
	}

	@Override
	public List<Family> queryFamilyByPhone(String phoneNumber, int start, int length) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select Limit ? ? * From \"Family\" Where \"House_Phone\" like ? " +
				"Or \"Contact_Phone_1\" like ? Or \"Contact_Phone_2\" like ?";
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + phoneNumber + "%";
		Object[] args = new Object[] { start, length, condition, condition, condition };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				Family family = new Family();
				family.setId(rs.getInt("Id"));
				family.setAddress(rs.getString("Address"));
				family.setHousePhone(rs.getString("House_Phone"));
				family.setPhone_1(rs.getString("Contact_Phone_1"));
				family.setPhone_2(rs.getString("Contact_Phone_2"));
				family.setCity(rs.getString("City"));
				family.setArea(rs.getString("Area"));
				
				// query householder
				int memberId = rs.getInt("Householder");
				family.setHouseholder(queryMemberById(memberId));
				
				return family;
			}
		};
		List<Family> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}

	@Override
	public int countFamilyByPhone(String phoneNumber) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		int result = 0;
		
		String sql = "Select Count(*) From \"Family\" Where \"House_Phone\" like ? " +
				"Or \"Contact_Phone_1\" like ? Or \"Contact_Phone_2\" like ?";
		macLog.debug("execute SQL : " + sql);
		
		String condition = "%" + phoneNumber + "%";
		Object[] args = new Object[] { condition, condition, condition };
		macLog.debug("Args = " + phoneNumber);
		
		result = this.jdbcTemplate.queryForInt(sql, args);
		return result;
	}

	@Override
	public boolean insertFamily(Family family) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Insert Into \"Family\" (\"Householder\", \"Address\", \"House_Phone\", " +
				"\"Contact_Phone_1\", \"Contact_Phone_2\", \"City\", \"Area\") Values " +
				"(?, ?, ?, ?, ?, ?, ?)";
		macLog.debug("SQL Command is " + sql);
		
		int memberId = -1;
		if (family.getHouseholder() != null && family.getHouseholder().getId() != -1)
			memberId = family.getHouseholder().getId();
		macLog.debug("member Id = " + memberId);
		
		Object[] args = new Object[] { memberId, family.getAddress(), family.getHousePhone(), 
				family.getPhone_1(), family.getPhone_2(), family.getCity(), 
				family.getArea() };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean insertMember(Member member, int familyId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Insert Into \"Member\" (\"Name\", \"Order\", \"Birth_Year\", " +
				"\"Birth_Month\", \"Birth_Day\", \"Birth_Time\", \"Light\", \"Asset\", " +
				"\"Seq_No\", \"Family\") Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { member.getName(), member.getOrder(), 
				member.getBirthYear(), member.getBirthMonth(), member.getBirthDay(), 
				member.getBirthTime(), member.isLight(), member.getAsset(), member.getSeqNo(), 
				familyId };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateFamily(Family family) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Update \"Family\" Set \"Householder\"=? , \"Address\"=?, " +
				"\"House_Phone\"=?, \"Contact_Phone_1\"=?, \"Contact_Phone_2\"=?, " +
				"\"City\"=?, \"Area\"=? Where \"Id\"=?";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { family.getHouseholder().getId(), family.getAddress(), 
				family.getHousePhone(), family.getPhone_1(), family.getPhone_2(), 
				family.getCity(), family.getArea(), family.getId() };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addCustomer(Family family, Member[] members, int householderIndex) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		macLog.debug("insert family...");
		boolean updateRtn = this.insertFamily(family);
		if (!updateRtn)
			throw new UnsupportedOperationException("insert family failed");
		
		macLog.debug("retrieve family Id...");
		Family newFamily = this.queryNewFamily(family.getAddress());
		int familyId = -1;
		if (newFamily != null)
			familyId = newFamily.getId();
		else
			throw new UnsupportedOperationException("retrieve family failed");
		macLog.debug("family Id = " + familyId);
		
		// for transaction test by MacGyver
		if (null == null)
			throw new UnsupportedOperationException("For transaction test by MacGyver");
		
		macLog.debug("insert members...");
		for (int i = 0; i < members.length; i++) {
			updateRtn = this.insertMember(members[i], familyId);
			if (!updateRtn)
				throw new UnsupportedOperationException("insert new member failed");
		}
		
		macLog.debug("retrieve member Id...");
		Member member = null;
		String houseHolderName = members[householderIndex].getName();
		List<Member> memberList = this.queryMemberByFamilyId(familyId);
		for (int i = 0; i < memberList.size(); i++) {
			if (houseHolderName.equals(memberList.get(i).getName()))
				member = memberList.get(i);
		}
		if (member == null)
			throw new UnsupportedOperationException("retrieve member failed");
		macLog.debug("member Id = " + member.getId());
		
		macLog.debug("update householder for family...");
		family.setId(familyId);
		family.setHouseholder(member);
		updateRtn = this.updateFamily(family);
		if (!updateRtn)
			throw new UnsupportedOperationException("update householder failed");
		
		return true;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean modifyCustomer(Family family, Member[] members, int householderIndex) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		boolean updateRtn = false;
		for (int i = 0; i < members.length; i++) {
			if (members[i].getId() != -1) {
				macLog.debug("update member...");
				updateRtn = this.updateMember(members[i]);
				if (!updateRtn)
					throw new UnsupportedOperationException("update member failed");
			} else {
				macLog.debug("insert member...");
				updateRtn = this.insertMember(members[i], family.getId());
				if (!updateRtn)
					throw new UnsupportedOperationException("insert new member failed");
			}
		}
		
		macLog.debug("retrieve member Id...");
		Member member = null;
		String houseHolderName = members[householderIndex].getName();
		List<Member> memberList = this.queryMemberByFamilyId(family.getId());
		for (int i = 0; i < memberList.size(); i++) {
			if (houseHolderName.equals(memberList.get(i).getName()))
				member = memberList.get(i);
		}
		if (member == null)
			throw new UnsupportedOperationException("retrieve member failed");
		macLog.debug("member Id = " + member.getId());
		
		macLog.debug("update family...");
		family.setHouseholder(member);
		updateRtn = this.updateFamily(family);
		if (!updateRtn)
			throw new UnsupportedOperationException("update householder failed");
		
		return true;
	}

	@Override
	public boolean updateMember(Member member) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Update \"Member\" Set \"Name\"=?, \"Order\"=?, \"Birth_Year\"=?, " +
				"\"Birth_Month\"=?, \"Birth_Day\"=?, \"Birth_Time\"=?, \"Light\"=?, " +
				"\"Asset\"=?, \"Seq_No\"=? Where \"Id\" = ?";
		macLog.debug("SQL Command is " + sql);
		Object[] args = new Object[] { member.getName(), member.getOrder(), member.getBirthYear(), 
				member.getBirthMonth(), member.getBirthDay(), member.getBirthTime(), 
				member.isLight(), member.getAsset(), member.getSeqNo(), member.getId() };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteFamilyById(int familyId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Delete From \"Family\" Where \"Id\"=?";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { familyId };
		macLog.debug("Args = " + familyId);
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteMemberById(int memberId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Delete From \"Member\" Where \"Id\"=?";
		macLog.debug("SQL Command is " + sql);
		
		Object[] args = new Object[] { memberId };
		macLog.debug("Args = " + memberId);
		int result = this.jdbcTemplate.update(sql, args);
		macLog.debug("update count = " + result);
		
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean deleteCustomer(int familyId) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		boolean updateRtn = false;
		List<Member> memberList = this.queryMemberByFamilyId(familyId);
		for (int i = 0; i < memberList.size(); i++) {
			updateRtn = this.deleteMemberById(memberList.get(i).getId());
			if (!updateRtn)
				throw new UnsupportedOperationException();
		}
		
		updateRtn = this.deleteFamilyById(familyId);
		if (!updateRtn)
			throw new UnsupportedOperationException();
		else
			return true;
	}
	
	@Override
	public List<String> queryAllCity() {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select \"CITY\" From \"Area\" Group By \"CITY\"";
		macLog.debug("execute SQL : " + sql);
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("CITY");
			}
		};
		List<String> result = this.jdbcTemplate.query(sql, rm);
		
		return result;
	}

	@Override
	public List<String> queryAreaByCity(String city) {
		// TODO Auto-generated method stub
		MacLogger macLog = new MacLogger();
		
		String sql = "Select AREA From \"Area\" Where \"CITY\"=? Order By \"ZIPCODE\"";
		macLog.debug("execute SQL : " + sql);
		
		Object[] args = new Object[] { city };
		macLog.debug("Args = " + this.pvd.diplayToString(args));
		
		RowMapper rm = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int count) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("AREA");
			}
		};
		List<String> result = this.jdbcTemplate.query(sql, args, rm);
		
		return result;
	}
}
