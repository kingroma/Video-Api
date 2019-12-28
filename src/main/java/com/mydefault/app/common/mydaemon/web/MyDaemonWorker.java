package com.mydefault.app.common.mydaemon.web;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;

public class MyDaemonWorker extends Thread{
	private MyDaemonVO vo = null ; 
	
	private ApplicationContext applicationContext = null ; 
	
	private boolean runnable = true ;
	
	private String intervalAt = "" ; 
	
	private long minute = 0 ;
	
	private Date lastWork = null ; 
	
	private long minToSec = 60 ;
	
	private long sToMs = 1000;
	
	public MyDaemonWorker ( MyDaemonVO vo , ApplicationContext applicationContext ) { 
		this.vo = vo ; 
		this.applicationContext = applicationContext ;
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
	
	private void interverWorker() {
		while ( runnable ) {
			try {
				Date current = new Date();
				
				if ( lastWork.getTime() + ( minute * minToSec * sToMs ) < current.getTime() ) {
					lastWork = current ;
					System.out.println("hello world" + new Date());
					
				}
				Thread.sleep(1 * sToMs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void everydayWorker() {
		
	}
	
	 
}
