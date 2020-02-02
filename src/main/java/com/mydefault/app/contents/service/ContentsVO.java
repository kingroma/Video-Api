package com.mydefault.app.contents.service;

import java.util.List;

import com.mydefault.app.common.util.CommonVO;

public class ContentsVO extends CommonVO {
	private static final long serialVersionUID = 3L;
	
	private String contentsId = "";
	
	private String contentsNm = "";
	
	private String videoSuperId = "";
	
	private String sortSn = "";
	
	private List<String> videoSuperIdArr = null ;
	
	private List<String> sortSnArr = null ; 
	
	private String useAt = "";
	
	private String delAt = "";
	
	public String getContentsId() {
		return contentsId;
	}

	public void setContentsId(String contentsId) {
		this.contentsId = contentsId;
	}

	public String getContentsNm() {
		return contentsNm;
	}

	public void setContentsNm(String contentsNm) {
		this.contentsNm = contentsNm;
	}

	public String getVideoSuperId() {
		return videoSuperId;
	}

	public void setVideoSuperId(String videoSuperId) {
		this.videoSuperId = videoSuperId;
	}

	public List<String> getVideoSuperIdArr() {
		return videoSuperIdArr;
	}

	public void setVideoSuperIdArr(List<String> videoSuperIdArr) {
		this.videoSuperIdArr = videoSuperIdArr;
	}

	public List<String> getSortSnArr() {
		return sortSnArr;
	}

	public void setSortSnArr(List<String> sortSnArr) {
		this.sortSnArr = sortSnArr;
	}

	public String getSortSn() {
		return sortSn;
	}

	public void setSortSn(String sortSn) {
		this.sortSn = sortSn;
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
