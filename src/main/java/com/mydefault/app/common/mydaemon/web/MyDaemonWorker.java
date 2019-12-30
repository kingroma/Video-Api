package com.mydefault.app.common.mydaemon.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;

public class MyDaemonWorker extends Thread{
	private MyDaemonVO vo = null ; 
	
	private MyDaemonExecutor myDaemonExecutor = null ; 
	
	private boolean runnable = true ;
	
	private String intervalAt = "" ; 
	
	private long minute = 0 ;
	
	private Date lastWork = null ; 
	
	private long minToSec = 60 ;
	
	private long sToMs = 1000;
	
	private long oneDay = 24 * 60 * 60 * sToMs ; 
	
	private SimpleDateFormat sdf = null ; 
	
	public MyDaemonWorker ( MyDaemonVO vo , ApplicationContext applicationContext ) { 
		this.vo = vo ; 
		this.myDaemonExecutor = new MyDaemonExecutor(vo,applicationContext);
	}
	
	@Override
	public void run() {
		if ( vo != null ) {
			lastWork = new Date() ;
			intervalAt = vo.getIntervalAt() ;
			minute = Long.parseLong(vo.getMinute()) ;
			
			if ( "Y".equalsIgnoreCase(intervalAt) ) {
				interverWorker();
			}
			else {
				everydayWorker();
			}
		}
	}
	
	// 분마다 
	private void interverWorker() {
		while ( runnable ) {
			try {
				Date current = new Date();
				
				if ( lastWork.getTime() + ( minute * minToSec * sToMs ) <= current.getTime() ) {
					lastWork = current ;
					try {
						myDaemonExecutor.run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// 1 Sec Interval
				Thread.sleep(1 * sToMs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 하루에 한번
	private void everydayWorker() {
		try {
			lastWork = firstDoDate ( minute ) ; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while ( runnable ) {
			try {
				Date current = new Date();
				
				if ( lastWork.getTime() <= current.getTime() ) {
					lastWork = new Date ( lastWork.getTime()  + oneDay );
					try {
						myDaemonExecutor.run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// 1 Sec Interval
				Thread.sleep(1 * sToMs);
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	public boolean isRunnable() {
		return runnable;
	}

	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}
}
