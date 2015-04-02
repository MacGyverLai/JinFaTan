package tw.lai.macgyver.jft.control;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.util.VoToString;
import tw.lai.macgyver.jft.vo.Member;
import tw.lai.macgyver.jft.vo.Order;
import tw.lai.macgyver.jft.vo.OrderStatisticsInfo;
import tw.lai.macgyver.jft.vo.SimplePagingList;
import tw.lai.macgyver.jft.vo.Family;

@Controller
public class Main {
	
	private IJinFaTanCenter jftCenter = null;
	
	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}

	@RequestMapping("/main_CustomerList.do")
	public ModelAndView custmoerList(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_CustomerList.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		// 以後要在User vo裡取得family Id
//		Family family = this.jftCenter.retvFamilyInfo(0);
		
		try {
			int pageAmount = 0;
			String selectItem = (String) req.getSession().getAttribute("searchItem");
			String searchValue = (String) req.getSession().getAttribute("searchValue");
			int currentPage = 1;
			if (req.getSession().getAttribute("currentPage") != null)
				currentPage = ((Integer) req.getSession().getAttribute("currentPage")).intValue();
			
			Family[] familys = null;
			if (searchValue != null && !"".equals(searchValue)) {
				if (IJinFaTanCenter.SEARCH_BY_NAME.equals(selectItem)) {
					familys = this.jftCenter.searchFamilyByName(searchValue, currentPage);
					pageAmount = this.jftCenter.retvFamilyPageAmountByName(searchValue);
				} else if (IJinFaTanCenter.SEARCH_BY_PHONE.equals(selectItem)) {
					familys = this.jftCenter.searchFamilyByPhone(searchValue, currentPage);
					pageAmount = this.jftCenter.retvFamilyPageAmountByPhonePage(searchValue);
				} else if (IJinFaTanCenter.SEARCH_BY_ADDRES.equals(selectItem)) {
					familys = this.jftCenter.searchFamilyByAddress(searchValue, currentPage);
					pageAmount = this.jftCenter.retvFamilyPageAmountByAddress(searchValue);
				}
			} else {
				familys = this.jftCenter.retvFamilyInfoByPage(currentPage);
				pageAmount = this.jftCenter.retvFamilyPageAmount();
			}
			macLog.debug("familys size = " + familys.length);
			macLog.debug("pageAmount = " + pageAmount);
			
			int startPage = currentPage - 4;
			if (startPage < 1)
				startPage = 1;
			int endPage = currentPage + 5;
			if (pageAmount >= 9 && endPage < 9)
				endPage = 9;
			if (endPage > pageAmount)
				endPage = pageAmount;
			
			String targetPage = null;
			if (familys != null)
				targetPage = "customer_list";
			else
				targetPage = "404";
			result = new ModelAndView(targetPage);
			result.addObject("familys", familys);
			result.addObject("pageAmount", pageAmount);
			result.addObject("currentPage", currentPage);
			result.addObject("startPage", startPage);
			result.addObject("endPage", endPage);
			
			// add for displaytag test
			/*
			List<Family> data = new ArrayList<Family>();
			for (int i = 0; i < familys.length; i++)
				data.add(familys[i]);
			SimplePagingList customerList = new SimplePagingList();
			customerList.setList(data);
			customerList.setPageNumber(1);
			customerList.setFullListSize(90);
			result.addObject("customerList", customerList);
			*/
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/main_CustomerManager.do")
	public ModelAndView customerManager(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_CustomerManager.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String familyId = (String) req.getSession().getAttribute("familyId");
			
			Family family = this.jftCenter.retvFamilyInfo(Integer.parseInt(familyId));
			Member[] members = this.jftCenter.retvMemberByFamilyId(Integer.parseInt(familyId));
			
			for (int i = 0; i < members.length; i++)
				members[i].setBirthDay(String.valueOf(this.jftCenter.convertToNumber(
						members[i].getBirthDay())));
			
			result = new ModelAndView("customer_change");
			result.addObject("family", family);
			result.addObject("members", members);
			result.addObject("orderList", VoToString.orderList.split(","));
			result.addObject("cityList", this.jftCenter.retvAllCity());
			result.addObject("areaList", this.jftCenter.retvAreaByCity(family.getCity()));
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/main_CustomerAdd.do")
	public ModelAndView customerAdd(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_CustomerAdd.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		String targetPage = "customer_add";
		result = new ModelAndView(targetPage);
		result.addObject("orderList", VoToString.orderList.split(","));
		result.addObject("cityList", this.jftCenter.retvAllCity());
		result.addObject("areaList", this.jftCenter.retvAreaByCity("新北市"));
		
		macLog.flushCollectDebug();
		return result;
	}
	
	@RequestMapping("/main_CustomerPrint.do")
	public ModelAndView customerPrint(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_CustomerPrint.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		String familyId = (String) req.getSession().getAttribute("familyId");
		macLog.log("Family Id = " + familyId);
		
		Member[] members = this.jftCenter.retvMemberByFamilyId(Integer.parseInt(familyId));
		
		result = new ModelAndView("print_view");
		result.addObject("members", members);
		
		macLog.flushCollectDebug();
		return result;
	}
	
	@RequestMapping("/main_CustomerOrder.do")
	public ModelAndView customerOrder(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_CustomerOrder.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			int[] orderYearList = this.jftCenter.retvOrderYears();
			
			String familyId = (String) req.getSession().getAttribute("familyId");
			macLog.log("Family Id = " + familyId);
			
			String householder = null;
			if (familyId != null) {
				Family family = this.jftCenter.retvFamilyInfo(Integer.parseInt(familyId));
				householder = family.getHouseholder().getName();
			}
			macLog.log("Householder = " + householder);
			
			/* mark for ajax order info list change by MacGyver on 20131222
			OrderStatisticsInfo[] orderStatisticsInfo = 
					this.jftCenter.retvOrderStatisticsInfo();
			 */
			
			String targetPage = "order_list";
			result = new ModelAndView(targetPage);
			result.addObject("orderYearList", orderYearList);
			result.addObject("familyId", familyId);
			result.addObject("householder", householder);
			
			/* mark for ajax order info list change by MacGyver on 20131222
			result.addObject("orderInfoList", orderStatisticsInfo);
			 */
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/main_Logout.do")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_Logout.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		req.getSession().setAttribute("Authorise", null);
		
		String targetPage = "login";
		result = new ModelAndView(targetPage);
		
//		res.sendRedirect("/WEB-INF/pages/login.jsp");
		
		macLog.flushCollectDebug();
		return result;
	}
	
	@RequestMapping("/main_SystemInfo.do")
	public ModelAndView retrieveSystemInfo(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_SystemInfo.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			// set character encode
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			
			DateFormat dateFormat = this.jftCenter.getDateFormat();
			Calendar calendar = Calendar.getInstance();
			String dateString = dateFormat.format(calendar.getTime());
			
			int currentYear = calendar.get(Calendar.YEAR) - 1911;
			String jiaZi = this.jftCenter.calculateJiaZi(currentYear);
			
			req.getSession().setAttribute("JiaZi", jiaZi);
			req.getSession().setAttribute("currentYear", currentYear);
			
			
			res.getWriter().print(dateString + "  " + jiaZi + " 年");
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/main_StatisticsReport.do")
	public ModelAndView calculateStatisticsReport(HttpServletRequest req, 
			HttpServletResponse res) throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("main_StatisticsReport.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			// set character encode
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			
			req.getParameter("");
			
			
			
			
			String targetPage = "statistics_report";
			result = new ModelAndView(targetPage);
			
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}	
}
