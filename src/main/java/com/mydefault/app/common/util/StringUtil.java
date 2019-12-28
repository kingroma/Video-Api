package com.mydefault.app.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class); 
	
	private static final int BYTE_KR = 3;
	
	public static String isString(Object object) {
        String string = ""; 
        if (object != null) {
            string = object.toString().trim();
        }
        return string;
    }
	
	public static Map<String, String> stringToMap(String params){
		return stringToMap(params, "&", "=");		
	}
	
	public static Map<String, String> stringToMap(String params, String token, String sprtr){
		Map<String, String> tMap = new HashMap<String, String>();
		String tToken = ( !"".equals(isString(token)) )?token:"&";
		String tSprtr = ( !"".equals(isString(sprtr)) )?sprtr:"=";		
		if(!"".equals(isString(params))){
			String[] strArr = params.split(tToken);
			if(strArr != null ){
				for (int i = 0; i < strArr.length; i++){ 
					String[] param = strArr[i].split(tSprtr, 2);
					if(param != null && param.length > 1){
						tMap.put(param[0], param[1]);
					}else if(param != null){
						tMap.put(param[0], "");
					}
				}	
			}
		}
		return tMap;		
	}
	
	public static String mapReplaceString(String changeCn, Map<String, String> changeMap){
		String temp = isString(changeCn);
		if(!"".equals(temp) && changeMap != null){
			for(Map.Entry<String, String> entry : changeMap.entrySet()) {
				temp = temp.replaceAll("(?i)#"+entry.getKey()+"#",  entry.getValue());
			}
		}
		return temp;
	}
	
	public static String getPrintStackTraceString(Exception e){
		if(e == null) return "";
		try{
			logger.error(e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		   	return errors.toString();
		} catch(RuntimeException ebe){
			return "";
		} catch (Exception e1) {
			 return "";
		}
	}
	
	public static String getPrintStackTraceString(Exception e, int iMaxByte){
		if(e == null) return "";
		try{
		   	return getStringByte(getPrintStackTraceString(e), iMaxByte);
		} catch(RuntimeException ebe){
			return "";
		} catch (Exception e1) {
			 return "";
		}
	}
	
	public static String getStringByte(String str, int iMaxByte){
		int strByte = 0;
		int endIndex = 0;
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			// 한글 아닌경우
			if (Character.getType(charArray[i]) != 5) {
				strByte = strByte + 1;
			}else{
				strByte = strByte + BYTE_KR; 
			}
			if(strByte > iMaxByte ){
	    		 break;
	    	}else{
	    		endIndex++;
	    	}
		} 
		return str.substring(0, endIndex);
	} 
	
	public static void exceptionMsg(Class<?> classNm, Exception e){		
		exceptionMsg(classNm, null, e);
	}
	public static void exceptionMsg(Exception e){		
		exceptionMsg(null, e);
	}
		
		
	public static void exceptionMsg(Class<?> classNm, String errorMsg){		
		exceptionMsg(classNm, errorMsg, null);
	}	

	public static void exceptionMsg(Class<?> classNm, String errorMsg, Exception e){		
		logger.error(e.getMessage());
		
		if(classNm != null){
			logger.error("Class Nm : "+classNm.getName());
		} 		
		if(errorMsg != null){
			logger.error("errorMsg : " +errorMsg );
		} 		
		if(e != null){
			logger.error("Exception : "+e);
		} 
	}
	
	public static String getExceptionMsg(Class<?> classNm, Exception e, String message){
		logger.error(e.getMessage());
		String extrcMsg = "";
		if(e.getMessage() != null && e.getMessage().startsWith(GenericCode.ERROR_CD)){
			extrcMsg = e.getMessage() + GenericCode.ERROR_CD;	
		}		
		if(!"".equals(extrcMsg)){
			message = ("".equals(message))?extrcMsg:message+"<br/>"+extrcMsg;
		}
		StringUtil.exceptionMsg(classNm, "message : "+message, e);
		return message;
	}
	
	public static String trim(String str) {
		if ( str != null  ) {
			str = str.trim();
		}else {
			str = "";
		}
		return str;
	}
	
	public static String firstLower(String str) {
    	return (!isNull(str))?(str.substring(0, 1).toLowerCase()+str.substring(1)):str;
	}
	
	public static boolean isNull(String str) {
		boolean ret = true ; 
		
		if ( str != null || !str.isEmpty())
			ret = false ; 
		
		return ret ; 
	}
	
	private String toProperCase(String s, boolean isCapital) {

		String rtnValue = "";

		if (isCapital) {
			rtnValue = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		} else {
			rtnValue = s.toLowerCase();
		}
		return rtnValue;
	}
	
	private String toCamelCase(String s) {
		String[] parts = s.split("_");
		StringBuilder camelCaseString = new StringBuilder();

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			camelCaseString.append(toProperCase(part, (i != 0 ? true : false)));
		}

		return camelCaseString.toString();
	}

}

