package tw.lai.macgyver.jft.control;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.form.OrderForm;
import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderClash;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;
import tw.lai.macgyver.tools.PowerVoDisplay;

@Controller
public class OrderPerform {

	private IJinFaTanCenter jftCenter = null;

	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}

	@RequestMapping("/ajax_listOrder.do")
	public void listOrder(@ModelAttribute("orderForm") OrderForm orderForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_listOrder.do", req.getRemoteAddr(), req.getRemoteUser(),
				"None");

		try {
			int currentPage = Integer.parseInt(req.getParameter("page"));
			// int screenRows = Integer.parseInt(req.getParameter("rows"));
			int filterOrderYear = Integer.parseInt(req.getParameter("inputOrderYear"));
			
			// add for filter householder by MacGyver on 20131119
			String filterHouseholder = new String(
					req.getParameter("inputHouseholder").getBytes("ISO-8859-1"), "UTF-8");

			Iterator parameterIterator = req.getParameterMap().keySet().iterator();
			while (parameterIterator.hasNext()) {
				String tmpKey = parameterIterator.next().toString();
				macLog.debug(tmpKey + " : " + ((String[]) req.getParameterMap().get(tmpKey))[0]);
			}
			macLog.debug("FilterOrderYear = " + req.getParameter("inputOrderYear"));
			// macLog.log("Page = " + orderForm.getPage());

			// modified for filter householder by MacGyver on 20131119
			Order[] orders = null;
			int pageAmount = 0;
			if (filterHouseholder != null && !"".equals(filterHouseholder)) {
				orders = this.jftCenter.retvOrderByHouseholder(filterHouseholder, filterOrderYear);
				pageAmount = this.jftCenter.retvOrderPageAmount(orders.length);
			} else {
				orders = this.jftCenter.retvOrderByRange(currentPage, filterOrderYear);
				Date filterStartDate = this.jftCenter.getDateFormat().parse(
						filterOrderYear + "/01/01");
				Date filterEndDate = this.jftCenter.getDateFormat().parse(
						filterOrderYear + "/12/31");
				pageAmount = this.jftCenter
						.retvOrderPageAmount(filterStartDate, filterEndDate);
			}

			JSONObject json = new JSONObject();
			JSONArray jsonArray = JSONArray.fromObject(orders, this.jftCenter.getJsonConfig());
			json.put("rows", jsonArray);

			json.put("page", currentPage);
			json.put("records", jsonArray.size());

			/*
			 * Another way but not work String[] dateFormats = new String[]
			 * {"yyyy/MM/dd"};
			 * JSONUtils.getMorpherRegistry().registerMorpher(new
			 * DateMorpher(dateFormats));
			 */
			
			macLog.log("Page amount = " + pageAmount);
			json.put("total", new Integer(pageAmount));

			macLog.log("Return Json = " + json);

			res.setContentType("application/json; charset=utf-8");
			res.getWriter().print(json);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
	}

	@RequestMapping("/ajax_editOrder.do")
	public void editOrder(@ModelAttribute("orderForm") OrderForm orderForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_editOrder.do", req.getRemoteAddr(), req.getRemoteUser(),
				"None");

		/* for debug
		Iterator parameterIterator = req.getParameterMap().keySet().iterator();
		while (parameterIterator.hasNext()) {
			String tmpKey = parameterIterator.next().toString();
			macLog.log(tmpKey + " : " + ((String[]) req.getParameterMap().get(tmpKey))[0]);
		}
		*/

		macLog.debug(new PowerVoDisplay().diplayToString(orderForm));

		try {
			String operation = req.getParameter("oper");
			macLog.log("Operation = " + operation);
			
			if (orderForm != null && orderForm.getFamilyId() != null) {
				Order order = new Order();
				order.setFamilyId(Integer.parseInt(orderForm.getFamilyId()));
				order.setHouseHolder(orderForm.getHouseHolder());
				order.setOrderYear(Integer.parseInt(orderForm.getOrderYear()));
				order.setOrderDate(this.jftCenter.getDateFormat().parse(
						orderForm.getOrderDate()));
				order.setOrderPeriod(orderForm.getOrderPeriod());
				if ("是".equals(orderForm.getCheckout()))
					order.setCheckout(true);
				else
					order.setCheckout(false);
				order.setMemo(orderForm.getMemo());
				
				boolean operationResult = false;
				if ("add".equals(operation)) {
					try {
						operationResult = this.jftCenter.addOrder(order);
					} catch (DataIntegrityViolationException ex) {
						// TODO Auto-generated catch block
						if (ex.getMessage().indexOf("Unique constraint violation") > -1)
							macLog.log("新增資料重複！");
					}
				} else if ("del".equals(operation)) {
					operationResult = this.jftCenter.deleteOrder(
							order.getOrderYear(), order.getFamilyId());
				} else {
					operationResult = this.jftCenter.modifyOrder(order);
				}
				macLog.log("Operation result is " + operationResult);
				
				res.setContentType("application/json; charset=utf-8");
				res.getWriter().print(operationResult);
			} else {
				macLog.log("OrderForm or Family Id is Null!");
			}
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
	}
	
	@RequestMapping("/ajax_beforeEditOrder.do")
	public void beforeEditOrder(@ModelAttribute("orderForm") OrderForm orderForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_beforeEditOrder.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			if (orderForm != null && orderForm.getFamilyId() != null) {
				int familyId = Integer.parseInt(orderForm.getFamilyId());
				int thisYear = Calendar.getInstance().get(Calendar.YEAR);
				
				OrderClash[] orderClashes = this.jftCenter.retvOrderClash(familyId, thisYear);
				
				res.setContentType("application/json; charset=utf-8");
				res.getWriter().print(JSONArray.fromObject(
						orderClashes, this.jftCenter.getJsonConfig()));
			} else {
				macLog.log("OrderForm or Family Id is Null!");
			}
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
	}
	
	@RequestMapping("/ajax_OrderInfoList.do")
	public ModelAndView getOrderInfoList(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_OrderInfoList.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			OrderStatisticsInfo[] orderStatisticsInfo = 
					this.jftCenter.retvOrderStatisticsInfo();
			
			result = new ModelAndView("order_info_list");
			result.addObject("orderInfoList", orderStatisticsInfo);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
}
