package com.mydefault.app.video.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.generic.serviceimpl.GenericMapper;
import com.mydefault.app.video.service.VideoVO;

@Mapper
public interface VideoMapper extends GenericMapper<VideoVO>{
	public String getNewVideoSuperId();
	
	public String getNewVideoId();
}
