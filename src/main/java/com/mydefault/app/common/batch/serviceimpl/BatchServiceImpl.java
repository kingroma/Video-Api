package com.mydefault.app.common.batch.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mydefault.app.common.batch.service.BatchService;
import com.mydefault.app.common.batch.service.BatchVO;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class BatchServiceImpl extends GenericServiceImpl<BatchVO,BatchMapper> implements BatchService{
	
	public BatchServiceImpl() {
		super(BatchMapper.class);
	}
	
	public List<MyMap> viewList(BatchVO entity){
		return mapper.viewList(entity);
	}
}
