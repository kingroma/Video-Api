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
	
	private ApplicationContext applicationContext = null ;
	
	private boolean runnable = true ;
	
	private String intervalAt = "" ; 
	
	private long minute = 0 ;
	
	private Date lastWork = null ; 
	
	private Date nextWork = null ; 
	
	private long minToSec = 60 ;
	
	private long sToMs = 1000;
	
	private long oneDay = 24 * 60 * 60 * sToMs ; 
	
	private int bgndeInt = -1 ;
	
	private int enddeInt = -1 ;
	
	private boolean isFinish = true ;
	
	private SimpleDateFormat sdfyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmm");
	private SimpleDateFormat sdfHHmm = new SimpleDateFormat("HHmm");
	private SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdfSsSSS = new SimpleDateFormat("ssSSS");
	
	public MyDaemonWorker ( MyDaemonVO vo , ApplicationContext applicationContext , MyDaemonService myDaemonService ) { 
		this.vo = vo ; 
		this.myDaemonExecutor = new MyDaemonExecutor(vo, applicationContext, myDaemonService); 
		this.applicationContext = applicationContext;
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
			intervalAt = vo.getIntervalAt() ;
			minute = Long.parseLong(vo.getMinute()) ;
			
			nextWork = new Date(removeSecond(new Date()).getTime() + ( minute * minToSec * sToMs ) );
			lastWork = removeSecond(new Date());
			
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
		boolean hasTerm = false ; 
		
		while ( runnable ) {
			try {
				Date current = new Date();
				if ( nextWork.getTime() <= current.getTime() ) { 
					if (canWork(current)){
						if ( isFinish ) {
							isFinish = true ; 
							try {
								if ( hasTerm ) {
									// term 이 있을경우 현재시간에서 + Interval Time
									nextWork = new Date(removeSecond(current).getTime() + ( minute * minToSec * sToMs )) ;
								}else {
									nextWork = removeSecond(new Date(nextWork.getTime() + ( minute * minToSec * sToMs ))) ;
								}
								lastWork = current;
								myDaemonExecutor = new MyDaemonExecutor(vo, applicationContext, myDaemonService);
								myDaemonExecutor.run();
								hasTerm = false ; 
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else {
							hasTerm = true ; 
						}
					}else {
						runnable = false; 
					}
					
				}
				// 1 Sec Interval
				Thread.sleep((long)(0.01 * sToMs));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 하루에 한번
	private void everydayWorker() {
		try {
			nextWork = removeSecond(firstDoDate ( minute ) ); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while ( runnable ) {
			try {
				Date current = new Date();
				
				if ( nextWork.getTime() <= current.getTime() ) {
					if (canWork(current)){
						try {
							nextWork = new Date ( nextWork.getTime()  + oneDay );
							lastWork = current ;
							myDaemonExecutor = new MyDaemonExecutor(vo, applicationContext, myDaemonService);
							myDaemonExecutor.run();
						} catch (Exception e) {
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

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Date getNextWork() {
		return nextWork;
	}

	public void setNextWork(Date nextWork) {
		this.nextWork = nextWork;
	}
	
	// s ms 삭제 
	private Date removeSecond(Date input) {
		try {
			input = new Date(input.getTime() - Long.parseLong(sdfSsSSS.format(input)) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return input ; 
	}
}
