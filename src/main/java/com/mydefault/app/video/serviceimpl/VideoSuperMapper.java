package com.mydefault.app.video.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.generic.serviceimpl.GenericMapper;
import com.mydefault.app.video.service.VideoSuperVO;

@Mapper
public interface VideoSuperMapper extends GenericMapper<VideoSuperVO>{
	public String getNewVideoSuperId();
}
