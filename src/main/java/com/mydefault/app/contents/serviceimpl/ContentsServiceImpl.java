package com.mydefault.app.contents.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mydefault.app.common.util.MyMap;
import com.mydefault.app.contents.service.ContentsService;
import com.mydefault.app.contents.service.ContentsVO;
import com.mydefault.app.generic.serviceimpl.GenericServiceImpl;

@Service
public class ContentsServiceImpl extends GenericServiceImpl<ContentsVO,ContentsMapper> implements ContentsService{
	protected static final Logger logger = LoggerFactory.getLogger(ContentsServiceImpl.class);
	
	public ContentsServiceImpl(){
		super(ContentsMapper.class);
	}

	public List<?> listVideo(ContentsVO entity){
		return mapper.listVideo(entity);
	}
	
	@Override
	public List<?> mainContents() {
		return mapper.mainContents();
	}
	
	@Override
	public ContentsVO insert(ContentsVO entity, HttpServletRequest request) throws Exception {
		mapper.insert(entity);
		
		List<String> videoSuperIdArr = entity.getVideoSuperIdArr();
		List<String> sortSnArr = entity.getSortSnArr();
		
		
		if ( videoSuperIdArr != null && sortSnArr != null && videoSuperIdArr.size() == sortSnArr.size() ) {
			
			for ( int i = 0 ; i < videoSuperIdArr.size() ; i ++ ) { 
				String videoSuperId = videoSuperIdArr.get(i);
				String sortSn = sortSnArr.get(i);
				
				if ( videoSuperId != null && !videoSuperId.isEmpty() && sortSn != null && !sortSn.isEmpty() ){
					entity.setVideoSuperId(videoSuperId);
					entity.setSortSn(sortSn);
					
					mapper.insertVideo(entity);
				}
			}
		}
		
		return entity ;
	}
	
	@Override
	public int update(ContentsVO entity, HttpServletRequest request) throws Exception {
		List<String> videoSuperIdArr = entity.getVideoSuperIdArr();
		List<String> sortSnArr = entity.getSortSnArr();
		
		if ( videoSuperIdArr != null && sortSnArr != null && videoSuperIdArr.size() == sortSnArr.size() ) {
			mapper.deleteVideo(entity);
			
			for ( int i = 0 ; i < videoSuperIdArr.size() ; i ++ ) { 
				String videoSuperId = videoSuperIdArr.get(i);
				String sortSn = sortSnArr.get(i);
				
				if ( videoSuperId != null && !videoSuperId.isEmpty() && sortSn != null && !sortSn.isEmpty() ){
					entity.setVideoSuperId(videoSuperId);
					entity.setSortSn(sortSn);
					
					mapper.insertVideo(entity);
				}
			}
		}
		
		return mapper.update(entity) ;
	}
	
	
	@Override
	public boolean insertVideo(ContentsVO entity){
		return mapper.insertVideo(entity);
	}
	
	@Override
	public boolean deleteVideo(ContentsVO entity){
		return mapper.deleteVideo(entity);
	}
	
	@Override
	public List<Map<String,Object>> apiMainVideoList() {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		ContentsVO entity = new ContentsVO();
		entity.setUseAt("Y");
		try {
			List<MyMap> list = (List<MyMap>) mapper.list(entity);
			
			if ( list != null ){
				for ( MyMap map : list ){
					Map<String,Object> resultMap = new HashMap<String,Object>();
					String contentsId = (String)map.get("contentsId");
					String contentsNm = (String)map.get("contentsNm");
					
					entity.setContentsId(contentsId);
					List<MyMap> videos = (List<MyMap>) mapper.listVideo(entity);
					
					resultMap.put("contentsId", contentsId);
					resultMap.put("contentsNm", contentsNm);
					resultMap.put("videos", videos);
					
					result.add(resultMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
