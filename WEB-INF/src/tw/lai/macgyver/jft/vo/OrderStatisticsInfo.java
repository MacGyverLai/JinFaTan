package tw.lai.macgyver.jft.vo;

import java.util.Date;

public class OrderStatisticsInfo implements Base {

	private Date orderDate = null;
	
	private String orderPeriod = null;
	
	private int amount = 0;
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getOrderPeriod() {
		return orderPeriod;
	}
	
	public void setOrderPeriod(String orderPeriod) {
		this.orderPeriod = orderPeriod;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
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
		
		result.append("orderDate:" + this.orderDate);
		result.append(", orderPeriod:" + this.orderPeriod);
		result.append(", amount:" + this.amount);
		
		return result.toString();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
