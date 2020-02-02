package com.mydefault.app.video.service;

import com.mydefault.app.common.util.CommonVO;

public class VideoSuperVO extends CommonVO{
	private static final long serialVersionUID = 2L;
	
	private String videoSuperId = "";
	
	private String videoSuperNm = "";
	
	private String videoSuperExplain = "";
	
	private String videoSuperActor = "";
	
	private String videoSuperGenre = "";
	
	private String videoSuperMainImage = "";

	private String useAt = "";
	
	private String delAt = "";

	public String getVideoSuperId() {
		return videoSuperId;
	}

	public void setVideoSuperId(String videoSuperId) {
		this.videoSuperId = videoSuperId;
	}

	public String getVideoSuperNm() {
		return videoSuperNm;
	}

	public void setVideoSuperNm(String videoSuperNm) {
		this.videoSuperNm = videoSuperNm;
	}

	public String getVideoSuperExplain() {
		return videoSuperExplain;
	}

	public void setVideoSuperExplain(String videoSuperExplain) {
		this.videoSuperExplain = videoSuperExplain;
	}

	public String getVideoSuperActor() {
		return videoSuperActor;
	}

	public void setVideoSuperActor(String videoSuperActor) {
		this.videoSuperActor = videoSuperActor;
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

	public String getVideoSuperGenre() {
		return videoSuperGenre;
	}

	public void setVideoSuperGenre(String videoSuperGenre) {
		this.videoSuperGenre = videoSuperGenre;
	}

	public String getVideoSuperMainImage() {
		return videoSuperMainImage;
	}

	public void setVideoSuperMainImage(String videoSuperMainImage) {
		this.videoSuperMainImage = videoSuperMainImage;
	}


}
