package com.mydefault.app.common.mydaemon.service;

import com.mydefault.app.common.util.CommonVO;

public class MyDaemonVO extends CommonVO{

	private static final long serialVersionUID = 1L;
	
	private String daemonId = "";
	
	private String daemonNm = "";
	
	private String daemonText = "";
	
	private String controllerNm = "";
	
	private String intervalAt = ""; // Y N
	
	private String minute = ""; 
	
	private String bgnde = ""; // YYYYMMDD
	
	private String endde = ""; // YYYYMMDD

	public String getDaemonId() {
		return daemonId;
	}

	public void setDaemonId(String daemonId) {
		this.daemonId = daemonId;
	}

	public String getDaemonNm() {
		return daemonNm;
	}

	public void setDaemonNm(String daemonNm) {
		this.daemonNm = daemonNm;
	}

	public String getDaemonText() {
		return daemonText;
	}

	public void setDaemonText(String daemonText) {
		this.daemonText = daemonText;
	}

	public String getControllerNm() {
		return controllerNm;
	}

	public void setControllerNm(String controllerNm) {
		this.controllerNm = controllerNm;
	}

	public String getIntervalAt() {
		return intervalAt;
	}

	public void setIntervalAt(String intervalAt) {
		this.intervalAt = intervalAt;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getBgnde() {
		return bgnde;
	}

	public void setBgnde(String bgnde) {
		this.bgnde = bgnde;
	}

	public String getEndde() {
		return endde;
	}

	public void setEndde(String endde) {
		this.endde = endde;
	}

	
	
}
