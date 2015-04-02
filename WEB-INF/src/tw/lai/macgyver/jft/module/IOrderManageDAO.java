package tw.lai.macgyver.jft.module;

import java.util.Date;
import java.util.List;

import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderClash;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;

public interface IOrderManageDAO {
	
public List<Integer> queryOrderYears();
	
	public List<Order> queryOrderByRange(int start, int length, Date startDate, Date endDate);
	
	public List<Order> queryOrderByHouseholder(String householder, Date startDate, Date endDate);
	
	public List<Order> queryOrderByIndex(int start, int length);
	
	public int queryOrderAmount(Date startDate, Date endDate);
	
	public boolean insertOrder(Order order);
	
	public boolean updateOrder(Order order);
	
	public boolean deleteOrder(int orderYear, int familyId);

	public List<String> queryConfiguration(String property);
	
	public List<OrderStatisticsInfo> queryOrderStatisticsInfo(Date[] orderStatisticsDate);
	
	public List<OrderClash> queryOrderClash(int orderYear);
}
