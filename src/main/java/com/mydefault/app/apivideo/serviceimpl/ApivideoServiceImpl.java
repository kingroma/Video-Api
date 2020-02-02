package com.mydefault.app.apivideo.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mydefault.app.apivideo.service.ApivideoService;
import com.mydefault.app.apivideo.service.ApivideoVO;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class ApivideoServiceImpl extends GenericServiceImpl<ApivideoVO,ApivideoMapper> implements ApivideoService{
	protected static final Logger logger = LoggerFactory.getLogger(ApivideoServiceImpl.class);
	
	public ApivideoServiceImpl(){
		super(ApivideoMapper.class);
	}
}
