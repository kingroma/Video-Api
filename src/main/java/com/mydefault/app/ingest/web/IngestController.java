package com.mydefault.app.ingest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydefault.app.generic.web.GenericController;
import com.mydefault.app.ingest.service.IngestService;
import com.mydefault.app.ingest.service.IngestVO;

@Controller
@RequestMapping("/ingest/*")
public class IngestController extends GenericController<IngestVO,IngestService>{
	protected static final Logger logger = LoggerFactory.getLogger(IngestController.class);
	
	protected IngestController(){
		super(IngestVO.class,IngestService.class);
	}
}
