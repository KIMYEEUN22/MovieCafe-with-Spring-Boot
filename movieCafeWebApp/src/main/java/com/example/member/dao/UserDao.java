package com.example.member.dao;

import java.util.Map;

import com.example.member.domain.UserInfoVo;

public interface UserDao {
	
	int checkIsUser(Map map);
	UserInfoVo sendUserInfo(String user);
	

}
