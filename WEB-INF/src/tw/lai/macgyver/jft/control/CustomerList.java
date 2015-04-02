package tw.lai.macgyver.jft.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.vo.SimplePagingList;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;

@Controller
public class CustomerList {
	
	private IJinFaTanCenter jftCenter = null;

	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}
	
	@RequestMapping("/ajax_HouseHolderList.do")
	public ModelAndView householderList(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_HouseHolderList.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			int currentPage = 1;
			if (req.getParameter("page") != null)
				currentPage = Integer.parseInt(req.getParameter("page").trim());
			
			macLog.log("currentPage = " + currentPage);
			
			Family[] familys = this.jftCenter.retvFamilyInfoByPage(currentPage);
			int pageAmount = this.jftCenter.retvFamilyPageAmount();
			
			req.getSession().setAttribute("currentPage", new Integer(currentPage));
			
			/* for displaytag
			List<Family> data = new ArrayList<Family>();
			for (int i = 0; i < familys.length; i++)
				data.add(familys[i]);
			
			SimplePagingList customerList = new SimplePagingList();
			customerList.setList(data);
			customerList.setPageNumber(1);
			
			result.addObject("customerList", customerList);
			*/
			
			int startPage = currentPage - 4;
			if (startPage < 1)
				startPage = 1;
			int endPage = currentPage + 5;
			if (pageAmount >=9 && endPage < 9)
				endPage = 9;
			if (endPage > pageAmount)
				endPage = pageAmount;
			
			result = new ModelAndView("householder_list");
			result.addObject("familys", familys);
			result.addObject("pageAmount", pageAmount);
			result.addObject("currentPage", currentPage);
			result.addObject("startPage", startPage);
			result.addObject("endPage", endPage);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/ajax_MemberList.do")
	public ModelAndView memberList(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_MemberList.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String familyId = req.getParameter("familyId");
			macLog.log("Family Id = " + familyId);
			
			req.getSession().setAttribute("familyId", familyId);
			Member[] members = this.jftCenter.retvMemberByFamilyId(Integer.parseInt(familyId));
			
			result = new ModelAndView("member_list");
			result.addObject("members", members);
			
			// add for display test
			List<Member> data = new ArrayList<Member>();
			for (int i = 0; i < members.length; i++)
				data.add(members[i]);
			SimplePagingList paginList = new SimplePagingList();
			paginList.setFullListSize(data.size());
			paginList.setPageNumber(1);
			paginList.setList(data);
			result.addObject("memberList", paginList);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/ajax_searchCustomer.do")
	public ModelAndView searchCustomer(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_searchCustomer.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		// set character encode
		req.setCharacterEncoding("UTF-8");
		
		try {
			String selectItem = req.getParameter("searchItem");
			String searchValue = req.getParameter("searchValue");
			String searchPage = req.getParameter("page");
			macLog.log("Select Item = " + selectItem);
			macLog.log("Search Value = " + searchValue);
			macLog.log("Search Page = " + searchPage);
			
			/* retrieve seting from session
			if (searchValue == null) {
				selectItem = (String) req.getSession().getAttribute("selectItem");
				searchValue = (String) req.getSession().getAttribute("searchValue");	
			}
			*/
			
			int currentPage = 1;
			int pageAmount = 0;
			if (searchPage != null)
				currentPage = Integer.parseInt(searchPage.trim());
			
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
				req.getSession().setAttribute("searchValue", searchValue);
			} else {
				familys = this.jftCenter.retvFamilyInfoByPage(currentPage);
				pageAmount = this.jftCenter.retvFamilyPageAmount();
				req.getSession().setAttribute("searchValue", null);
			}
			req.getSession().setAttribute("searchItem", selectItem);
			req.getSession().setAttribute("currentPage", new Integer(currentPage));
			
//			if (familys != null && familys.length > 0) {
//				macLog.log("total family amount = " + familys.length);
//				
//				if (pageAmount < 1) {
//					pageAmount = this.jftCenter.getTotalPage(familys.length);
//					familys = this.jftCenter.getListFromPage(currentPage, familys);
//				}
//				
//				/* for display test
//				SimplePagingList customerList = null;
//				List<Family> data = new ArrayList<Family>();
//				for (int i = 0; i < familys.length; i++)
//					data.add(familys[i]);
//				customerList = new SimplePagingList();
//				customerList.setList(data);
//				customerList.setFullListSize(familys.length);
//				customerList.setPageNumber(currentPage);
//				*/
//				
//				macLog.log("family amount of page = " + familys.length);
//			}
			macLog.log("total page = " + pageAmount);
			
			int startPage = currentPage - 4;
			if (startPage < 1)
				startPage = 1;
			int endPage = currentPage + 5;
			if (pageAmount >= 9 && endPage < 9)
				endPage = 9;
			if (endPage > pageAmount)
				endPage = pageAmount;
			
			result = new ModelAndView("householder_list");
			result.addObject("familys", familys);
			result.addObject("pageAmount", pageAmount);
			result.addObject("currentPage", currentPage);
			result.addObject("startPage", startPage);
			result.addObject("endPage", endPage);
			/* for display test
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
}
