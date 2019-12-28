package com.mydefault.app.common.mydaemon.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/daemon/*")
public class MyDaemonController extends GenericController<MyDaemonVO,MyDaemonService> { // implements ApplicationContextAware, InitializingBean, DisposableBean{
	
	private List<MyDaemonWorker> daemonList = null ; 
	
	private ApplicationContext applicationContext;
	
	protected MyDaemonController() {
		super(MyDaemonVO.class,MyDaemonService.class);
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
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
			System.out.println(service);
			List<?> list = service.list(new MyDaemonVO());
			
			if ( list != null ) {
				List<MyDaemonVO> l = (List<MyDaemonVO>)service.list(new MyDaemonVO());
				for ( MyDaemonVO vo : l ) {
					MyDaemonWorker mdw = new MyDaemonWorker(vo,this.applicationContext);
					daemonList.add(mdw);
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
