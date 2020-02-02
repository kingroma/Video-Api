package com.mydefault.app.video.service;

public class VideoVO extends VideoSuperVO{
	private static final long serialVersionUID = 2L;
	
	private String videoSuperId = "";
	
	private String videoId = "";
	
	private String videoNm = "";
	
	private String videoExplain = "";
	
	private String videoActor = "";
	
	private String videoPlayPath = "";
	
	private String videoRealPath = "";
	
	private String useAt = "";
	
	private String delAt = "";

	public String getVideoSuperId() {
		return videoSuperId;
	}

	public void setVideoSuperId(String videoSuperId) {
		this.videoSuperId = videoSuperId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoNm() {
		return videoNm;
	}

	public void setVideoNm(String videoNm) {
		this.videoNm = videoNm;
	}

	public String getVideoExplain() {
		return videoExplain;
	}

	public void setVideoExplain(String videoExplain) {
		this.videoExplain = videoExplain;
	}

	public String getVideoActor() {
		return videoActor;
	}

	public void setVideoActor(String videoActor) {
		this.videoActor = videoActor;
	}

	public String getVideoRealPath() {
		return videoRealPath;
	}

	public void setVideoRealPath(String videoRealPath) {
		this.videoRealPath = videoRealPath;
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

	public String getVideoPlayPath() {
		return videoPlayPath;
	}

	public void setVideoPlayPath(String videoPlayPath) {
		this.videoPlayPath = videoPlayPath;
	}
	
	
}
