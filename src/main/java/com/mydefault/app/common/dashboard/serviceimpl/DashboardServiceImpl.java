package com.mydefault.app.common.dashboard.serviceimpl;

import org.springframework.stereotype.Service;

import com.mydefault.app.common.dashboard.service.DashboardService;
import com.mydefault.app.common.dashboard.service.DashboardVO;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class DashboardServiceImpl extends GenericServiceImpl<DashboardVO,DashboardMapper> implements DashboardService{
	public DashboardServiceImpl(){
		super(DashboardMapper.class);
	}
}
