package com.mydefault.app.common.quartz.service;

import java.io.Serializable;

public class QuartzType implements Serializable {
	private static final long serialVersionUID = 1L;
	// 실행 구분 
	public static String ALL_DAY = "매번" ; // 매번 COM_053_001 1 
	
	public static String ONE_TIME = "일회" ; // 일회 COM_053_002 2 
	
	// 실행기간
	public static String PERMANENT = "영구"; // 영구 COM_054_001 3 
	
	public static String TERM = "기간" ; // 기간  COM_054_002 4 
	
	// 실행주기 
	public static String MINUTE = "분단위" ; // 분단위 COM_055_001 5
	
	public static String HOUR = "시간단위"; // 시간단위  COM_055_002 6
	
	public static String DAY = "일단위" ; // 일단위 COM_055_003 7
	
	public static String WEEK = "주단위" ; // 주단위 COM_055_004 8
	
	public static String MONTH = "월단위" ; // 월단위 COM_055_005 9
	
	public static String YEAR = "년단위" ; // 년단위   COM_055_006 10
	
	// 수행 타입 
	public static String AUTO = "자동" ; // 자동 COM_056_001 11
	
	public static String MANUAL = "수동" ; // 수동  COM_056_002 12

	
	
	
}
