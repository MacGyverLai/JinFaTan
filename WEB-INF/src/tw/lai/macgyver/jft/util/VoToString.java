package tw.lai.macgyver.jft.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import tw.lai.macgyver.jft.vo.Base;

public class VoToString {
	
	public static String orderList = null;
	
	public static String orderMan = null;
	
//	public static String[] CLASH = null;
	
	static {
		try {
			Properties config = new Properties();
			config.load(new InputStreamReader(new FileInputStream(
					"C:/JinFaTan/config.properties"), "UTF-8"));
			orderList = config.getProperty("order.list");
			orderMan = config.getProperty("order.man");
			
			// load sacred from property
			/*
			List<String> clashList = new ArrayList<String>();
			clashList.add(config.getProperty("sacred.zi"));
			clashList.add(config.getProperty("sacred.chou"));
			clashList.add(config.getProperty("sacred.yin"));
			clashList.add(config.getProperty("sacred.mao"));
			clashList.add(config.getProperty("sacred.chen"));
			clashList.add(config.getProperty("sacred.si"));
			clashList.add(config.getProperty("sacred.wu"));
			clashList.add(config.getProperty("sacred.wei"));
			clashList.add(config.getProperty("sacred.shen"));
			clashList.add(config.getProperty("sacred.you"));
			clashList.add(config.getProperty("sacred.xu"));
			clashList.add(config.getProperty("sacred.hai"));
			CLASH = (String[]) clashList.toArray(new String[0]);
			*/
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			MacLogger.sysError(ex);
		}
	}

	public static String toString(Object object) {
		String result = null;
		
		if (object instanceof String)
			result = object.toString();
		else
			result = object.toString();
		
		return result;
	}

}
