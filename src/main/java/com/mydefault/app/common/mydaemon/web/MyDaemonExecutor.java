package com.mydefault.app.common.mydaemon.web;

import java.util.Map;

import org.springframework.context.ApplicationContext;

public class MyDaemonExecutor extends Thread{
	
	private ApplicationContext applicationContext = null ; 
	
	private Object obj = null ; 
	
	private String controllerNm = null ; 
	
	public MyDaemonExecutor(String controllerNm , ApplicationContext applicationContext) {
		this.controllerNm = controllerNm ; 
		this.applicationContext = applicationContext ; 
	}
	
	public void init ()  {
		
		
		Object obj = applicationContext.getBean(controllerNm);
		Class<?>[] paramTypes = {Map.class}; 					 
		// Object[] paramObjs 	  = {tMap}; 
		// obj.getClass().getDeclaredMethod("execute", paramTypes).invoke(obj, paramObjs);
	}
	
	@Override
	public void run() {
		
	}
}
