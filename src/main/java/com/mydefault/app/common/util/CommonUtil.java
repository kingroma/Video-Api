package com.mydefault.app.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

public class CommonUtil {
	
	
	private static int encKey = 12345;

	/**
	 * redirect시 post방식으로 값 가저오기
	 * ex)
	 * 		CommonUtil.getRedirectPost(request, model); 
	 * @param request - HttpServletRequest
	 * @param ModelMap - model
	 * @return ModelMap - 전달 값
	 */
	public static ModelMap getRedirectPost(HttpServletRequest request, ModelMap model) {

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);  
		String key;				
		Object value; 
		if(flashMap != null) { 
			Iterator<String> ir  = flashMap.keySet().iterator(); 
			while(ir.hasNext()){
				key  = (String)ir.next();				
				value = flashMap.get(key);
				model.addAttribute(key, value); 
			}  
		}  
		return model;  
	}
	
	public static ModelAndView getRedirectView(String url) {
		RedirectView rv = new RedirectView(url, true);
		rv.setExposeModelAttributes(false);
		return new ModelAndView(rv);
	}
	
	public static String getClientIp(HttpServletRequest request) {
		String clientIp = request.getHeader("X-Real-IP");
		if(clientIp == null){
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if (clientIp == null) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIp == null) {
			clientIp = request.getHeader("X-Forwarded-For");
		}
		if (clientIp == null) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

	
	/*
	 * 그룹웨어 암호화
	 * @PARAM input 암호화대상값
	 */
	public static String getGwEncrypt(String input) {
		
		String encrypt = "";
		
		String key = Long.toOctalString(encKey);//Key를 8진수로 변환
		for ( int i= 0; i < input.length() ; i++) {
			String sspwd = Long.toOctalString((int)(input.charAt(i))); //ANSI 변환후 8진수 변환
			encrypt += (Long.parseLong(sspwd))^Long.parseLong(key); //논리배타적 논리합연산
			
		}
		
		return encrypt;
	}
	
	/*
	 * 그룹웨어 복호화
	 * @PARAM input 복호화대상값
	 */
	public static  String getGwDecrypt(String input) {
		
		String decrypt = "";
		
		String key = Long.toOctalString(encKey);//key를 8진수로 변환
		for ( int i= 0; i < (input.length()/5) ; i++) {
			
			int sum = 0;
			String str = Long.toString( (Long.parseLong(key)^Long.parseLong(input.substring(i*5,i*5 +5 ))));
			for ( int j= 1; j <= str.length();j++)
			{
				String param = Integer.toString((int)Math.pow(8d, (double)(str.length()-j))); //제곱근
				//계산
				sum += Integer.parseInt(str.substring(j-1,j)) * Integer.parseInt(param);
			}
			
			decrypt += Character.toString((char)(sum)); //Ansi코드를 유니코드로 변환
			
		}
		System.out.println("===MakeDecrypt====" + decrypt );
		
		return decrypt;
	}
	
	
}