package com.mydefault.app.common.mydaemon.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;

import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;

public class MyDaemonWorker extends Thread{
	public MyDaemonService myDaemonService;
	
	private MyDaemonVO vo = null ; 
	
	private MyDaemonExecutor myDaemonExecutor = null ; 
	
	private boolean runnable = true ;
	
	private String intervalAt = "" ; 
	
	private long minute = 0 ;
	
	private Date lastWork = null ; 
	
	private long minToSec = 60 ;
	
	private long sToMs = 1000;
	
	private long oneDay = 24 * 60 * 60 * sToMs ; 
	
	private int bgndeInt = -1 ;
	
	private int enddeInt = -1 ;
	
	private SimpleDateFormat sdfyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmm");
	private SimpleDateFormat sdfHHmm = new SimpleDateFormat("HHmm");
	private SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public MyDaemonWorker ( MyDaemonVO vo , ApplicationContext applicationContext , MyDaemonService myDaemonService ) { 
		this.vo = vo ; 
		this.myDaemonExecutor = new MyDaemonExecutor(vo,applicationContext);
		this.myDaemonService = myDaemonService ;
		
		try {
			bgndeInt = Integer.parseInt(vo.getBgnde());
			enddeInt = Integer.parseInt(vo.getEndde());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if ( vo != null ) {
			lastWork = new Date() ;
			intervalAt = vo.getIntervalAt() ;
			minute = Long.parseLong(vo.getMinute()) ;
			
			if ( "Y".equalsIgnoreCase(intervalAt) && minute > 0 ) {
				interverWorker();
			}
			else if ( "N".equalsIgnoreCase(intervalAt) && minute > 0) {
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
					if (canWork(current)){
						try {
							myDaemonExecutor.run();
							insertLog("Y","");
						} catch (Exception e) {
							insertLog("Y",e.getMessage());
							e.printStackTrace();
						}
					}else {
						runnable = false; 
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
					if (canWork(current)){
						try {
							myDaemonExecutor.run();
							insertLog("Y","");
						} catch (Exception e) {
							insertLog("N",e.getMessage());
							e.printStackTrace();
						}
					}else {
						runnable = false;
					}
				}
				
				// 1 Sec Interval
				Thread.sleep(1 * sToMs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean canWork(Date current){
		boolean ret = false ;
		
		try {
			int c = Integer.parseInt(sdfyyyyMMdd.format(current));
			
			if ( bgndeInt <= c && c <= enddeInt ){
				ret = true ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret ; 
	}
	
	private Date firstDoDate(long minute){
		Date current = new Date();
		
		long hour = minute / 60 ;
		long min = minute % 60  ; 
		
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

	private void insertLog(String succAt, String message){
		try {
			vo.setSuccAt(succAt);
			vo.setMessage(message);
			myDaemonService.insertBatchLog(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRunnable() {
		return runnable;
	}

	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}

	public MyDaemonVO getVo() {
		return vo;
	}

	public void setVo(MyDaemonVO vo) {
		this.vo = vo;
	}
	
	
}
