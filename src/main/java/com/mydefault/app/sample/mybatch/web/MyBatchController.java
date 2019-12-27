package com.mydefault.app.sample.mybatch.web;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;

/**
 * It is [ Batch Sample ]
 */
@Controller
public class MyBatchController {

	public void execute(Map<String, String> imap) throws Exception {
		// System.out.println("MyBatchStart " + new Date());

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("MyBatchEnd " + new Date());
	}
}
