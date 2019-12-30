package com.mydefault.app.common.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
	protected static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	/**
	 * Tiles 때문에 여러번 불림 참고 
	 * */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		String url = request.getRequestURI();
		logger.info(url);
		logger.info("MyInterCeptor Do SomeThing ");
		
		setModelAndView(mav);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	} 
	
	public ModelAndView setModelAndView(ModelAndView mav) throws Exception {
		if(mav!=null) {
		}
		return mav;
	}
}
