package com.mydefault.app.contents.service;

import java.util.List;
import java.util.Map;

import com.mydefault.app.contents.serviceimpl.ContentsMapper;
import com.mydefault.app.generic.service.GenericService;

public interface ContentsService extends GenericService<ContentsVO,ContentsMapper>{
	
	public List<?> listVideo(ContentsVO entity);
	
	public boolean insertVideo(ContentsVO entity);
	
	public boolean deleteVideo(ContentsVO entity);
	
	public List<?> mainContents();
	
	public List<Map<String,Object>> apiMainVideoList();
	
}
