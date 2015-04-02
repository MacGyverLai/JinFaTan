package tw.lai.macgyver.jft.form;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import tw.lai.macgyver.jft.util.MacLogger;

public class CustomerForm implements Serializable {
	
	private String housePhone = null;
	
	private String phone_1 = null;
	
	private String phone_2 = null;
	
	private String city = null;
	
	private String town = null;
	
	private String address = null;
	
	private String houseHolder = null;
	
	private String[] memberId = null;
	
	private String[] name = null;
	
	private String[] order = null;
	
	private String[] birthYear = null;
	
	private String[] birthMonth = null;
	
	private String[] birthDay = null;
	
	private String[] birthTime = null;
	
	private String[] light = null;
	
	private String[] asset = null;

	public String getHousePhone() {
		return housePhone;
	}

	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
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

	public String getCity() {
		return this.decodeURI(city);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return this.decodeURI(town);
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddress() {
		return this.decodeURI(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHouseHolder() {
		return houseHolder;
	}

	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}

	public String[] getMemberId() {
		return memberId;
	}

	public void setMemberId(String[] memberId) {
		this.memberId = memberId;
	}

	public String[] getName() {
		String[] result = new String[this.name.length];
		for (int i = 0; i < this.name.length; i++)
			result[i] = this.decodeURI(this.name[i]);
		
		return result;
	}

	public void setName(String[] name) {
		this.name = name;
	}
	
	public String[] getOrder() {
		String[] result = new String[this.order.length];
		for (int i = 0; i < this.order.length; i++)
			result[i] = this.decodeURI(this.order[i]);
			
		return result;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	public String[] getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String[] birthYear) {
		this.birthYear = birthYear;
	}

	public String[] getBirthMonth() {
		String[] result = new String[this.birthMonth.length];
		for (int i = 0; i < this.birthMonth.length; i++) {
			result[i] = this.decodeURI(this.birthMonth[i]);
			System.out.println("MacLog: birthMonth = " + result[i]);
		}
		
		return result;
	}

	public void setBirthMonth(String[] birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String[] getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String[] birthDay) {
		this.birthDay = birthDay;
	}

	public String[] getBirthTime() {
		String[] result = new String[this.birthTime.length];
		for (int i = 0; i < this.birthTime.length; i++)
			result[i] = this.decodeURI(this.birthTime[i]);
		
		return result;
	}

	public void setBirthTime(String[] birthTime) {
		this.birthTime = birthTime;
	}

	public String[] getLight() {
		return light;
	}

	public void setLight(String[] light) {
		this.light = light;
	}
	
	public String[] getAsset() {
		return asset;
	}

	public void setAsset(String[] asset) {
		this.asset = asset;
	}

	private String decodeURI(String parameter) {
		String result = null;
		MacLogger macLog = new MacLogger();
		
		try {
			result = new String(parameter.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		}
		
		return result;
	}
}
