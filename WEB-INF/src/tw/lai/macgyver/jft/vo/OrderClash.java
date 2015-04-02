package tw.lai.macgyver.jft.vo;

import java.util.Date;

public class OrderClash implements Base {
	
	int orderYear = -1;
	
	Date clashDate = null;
	
	int clashBirthYear = -1;
	
	String name = null;
	
	boolean householder = false;

	public int getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(int orderYear) {
		this.orderYear = orderYear;
	}

	public Date getClashDate() {
		return clashDate;
	}

	public void setClashDate(Date clashDate) {
		this.clashDate = clashDate;
	}

	public int getClashBirthYear() {
		return clashBirthYear;
	}

	public void setClashBirthYear(int clashBirthYear) {
		this.clashBirthYear = clashBirthYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHouseholder() {
		return householder;
	}

	public void setHouseholder(boolean householder) {
		this.householder = householder;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer result = new StringBuffer();
		
		result.append("orderYear:" + this.orderYear);
		result.append("clashDate:" + this.clashDate);
		result.append("clashBirthYear:" + this.clashBirthYear);
		result.append("name:" + this.name);
		result.append("householder:" + this.householder);
		
		return result.toString();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
