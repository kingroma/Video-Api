package com.mydefault.app.common.mydaemon.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mydefault.app.common.mydaemon.service.MyDaemonService;
import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.quartz.web.Quartz;
import com.mydefault.app.common.util.CommonUtil;
import com.mydefault.app.common.util.GenericCode;
import com.mydefault.app.common.util.StringUtil;
import com.mydefault.app.generic.web.GenericController;

@Controller
@RequestMapping("/common/mydaemon/*")
@SessionAttributes(types=MyDaemonVO.class)
public class MyDaemonController extends GenericController<MyDaemonVO,MyDaemonService> { 
	
	protected static final Logger logger = LoggerFactory.getLogger(MyDaemonController.class);
	
	private List<MyDaemonWorker> daemonList = null ; 
	
	protected MyDaemonController() {
		super(MyDaemonVO.class,MyDaemonService.class);
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
			List<?> list = service.list(new MyDaemonVO());
			
			if ( list != null ) {
				List<MyDaemonVO> l = (List<MyDaemonVO>) list ;
				for ( MyDaemonVO vo : l ) {
					if ( vo != null && "Y".equalsIgnoreCase(vo.getUseAt())){
						MyDaemonWorker mdw = new MyDaemonWorker(vo,this.applicationContext, service);
						daemonList.add(mdw);
						logger.info("Add Daemon [ " + vo.getDaemonId() + " / " + vo.getDaemonNm() + " / " + vo.getControllerNm() + " ] ");
					}
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

	@RequestMapping("/insert.do") 
	public ModelAndView insert(@ModelAttribute MyDaemonVO entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");	

		
    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView(viewMapper(GenericCode.REGIST));
    	}

    	try {
			service.insert(entity, request);
			workerDeleteAdd(entity);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
    	ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd);
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.MODIFY_REDIRECT));
	}
	
	@RequestMapping("/update.do") 
	public ModelAndView update(@ModelAttribute MyDaemonVO entity, BindingResult bindingResult, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.save.fail");	
 
				
    	if (bindingResult.hasErrors()) { 
    		model.addAttribute(GenericCode.RESULT_CD, GenericCode.VALIDATE);
    		model.addAttribute(GenericCode.MESSAGE, validateMsg(bindingResult));
    		return new ModelAndView(viewMapper(GenericCode.REGIST));
    	}
    	
		try {
			service.update(entity, request);
			workerDeleteAdd(entity);
			resultCd = GenericCode.SUCCESS;
			message = myMessageSource.getMessage("common.msg.save.success");
		} catch (Exception e) {	
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
		ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd);
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.MODIFY_REDIRECT));
	}
	
	@RequestMapping("/delete.do")
	public ModelAndView delete(@ModelAttribute MyDaemonVO entity, HttpServletRequest request, ModelMap model, RedirectAttributes ra) throws Exception {	
		String resultCd = GenericCode.FAILURE;
		String message  = myMessageSource.getMessage("common.msg.delete.fail");

		try {	
			service.delete(entity, request);	
			resultCd = GenericCode.SUCCESS;
			workerDeleteAdd(entity);
			message = myMessageSource.getMessage("common.msg.delete.success");
		} catch (Exception e) {
			message = StringUtil.getExceptionMsg(this.getClass(), e, message);
		}
		ra.addFlashAttribute(GenericCode.RESULT_CD, resultCd); 
		ra.addFlashAttribute(GenericCode.MESSAGE, message);
		return CommonUtil.getRedirectView(viewMapper(GenericCode.LIST_REDIRECT));
	}
	
	private void workerDeleteAdd(MyDaemonVO myDaemonVO){
		if ( myDaemonVO != null ){
			int where = -1 ;
			MyDaemonWorker targetWorker = null ; 
			for (int i = 0 ; i < daemonList.size() ; i ++){
				MyDaemonWorker w = daemonList.get(i);
				String workerDaemonId = w.getVo().getDaemonId();
				
				if (workerDaemonId.equalsIgnoreCase(myDaemonVO.getDaemonId())){
					where = i ;
					targetWorker = w;
					break;
				}
			}
			
			if ( targetWorker != null ){
				targetWorker.setRunnable(false);
				targetWorker = null ; 
				daemonList.remove(where);
			}
			
			if ( "Y".equalsIgnoreCase(myDaemonVO.getUseAt()) && "N".equalsIgnoreCase(myDaemonVO.getDelAt()) ) {
				MyDaemonWorker newMyDaemonWork = new MyDaemonWorker(myDaemonVO,this.applicationContext, service);
				daemonList.add(newMyDaemonWork);
				newMyDaemonWork.start();
				logger.info("Add Daemon [ " + myDaemonVO.getDaemonId() + " / " + myDaemonVO.getDaemonNm() + " / " + myDaemonVO.getControllerNm() + " ] ");
			}
			
		}
	}
	
}
