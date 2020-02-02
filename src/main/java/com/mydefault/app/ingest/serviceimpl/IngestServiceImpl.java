package com.mydefault.app.ingest.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mydefault.app.common.util.MyDefaultSource;
import com.mydefault.app.common.util.MyMessageSource;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;
import com.mydefault.app.ingest.service.IngestService;
import com.mydefault.app.ingest.service.IngestVO;
import com.mydefault.app.ingest.web.IngestController;

@Service
public class IngestServiceImpl extends GenericServiceImpl<IngestVO,IngestMapper> implements IngestService{
	protected static final Logger logger = LoggerFactory.getLogger(IngestServiceImpl.class);
	
	@Resource
    protected MyDefaultSource myDefaultSource;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public IngestServiceImpl(){
		super(IngestMapper.class);
	}
	
	
	@Override
	public IngestVO insert(IngestVO entity, HttpServletRequest request) throws Exception {
		if ( entity != null ){
			String videoStorage = myDefaultSource.getMessage("video.storage");
			String ingestNm = entity.getIngestNm();
			String ingestRealPath = entity.getIngestRealPath();
			
			if ( entity.getSuperIngestId() == null || entity.getSuperIngestId().isEmpty() ){
				entity.setSuperIngestId(mapper.getNewSuperIngestId()); 
			}
			
			if ( entity.getIngestId() == null || entity.getIngestId().isEmpty() ){
				entity.setIngestId(mapper.getNewIngestId()); 
			}
			
			File copyFile = new File(ingestRealPath);
			
			if (copyFile.exists()){
				entity.setIngestRealPath(fileCopy(copyFile,videoStorage,entity.getIngestId() + "."  + getExtension(copyFile.getName()) ));
			}
			
			mapper.insert(entity);
		}
		return entity;
	}
	
	private String fileCopy(File copyFile, String storagePath , String fileName){
		String ret = null ; 
		FileInputStream fis = null ; 
		FileOutputStream fos = null ; 
		
		try {
			if ( storagePath != null && !( storagePath.endsWith("\\") || storagePath.endsWith("/") ) ){
				storagePath += "/";
			}
			
			File storageDirectory = new File ( storagePath );
			if ( !storageDirectory.isDirectory() ){
				storageDirectory.mkdir();
			}
			
			File targetFile = new File(storagePath + fileName);
			
			if ( targetFile.exists() ){
				File tempFile = new File ( targetFile.getAbsolutePath() + "." + sdf.format(new Date()) ) ;
				targetFile.renameTo(tempFile);
			}
			
			fis = new FileInputStream(copyFile);
			fos = new FileOutputStream(targetFile) ;
			byte[] b = new byte[4096];
			int cnt = 0;
			while((cnt=fis.read(b)) != -1){
				fos.write(b, 0, cnt);
			}
			
			ret = targetFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return ret ; 
	}
	
	private String getExtension(String path){
		int pos = path.lastIndexOf( "." );
		String ext = path.substring( pos + 1 );
		
		return ext ; 
	}
}
