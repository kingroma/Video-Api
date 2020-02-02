package com.mydefault.app.contents.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.contents.service.ContentsService;
import com.mydefault.app.contents.service.ContentsVO;
import com.mydefault.app.generic.web.GenericController;

@Controller
@SessionAttributes(types = ContentsVO.class)
@RequestMapping("/contents/*")
public class ContentsController extends GenericController<ContentsVO,ContentsService>{
	protected static final Logger logger = LoggerFactory.getLogger(ContentsController.class);
	
	protected ContentsController(){
		super(ContentsVO.class,ContentsService.class);
	}
	
	@Override
	public String modify(@ModelAttribute ContentsVO entity, HttpServletRequest request, ModelMap model) throws Exception {
		setModelEntity(model, entity, "ivo");
		
		try {
			entity = service.view(entity);
			model.addAttribute("list",service.listVideo(entity));
			
			entity = addViewReference(entity, model);
			setModelEntity(model, entity, getNameVO());
		} catch (Exception e) { 
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		return viewMapper(GenericCode.REGIST);
	}
	
	// http://localhost:8010/app/contents/api/mainVideoList.do
	@RequestMapping("/api/mainVideoList.do") 
	public ModelAndView ajaxList(@ModelAttribute ContentsVO entity, HttpServletRequest request, ModelMap model) throws Exception {
		try {
			entity.setUseAt("Y");
			List<Map<String,Object>> list = service.apiMainVideoList();
			model.clear();
			model.addAttribute("data",list);
			
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
		return new ModelAndView("jsonView", model);
	}
}
