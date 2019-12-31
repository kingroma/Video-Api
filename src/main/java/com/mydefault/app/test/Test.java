package com.mydefault.app.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		try {
			String str = "2019-11-11";
			
			System.out.println(str.replaceAll("-", ""));
			// System.out.println(firstDoDate(minute));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Date firstDoDate(long minute){
		Date current = new Date();
		
		SimpleDateFormat sdfyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmm");
		SimpleDateFormat sdfHHmm = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		
		long hour = minute / 3600 ;
		long min = minute % 3600 / 60 ; 
		
		String h = ( hour > 10 ? "" + hour : "0" + hour ); 
		String m = ( min > 10 ? "" + min : "0" + min );  
		
		String format = "";
		Date ret = null ; 
		try {
			if ( Integer.parseInt(h+m) > Integer.parseInt(sdfHHmm.format(current)) ){
				format = sdfyyyyMMdd.format(current) + h + m ;
			}else { 
				Date nextDay = new Date(current.getTime() + 24 * 60 * 60 * 1000);
				
				format = sdfyyyyMMdd.format(nextDay) + h + m ;
			}
			
			ret = sdfyyyyMMddHHmmss.parse(format) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ret ; 
	}
	
	public static long nextJobTime(long minute){
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date current = new Date();
		
		long ret = 0 ; 
		
		long hour = minute / 3600 ;
		long min = minute % 3600 / 60 ;
		
		String h = ( hour > 10 ? "" + hour : "0" + hour ); 
		
		String m = ( min > 10 ? "" + min : "0" + min );  
		
		return ret ; 
	}

}
