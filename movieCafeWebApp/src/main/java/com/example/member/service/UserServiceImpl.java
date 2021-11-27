package com.example.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.member.dao.UserDao;
import com.example.member.domain.UserInfoVo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public int isCheckUserCount(Map map) {
		return this.userDao.checkIsUser(map);
	}

	@Override
	public UserInfoVo uploadUserInfo(String userId) {
		return this.userDao.sendUserInfo(userId);
	}

}
