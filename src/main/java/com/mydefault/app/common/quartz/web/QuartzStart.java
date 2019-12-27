package com.mydefault.app.common.quartz.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;

import com.mydefault.app.common.quartz.service.QuartzService;
import com.mydefault.app.common.quartz.service.QuartzVO;
import com.mydefault.app.common.util.DateUtil;
import com.mydefault.app.common.util.StringUtil;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzStart extends QuartzJobBean {

	/** 배치 실행전 선행 작업 존재하는 경우 선행 작업 미 완료시 주어진 시간에 따라 별도의 배치 정보 등록 */
	private static String cronIdPrefix  = "_TEMP";				// 배치ID 접두어
	private static int cronRepeatHours  = 1;					// 반복 시간(1-23)
	private static int cronRepeatCount  = 10;					// 반복 건수(0이면 제한 없음)

	private ApplicationContext applicationContext;

	private QuartzService quartzService;
	 
	private Scheduler scheduler;
		
	/** 쿼츠 실행 메소드 */ 
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		scheduler = context.getScheduler();
		JobDetail jobDetail = context.getJobDetail();		
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		JobKey jobKey = jobDetail.getKey(); 
		applicationContext = (ApplicationContext) jobDataMap.get("applicationContext");
		quartzService = (QuartzService)applicationContext.getBean("quartzServiceImpl"); 

		// 배치 실행 여부가 true인경우 실행
		String jobExecut = jobDataMap.getString("jobExecut");
		String jobParam = jobDataMap.getString("jobParam");
		String batchId = jobDataMap.getString("batchId"); 
		String batchNm = jobDataMap.getString("batchNm"); 			
		String jobSn = jobDataMap.getString("jobSn"); 
		String batchExcCdId = jobDataMap.getString("batchExcCdId");
		String cronId = jobDataMap.getString("cronId");
		String precdngAt = jobDataMap.getString("precdngAt");

		QuartzVO entity = new QuartzVO();
		entity.setJobExecut(jobExecut);
		entity.setJobParam(jobParam);
		entity.setBatchId(batchId);
		entity.setBatchNm(batchNm);			
		String jobDate = (!"".equals(StringUtil.isString(jobDataMap.getString("jobDate"))))?jobDataMap.getString("jobDate"):DateUtil.getNowDate();
		entity.setJobDate(jobDate.replaceAll("-", ""));			
		entity.setBatchBgndt(DateUtil.getNow("yyyy-MM-dd HH:mm:ss"));
		entity.setResultSttusCdId("COM_057_001");
		entity.setResultMsg("OK");
		entity.setJobSn(jobSn);
		entity.setBatchExcCdId(batchExcCdId);
					
		boolean preCheckAt = (precdngAt != null && "Y".equals(precdngAt) )?true:false;
			String cronIdTemp = cronId+cronIdPrefix;
			
			try{
 
				/*-------------------- 선행 체크 로직 추가 
			 * 
			 */
			
			// 로그
//				triggerLog();

			// 선행 체크 결과 미완료
			if(preCheckAt){
				entity.setResultSttusCdId("COM_057_002");
				entity.setResultMsg("batch.msg.preCheck.fail");
				// trigger 추가 생성 : 1번만 생성 해줌
				if(scheduler != null && !cronIdTemp.equals(jobKey.getName()) ){
					createSchedule(jobDataMap);
				}
			}else {
									
				// 특정 trigger 취소
				if( scheduler != null && cronIdTemp.equals(jobKey.getName()) ){
					scheduler.unscheduleJob(new TriggerKey(cronIdTemp, StdScheduler.DEFAULT_GROUP));
					//scheduler.deleteJob(jobKey);	// 삭제
				}
				
				// 전달 파라미터 map으로 셋팅
				Map<String, String> tMap = StringUtil.stringToMap(entity.getJobParam()); 
				if(tMap == null){
					tMap = new HashMap<String, String>();
				}
				tMap.put("jobDate", entity.getJobDate() );
				tMap.put("sdate", entity.getJobDate() );
				
				Object obj = applicationContext.getBean(jobExecut);
				Class<?>[] paramTypes = {Map.class}; 					 
	    		Object[] paramObjs 	  = {tMap}; 
				obj.getClass().getDeclaredMethod("execute", paramTypes).invoke(obj, paramObjs);
			}			
		} catch (Exception e) { 
			StringUtil.exceptionMsg(this.getClass(), e);
			entity.setResultSttusCdId("COM_057_002");
			entity.setResultMsg(StringUtil.getPrintStackTraceString(e, 3000));
		} finally {
			entity.setBatchEnddt(DateUtil.getNow("yyyy-MM-dd HH:mm:ss"));
			try{
				quartzService.noRollbackInsert(entity);
			} catch (Exception e) {
				StringUtil.exceptionMsg(this.getClass(), e);
			}
		}			
	}
	
	/**배치 trigger 추가 생성*/
	public void createSchedule(JobDataMap jobDataMap) throws Exception {
		
		try {
			String cronId = jobDataMap.getString("cronId");
			String cronIdTemp = cronId+cronIdPrefix;
						
			// 이전 생성된 trigger 취소
			scheduler.unscheduleJob(new TriggerKey(cronIdTemp, StdScheduler.DEFAULT_GROUP));						
			
			// JobDetail 생성
			JobDetail jobDetailTemp = JobBuilder.newJob(QuartzStart.class).withIdentity(cronIdTemp, StdScheduler.DEFAULT_GROUP).build();						
			JobDataMap jobDataMapTemp = jobDetailTemp.getJobDataMap();
			jobDataMapTemp.put("jobExecut", jobDataMap.getString("jobExecut"));
			jobDataMapTemp.put("jobParam",  jobDataMap.getString("jobParam"));
			jobDataMapTemp.put("batchId",   jobDataMap.getString("batchId"));
			jobDataMapTemp.put("batchNm",   jobDataMap.getString("batchNm"));
			jobDataMapTemp.put("jobSn",   	jobDataMap.getString("jobSn")); 
			jobDataMapTemp.put("cronId",   	jobDataMap.getString("cronId")); 
			jobDataMapTemp.put("precdngAt", jobDataMap.getString("precdngAt"));
			jobDataMapTemp.put("batchExcCdId", jobDataMap.getString("batchExcCdId"));
			jobDataMapTemp.put("applicationContext", applicationContext);
	
			//Trigger 생성
			TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger().withIdentity(cronIdTemp, StdScheduler.DEFAULT_GROUP);
			//builder.withSchedule(CronScheduleBuilder.cronSchedule(CRON_EXPRESSION));
			// 시작시간 : 0이면 바로 시작됨
			if(cronRepeatHours > 0){
				builder.startAt( DateBuilder.futureDate(cronRepeatHours, IntervalUnit.HOUR) ); 	// 반복시간 뒤 실행
			}
			// 종료시간 : 당일 23시 59분까지
			Date triggerEndTime = DateUtil.dateFormatCheck((DateUtil.getNow("yyyy-MM-dd")+" 23:59"), "yyyy-MM-dd HH:mm");
			builder.endAt(triggerEndTime); 

			SimpleScheduleBuilder simpleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(cronRepeatHours);
			if(cronRepeatCount > 0){
				simpleBuilder.withRepeatCount((cronRepeatCount-1));	// 한번은 무조건 시작되므로 -1해줌
			}else {	// 무제한
				simpleBuilder.repeatForever();
			}
			
			Trigger trigger = builder.withSchedule(simpleBuilder).build();						
			// 스케줄러에 추가
			scheduler.scheduleJob(jobDetailTemp, trigger);
		}catch(RuntimeException ebe){ 
			StringUtil.exceptionMsg(this.getClass(), ebe);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
	}
	
	/** trigger 로그 */
	public void triggerLog()  throws Exception {
		try { 
			for (String groupName : scheduler.getJobGroupNames()) {
			    for (JobKey jobKey2 : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
			    	String jobName = jobKey2.getName();
			    	String jobGroup = jobKey2.getGroup(); 
			    	@SuppressWarnings("unchecked")
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey2);
			    	
			    	StringUtil.exceptionMsg(this.getClass(), "[jobName] : " + jobName + " [groupName] : " + jobGroup);
			    	 
			    	try{
						Date nextFireTime = triggers.get(0).getNextFireTime();
						StringUtil.exceptionMsg(this.getClass(), " 다음 실행 시간 :  "+ DateUtil.getDateFormat(nextFireTime, ""));
					
			    	} catch(RuntimeException ebe){ 
			    		StringUtil.exceptionMsg(this.getClass(), " 다음 실행 시간 없음");
			    	} catch (Exception e) {
						StringUtil.exceptionMsg(this.getClass(), " 다음 실행 시간 없음");
					}
			    }
			}
		
		}catch(RuntimeException ebe){ 
			StringUtil.exceptionMsg(this.getClass(), ebe);
		} catch (Exception e) {
			StringUtil.exceptionMsg(this.getClass(), e);
		}
	}
	
	
}
 
