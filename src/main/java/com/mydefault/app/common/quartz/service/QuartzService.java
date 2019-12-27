package com.mydefault.app.common.quartz.service;

import java.util.List;

public interface QuartzService {
	
	public List<QuartzVO> selectQuartzList() throws Exception;
	
	public void noRollbackInsert(QuartzVO entity) throws Exception;
	
}
