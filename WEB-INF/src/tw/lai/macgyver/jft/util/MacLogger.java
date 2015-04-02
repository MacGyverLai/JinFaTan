package tw.lai.macgyver.jft.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;


public class MacLogger {

	final static SimpleDateFormat dateFormatInst = new SimpleDateFormat(
			"yyyy.MM.dd.HH.mm.ss.S");
	final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final static String keyProfileSummaryLog = "Disp.SummaryLog";
	final static String keyProfileLog = "Disp.ProfileLog";
	final static String keyDebugLog = "Disp.DebugLog";
	final static String keySystemLog = "Disp.SystemLog";
	final static public int PROFILE_LOCAL = 1;
	final static public int PROFILE_REMOTE = 2;

	static private Logger catSystem = Logger.getLogger(keySystemLog);
	static private Logger catDebug = Logger.getLogger(keyDebugLog);
	static private Logger catProfile = Logger.getLogger(keyProfileLog);
	static private Logger catProfileSummary = Logger.getLogger(keyProfileSummaryLog);

	static private Random generator = new Random();

	private String profileMsg = null;
	private int proType = 0;
	private long tsProfile = 0;

	static {
		System.out.println("Now path = " + new File(".").getAbsolutePath());
		String domainHome = System.getProperty("Domain_Home");
		System.out.println("Domain_Home URL is " + domainHome);
//		PropertyConfigurator.configure(domainHome + "/conf/MacLog.properties");
		PropertyConfigurator.configure("C:/JinFaTan/log4j.properties");
	}

	public String initCollectDebug(String apiName, String remoteAddress, String userName,
			String channelId) {
		String instId = dateFormatInst.format(new Date()) + "." + generator.nextInt(10000)
				+ "_" + apiName;

		CollectDebug collectDebug = this.getCollectDebug();

		if (collectDebug == null) {
			collectDebug = new CollectDebug();
			collectDebug.setInstanceId(instId);
			collectDebug.setApiName(apiName);
			collectDebug.setRemoteAddress(remoteAddress);
			collectDebug.setUserName(userName);
			collectDebug.setChannelId(channelId);

			MDC.put(CollectDebug.class.getName(), collectDebug);

			this.log("****************************************");
			this.log("Starts---" + collectDebug.getInstanceId());
		} else {
			instId = collectDebug.getInstanceId();
		}

		return instId;
	}

	public void log(String msg) {
		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null)
			collectDebug.getSbDebugLog().append(
					"\n[" + dateFormat.format(new Date()) + "] " + msg);
		else
			this.sysError("CollectDebug non-initiate");
	}
	
	public void debug(String msg) {
		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null) {
			if (catDebug.getEffectiveLevel() == Level.ALL || 
					catDebug.getEffectiveLevel() == Level.DEBUG)
				collectDebug.getSbDebugLog().append(
						"\n[" + dateFormat.format(new Date()) + "] " + msg);
		} else {
			this.sysError("CollectDebug non-initiate");
		}
	}

	public void log(Exception ex) {
		Throwable e = ex;
		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ex.printStackTrace(new PrintStream(outputStream));
			collectDebug.getSbDebugLog().append(outputStream);
			
			/*
			int round = 0;
			while (e != null) {
				if (round == 0)
					collectDebug.getSbDebugLog()
							.append("Exception Occured: " + e.getMessage());
				else
					collectDebug.getSbDebugLog().append("\n Caused by: " + e.getMessage());

				StackTraceElement[] stackTrace = e.getStackTrace();
				if (stackTrace != null) {
					for (int i = 0; i < stackTrace.length; i++) {
						collectDebug.getSbDebugLog().append(
								"\n\tat " + stackTrace[i].toString());
					}
				}
				e = e.getCause();
				round++;
			}
			*/
		} else {
			this.sysError("CollectDebug non-initiate");
		}
	}

	static public void sysLog(String message) {
		catSystem.debug(message);
	}

	static public void sysError(String errMsg) {
		// catSystem.error("<<" + this.collectDebug.instanceId + ">>");
		catSystem.error(errMsg);
	}

	static public void sysError(Exception ex) {
		sysError(ex.getMessage(), ex);
	}

	static public void sysError(String errMsg, Exception ex) {
		// catSystem.error("<<" + this.collectDebug.instanceId + ">>");
		catSystem.error(errMsg, ex);
	}

	/*
	 * public void profileStart(int profileType) {
	 * this.profileStart(profileType, this.remoteAddress); }
	 */

	public void profileStart(int profileType, String msg) {
		profileMsg = msg;
		this.proType = profileType;
		this.log("Profiling_InputInfo => " + profileMsg);
		this.tsProfile = System.currentTimeMillis();

		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null)
			collectDebug.getSbProfileLog().append(
					"\n[" + dateFormat.format(new Date()) + "] Profiling_Start => "
					+ profileMsg);
		else
			this.sysError("CollectDebug non-initiate at profileStart");
	}

	public void profileEnd(String msg) {
		CollectDebug collectDebug = this.getCollectDebug();
		if ((this.tsProfile == 0) || (collectDebug == null))
			return;

		long tmpTime = System.currentTimeMillis() - this.tsProfile;
		switch (this.proType) {
		case PROFILE_LOCAL:
			collectDebug.setCumTimeLocal(collectDebug.getCumTimeLocal() + tmpTime);
			break;
		case PROFILE_REMOTE:
			collectDebug.setCumTimeRemote(collectDebug.getCumTimeRemote() + tmpTime);
			break;
		}
		this.log("Profiling_OutputInfo => " + msg);
		collectDebug.getSbProfileLog().append(
				"\n[" + dateFormat.format(new Date()) + "] Profiling_OutputInfo => " + msg);
	}

	protected String profileSummary() {
		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null) {
			long tmTotal = System.currentTimeMillis() - collectDebug.getTimeStampStart();
			collectDebug.setCumTimeLocal(tmTotal - collectDebug.getCumTimeRemote());
			String proSummary = collectDebug.getRemoteAddress() + ": "
					+ collectDebug.getApiName() + ": " + collectDebug.getUserName() + ": "
					+ collectDebug.getChannelId() + " => Local "
					+ collectDebug.getCumTimeLocal() + " | Remote "
					+ collectDebug.getCumTimeRemote() + "| Total " + tmTotal;
			return proSummary;
		}

		return null;
	}

	public void flushCollectDebug() {
		CollectDebug collectDebug = this.getCollectDebug();
		if (collectDebug != null) {
			String proSummary = this.profileSummary();

			collectDebug.getSbProfileLog().append("\n[" + dateFormat.format(new Date()) + "] "
					+ proSummary);

			this.log("ProfileSummary ==>" + proSummary);
			this.log("END---" + collectDebug.getInstanceId());

			catDebug.info(collectDebug.getSbDebugLog().toString());
			catProfile.info(collectDebug.getSbProfileLog().toString());
			catProfileSummary.info("[" + dateFormat.format(new Date()) + "] " + proSummary);
			MDC.remove(CollectDebug.class.getName());
		} else {
			this.sysError("SigdispLog not initialized", new Exception(
			"SigdispLog not initialized"));
		}
	}

	private CollectDebug getCollectDebug() {
		return (CollectDebug) MDC.get(CollectDebug.class.getName());
	}

}
