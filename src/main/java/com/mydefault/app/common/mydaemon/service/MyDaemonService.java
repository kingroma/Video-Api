package com.mydefault.app.common.mydaemon.service;

import java.util.List;

import com.mydefault.app.common.mydaemon.serviceimpl.MyDaemonMapper;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.service.GenericService;

public interface MyDaemonService extends GenericService<MyDaemonVO,MyDaemonMapper>{
	public int insertBatchLog(MyDaemonVO entity) throws Exception ;
	
	public int updateBatchLog(MyDaemonVO entity) throws Exception ;
	
	public MyMap dashboardMonth() throws Exception ;
	
	public MyMap dashboardSixMonth() throws Exception ;
}
