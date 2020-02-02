package com.mydefault.app.video.serviceimpl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;
import com.mydefault.app.video.service.VideoSuperService;
import com.mydefault.app.video.service.VideoSuperVO;

@Service
public class VideoSuperServiceImpl extends GenericServiceImpl<VideoSuperVO,VideoSuperMapper> implements VideoSuperService{
	public VideoSuperServiceImpl(){
		super(VideoSuperMapper.class);
	}
	
	@Override
	public VideoSuperVO insert(VideoSuperVO entity, HttpServletRequest request) throws Exception {
		if ( entity != null ){
			
			if ( entity.getVideoSuperId() == null || entity.getVideoSuperId().isEmpty() ){
				entity.setVideoSuperId(mapper.getNewVideoSuperId()); 
			}
			
			mapper.insert(entity);
		}
		return entity;
	}
}
