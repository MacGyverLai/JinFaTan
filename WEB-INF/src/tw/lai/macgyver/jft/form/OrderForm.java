package tw.lai.macgyver.jft.form;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrderForm implements Serializable {
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	private static DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private int page = -1;
	
	private String rows = null;
	
	private String sord = null;
	
	private String oper = null;
	
	private String orderYear = null;
	
	private String familyId = null;
	
	private String houseHolder = null;
	
	private String orderDate = null;
	
	private String orderPeriod = null;
	
	private String checkout = null;
	
	private String memo = null;
	
	private String insertTime = null;
	
	private String updateTime = null;

	public int getPage() {
//		if (this.page == null)
//			return 0;
//		else
//			return Integer.parseInt(this.page);
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOrderYear() {
		if (this.orderYear == null)
			return this.orderDate.substring(0, 4);
		else
			return this.orderYear;
	}

	public void setOrderYear(String orderYear) {
		this.orderYear = orderYear;
	}
	
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getHouseHolder() {
		String result = null;
		try {
			result = new String(this.houseHolder.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;
	}

	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}

	public String getOrderDate() {
		return orderDate.replace('-', '/');
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderPeriod() {
		String result = null;
		try {
			result = new String(this.orderPeriod.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;
	}

	public void setOrderPeriod(String orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public String getCheckout() {
		String result = null;
		try {
			result = new String(checkout.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public String getMemo() {
		String result = null;
		try {
			result = new String(this.memo.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
