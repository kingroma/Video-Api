package com.mydefault.app.user.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.web.GenericController;
import com.mydefault.app.user.service.UserService;
import com.mydefault.app.user.service.UserVO;

@Controller
@RequestMapping("/user/*")
public class UserController extends GenericController<UserVO,UserService> {
	protected static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	protected UserController(){
		super(UserVO.class,UserService.class);
	}
	
	@RequestMapping(value = "/getUser.do"  )
	public ModelAndView getUser( @ModelAttribute UserVO entity, HttpServletRequest request, ModelMap model) throws Exception {
		
		try {
			System.out.println(entity.getUsername());
			System.out.println(entity.getPassword());
			UserVO vo = service.getUser(entity);
			if ( vo == null ) { 
				vo = new UserVO();
			}
			model.clear();
			model.addAttribute(vo);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(),e);
		}
		
		return new ModelAndView("jsonView",model);
	}
	
	@RequestMapping("/ajaxInsert.do") 
	public ModelAndView ajaxInsert(@ModelAttribute UserVO entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");			
		setModelEntity(model, entity, "ivo");
		
    	try {
			System.out.println(entity.getUsername());
			System.out.println(entity.getPassword());
			UserVO vo = service.getUser(entity);
			if ( vo == null ) { 
				vo = new UserVO();
			}
			model.clear();
			model.addAttribute(vo);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(),e);
		}	
		model.addAttribute(GenericCode.RESULT_CD, resultCd);
		model.addAttribute(GenericCode.MESSAGE, message);
		return new ModelAndView("jsonView", model);
	}
	
}
