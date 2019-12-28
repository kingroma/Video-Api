package com.mydefault.app.common.mydaemon.serviceimpl;

import org.springframework.stereotype.Service;

import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class MyDaemonServiceImpl extends GenericServiceImpl<MyDaemonVO,MyDaemonMapper> implements MyDaemonService {

	public MyDaemonServiceImpl() {
		super(MyDaemonMapper.class);
	}

}
