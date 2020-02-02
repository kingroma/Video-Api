package com.mydefault.app.video.serviceimpl;

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
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;
import com.mydefault.app.video.service.VideoService;
import com.mydefault.app.video.service.VideoVO;

@Service
public class VideoServiceImpl extends GenericServiceImpl<VideoVO,VideoMapper> implements VideoService{
	protected static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
	
	@Resource
    protected MyDefaultSource myDefaultSource;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
	
	public VideoServiceImpl(){
		super(VideoMapper.class);
	}
	
	@Override
	public VideoVO insert(VideoVO entity, HttpServletRequest request) throws Exception {
		if ( entity != null ){
			String videoStorage = myDefaultSource.getMessage("video.storage");
			String videoRealPath = entity.getVideoRealPath();
			
			if ( entity.getVideoSuperId() == null || entity.getVideoSuperId().isEmpty() ){
				entity.setVideoSuperId(mapper.getNewVideoSuperId()); 
			}
			
			if ( entity.getVideoId() == null || entity.getVideoId().isEmpty() ){
				entity.setVideoId(mapper.getNewVideoId()); 
			}
			
			File copyFile = new File(videoRealPath);
			
			if (copyFile.exists()){
				entity.setVideoPlayPath(fileCopy(copyFile,videoStorage,entity.getVideoId() + "."  + getExtension(copyFile.getName()) ));
			}
			
			mapper.insert(entity);
		}
		return entity;
	}
	
	@Override
	public int update(VideoVO entity, HttpServletRequest request) throws Exception {
		String videoStorage = myDefaultSource.getMessage("video.storage");
		String videoRealPath = entity.getVideoRealPath();
		
		File copyFile = new File(videoRealPath);
		
		if (copyFile.exists()){
			entity.setVideoPlayPath(fileCopy(copyFile,videoStorage,entity.getVideoId() + "."  + getExtension(copyFile.getName()) ));
		}
		
		return mapper.update(entity);
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
