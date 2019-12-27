package com.mydefault.app.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

 
public class DateUtil  {

	/**
     * 17자리의TIMESTAMP값을 구하는 기능 
     * @return Timestamp 값
     * @exception Exception  
     */
    public static String getTimeStamp() { 
		String pattern = "yyyyMMddhhmmssSSS";  // 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		return getNow(pattern);
    }
    
    /**
     * 현재 날짜정보를 얻는다.
     * @return String 'yyyy-MM-dd HH:mm'형태의  시간
     */
    public static String getNow() {
    	return getNow("yyyy-MM-dd HH:mm");
	}
    /**
     * 현재 날짜정보를 얻는다.
     * @return String 'yyyy-MM-dd'형태의  시간
     */
    public static String getNowDate() {
    	return getNow("yyyy-MM-dd");
	}
    
    /**
     * 현재 날짜정보를 얻는다.
     * @return String 형태 지정
     */
    public static String getNow(String formatStr) {
		Date now = new Date(System.currentTimeMillis()); 
		SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr, Locale.getDefault(Locale.Category.FORMAT));
		return simpledateformat.format(now);
	}
    
    /**
     * format에 맞는 날짜정보를 얻는다.
     * @return String 형태 지정
     */
    public static String getDateFormat(String dateStr, String formatStr) throws ParseException {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault(Locale.Category.FORMAT));
    	Date now = formatter.parse(dateStr);
    	SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr, Locale.getDefault(Locale.Category.FORMAT));
    	return simpledateformat.format(now);
    }
        
    public static String getDateFormat(Date dateStr, String iformat) throws ParseException {
    	String formatStr = iformat;
    	if(formatStr == null || "".equals(formatStr)){
    		formatStr = "yyyy-MM-dd HH:mm";
    	}
    	SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr, Locale.getDefault(Locale.Category.FORMAT));
    	return simpledateformat.format(dateStr);
    }
    
    public static Date dateFormatCheck(String date, String format) {
        SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
        dateFormatParser.setLenient(false);
        try {
            ;
            return dateFormatParser.parse(date);
        } catch (Exception Ex) {
            return null ;
        }
    }
	    
}