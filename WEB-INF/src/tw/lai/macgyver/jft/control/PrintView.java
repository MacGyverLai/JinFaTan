package tw.lai.macgyver.jft.control;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tw.lai.macgyver.jft.module.IJinFaTanCenter;
import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.util.VoToString;
import tw.lai.macgyver.jft.vo.Family;
import tw.lai.macgyver.jft.vo.Member;

@Controller
public class PrintView {
	
	private static String memberOutput_1 = "本命";
	
	private static String memberOutput_2 = "時生　";
	
	private static String memberOutput_3 = "茲因五鬼天狗白虎車關水火關";
	
	private static String memberOutput_4 = "　　　　　欲求平安";
	
	private NumberFormat numberFormat = new DecimalFormat("00");

	private IJinFaTanCenter jftCenter = null;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}

	@RequestMapping("/ajax_PreviewReport.do")
	public ModelAndView previewReport(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		ModelAndView result = null;
		MacLogger macLog = new MacLogger();
		macLog.initCollectDebug("ajax_PreviewReport.do", req.getRemoteAddr(), 
				req.getRemoteUser(), "None");
		
		try {
			String reportPath = req.getSession().getServletContext().getInitParameter("reportPath");
			/* mark for murti-report on 20120107
			String reportName = reportPath + "/" + 
					req.getSession().getServletContext().getInitParameter("reportName");
			*/
			
			String memberIdInput = req.getParameter("memberIds");
			int printFormat = Integer.parseInt(req.getParameter("printFormat"));
			String performDate = req.getParameter("performDate");
			String familyId = (String) req.getSession().getAttribute("familyId");
			String jiaZi = (String) req.getSession().getAttribute("JiaZi");
			macLog.log("memberIdInput = " + memberIdInput + " | performDate = " + performDate 
					+ " | printFormat = " + printFormat);
			String[] selectedMemberIds = null;
			if (memberIdInput != null)
				selectedMemberIds = memberIdInput.split(":");
			
			Calendar calendar = Calendar.getInstance();
			if (performDate != null && !"".equals(performDate)) {
				calendar.setTime(dateFormat.parse(performDate));
				jiaZi = this.jftCenter.calculateJiaZi(calendar.get(Calendar.YEAR) - 1911);
			}
			
			res.setContentType("application/pdf");
			
			switch (printFormat) {
			case 1:
			case 2:
			case 3:
			case 4:
				this.previewOfficialReport(jiaZi, res.getOutputStream(), reportPath, printFormat, 
						familyId, selectedMemberIds, calendar);
				break;
			case 5:
			case 6:
				this.previewNormalReport(jiaZi, res.getOutputStream(), reportPath, printFormat, 
						familyId);
			}
			
		} catch (RuntimeException ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
		} finally {
			macLog.flushCollectDebug();
		}
		
		return result;
	}
	
	private void previewNormalReport(String jiaZi, OutputStream outputStream, String reportPath, 
			int printFormat, String familyId) throws JRException {
		String printDate = this.jftCenter.getDateFormat().format(new Date());
		Map<String, Object> reportParameter = new HashMap<String, Object>();
		JRBeanArrayDataSource dataSource = null;
		
		if (printFormat == 5) {
			Family family = this.jftCenter.retvFamilyInfo(Integer.parseInt(familyId));
			Member[] members = this.jftCenter.retvMemberByFamilyId(Integer.parseInt(familyId));
			
			// prepare parameters
			reportParameter.put("JiaZi", jiaZi);
			reportParameter.put("PrintDate", printDate);
			reportParameter.put("HouseHolder", family.getHouseholder().getName());
			reportParameter.put("HousePhone", family.getHousePhone());
			reportParameter.put("Phone_1", family.getPhone_1());
			reportParameter.put("Phone_2", family.getPhone_2());
			reportParameter.put("City", family.getCity() + family.getArea());
			reportParameter.put("Address", family.getAddress());
			
			// prepare data source
			System.out.println("Report member size = " + members.length);
			dataSource = new JRBeanArrayDataSource(members);
		} else if (printFormat == 6) {
			
		}
		
		// use jasper file
		String reportName = reportPath + "/" + "Normal.jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportName, reportParameter, 
				dataSource);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	private void previewOfficialReport(String jaiZi, OutputStream outputStream,
			String reportPath, int printFormat, String familyId, String[] selectedMemberIds, 
			Calendar performDate) throws JRException, IOException {
		MacLogger macLog = new MacLogger();
		
		int performYear = performDate.get(Calendar.YEAR);
		Family family = this.jftCenter.retvFamilyInfo(Integer.parseInt(familyId));
		Member[] members = this.jftCenter.retvMemberByFamilyId(Integer.parseInt(familyId), 
				performYear);
		
		int memberIdLength = 0;
		for (int i = 0; i < selectedMemberIds.length; i++) {
			if (selectedMemberIds[i].length() > 0)
				memberIdLength++;
		}
		macLog.debug("memberIdLength = " + memberIdLength);
		
		// prepare parameters
		Map<String, Object> reportParameter = new HashMap<String, Object>();
		reportParameter.put("City", family.getCity() + family.getArea());
		reportParameter.put("Address", family.getAddress());
		reportParameter.put("JiaZi", jaiZi);
		
		int memberCount = 0;
		String houseHolder = null;
		Set<String> clashSet = new HashSet<String>();
		boolean calculateSex = true;
		int manCount = 0;
		int womanCount = 0;
		for (int i = 0; i < selectedMemberIds.length; i++) {
			for (int j = 0; j < members.length; j++) {
				if (selectedMemberIds[i].equals(String.valueOf(members[j].getId()))) {
					memberCount++;
					houseHolder =  members[j].getName();
					
					// change for calculate sex by MacGyver on 20130216
					String tmpOrder = null;
					if (VoToString.orderMan.indexOf(members[j].getOrder()) > -1) {
						if (memberIdLength > 1)
							tmpOrder = members[j].getOrder();
						else
							tmpOrder = "信士";
						manCount++;
					} else if (VoToString.orderList.indexOf(members[j].getOrder()) > -1) {
						if (memberIdLength > 1)
							tmpOrder = members[j].getOrder();
						else
							tmpOrder = "信女";
						womanCount++;
					} else {
						tmpOrder = "";
						calculateSex = false;
					}
					
					String memberOutput = this.jftCenter.fullSpace(3, tmpOrder, true) + 
							this.jftCenter.fullSpace(4, this.jftCenter.convertNameFormat(
							members[j].getName()), true) + 
							memberOutput_1 + members[j].getSixtyYear() + "年" + 
							this.jftCenter.fullSpace(2, members[j].getBirthMonth(), false) + "月" + 
							this.jftCenter.fullSpace(3, members[j].getBirthDay(), false) + "日" +
							this.jftCenter.fullSpace(2, members[j].getBirthTime(), false) + 
							memberOutput_2 + 
							this.jftCenter.fullSpace(3, members[j].getYears(), false) + "歲";
					reportParameter.put("Member" + this.numberFormat.format(memberCount), 
							memberOutput);
					
					// added for asset
					if (members[j].getAsset() != null && !"".equals(members[j].getAsset()))
						reportParameter.put("Asset" + this.numberFormat.format(memberCount), 
								this.jftCenter.convertSmallToBig(members[j].getAsset()));
					
					// calculate member clash
					clashSet.add(this.jftCenter.calculateClash(members[j].getBirthYear(), 
							performYear));
				}
			}
		}
		if (memberCount > 1) {
			houseHolder = family.getHouseholder().getName();
			houseHolder += "合家";
		}
		macLog.debug(houseHolder);
		reportParameter.put("HouseHolder", houseHolder);
		macLog.log("member count = " + memberCount);
		
		// Output sacrifice
		if (printFormat == 1 || printFormat == 3) {
			StringBuffer clash = new StringBuffer(memberOutput_3);
			Iterator<String> iterator = clashSet.iterator();
			while (iterator.hasNext())
				clash.append(iterator.next());
			macLog.log("Sacrifice = " + clash.toString());
			reportParameter.put("Sacrifice", clash.toString());
			
			// added for calculate sex by MacGyver on 20130216
			String mark = family.getHouseholder().getName() + "　";
			if (calculateSex) {
				if (manCount > 0)
					mark += this.jftCenter.convertToChinese(manCount, false) + "丁";
				if (womanCount > 0)
					mark += this.jftCenter.convertToChinese(womanCount, false) + "口";
			}
			reportParameter.put("Mark", mark);
		} else if (printFormat == 2 || printFormat == 4) {
			reportParameter.put("Sacrifice", memberOutput_4);
		}
		
		// output pdf file
		// use jrxml file
//			JasperReport jasperReport = JasperCompileManager.compileReport(reportName);
//			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameter);
		
		// use jasper file
		String reportName = null;
		if (printFormat == 1 || printFormat == 2)
			reportName = reportPath + "/" + "FuChengGong.jasper";
		else if (printFormat == 3 || printFormat == 4)
			reportName = reportPath + "/" + "JinFaTan.jasper";
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportName, reportParameter);
		
		macLog.debug("jasperPrint = " + jasperPrint.getName());
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
}
