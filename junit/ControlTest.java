import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import tw.lai.macgyver.jft.module.HsqlCustomerManageDAO;
import tw.lai.macgyver.jft.module.HsqlOrderManageDAO;
import tw.lai.macgyver.jft.module.JinFaTanCenter;
import tw.lai.macgyver.jft.vo.Base;
import tw.lai.macgyver.jft.vo.OrderClash;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import junit.framework.TestCase;


public class ControlTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testCreateReport() {
		String reportName = "C:/JinFaTan/report/JinFaTan.jrxml";
//		String reportName = "C:/WBX343P.jrxml";
		
		Map<String, Object> reportParameter = new HashMap<String, Object>();
		reportParameter.put("City", "新北市三芝區");
		reportParameter.put("Address", "長勤街三九號二樓");
		reportParameter.put("HouseHolder", "賴雲禎");
		reportParameter.put("JiaZi", "甲子");
		
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(reportName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameter);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/PdfTest.pdf");
			
		} catch (JRException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void testConvertToChinese() {
		JinFaTanCenter jftCenter = new JinFaTanCenter();
		
		int number = 7;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
		
		number = 10;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
		
		number = 13;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
		
		number = 26;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
		
		number = 102;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
		
		number = 0;
		System.out.println(number + " = " + jftCenter.convertToChinese(number, false));
		System.out.println(number + " = " + jftCenter.convertToChinese(number, true));
	}
	
	public void testConvertJiaZiToYear() {
		String tianGan = "壬";
		String diZhi = "辰";
		
		JinFaTanCenter jinFaTanCenter = new JinFaTanCenter();
		int rtnYear = jinFaTanCenter.convertJiaZiToYear(tianGan, diZhi);
		
		System.out.println("rtnYear = "+ rtnYear);
	}
	
	public void testConvertSmallToBig() {
		String input = "BOX-119";
		
		System.out.println("input = " + input);
		JinFaTanCenter jinFaTanCenter = new JinFaTanCenter();
		String output = jinFaTanCenter.convertSmallToBig(input);
		System.out.println("output = " + output);
	}
	
	public void testCalculateClash() {
		JinFaTanCenter jinFaTanCenter = new JinFaTanCenter();
		String output = jinFaTanCenter.calculateClash(73, 2012);
		
		System.out.println(output);
	}
	
	public void testBaseString() {
		for (int i = 0; i < Base.CLASH.length; i++) {
			System.out.println(i + " " + Base.CLASH[i]);
		}
	}
	
	public void convertDbData() {
		String inputFile = "C:/AreaData_input.txt";
		String outputFile = "C:/AreaDataOutput.sql";
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(new FileWriter(outputFile));
			reader = new BufferedReader(new FileReader(inputFile));
			
			String lineString = reader.readLine();
			while (lineString != null) {
				if (lineString.length() < 1) {
					lineString = reader.readLine();
					continue;
				}
				
				StringBuffer stringComponent = new StringBuffer();
				String[] tmpString = lineString.split("','");
				stringComponent.append(tmpString[0]);
				stringComponent.append("','");
				stringComponent.append(tmpString[1]);
				stringComponent.append("','");
				stringComponent.append(tmpString[2]);
				stringComponent.append("','");
				stringComponent.append(tmpString[3]);
				stringComponent.append("','");
				stringComponent.append(tmpString[7]);
				stringComponent.append("','");
				stringComponent.append(tmpString[8]);
				stringComponent.append("');");
				writer.println(stringComponent.toString());
				
				lineString = reader.readLine();
			}
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
				reader.close();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		
	}
	
	public void testChar() {
		int aa = '\n';
		
		System.out.println(aa);
		
		String tmp = "car" + ((char) 10) + "dog";
		System.out.println(tmp);
		
		System.out.println((byte) "甲".charAt(0));
	}
	
	public void testLoggerLevel() {
		PropertyConfigurator.configure("C:/JinFaTan/log4j.properties");
		
		Logger catDebug = Logger.getLogger("Disp.DebugLog");
		System.out.println(catDebug.getEffectiveLevel());
		
		if (catDebug.getEffectiveLevel() == Level.INFO)
			System.out.println("ABC");
	}
	
	public void testCalculateTaiSui() {
		JinFaTanCenter jftCenter = new JinFaTanCenter();
		System.out.println(jftCenter.calculateTaiSui(30));
	}
	
	public void testReplaceNumberToChinese() {
		String input = "你 2我10他15妳21我們30他們之32";
		
		JinFaTanCenter jftCenter = new JinFaTanCenter();
		System.out.println(jftCenter.replaceNumberToChinese(input));
	}
	
	public void testCalculateJiaZi() {
		int birthYear = 40;
		
		JinFaTanCenter jftCenter = new JinFaTanCenter();
		System.out.println(jftCenter.calculateJiaZi(birthYear));
	}
	
	public void testRetvOrderClash() {
		String filePath = "C:/JinFaTan/JinFaTan";
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:" + filePath + ";shutdown=true");
		dataSource.setUsername("SA");
		dataSource.setPassword("");
		
		HsqlCustomerManageDAO hsqlDao = new HsqlCustomerManageDAO();
		hsqlDao.setDataSource(dataSource);
		
		HsqlOrderManageDAO hsqlOrderManageDao = new HsqlOrderManageDAO();
		hsqlOrderManageDao.setDataSource(dataSource);
		
		JinFaTanCenter jftCenter = new JinFaTanCenter();
		jftCenter.setHsqlDAO(hsqlDao);
		jftCenter.setHsqlOrderManageDAO(hsqlOrderManageDao);
		
		OrderClash[] orderClash = jftCenter.retvOrderClash(103, 2013);
		if (orderClash != null) {
			for (int i = 0; i < orderClash.length; i++)
				System.out.println("OrderClash = " + orderClash[i]);
		}
	}
	
	public void testSimpleDateFormat() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd F");
		System.out.println(dateFormat.format(new Date()));
	}
	
	public void testSelectName() {
		String lastName = "賴";
		String[] firstWord = new String[] { "玟", "玥", "芃", "芊", "姿", "姵", "音" };
		String[] secondWord = new String[] { "希", "吟", "妍", "妗", "妘", "伶", "彤", "妤", "岑" };
		
		int seqNo = 1;
		for (int i = 0; i < firstWord.length; i++) {
			for (int j = 0; j < secondWord.length; j++) {
				seqNo++;
				System.out.println(seqNo + " " + lastName + firstWord[i] + secondWord[j]);
			}
		}
	}
}
