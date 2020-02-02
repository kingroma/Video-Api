package com.mydefault.app.contents.serviceimpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.contents.service.ContentsVO;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

@Mapper
public interface ContentsMapper extends GenericMapper<ContentsVO>{
	
	public List<?> listVideo(ContentsVO entity);
	
	public boolean insertVideo(ContentsVO entity);
	
	public boolean deleteVideo(ContentsVO entity);
	
	public List<?> mainContents();
}
