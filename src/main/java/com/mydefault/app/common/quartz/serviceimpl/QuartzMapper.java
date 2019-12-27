package com.mydefault.app.common.quartz.serviceimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.common.quartz.service.QuartzVO;

@Mapper
public interface QuartzMapper {

	public List<QuartzVO> selectQuartzList() throws Exception;
	
	public void quartzResult(QuartzVO entity) throws Exception;
}
