package com.mydefault.app.user.serviceimpl;

import org.apache.ibatis.annotations.Mapper;

import com.mydefault.app.generic.serviceimpl.GenericMapper;
import com.mydefault.app.user.service.UserVO;

@Mapper
public interface UserMapper extends GenericMapper<UserVO>{
	public UserVO getUser(UserVO entity);
}
