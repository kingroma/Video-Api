package com.mydefault.app.generic.serviceimpl;

import java.util.List;

public interface GenericMapper<T> {
	public List<?> list(T entity) throws Exception;
	
	public T view(T entity) throws Exception;

	public int insert(T entity) throws Exception;
	
	public int update(T entity) throws Exception;
	
	public int delete(Object entity) throws Exception; 
	
	public int duplPk(T entity) throws Exception;
}
