package com.mydefault.app.common.dashboard.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydefault.app.common.dashboard.service.DashboardService;
import com.mydefault.app.common.dashboard.service.DashboardVO;
import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.util.DateUtil;
import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/dashboard/*")
public class DashboardController extends GenericController<DashboardVO,DashboardService>{

	protected static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Resource
	public MyDaemonService myDaemonService;
	
	protected DashboardController(){
		super(DashboardVO.class,DashboardService.class);
	}
	
	@Override
	@RequestMapping({"/list.do","/search.do"})
	public String list(@ModelAttribute DashboardVO entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");	
		
		// entity 초기화
		if(retrieveUrlSub(request).indexOf("list") > -1){
			entity = addReference(entity, model);
		}

		try {
			List<?> myDaemonList = myDaemonService.list(new MyDaemonVO());
			model.addAttribute("myDaemonList", myDaemonList);
			
			int intervalCount = 0 ; 
			int everydayCount = 0 ; 
			
			if ( myDaemonList!=null ){
				List<MyDaemonVO> mdl = (List<MyDaemonVO>)myDaemonList;
				
				for ( MyDaemonVO vo : mdl ){
					if ( "Y".equalsIgnoreCase(vo.getIntervalAt()) && "Y".equalsIgnoreCase(vo.getUseAt())) {
						intervalCount++ ;
					}else if ( "N".equalsIgnoreCase(vo.getIntervalAt()) && "Y".equalsIgnoreCase(vo.getUseAt())) {
						everydayCount++ ;
					}
				}
			}
			
			model.addAttribute("intervalCount",intervalCount);
			model.addAttribute("everydayCount",everydayCount);
			
			
			MyMap monthMap = myDaemonService.dashboardMonth();
			MyMap sixMonthMap = myDaemonService.dashboardSixMonth();
			
			model.addAttribute("monthMap",monthMap);
			model.addAttribute("sixMonthMap",sixMonthMap);
			
			SimpleDateFormat sdfMM = new SimpleDateFormat("MM"); 
			model.addAttribute("mm",sdfMM.format(new Date()));
			
		} catch (Exception e) {
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		setModelEntity(model, entity, getNameVO());
		return viewMapper(GenericCode.LIST);
	}
}
