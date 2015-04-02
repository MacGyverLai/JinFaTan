package tw.lai.macgyver.jft.vo;

import java.util.Properties;

public interface Base extends Cloneable, Comparable {

	public static final String TIAN_GAN_JIA = "\u7532";
	
	public static final String TIAN_GAN_YI = "\u4e59";
	
	public static final String TIAN_GAN_BING = "\u4e19";
	
	public static final String TIAN_GAN_DING = "\u4e01";
	
	public static final String TIAN_GAN_WU = "\u620a";
	
	public static final String TIAN_GAN_JI = "\u5df1";
	
	public static final String TIAN_GAN_GENG = "\u5e9a";
	
	public static final String TIAN_GAN_XIN = "\u8f9b";
	
	public static final String TIAN_GAN_REN = "\u58ec";
	
	public static final String TIAN_GAN_GUI = "\u7678";
	
	public static final String DI_ZHI_ZI = "\u5b50";
	
	public static final String DI_ZHI_CHOU = "\u4e11";
	
	public static final String DI_ZHI_YIN = "\u5bc5";
	
	public static final String DI_ZHI_MAO = "\u536f";
	
	public static final String DI_ZHI_CHEN = "\u8fb0";
	
	public static final String DI_ZHI_SI = "\u5df3";
	
	public static final String DI_ZHI_WU = "\u5348";
	
	public static final String DI_ZHI_WEI = "\u672a";
	
	public static final String DI_ZHI_SHEN = "\u7533";
	
	public static final String DI_ZHI_YOU = "\u9149";
	
	public static final String DI_ZHI_XU = "\u620c";
	
	public static final String DI_ZHI_HAI = "\u4ea5";
	
	public static final String TIME_UNKNOW = "\u5409";
	
	public static final String ANIMAL_SHU = "\u9f20";
	
	public static final String ANIMAL_NIU = "\u725b";
	
	public static final String ANIMAL_HU = "\u864e";
	
	public static final String ANIMAL_TU = "\u5154";
	
	public static final String ANIMAL_LONG = "\u9f8d";
	
	public static final String ANIMAL_SHE = "\u86c7";
	
	public static final String ANIMAL_MA = "\u99ac";
	
	public static final String ANIMAL_YANG = "\u7f8a";
	
	public static final String ANIMAL_HOU = "\u7334";
	
	public static final String ANIMAL_JI = "\u96de";
	
	public static final String ANIMAL_GOU = "\u72d7";
	
	public static final String ANIMAL_ZHU = "\u8c6c";
	
	// 上午
	public static final String PERIOD_FORENOON = "\u4e0a\u5348";
	
	// 下午
	public static final String PERIOD_AFTERNOON = "\u4e0b\u5348";
	
	// 晚上
	public static final String PERIOD_NIGHT = "\u665a\u4e0a";
	
	// 未定
	public static final String PERIOD_UNKNOW = "\u672a\u5b9a";
	
	public static final String FIRST_DATE_2012 = "2012/01/23";
	
	public static final String[] FIRST_DATE_CLASH = new String[] { TIAN_GAN_DING, DI_ZHI_CHOU };
	
	public static final String[] CLASH = new String[] {
			"", 
			"\u75c5\u7b26", 
			"", 
			"\u5e74\u715e", 
			"", 
			"\u5929\u5384", 
			"\u516d\u6c96\u6b72\u7834", 
			"\u6b7b\u7b26", 
			"", 
			"\u5e74\u5211", 
			"\u55aa\u9580", 
			"", 
			"" };
	
}
