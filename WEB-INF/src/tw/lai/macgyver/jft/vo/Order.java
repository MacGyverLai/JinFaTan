package tw.lai.macgyver.jft.vo;

import java.util.Date;

public class Order implements Base {
	
	private int orderYear = -1;
	
	private int familyId = -1;
	
	private String houseHolder = null;
	
	private Date orderDate = null;
	
	private String orderPeriod = null;
	
	private boolean checkout = false;
	
	private String memo = null;
	
	private Date insertTime = null;
	
	private Date updateTime = null;

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getOrderYear() {
		return orderYear;
	}

	public void setOrderYear(int orderYear) {
		this.orderYear = orderYear;
	}

	public int getFamilyId() {
		return familyId;
	}

	public void setFamilyId(int familyId) {
		this.familyId = familyId;
	}

	public String getHouseHolder() {
		return houseHolder;
	}

	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public String getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(String orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
		result.append(", familyId:" + this.familyId);
		result.append(", orderDate:" + this.orderDate);
		result.append(", checkout:" + this.checkout);
		result.append(", memo:" + this.memo);
		result.append(", insertTime:" + this.insertTime);
		result.append(", updateTime:" + this.updateTime);
		
		return result.toString();
	}
}
