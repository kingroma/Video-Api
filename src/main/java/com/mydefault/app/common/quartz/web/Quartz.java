package com.mydefault.app.common.quartz.web;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;

import com.mydefault.app.common.quartz.service.QuartzService;
import com.mydefault.app.common.quartz.service.QuartzType;
import com.mydefault.app.common.quartz.service.QuartzVO;
import com.mydefault.app.common.util.DateUtil;
import com.mydefault.app.common.util.StringUtil;

@Controller
public class Quartz extends StdSchedulerFactory implements ApplicationContextAware, InitializingBean, DisposableBean {
	
	protected static final Logger logger = LoggerFactory.getLogger(Quartz.class); 
	
	@Resource
	private QuartzService quartzService;
	
	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		executeQuartz();
	}
	
	@Override
	public void destroy() throws Exception {		
		try{
			getScheduler().shutdown();
		} catch(SchedulerException se) {
			throw new Exception(se);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public void quartzRestart() throws Exception {    	
    	try{
    		getScheduler().shutdown(true);
        	executeQuartz();
		} catch(SchedulerException se) {
			throw new Exception(se);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/** Job 실행 주기 설정 */
	public void executeQuartz() throws Exception {
		try{
			
			List<QuartzVO> list = quartzService.selectQuartzList();

			if(list != null && !list.isEmpty()){				
				int len = list.size();
				
				getScheduler().start();
				
				for(int i=0; i<len; i++){	
					QuartzVO quartzVO = list.get(i);					
					String cronId = quartzVO.getBatchId() + quartzVO.getJobSn();
					String batchId = quartzVO.getBatchId();
					String batchNm = quartzVO.getBatchNm();
					String jobSeCdId = quartzVO.getJobSeCdId();				// 배치 실행구분(COM_053)
					String jobCycleCdId = quartzVO.getJobCycleCdId();		// 스케줄주기COM_055					
					String jobExecut = quartzVO.getJobExecut();				// 배치실행문(SQL문, 프로시져)
					String jobParam = quartzVO.getJobParam();				// 배치파라미터
					String precdngAt = quartzVO.getPrecdngAt();				// 선행여부
					String jobDate = quartzVO.getJobDate();					// 실행일자
					
					// job정보
					String jobSn = quartzVO.getJobSn();
					String jobDe = quartzVO.getJobDe();				// 일회성 스케줄 일자
					String month = quartzVO.getJobMonth();		
					String week = quartzVO.getJobWeek();		
					String day = quartzVO.getJobDay();			
					String hour = quartzVO.getJobHour();		
					String minute = quartzVO.getJobMinute();
					String second = "00";
					
					String cronExpression = null;
					long stime = 0;
					long ctime = 0; 

					//DB에서 불러온 시간을 CronExpression 형식으로 바꾸기
					if(QuartzType.ONE_TIME.equals(jobSeCdId)){		// 일회
						String y = jobDe.substring(0, 4);
						String d = jobDe.substring(6, 8);
						String m = jobDe.substring(4, 6);
						cronExpression = second + " " + minute + " " + hour + " " + d + " " + m + " ? " + y;
						
						//비교할 시간
						stime = Long.parseLong(jobDe+hour+minute+second);
						ctime = Long.parseLong(DateUtil.getNow("yyyyMMddHHmmss"));
						
						//일회성 스케줄 중 시간이 지나지 않은것만 tigger 등록
						//만약 지난 시간을 넣을 경우 error
						if(stime < ctime || stime == 0) continue;
						
					} else if(QuartzType.ALL_DAY.equals(jobSeCdId)){		// 매번						
						if(QuartzType.MINUTE.equals(jobCycleCdId)){				// 분	
							cronExpression = "0 0/" + minute + " * * * ?";
						}else if(QuartzType.HOUR.equals(jobCycleCdId)){		// 시간		
							cronExpression = "0 0 0/" + hour + " * * ?";
						}else if(QuartzType.DAY.equals(jobCycleCdId)){		// 일
							cronExpression = second + " " + minute + " " + hour + " * * ?";
						}else if(QuartzType.WEEK.equals(jobCycleCdId)){		// 주
							cronExpression = second + " " + minute + " " + hour + " ? * " + week; 
						}else if(QuartzType.MONTH.equals(jobCycleCdId)){		// 월
							cronExpression = second + " " + minute + " " + hour + " " + day + " * ?";
						}else if(QuartzType.YEAR.equals(jobCycleCdId)){		// 년
							cronExpression = second + " " + minute + " " + hour + " " + day + " " + month + " ?";
						}else{
							cronExpression = null; 
						}
					}

					if (cronExpression != null) {
						// JobDetail 생성
						JobDetail jobDetail = JobBuilder.newJob(QuartzStart.class).withIdentity(cronId, StdScheduler.DEFAULT_GROUP).build();						
						JobDataMap jobDataMap = jobDetail.getJobDataMap();
						jobDataMap.put("jobExecut", jobExecut);
						jobDataMap.put("jobParam",  jobParam);
						jobDataMap.put("jobDate",   jobDate);
						jobDataMap.put("batchId",   batchId);
						jobDataMap.put("batchNm",   batchNm);
						jobDataMap.put("jobSn",   	jobSn); 
						jobDataMap.put("cronId",   	cronId); 
						jobDataMap.put("precdngAt", precdngAt); 
						jobDataMap.put("batchExcCdId", QuartzType.AUTO);						
						jobDataMap.put("applicationContext", applicationContext);
						// Trigger 생성
						Trigger trigger = TriggerBuilder.newTrigger().withIdentity(cronId, StdScheduler.DEFAULT_GROUP).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
						// 스케줄러에 추가
						getScheduler().scheduleJob(jobDetail, trigger);
						
						System.out.println(" batch ADD : "+ batchId);
						logger.info("BATCH ADD = {} {} " , batchId , batchNm);
					}
				}
			}
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
	}
}
