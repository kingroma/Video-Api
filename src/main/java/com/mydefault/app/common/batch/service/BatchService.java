package com.mydefault.app.common.batch.service;

import java.util.List;

import com.mydefault.app.common.batch.serviceimpl.BatchMapper;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.service.GenericService;

public interface BatchService extends GenericService<BatchVO,BatchMapper>{
	public List<MyMap> viewList(BatchVO entity);
}
