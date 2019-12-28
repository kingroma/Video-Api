package com.mydefault.app.common.mydaemon.web;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.util.StringUtil;

public class MyDaemonExecutor extends Thread{
	private static final Logger logger = LoggerFactory.getLogger(MyDaemonExecutor.class); 
	
	private ApplicationContext applicationContext = null ; 
	
	private Object obj = null ; 
	
	private MyDaemonVO vo = null ; 
	
	private String controllerNm = null ; 
	
	public MyDaemonExecutor(MyDaemonVO vo , ApplicationContext applicationContext) {
		this.vo = vo ;
		this.controllerNm = vo.getControllerNm();
		this.applicationContext = applicationContext ; 
	}
	
	@Override
	public void run() {
		try {
			logger.info("Start Daemon [ " + controllerNm + " ] " + new Date()) ;
			Object obj = applicationContext.getBean(controllerNm);
			Class<?>[] paramTypes = {vo.getClass()}; 					 
			Object[] paramObjs 	  = {vo}; 
			obj.getClass().getDeclaredMethod("execute", paramTypes).invoke(obj, paramObjs);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
