package com.mydefault.app.common.batch.serviceimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.common.batch.service.BatchVO;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

@Mapper
public interface BatchMapper extends GenericMapper<BatchVO>{

	public List<MyMap> viewList(BatchVO entity);
}
