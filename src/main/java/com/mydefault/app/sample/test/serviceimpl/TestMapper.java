package com.mydefault.app.sample.test.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.generic.serviceimpl.GenericMapper;
import com.mydefault.app.sample.test.service.TestVO;

@Mapper
public interface TestMapper extends GenericMapper<TestVO>{
	public String selectNow();
}
