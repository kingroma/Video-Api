package com.mydefault.app.ingest.service;

import com.mydefault.app.common.util.CommonVO;

public class IngestVO extends CommonVO{

	private static final long serialVersionUID = 1L;

	private String superIngestId = "";
	
	private String ingestId = "";
	
	private String ingestNm = "";
	
	private String ingestText = "";
	
	private String ingestRealPath = "";
	
	private String useAt = "";
	
	private String delAt = "";

	public String getSuperIngestId() {
		return superIngestId;
	}

	public void setSuperIngestId(String superIngestId) {
		this.superIngestId = superIngestId;
	}

	public String getIngestId() {
		return ingestId;
	}

	public void setIngestId(String ingestId) {
		this.ingestId = ingestId;
	}

	public String getIngestNm() {
		return ingestNm;
	}

	public void setIngestNm(String ingestNm) {
		this.ingestNm = ingestNm;
	}

	public String getIngestText() {
		return ingestText;
	}

	public void setIngestText(String ingestText) {
		this.ingestText = ingestText;
	}

	public String getIngestRealPath() {
		return ingestRealPath;
	}

	public void setIngestRealPath(String ingestRealPath) {
		this.ingestRealPath = ingestRealPath;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getDelAt() {
		return delAt;
	}

	public void setDelAt(String delAt) {
		this.delAt = delAt;
	}
	

	
}
