-- BATCH

DROP TABLE TB_CO_BATCH ; 
CREATE TABLE TB_CO_BATCH (
	BATCH_ID			VARCHAR(30)        NOT NULL , 
	BATCH_NM			VARCHAR(200)			NULL,
  	BATCH_DC			VARCHAR(1000)			NULL,   	
  	JOB_EXECUT 			VARCHAR(4000)			NULL,   	
  	JOB_PARAM 			VARCHAR(100)			NULL,   	
  	JOB_SE_CD_ID		VARCHAR(30)			NULL,  
	JOB_PD_CD_ID		VARCHAR(30)			NULL,  
	JOB_BGNDE			VARCHAR(8)        		NULL,
	JOB_ENDDE			VARCHAR(8)        		NULL, 
	JOB_CYCLE_CD_ID		VARCHAR(30)			NULL, 	
	UNITY_ID			VARCHAR(100)			NULL,  
	SCEN_ID				VARCHAR(30)			NULL,  
	SCEN_NM				VARCHAR(200)			NULL, 
	PRECDNG_AT			VARCHAR(1)				NULL, 
	USE_AT				VARCHAR(1)				NULL, 
  	RGS_DT				DATE					NULL,
  	RGS_USER_ID			VARCHAR(30)			NULL,
  	UPD_DT				DATE					NULL,
  	UPD_USER_ID			VARCHAR(30)			NULL ,
  	PRIMARY KEY(BATCH_ID)
);
DROP TABLE TB_CO_BATCH_JOB ; 
CREATE TABLE TB_CO_BATCH_JOB (
	BATCH_ID			VARCHAR(30)        	NOT NULL , 
	JOB_SN				NUMERIC(10)				NOT	NULL,
	JOB_DE				VARCHAR(8)					NULL, 
	JOB_WEEK        	VARCHAR(1)					NULL, 
   	JOB_MONTH        	VARCHAR(2)					NULL, 
   	JOB_DAY          	VARCHAR(2)					NULL, 
   	JOB_HOUR         	VARCHAR(2)					NULL, 
   	JOB_MINUTE			VARCHAR(2)					NULL,
   	PRIMARY KEY(BATCH_ID , JOB_SN)
);
DROP TABLE TB_CO_BATCH_RESULT;
CREATE TABLE TB_CO_BATCH_RESULT (
	BATCH_ID			VARCHAR(30)        NOT NULL,
    RESULT_SN			NUMERIC(10)			NOT NULL,
    BATCH_NM			VARCHAR(200)			NULL,
	BATCH_BGNDT			VARCHAR(23)			NULL,
	BATCH_ENDDT			VARCHAR(23)			NULL,
	RESULT_STTUS_CD_ID	VARCHAR(30)			NULL,	
	RESULT_MSG			VARCHAR(4000)			NULL,	
	RGS_DT				DATE					NULL,
	JOB_SN				NUMERIC(10)				NULL,
	BATCH_EXC_CD_ID		VARCHAR(30)			NULL, 
	PRIMARY KEY(BATCH_ID , RESULT_SN)
);


COMMIT;

