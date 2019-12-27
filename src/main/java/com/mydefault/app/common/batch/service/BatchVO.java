package com.mydefault.app.common.batch.service;

import com.mydefault.app.common.quartz.service.QuartzType;
import com.mydefault.app.common.util.CommonVO;

public class BatchVO extends CommonVO{

	private static final long serialVersionUID = 1L;
	
	private QuartzType quartzType = new QuartzType();
	
	private String batchId = "" ;
	
	private String batchNm = "" ;
	
	private String batchDc = "" ;
	
	private String jobExecut = "" ;
	
	private String jobParam = "" ;
	
	private String jobSeCdId = "" ;
	
	private String jobPdCdId = "" ;
	
	private String jobBgnde = "" ;
	
	private String jobEndde = "" ;
	
	private String jobCycleCdId = "" ;
	
	private String useAt = "" ;
	
    private String jobSn = "" ;
    
	private String jobDe = "" ;
	
	private String jobWeek = "" ;
	
	private String jobMonth = "" ;
	
	private String jobDay = "" ;
	
	private String jobHour = "" ;
	
	private String jobMinute = "" ;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBatchNm() {
		return batchNm;
	}

	public void setBatchNm(String batchNm) {
		this.batchNm = batchNm;
	}

	public String getBatchDc() {
		return batchDc;
	}

	public void setBatchDc(String batchDc) {
		this.batchDc = batchDc;
	}

	public String getJobExecut() {
		return jobExecut;
	}

	public void setJobExecut(String jobExecut) {
		this.jobExecut = jobExecut;
	}

	public String getJobParam() {
		return jobParam;
	}

	public void setJobParam(String jobParam) {
		this.jobParam = jobParam;
	}

	public String getJobSeCdId() {
		return jobSeCdId;
	}

	public void setJobSeCdId(String jobSeCdId) {
		this.jobSeCdId = jobSeCdId;
	}

	public String getJobPdCdId() {
		return jobPdCdId;
	}

	public void setJobPdCdId(String jobPdCdId) {
		this.jobPdCdId = jobPdCdId;
	}

	public String getJobBgnde() {
		return jobBgnde;
	}

	public void setJobBgnde(String jobBgnde) {
		this.jobBgnde = jobBgnde;
	}

	public String getJobEndde() {
		return jobEndde;
	}

	public void setJobEndde(String jobEndde) {
		this.jobEndde = jobEndde;
	}

	public String getJobCycleCdId() {
		return jobCycleCdId;
	}

	public void setJobCycleCdId(String jobCycleCdId) {
		this.jobCycleCdId = jobCycleCdId;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getJobSn() {
		return jobSn;
	}

	public void setJobSn(String jobSn) {
		this.jobSn = jobSn;
	}

	public String getJobDe() {
		return jobDe;
	}

	public void setJobDe(String jobDe) {
		this.jobDe = jobDe;
	}

	public String getJobWeek() {
		return jobWeek;
	}

	public void setJobWeek(String jobWeek) {
		this.jobWeek = jobWeek;
	}

	public String getJobMonth() {
		return jobMonth;
	}

	public void setJobMonth(String jobMonth) {
		this.jobMonth = jobMonth;
	}

	public String getJobDay() {
		return jobDay;
	}

	public void setJobDay(String jobDay) {
		this.jobDay = jobDay;
	}

	public String getJobHour() {
		return jobHour;
	}

	public void setJobHour(String jobHour) {
		this.jobHour = jobHour;
	}

	public String getJobMinute() {
		return jobMinute;
	}

	public void setJobMinute(String jobMinute) {
		this.jobMinute = jobMinute;
	}

	public QuartzType getQuartzType() {
		return quartzType;
	}

	public void setQuartzType(QuartzType quartzType) {
		this.quartzType = quartzType;
	}
}
