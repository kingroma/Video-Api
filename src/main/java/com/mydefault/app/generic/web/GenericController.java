package com.mydefault.app.generic.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mydefault.app.common.util.ApplicationContextUtils;
import com.mydefault.app.common.util.CommonUtil;
import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.MyMessageSource;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.service.GenericService;
import com.mydefault.app.generic.serviceimpl.GenericMapper;

public class GenericController<T, S extends GenericService<T, ? extends GenericMapper<T>>> implements ApplicationContextAware, InitializingBean {

	@Resource
    protected MyMessageSource myMessageSource;
	
	protected String pathBase 	= "";	// jsp �뙆�씪 寃쎈줈 
	protected String filePath   = "";	// jsp �뙆�씪 寃쎈줈(TILES �룷�븿)
	protected String fileSuffix = "";	// jsp �뙆�씪 �젒�몢�뼱	
	protected String urlBase 	= "";	// url�젙蹂�
		
	protected S service;	
	protected Class<T> voClass;
	protected Class<S> serviceClass;
	protected ApplicationContext applicationContext;
	
	/** �깮�꽦�옄 */ 
	protected GenericController(Class<T> voClass, Class<S> serviceClass) {
		this.voClass = voClass;
		this.serviceClass = serviceClass;
		this.urlBase = retrieveUrlBase();
		this.filePath   = getFilePathStr("");
		this.fileSuffix = getFileSuffixStr("");
	}
	
