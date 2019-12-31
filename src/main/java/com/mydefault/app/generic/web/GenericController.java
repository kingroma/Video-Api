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
	
	protected String pathBase 	= "";	// jsp 파일 경로 
	protected String filePath   = "";	// jsp 파일 경로(TILES 포함)
	protected String fileSuffix = "";	// jsp 파일 접두어	
	protected String urlBase 	= "";	// url정보
		
	protected S service;	
	protected Class<T> voClass;
	protected Class<S> serviceClass;
	protected ApplicationContext applicationContext;
	
	/** 생성자 */ 
	protected GenericController(Class<T> voClass, Class<S> serviceClass) {
		this.voClass = voClass;
		this.serviceClass = serviceClass;
		this.urlBase = retrieveUrlBase();
		this.filePath   = getFilePathStr("");
		this.fileSuffix = getFileSuffixStr("");
	}
	
	/**
     * 생성자 
     * @param voClass  - vo 클래스
     * @param serviceClass - service 클래스
     * @param filePath 	   - jsp 파일 경로
     * @param fileSuffix   - jsp 파일 접두어
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
     * 리스트 : 페이징 처리됨
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return String - 페이지 URL
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
     * @throws Exception
     */
	@RequestMapping({"/list.do","/search.do"})
	public String list(@ModelAttribute T entity, HttpServletRequest request, ModelMap model) throws Exception {		
		setModelEntity(model, entity, "ivo");	
		
		// entity 초기화
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
	 * 전체 리스트
	 * @param vo - entity VO
	 * @param request - HttpServletRequest
	 * @param model - ModelMap
	 * @return String - 페이지 URL
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
     * 상세조회 
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - 페이지 URL
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
     * 등록화면
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - 페이지 URL
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
     * 수정화면 
     * @param vo - entity VO
     * @param request - HttpServletRequest
     * @param model - ModelMap 
     * @return String - 페이지 URL
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
     * 입력 처리 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request  -HttpServletRequest
     * @param model - ModelMap
     * @return String - 페이지 URL
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
		return CommonUtil.getRedirectView(viewMapper(GenericCode.VIEW_REDIRECT));
	}
		
	/**
     * 수정 처리 
     * @param vo - entity VO
     * @param bindingResult -BindingResult
     * @param request  -HttpServletRequest
     * @param model - ModelMap
     * @return String - 페이지 URL
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
		return CommonUtil.getRedirectView(viewMapper(GenericCode.VIEW_REDIRECT));
	}
		
	/**
     * 삭제
     * @param vo - entity VO
     * @param request -HttpServletRequest
     * @param ra - RedirectAttributes
     * @param model - ModelMap 
     * @return String - 페이지 URL
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
     * ajax 리스트 : 페이징 처리됨 
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
     * ajax 전체 리스트 
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
     * ajax 상세조회
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
     * ajax 입력 처리 
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
     * ajax 수정 처리 
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
     * ajax 삭제 처리 
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
	
	/** HP Fortify 처리 : model.addAttribute(getNameVO(), entity);   model.addAttribute("ivo", entity);  대처  */
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
	 * 이동 페이지 URL 
     * @param action - 액션 페이지 번호 (Integer)
     * @return String - 페이지 URL
     * @throws Exception
     */
	public String viewMapper(int action) {		
		String view = "";
		switch(action){
			// base URL 
			case GenericCode.LIST 	    : view = this.filePath + "/" + fileSuffix + "List";   break;			
			case GenericCode.VIEW 	    : view = this.filePath + "/" + fileSuffix + "View";   break;
			case GenericCode.REGIST	    : view = this.filePath + "/" + fileSuffix + "Regist"; break;
			
			// redirect URL 
			case GenericCode.LIST_REDIRECT       : view = this.urlBase + "/list.do"      ;   break;
			case GenericCode.LIST_ALL_REDIRECT   : view = this.urlBase + "/listAll.do"   ;   break;
			case GenericCode.VIEW_REDIRECT       : view = this.urlBase + "/view.do"      ;   break;			
			case GenericCode.REGIST_REDIRECT     : view = this.urlBase + "/regist.do"    ;   break;			
			case GenericCode.MODIFY_REDIRECT     : view = this.urlBase + "/modify.do"    ;   break;			
			default : 
		}
		return view;
	}
	
	/** 
	 * 이동 페이지 URL 
     * @param action - 액션 페이지 세부 URL (String)
     * @return String - 페이지 URL
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
     * 기본 url 추출 : RequestMapping에서 추출
     * @return url  - 입력 url
     */
	private String retrieveUrlBase() {
		RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
		String url = rm.value()[0];
		return url.substring(0, url.indexOf("/*") );
	}
	
	/** request url에서 마지막 '/'뒤의 url 추출*/
	public String retrieveUrlSub(HttpServletRequest request) {
		String url = request.getRequestURI();
		return url.substring(url.lastIndexOf("/")+1);
	}
		
	/**
     * jsp파일  경로 계산 :  filePath가 없는 경우 추출된 urlBase 선언
     * @param objPath  	- 파일 경로
     * @return resultPath  - 결과 경로
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
     * jsp파일 접두어 계산 : fileSuffix가 없는 경우 voClass에서 VO를 제외한 이름으로 셋팅
     * @param objSuffix  	- 파일 접두어
     * @return resultSuffix  - 결과 접두어
     */
	private String getFileSuffixStr(Object objSuffix) {
		String resultSuffix = StringUtil.isString(objSuffix);
		
		if("".equals(resultSuffix)){
			String domainNm = StringUtil.isString(voClass.getSimpleName());
			int domainLen = domainNm.length();					
			// 2자 이상, 문자중 VO 존재하는 경우
			if ( (domainLen > 2) &&  (domainNm.toUpperCase()).matches(".*VO") ) { 
				resultSuffix = domainNm.substring(0, (domainLen-2));
			}else{
				resultSuffix = domainNm;
			}		
		}		
		return resultSuffix;		 
	}
			
	/**
	 * 오류 메시지
	 * @param bindingResult - BindingResult
	 * @param ModelMap - model
	 * @return String - 오류 메시지
	 */
	public String validateMsg(BindingResult bindingResult) {
		String errorMsg = "";		
 
		try {
			if (bindingResult.hasErrors()) { 
	    		List<FieldError> errorList = bindingResult.getFieldErrors();            
	            for(FieldError fe : errorList){
	            	// 자바스크립트에서 <br/>를 \n으로 치환(\n 직접 사용시 오류 발생)
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
     * 예외 처리된 내용에서  메시지를 추출하여 message에 추가해줌.
     * @param e - Exception
     * @param message - 메시지
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
		
	/** vo 클래의 첫 문자를 소분자로 변경 
	 * @return String  
     */
	public String getNameVO(){
		return StringUtil.firstLower(voClass.getSimpleName());
	}
}