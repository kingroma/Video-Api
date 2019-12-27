package com.mydefault.app.sample.test.service;

import com.mydefault.app.common.util.CommonVO;

public class TestVO extends CommonVO{

	private static final long serialVersionUID = 2L;
	
	private String uuid = "";
	
	private String text = "";

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
