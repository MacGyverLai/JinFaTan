package tw.lai.macgyver.jft.vo;

import java.util.Date;

public class Member implements Base {
	
	public static final int SEX_MAN = 0;
	
	public static final int SEX_WOMAN = 1;
	
	private int Id = -1;

	private String name = null;
	
	private String order = null;
	
	private int birthYear = 0;
	
	private String birthMonth = null;
	
	private String birthDay = null;
	
	private int sex = SEX_MAN;
	
	private String birthTime = TIME_UNKNOW;
	
	private String sixtyYear = null;
	
	private String animal = null;
	
	private String years = null;
	
	private boolean light = false;
	
	private boolean taiSui = false;
	
	private String asset = "";
	
	private int seqNo = 1;
	
	public boolean isTaiSui() {
		return taiSui;
	}

	public void setTaiSui(boolean taiSui) {
		this.taiSui = taiSui;
	}

	public boolean isLight() {
		return light;
	}

	public void setLight(boolean light) {
		this.light = light;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}

	public String getSixtyYear() {
		return sixtyYear;
	}

	public void setSixtyYear(String sixtyYear) {
		this.sixtyYear = sixtyYear;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		
		result.append("Id:" + this.Id);
		result.append(", name:" + this.name);
		result.append(", order:" + this.order);
		result.append(", birthYear:" + this.birthYear);
		result.append(", birthMonth:" + this.birthMonth);
		result.append(", birthDay:" + this.birthDay);
		result.append(", birthTime:" + this.birthTime);
		result.append(", sixtyYear:" + this.sixtyYear);
		result.append(", animal:" + this.animal);
		result.append(", years:" + this.years);
		result.append(", light:" + this.light);
		result.append(", taiSui:" + this.taiSui);
		result.append(", asset:" + this.asset);
		result.append(", seqNo:" + this.seqNo);
		
		return result.toString();
	}

}
