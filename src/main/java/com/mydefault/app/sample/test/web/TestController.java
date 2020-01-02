package com.mydefault.app.sample.test.web;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydefault.app.common.mydaemon.service.MyDaemonVO;
import com.mydefault.app.common.mydaemon.web.MyDaemonController;
import com.mydefault.app.generic.web.GenericController;
import com.mydefault.app.sample.test.service.TestService;
import com.mydefault.app.sample.test.service.TestVO;

/**
 * CREATE TABLE TB_CO_TEST( 
 * 		UUID VARCHAR(30) , 
 * 		TEXT VARCHAR(1000) , 
 * 		PRIMARY KEY (UUID) 
 * ) ;
 */
@Controller
@RequestMapping("/sample/test/*")
public class TestController extends GenericController<TestVO,TestService>{
	protected static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	protected TestController() {
		super(TestVO.class,TestService.class);
	}
	
	// 배치 실행
	public void execute(MyDaemonVO vo) throws Exception {
		logger.info("Start execute" + vo.getDaemonId());
		logger.info("param : " + vo.getDaemonParam());
		logger.info("sortsn : " + vo.getSortSn());
	}

}
