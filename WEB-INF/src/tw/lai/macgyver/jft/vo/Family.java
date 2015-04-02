package tw.lai.macgyver.jft.vo;

import java.util.Date;

public class Family implements Base {

	private int Id = -1;
	
	private Member householder = null;
	
	private String phone_1 = null;
	
	private String phone_2 = null;
	
	private String housePhone = null;
	
	private String city = null;
	
	private String area = null;
	
	private String address = null;
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Member getHouseholder() {
		return householder;
	}

	public void setHouseholder(Member householder) {
		this.householder = householder;
	}

	public String getPhone_1() {
		return phone_1;
	}

	public void setPhone_1(String phone_1) {
		this.phone_1 = phone_1;
	}

	public String getPhone_2() {
		return phone_2;
	}

	public void setPhone_2(String phone_2) {
		this.phone_2 = phone_2;
	}

	public String getHousePhone() {
		return housePhone;
	}

	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		
		result.append("Id:" + this.Id);
		result.append(", householder:" + this.householder);
		result.append(", phone_1:" + this.phone_1);
		result.append(", phone_2:" + this.phone_2);
		result.append(", housePhone:" + this.housePhone);
		result.append(", city:" + this.city);
		result.append(", area:" + this.area);
		result.append(", address:" + this.address);
		
		return result.toString();
	}
	
}
