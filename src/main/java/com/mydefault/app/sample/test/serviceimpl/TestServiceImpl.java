package com.mydefault.app.sample.test.serviceimpl;

import org.springframework.stereotype.Service;

import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;
import com.mydefault.app.sample.test.service.TestService;
import com.mydefault.app.sample.test.service.TestVO;

@Service
public class TestServiceImpl extends GenericServiceImpl<TestVO,TestMapper> implements TestService {
	
	public TestServiceImpl() {
		super(TestMapper.class);
	}
}
