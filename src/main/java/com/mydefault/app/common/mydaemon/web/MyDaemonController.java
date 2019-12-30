package com.mydefault.app.common.mydaemon.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.quartz.web.Quartz;
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/mydaemon/*")
public class MyDaemonController extends GenericController<MyDaemonVO,MyDaemonService> { 
	
	protected static final Logger logger = LoggerFactory.getLogger(MyDaemonController.class);
	
	private List<MyDaemonWorker> daemonList = null ; 
	
	protected MyDaemonController() {
		super(MyDaemonVO.class,MyDaemonService.class);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		daemonIniter(); 
		daemonRunner();
	}
	
	private void daemonIniter() {
		daemonList = new ArrayList<MyDaemonWorker>();
		try {
			List<?> list = service.list(new MyDaemonVO());
			
			if ( list != null ) {
				List<MyDaemonVO> l = (List<MyDaemonVO>) list ;
				for ( MyDaemonVO vo : l ) {
					MyDaemonWorker mdw = new MyDaemonWorker(vo,this.applicationContext);
					daemonList.add(mdw);
					logger.info("Add Daemon [ " + vo.getDaemonId() + " / " + vo.getDaemonNm() + " / " + vo.getControllerNm() + " ] ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void daemonRunner() {
		for ( MyDaemonWorker mdw : daemonList ) {
			mdw.start();
		}
	}

//	@Override
//	public void destroy() throws Exception {
//		
//	}
	
}
