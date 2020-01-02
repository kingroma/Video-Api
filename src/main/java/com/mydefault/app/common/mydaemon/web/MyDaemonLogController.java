package com.mydefault.app.common.mydaemon.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mydefault.app.common.mydaemon.service.MyDaemonLogService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.util.DateUtil;
import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/mydaemonlog/*")
@SessionAttributes(types=MyDaemonVO.class)
public class MyDaemonLogController extends GenericController<MyDaemonVO,MyDaemonLogService> {
	protected static final Logger logger = LoggerFactory.getLogger(MyDaemonLogController.class);
	
	protected MyDaemonLogController() {
		super(MyDaemonVO.class,MyDaemonLogService.class,"","MyDaemonLog");
	}
	
	@RequestMapping({"/list.do","/search.do"})
	public String list(@ModelAttribute MyDaemonVO entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");	
		
		// entity 초기화
		if(retrieveUrlSub(request).indexOf("list") > -1){
			entity = addReference(entity, model);
			entity.setSrchBgnde(DateUtil.getNowDate());
			entity.setSrchEndde(DateUtil.getNowDate());
		}

		try {
			List<?> list = service.list(entity);
			model.addAttribute("list", list);
			
		} catch (Exception e) {
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		setModelEntity(model, entity, getNameVO());
		return viewMapper(GenericCode.LIST);
	}

}
