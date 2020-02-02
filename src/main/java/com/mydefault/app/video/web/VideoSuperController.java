package com.mydefault.app.video.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mydefault.app.generic.web.GenericController;
import com.mydefault.app.video.service.VideoSuperService;
import com.mydefault.app.video.service.VideoSuperVO;

@Controller
@SessionAttributes(types = VideoSuperVO.class)
@RequestMapping("/videosuper/*")
public class VideoSuperController extends GenericController<VideoSuperVO,VideoSuperService>{
	protected static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	protected VideoSuperController(){
		super(VideoSuperVO.class,VideoSuperService.class,"/video","VideoSuper");
	}
}
