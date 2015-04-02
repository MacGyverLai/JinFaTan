package tw.lai.macgyver.jft.util;

public class CollectDebug {

	private String apiName = null;
	private String instanceId = null;
	
	private String remoteAddress = null;
	
	private String userName = null;
	
	private String channelId = null;
	
	private long timeStampStart = System.currentTimeMillis();
	private StringBuffer sbDebugLog = new StringBuffer();
	private StringBuffer sbProfileLog = new StringBuffer("--------------------");

	// following attributes are for profiling
	private long cumTimeLocal = 0;
	private long cumTimeRemote = 0;

	public CollectDebug() {
		this.timeStampStart = System.currentTimeMillis();
		this.sbDebugLog = new StringBuffer();
		this.sbProfileLog = new StringBuffer("--------------------");
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public long getTimeStampStart() {
		return timeStampStart;
	}

	public void setTimeStampStart(long timeStampStart) {
		this.timeStampStart = timeStampStart;
	}

	public StringBuffer getSbDebugLog() {
		return sbDebugLog;
	}

	public void setSbDebugLog(StringBuffer sbDebugLog) {
		this.sbDebugLog = sbDebugLog;
	}

	public StringBuffer getSbProfileLog() {
		return sbProfileLog;
	}

	public void setSbProfileLog(StringBuffer sbProfileLog) {
		this.sbProfileLog = sbProfileLog;
	}

	public long getCumTimeLocal() {
		return cumTimeLocal;
	}

	public void setCumTimeLocal(long cumTimeLocal) {
		this.cumTimeLocal = cumTimeLocal;
	}

	public long getCumTimeRemote() {
		return cumTimeRemote;
	}

	public void setCumTimeRemote(long cumTimeRemote) {
		this.cumTimeRemote = cumTimeRemote;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}
