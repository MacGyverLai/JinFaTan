package tw.lai.macgyver.jft.module;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;

public interface ICustomerManageDAO {
	
	public List<Family> queryFamilyByIndex(int start, int length);

	public List<Family> queryFamilyByName(String name, int start, int length);
	
	public int countFamilyByName(String name);
	
	public Family queryFamilyById(int familyId);
	
	public Member queryMemberById(int memberId);
	
	public List<Member> queryMemberByFamilyId(int familyId);
	
	public int queryFamilyAmount();
	
	public List<Family> queryFamilyByPhone(String phoneNumber, int start, int length);
	
	public int countFamilyByPhone(String phoneNumber);
	
	public List<Family> queryFamilyByAddress(String address, int start, int length);
	
	public int countFamilyByAddress(String address);
	
	public Family queryNewFamily(String address);
	
	public boolean insertFamily(Family family);
	
	public boolean insertMember(Member member, int familyId);
	
	public boolean updateFamily(Family family);
	
	public boolean addCustomer(Family family, Member[] members, int householderIndex);
	
	public boolean modifyCustomer(Family family, Member[] members, int householderIndex);
	
	public boolean updateMember(Member member);
	
	public boolean deleteFamilyById(int familyId);
	
	public boolean deleteMemberById(int memberId);
	
	public boolean deleteCustomer(int familyId);
	
	public List<String> queryAllCity();
	
	public List<String> queryAreaByCity(String city);
}
