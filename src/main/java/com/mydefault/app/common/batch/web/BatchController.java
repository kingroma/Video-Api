package com.mydefault.app.common.batch.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydefault.app.common.batch.service.BatchService;
import com.mydefault.app.common.batch.service.BatchVO;
import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/batch/*")
public class BatchController extends GenericController<BatchVO,BatchService> {
	protected BatchController () {
		super(BatchVO.class,BatchService.class);
	}
	
	
	@RequestMapping("/view.do")
	public String view(@ModelAttribute BatchVO entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");
		
		try {
			entity = service.view(entity);
			List<?> list = service.viewList(entity);
			model.addAttribute("list", list);
			
			entity = addViewReference(entity, model);
			setModelEntity(model, entity, getNameVO());
		} catch (Exception e) { 
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		return viewMapper(GenericCode.VIEW);
	}
		
}
