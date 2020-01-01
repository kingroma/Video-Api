package com.mydefault.app.common.util;

import java.io.Serializable;

public class CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String useAt = "";

	private String useAtNm = "";
	
	private String rownum = ""; 
	
	private String resultAck = "";
	
	private String srchBgnde = "";
	
	private String srchEndde = "";
	
	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getUseAtNm() {
		return useAtNm;
	}

	public void setUseAtNm(String useAtNm) {
		this.useAtNm = useAtNm;
	}

	public String getResultAck() {
		return resultAck;
	}

	public void setResultAck(String resultAck) {
		this.resultAck = resultAck;
	}

	public String getSrchBgnde() {
		return srchBgnde;
	}

	public void setSrchBgnde(String srchBgnde) {
		this.srchBgnde = srchBgnde;
	}

	public String getSrchEndde() {
		return srchEndde;
	}

	public void setSrchEndde(String srchEndde) {
		this.srchEndde = srchEndde;
	}
	
}
