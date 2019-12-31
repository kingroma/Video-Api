package com.mydefault.app.common.mydaemon.serviceimpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class MyDaemonServiceImpl extends GenericServiceImpl<MyDaemonVO,MyDaemonMapper> implements MyDaemonService {

	public MyDaemonServiceImpl() {
		super(MyDaemonMapper.class);
	}

	@Override
	public MyDaemonVO insert(MyDaemonVO entity, HttpServletRequest request) throws Exception {
		System.out.println(entity.getDaemonNm());
		System.out.println(entity.getControllerNm());
		System.out.println(entity.getIntervalAt());
		System.out.println(entity.getMinute());
		System.out.println(entity.getBgnde()); 
		System.out.println(entity.getEndde());
		System.out.println(entity.getUseAt());
		mapper.insert(entity);
		System.out.println(entity.getDaemonId());
		return entity;
	}
	
	@Override
	public int update(MyDaemonVO entity, HttpServletRequest request) throws Exception {
		return mapper.update(entity);	
	}
	
	@Override
	public int insertBatchLog(MyDaemonVO entity) throws Exception {
		return mapper.insertBatchLog(entity);
	}
	
	@Override
	public MyMap dashboardMonth() throws Exception {
		return mapper.dashboardMonth();
	}
	
	@Override
	public MyMap dashboardSixMonth() throws Exception {
		return mapper.dashboardSixMonth();
	}
}
