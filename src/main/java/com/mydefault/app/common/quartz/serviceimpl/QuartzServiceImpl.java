package com.mydefault.app.common.quartz.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mydefault.app.common.quartz.service.QuartzService;
import com.mydefault.app.common.quartz.service.QuartzVO;

@Service
public class QuartzServiceImpl implements QuartzService{
	@Resource
	private QuartzMapper quartzMapper;
	
	public List<QuartzVO> selectQuartzList() throws Exception {
		return quartzMapper.selectQuartzList();
	}
	
	public void quartzResult(QuartzVO entity) throws Exception {
		quartzMapper.quartzResult(entity);
	}
	
	public void noRollbackInsert(QuartzVO entity) throws Exception{
		quartzMapper.quartzResult(entity);
	}
	
}
