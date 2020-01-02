package com.mydefault.app.common.mydaemon.serviceimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

@Mapper
public interface MyDaemonMapper extends GenericMapper<MyDaemonVO>{

	public int insertBatchLog(MyDaemonVO entity) throws Exception ;
	
	public int updateBatchLog(MyDaemonVO entity) throws Exception ;
	
	public MyMap dashboardMonth() throws Exception ;
	
	public MyMap dashboardSixMonth() throws Exception ;
	
}
