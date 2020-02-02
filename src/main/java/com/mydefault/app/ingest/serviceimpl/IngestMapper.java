package com.mydefault.app.ingest.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.generic.serviceimpl.GenericMapper;
import com.mydefault.app.ingest.service.IngestVO;

@Mapper
public interface IngestMapper extends GenericMapper<IngestVO>{

	public String getNewSuperIngestId();
	
	public String getNewIngestId();
}
