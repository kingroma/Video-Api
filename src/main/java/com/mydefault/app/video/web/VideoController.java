package com.mydefault.app.video.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mydefault.app.generic.web.GenericController;
import com.mydefault.app.video.service.VideoService;
import com.mydefault.app.video.service.VideoVO;


@Controller
@SessionAttributes(types = VideoVO.class)
@RequestMapping("/video/*")
public class VideoController extends GenericController<VideoVO,VideoService>{
	protected static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	protected VideoController(){
		super(VideoVO.class,VideoService.class);
	}
}
