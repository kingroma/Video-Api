package com.mydefault.app.common.mydaemon.serviceimpl;


import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

@Mapper
public interface MyDaemonLogMapper extends GenericMapper<MyDaemonVO>{

	
}
