package com.mydefault.app.common.dashboard.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.common.dashboard.service.DashboardVO;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

@Mapper
public interface DashboardMapper extends GenericMapper<DashboardVO>{

}
