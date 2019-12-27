package com.mydefault.app.generic.serviceimpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.mydefault.app.common.util.ApplicationContextUtils;
import com.mydefault.app.generic.service.GenericService;

@Transactional
public class GenericServiceImpl<T, M extends GenericMapper<T>> implements GenericService<T, M>, ApplicationContextAware, InitializingBean {

    protected Class<M> mapperClass;
	
	protected M mapper;
	
	protected ApplicationContext applicationContext;

    
	protected GenericServiceImpl(Class<M> mapperClass) {
		this.mapperClass = mapperClass;
	}	

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public void afterPropertiesSet() throws Exception {
		if(mapper == null)	{
			mapper = (M)ApplicationContextUtils.getBeanByType(applicationContext, mapperClass);
		}
	}
	
	@Override
	public List<?> list(T entity) throws Exception { 
		return mapper.list(entity);	
	}
	
	@Override
	public T view(T entity) throws Exception {
		return mapper.view(entity);
	}

	@Override
	public T insert(T entity, HttpServletRequest request) throws Exception {
		mapper.insert(entity);
		return entity;
	}
	
	@Override
	public int update(T entity, HttpServletRequest request) throws Exception {
		return mapper.update(entity);	
	}
		
	@Override
	public int delete(T entity, HttpServletRequest request) throws Exception {
		return mapper.delete(entity);
	}
	
	@Override
	public int delete(Object entity) throws Exception {
		return mapper.delete(entity);
	}
	
}
