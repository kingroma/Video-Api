package com.mydefault.app.user.service;

import com.mydefault.app.generic.service.GenericService;
import com.mydefault.app.user.serviceimpl.UserMapper;

public interface UserService extends GenericService<UserVO,UserMapper>{
	public UserVO getUser(UserVO entity);
}
