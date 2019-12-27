package com.mydefault.app.generic.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mydefault.app.generic.serviceimpl.GenericMapper;

public interface GenericService<T, M extends GenericMapper<T>> {
	
	public List<?> list(T entity) throws Exception;
	
	public T view(T entity) throws Exception;
	
	public T insert(T entity, HttpServletRequest request) throws Exception;
	
	public int update(T entity, HttpServletRequest request) throws Exception;
	
	public int delete(T entity, HttpServletRequest request) throws Exception;
	
	public int delete(Object entity) throws Exception;
}
