package com.example.member.service;

import java.util.Map;

import com.example.member.domain.UserInfoVo;

public interface UserService {
	int isCheckUserCount(Map map);
	UserInfoVo uploadUserInfo(String userId);

}