	/**
     * �깮�꽦�옄 
     * @param voClass  - vo �겢�옒�뒪
     * @param serviceClass - service �겢�옒�뒪
     * @param filePath 	   - jsp �뙆�씪 寃쎈줈
     * @param fileSuffix   - jsp �뙆�씪 �젒�몢�뼱
     */
	protected GenericController(Class<T> voClass, Class<S> serviceClass, String filePath, String fileSuffix) {
		this.voClass = voClass;
		this.serviceClass = serviceClass;
		this.urlBase = retrieveUrlBase();		 
		this.pathBase   = StringUtil.isString(filePath);
		this.filePath   = getFilePathStr(filePath);
		this.fileSuffix = getFileSuffixStr(fileSuffix);
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
		
	public void afterPropertiesSet() throws Exception {
		service = (S)ApplicationContextUtils.getBeanByType(applicationContext, serviceClass);
	}

	@ModelAttribute
	public T command() throws Exception {
		try{
			T vo = (T) voClass.newInstance();
			return vo;
		} catch(InstantiationException ie) {
			throw new Exception(ie);
		} catch(IllegalAccessException iae) {
			throw new Exception(iae);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public T addReference(T entity, ModelMap model) throws Exception {
		try{
			//entity = (T) voClass.newInstance(); // findBugs
			T vo = (T) voClass.newInstance();
			return vo;
		} catch(InstantiationException ie) {
			throw new Exception(ie);
		} catch(IllegalAccessException iae) {
			throw new Exception(iae);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public T addViewReference(T entity, ModelMap model) throws Exception {
		return entity;
	}
	
	/**
     * 由ъ뒪�듃 : �럹�씠吏� 泥섎━�맖
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return String - �럹�씠吏� URL
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
     * @throws Exception
     */
	@RequestMapping({"/list.do","/search.do"})
	public String list(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");	
		
		// entity 珥덇린�솕
		if(retrieveUrlSub(request).indexOf("list") > -1){
			entity = addReference(entity, model);
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
	
	/**
	 * �쟾泥� 由ъ뒪�듃
	 * @param vo - entity VO
	 * @param request - HttpServletRequest
	 * @param model - ModelMap
	 * @return String - �럹�씠吏� URL
	 * @throws Exception
	 */
	@RequestMapping({"/listAll.do","/searchAll.do"})
	public String listAll(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");	
		
		if(retrieveUrlSub(request).indexOf("list") > -1){
			entity = addReference(entity, model);
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
		
	/**
     * �긽�꽭議고쉶 
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	@RequestMapping("/view.do")
	public String view(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");
		
		try {
			entity = service.view(entity);
			entity = addViewReference(entity, model);
			setModelEntity(model, entity, getNameVO());
		} catch (Exception e) { 
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		return viewMapper(GenericCode.VIEW);
	}
		
	/**
     * �벑濡앺솕硫�
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	@RequestMapping("/regist.do")
	public String regist(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {
		setModelEntity(model, entity, "ivo");
		
		entity = addReference(entity, model);
		setModelEntity(model, entity, getNameVO());
		return viewMapper(GenericCode.REGIST);
	}
	
	/**
     * �닔�젙�솕硫� 
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	@RequestMapping("/modify.do")
	public String modify(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {
		setModelEntity(model, entity, "ivo");
		
		try {
			entity = service.view(entity);
			entity = addViewReference(entity, model);
			setModelEntity(model, entity, getNameVO());
		} catch (Exception e) { 
			 StringUtil.exceptionMsg(this.getClass(), e);
		}
		return viewMapper(GenericCode.REGIST);
	}
	
	
	/**
     * �엯�젰 泥섎━ 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request  -HttpServletRequest
     * @param model - ModelMap
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	@RequestMapping("/insert.do") 
	public ModelAndView insert(@ModelAttribute T entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");	

		
    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView(viewMapper(GenericCode.REGIST));
    	}

    	try {
			service.insert(entity, request);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
    	ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd);
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.MODIFY_REDIRECT));
	}
		
	/**
     * �닔�젙 泥섎━ 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request  -HttpServletRequest
     * @param model - ModelMap
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	@RequestMapping("/update.do") 
	public ModelAndView update(@ModelAttribute T entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");	
 
				
    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView(viewMapper(GenericCode.REGIST));
    	}
    	
		try {
			service.update(entity, request);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {	
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
		ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd);
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.MODIFY_REDIRECT));
	}
		
	/**
     * �궘�젣
     * @param vo - entity VO
     * @param request -HttpServletRequest
     * @param ra - RedirectAttributes
     * @param model - ModelMap 
     * @return String - �럹�씠吏� URL
     * @throws Exception
     * if (EgovDoubleSubmitHelper.checkAndSaveToken(request)) {}
     */
	@RequestMapping("/delete.do")
	public ModelAndView delete(@ModelAttribute T entity, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {	
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.delete.fail");

		try {	
			service.delete(entity, request);	
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.delete.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
		ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd); 
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.LIST_REDIRECT));
	}
	
		
	/**
     * ajax 由ъ뒪�듃 : �럹�씠吏� 泥섎━�맖 
     * @param vo - entity VO
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxList.do") 
	public ModelAndView ajaxList(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {
		try {
			List<?> list = service.list(entity);			
			model.addAttribute("list", list);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
		return new ModelAndView("jsonView", model);
	}
		
	/**
     * ajax �쟾泥� 由ъ뒪�듃 
     * @param vo - entity VO
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxListAll.do") 
	public ModelAndView ajaxListAll(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {
		try {
			List<?> list = service.list(entity);			
			model.addAttribute("list", list);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
		return new ModelAndView("jsonView", model);
	}
		
	/**
     * ajax �긽�꽭議고쉶
     * @param vo - entity VO
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxView.do") 
	public ModelAndView ajaxView(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {
		try { 			
			model.addAttribute("vo", service.view(entity));
			addViewReference(entity, model);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
		return new ModelAndView("jsonView", model);
	}
	
	/**
     * ajax �엯�젰 泥섎━ 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxInsert.do") 
	public ModelAndView ajaxInsert(@ModelAttribute T entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");			

		
    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView("jsonView", model);
    	}
    	
		try {
			service.insert(entity, request);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}		
		model.addAttribute(GenericCode.RESULT_CD, resultCd);
		model.addAttribute(GenericCode.MESSAGE, message);
		return new ModelAndView("jsonView", model);
	}
	
	/**
     * ajax �닔�젙 泥섎━ 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxUpdate.do") 
	public ModelAndView ajaxUpdate(@ModelAttribute T entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");			

    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView("jsonView", model);
    	}
    	
		try {		
			service.update(entity, request);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}		
		model.addAttribute(GenericCode.RESULT_CD, resultCd);
		model.addAttribute(GenericCode.MESSAGE, message);
		return new ModelAndView("jsonView", model);
	}
	
	/**
     * ajax �궘�젣 泥섎━ 
     * @param vo - entity VO 
     * @param request -HttpServletRequest
     * @param model - ModelMap
     * @return model - ModelAndView 
     * @throws Exception
     */
	@RequestMapping("/ajaxDelete.do") 
	public ModelAndView ajaxDelete(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {

		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.delete.fail");
		 	
		try {		
			service.delete(entity, request);	
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.delete.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
		model.addAttribute(GenericCode.RESULT_CD, resultCd);
		model.addAttribute(GenericCode.MESSAGE, message);
		return new ModelAndView("jsonView", model);
	}
	
	@RequestMapping("/ajaxDuplPk.do")
	public ModelAndView ajaxDuplPk(T entity, HttpServletRequest request,  ModelMap model) throws Exception {
		String resultCd = GenericCode.FAILURE;
		try {
			int duplchk = service.duplPk(entity);
			if(duplchk == 0){
				resultCd =  GenericCode.SUCCESS;
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute(GenericCode.RESULT_CD, resultCd);
		return new ModelAndView("jsonView", model);
	}
	
	/** HP Fortify 泥섎━ : model.addAttribute(getNameVO(), entity);   model.addAttribute("ivo", entity);  ��泥�  */
	public ModelMap setModelEntity(ModelMap model, T entity, String attributeName) throws Exception {
		String attrNm = (attributeName == null || "".equals(attributeName))?getNameVO():attributeName;
		if(entity == null){
			model.addAttribute(attrNm, addReference(entity, model));
		} else {
			model.addAttribute(attrNm, entity);
		}		
		return model;
	}
	
		
	/** 
	 * �씠�룞 �럹�씠吏� URL 
     * @param action - �븸�뀡 �럹�씠吏� 踰덊샇 (Integer)
     * @return String - �럹�씠吏� URL
     * @throws Exception
     */
	public String viewMapper(int action) {		
		String view = "";
		switch(action){
			// base URL 
			case GenericCode.LIST 	    : view = this.filePath + "/" + fileSuffix + "List";   break;			
			case GenericCode.VIEW 	    : view = this.filePath + "/" + fileSuffix + "Regist";   break;
			case GenericCode.REGIST	    : view = this.filePath + "/" + fileSuffix + "Regist"; break;
			
			// redirect URL 
			case GenericCode.LIST_REDIRECT       : view = this.urlBase + "/list.do"      ;   break;
			case GenericCode.LIST_ALL_REDIRECT   : view = this.urlBase + "/listAll.do"   ;   break;
			case GenericCode.VIEW_REDIRECT       : view = this.urlBase + "/regist.do"      ;   break;			
			case GenericCode.REGIST_REDIRECT     : view = this.urlBase + "/regist.do"    ;   break;			
			case GenericCode.MODIFY_REDIRECT     : view = this.urlBase + "/modify.do"    ;   break;			
			default : 
		}
		return view;
	}
	
	/** 
	 * �씠�룞 �럹�씠吏� URL 
     * @param action - �븸�뀡 �럹�씠吏� �꽭遺� URL (String)
     * @return String - �럹�씠吏� URL
     * @throws Exception
     * Example : return viewMapper("columnPopupList");
     * 			 return viewMapper(GenericCode.TILES_POPUP, "columnPopupList");
     * @author Hong Seok-chan
     */
	public String viewMapper(String action) {	
		return mergeMapperUrl(this.filePath, action);
	}
	
	public String viewMapper(String titleBase, String action) {		
		return mergeMapperUrl(titleBase + this.pathBase, action);
	} 
	
	public String mergeMapperUrl(String baseUrlPath, String action) {		
		String view = "";
		
		if(action == null || action.equals("")) {
			view = baseUrlPath + "/" + fileSuffix + "List"; //pathBase
		}
		else {
			view =  baseUrlPath + "/" + action;
		}
		return view;
	} 
		
	/**
     * 湲곕낯 url 異붿텧 : RequestMapping�뿉�꽌 異붿텧
     * @return url  - �엯�젰 url
     */
	private String retrieveUrlBase() {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		String url = rm.value()[0];
		return url.substring(0, url.indexOf("/*") );
	}
	
	/** request url�뿉�꽌 留덉�留� '/'�뮘�쓽 url 異붿텧*/
	public String retrieveUrlSub(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/")+1);
	}
		
	/**
     * jsp�뙆�씪  寃쎈줈 怨꾩궛 :  filePath媛� �뾾�뒗 寃쎌슦 異붿텧�맂 urlBase �꽑�뼵
     * @param objPath  	- �뙆�씪 寃쎈줈
     * @return resultPath  - 寃곌낵 寃쎈줈
     */
	private String getFilePathStr(Object objPath) {
		String resultPath = StringUtil.isString(objPath);
		
		if("".equals(resultPath)){			
			resultPath = StringUtil.isString(GenericCode.TILES_SUB+this.urlBase);
		}else{
			resultPath = GenericCode.TILES_SUB+resultPath;
		}
		return resultPath;		 
	}
		
	/**
     * jsp�뙆�씪 �젒�몢�뼱 怨꾩궛 : fileSuffix媛� �뾾�뒗 寃쎌슦 voClass�뿉�꽌 VO瑜� �젣�쇅�븳 �씠由꾩쑝濡� �뀑�똿
     * @param objSuffix  	- �뙆�씪 �젒�몢�뼱
     * @return resultSuffix  - 寃곌낵 �젒�몢�뼱
     */
	private String getFileSuffixStr(Object objSuffix) {
		String resultSuffix = StringUtil.isString(objSuffix);
		
		if("".equals(resultSuffix)){
			String domainNm = StringUtil.isString(voClass.getSimpleName());
			int domainLen = domainNm.length();					
			// 2�옄 �씠�긽, 臾몄옄以� VO 議댁옱�븯�뒗 寃쎌슦
			if ( (domainLen > 2) &&  (domainNm.toUpperCase()).matches(".*VO") ) { 
				resultSuffix = domainNm.substring(0, (domainLen-2));
			}else{
				resultSuffix = domainNm;
			}		
		}		
		return resultSuffix;		 
	}
			
	/**
	 * �삤瑜� 硫붿떆吏�
	 * @param bindingResult - BindingResult
	 * @param ModelMap - model
	 * @return String - �삤瑜� 硫붿떆吏�
	 */
	public String validateMsg(BindingResult bindingResult) {
		String errorMsg = "";		
 
		try {
			if (bindingResult.hasErrors()) { 
	    		List<FieldError> errorList = bindingResult.getFieldErrors();            
	            for(FieldError fe : errorList){
	            	// �옄諛붿뒪�겕由쏀듃�뿉�꽌 <br/>瑜� \n�쑝濡� 移섑솚(\n 吏곸젒 �궗�슜�떆 �삤瑜� 諛쒖깮)
	            	errorMsg += myMessageSource.getMessage(fe.getDefaultMessage(), fe.getArguments())+"<br/>";
	            }
	    	} 			 
		
		} catch(RuntimeException ebe){ 
			StringUtil.exceptionMsg(this.getClass(), "errorMsg : "+errorMsg, ebe);
		} catch(Exception e) {
			StringUtil.exceptionMsg(this.getClass(), "errorMsg : "+errorMsg, e);
		}
		return errorMsg;  
	}
	
	/**
     * �삁�쇅 泥섎━�맂 �궡�슜�뿉�꽌  硫붿떆吏�瑜� 異붿텧�븯�뿬 message�뿉 異붽��빐以�.
     * @param e - Exception
     * @param message - 硫붿떆吏�
     * @return String - message
     * @throws Exception
     */
	public String getExceptionMsg(Exception e, String message){
		String extrcMsg = "";
		if(e.getMessage().startsWith(GenericCode.ERROR_CD)){
			extrcMsg = e.getMessage() + GenericCode.ERROR_CD;	
		}		
		if(!"".equals(extrcMsg)){
			message = ("".equals(message))?extrcMsg:message+"<br/>"+extrcMsg;
		}
		StringUtil.exceptionMsg(this.getClass(), "message : "+message, e);
		return message;
	}
		
	/** vo �겢�옒�쓽 泥� 臾몄옄瑜� �냼遺꾩옄濡� 蹂�寃� 
	 * @return String  
     */
	public String getNameVO(){
		return StringUtil.firstLower(voClass.getSimpleName());
	}
}