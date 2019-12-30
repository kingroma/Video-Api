package com.mydefault.app.common.layout.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layout/*")
public class LayoutController {
	@RequestMapping("/header.do")
	public String header(HttpServletRequest request, ModelMap model) throws Exception {
		return "common/layout/header";
	}
	
	@RequestMapping("/topMenu.do")
	public String topMenuList(HttpServletRequest request, ModelMap model) throws Exception {
		return "common/layout/topMenu";
	}
	
	@RequestMapping("/leftMenu.do")
	public String leftMenuList(HttpServletRequest request, ModelMap model) throws Exception {
		return "common/layout/leftMenu";
	}
	
	@RequestMapping("/footer.do")
	public String incFooter(HttpServletRequest request, ModelMap model) throws Exception {	
		return "common/layout/footer";
	}
}
