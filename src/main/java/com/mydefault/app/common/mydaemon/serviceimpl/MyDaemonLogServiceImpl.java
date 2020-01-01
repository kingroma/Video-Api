package com.mydefault.app.common.mydaemon.serviceimpl;


import org.springframework.stereotype.Service;

import com.mydefault.app.common.mydaemon.service.MyDaemonLogService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class MyDaemonLogServiceImpl extends GenericServiceImpl<MyDaemonVO,MyDaemonLogMapper> implements MyDaemonLogService {

	public MyDaemonLogServiceImpl() {
		super(MyDaemonLogMapper.class);
	}
}
