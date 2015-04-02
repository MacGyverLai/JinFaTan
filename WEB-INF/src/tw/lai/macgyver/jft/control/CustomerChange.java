package tw.lai.macgyver.jft.control;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.form.CustomerForm;
import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;

@Controller
public class CustomerChange {

	private IJinFaTanCenter jftCenter = null;

	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}
	
	@RequestMapping("/ajax_addCustomer.do")
	public ModelAndView addCustomer(@ModelAttribute("customerForm") CustomerForm customerForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_addCustomer.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			macLog.debug("prepare family data...");
			Family family = new Family();
			family.setAddress(customerForm.getAddress());
			family.setArea(customerForm.getTown());
			family.setCity(customerForm.getCity());
			family.setHousePhone(customerForm.getHousePhone());
			family.setPhone_1(customerForm.getPhone_1());
			family.setPhone_2(customerForm.getPhone_2());
			
			macLog.debug("prepare members data...");
			Date tmpDate = null;
			Calendar tmpCalendar = Calendar.getInstance();
			Member[] members = new Member[customerForm.getName().length];
			for (int i = 0; i < members.length; i ++) {
//				String tmpName = customerForm.getName()[i];
//				
//				macLog.log(" -> UTF-8 Name = " + new String(tmpName.getBytes(), "UTF-8"));
//				macLog.log("ISO -> Name = " + new String(tmpName.getBytes("ISO-8859-1")));
//				macLog.log("UTF-8 -> Name = " + new String(tmpName.getBytes("UTF-8")));
//				macLog.log("ISO -> UTF-8 Name = " + new String(tmpName.getBytes("ISO-8859-1"), "UTF-8"));
				
				members[i] = new Member();
				members[i].setName(customerForm.getName()[i]);
				members[i].setOrder(customerForm.getOrder()[i]);
				
				/* change for type modify on 20111218
				tmpDate = this.jftCenter.getDateFormat().parse(customerForm.getBirthday()[i]);
				tmpCalendar.setTime(tmpDate);
				*/
				members[i].setBirthYear(Integer.parseInt(customerForm.getBirthYear()[i]));
				members[i].setBirthMonth(customerForm.getBirthMonth()[i]);
				members[i].setBirthDay(customerForm.getBirthDay()[i]);
				
				members[i].setBirthTime(customerForm.getBirthTime()[i]);
				
				// added for asset on 20120102
				members[i].setAsset(customerForm.getAsset()[i]);
			}
			
			if (customerForm.getLight() != null) {				
				macLog.log("handle light column...");
				for (int i = 0; i < customerForm.getLight().length; i++) {
					int tmpLightIndex = Integer.parseInt(customerForm.getLight()[i]);
					macLog.log("Light index = " + tmpLightIndex);
					members[tmpLightIndex].setLight(true);
				}
			}
			macLog.debug("customerForm.getHouseHolder = " + customerForm.getHouseHolder());
			
			int houseHolder = Integer.parseInt(customerForm.getHouseHolder());
			boolean addRtn = this.jftCenter.addCustomer(family, members, houseHolder);
			macLog.log("add customer result = " + addRtn);
			res.getWriter().print(addRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/ajax_deleteCustomer.do")
	public ModelAndView deleteCustomer(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_deleteCustomer.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String familyId = req.getParameter("familyId");
			macLog.log("Family Id = " + familyId);
			
			macLog.debug("delete customer...");
			boolean updateRtn = this.jftCenter.deleteCustomer(Integer.parseInt(familyId));
			macLog.log("Delete result = " + updateRtn);
			
			macLog.debug("prepare refresh page data...");
			int currentPage = 1;
			Family[] familys = this.jftCenter.retvFamilyInfoByPage(currentPage);
			int pageAmount = this.jftCenter.retvFamilyPageAmount();
			
			macLog.log("familys size = " + familys.length);
			macLog.log("pageAmount = " + pageAmount);
			
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
				targetPage = "householder_list";
			else
				targetPage = "404";
			result = new ModelAndView(targetPage);
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
	
	@RequestMapping("/ajax_deleteMember.do")
	public ModelAndView deleteMember(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_deleteCustomer.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String memberId = req.getParameter("memberId");
			macLog.log("memberId = " + memberId);
			
			macLog.log("delete member...");
			boolean updateRtn = this.jftCenter.deleteMember(Integer.parseInt(memberId));
			macLog.log("Delete result = " + updateRtn);
			
			res.getWriter().print(updateRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/ajax_modifyCustomer.do")
	public ModelAndView modifyCustomer(@ModelAttribute("customerForm") CustomerForm customerForm, 
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_modifyCustomer.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String familyId = (String) req.getSession().getAttribute("familyId");
			macLog.log("Family Id = " + familyId);
			
			macLog.debug("prepare family data...");
			Family family = new Family();
			family.setId(Integer.parseInt(familyId));
			family.setAddress(customerForm.getAddress());
			family.setArea(customerForm.getTown());
			family.setCity(customerForm.getCity());
			family.setHousePhone(customerForm.getHousePhone());
			family.setPhone_1(customerForm.getPhone_1());
			family.setPhone_2(customerForm.getPhone_2());
			
			macLog.debug("prepare members data...");
			Member[] members = new Member[customerForm.getName().length];
			for (int i = 0; i < members.length; i ++) {
				members[i] = new Member();
				members[i].setId(Integer.parseInt(customerForm.getMemberId()[i]));
				members[i].setName(customerForm.getName()[i]);
				members[i].setOrder(customerForm.getOrder()[i]);
				members[i].setBirthYear(Integer.parseInt(customerForm.getBirthYear()[i]));
				members[i].setBirthMonth(customerForm.getBirthMonth()[i]);
				members[i].setBirthDay(customerForm.getBirthDay()[i]);
				members[i].setBirthTime(customerForm.getBirthTime()[i]);
				
				// added for asset on 20120103
				members[i].setAsset(customerForm.getAsset()[i]);
			}
			
			if (customerForm.getLight() != null) {				
				macLog.debug("handle light column...");
				for (int i = 0; i < customerForm.getLight().length; i++) {
					int tmpLightIndex = Integer.parseInt(customerForm.getLight()[i]);
					macLog.log("Light index = " + tmpLightIndex);
					members[tmpLightIndex].setLight(true);
				}
			}
			
			int houseHolder = Integer.parseInt(customerForm.getHouseHolder());
			
			boolean updateRtn = this.jftCenter.modifyCustomer(family, members, houseHolder);
			macLog.log("modify customer result = " + updateRtn);
			res.getWriter().print(updateRtn);
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	@RequestMapping("/ajax_calculateJiaZhi.do")
	public ModelAndView calculateJiaZhi(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		
		// set character encode
		req.setCharacterEncoding("UTF-8");
		
		String tianGan = req.getParameter("tian_gan");
		String diZhi = req.getParameter("di_zhi");
		
		int rtnYear = this.jftCenter.convertJiaZiToYear(tianGan, diZhi);
		int year_1 = rtnYear - 1911;
		int year_2 = year_1 + 60;
		int year_3 = year_2 + 60;
		res.getWriter().write(year_1 + ", " + year_2 + ", " + year_3);
		res.flushBuffer();
		
		return result;
	}
	
	@RequestMapping("/ajax_calculateAnimal.do")
	public ModelAndView calculateAnimal(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		
		// set character encode
		req.setCharacterEncoding("UTF-8");
		
		String animalSelect = req.getParameter("animal_select");
		String ageInput = req.getParameter("age_input");
		if (ageInput == null || "".equals(ageInput))
			return result;
		int aboutAge = Integer.parseInt(ageInput);
		
		int rtnYear = this.jftCenter.convertAnimalToYear(aboutAge, animalSelect);
		res.getWriter().write(rtnYear - 1911 + "");
		res.flushBuffer();
		
		return result;
	}
	
	@RequestMapping("/ajax_retvAreaByCity.do")
	public ModelAndView retvAreaByCity(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_retvAreaByCity.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			// set character encode
			req.setCharacterEncoding("UTF-8");
			
			String selectedCity = req.getParameter("selected_city");
			
			macLog.debug("Selected City = " + selectedCity);
			String[] areaList = this.jftCenter.retvAreaByCity(selectedCity);
			
			res.setContentType("application/json; charset=utf-8");
			res.getWriter().print(JSONArray.fromObject(areaList));
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
}
