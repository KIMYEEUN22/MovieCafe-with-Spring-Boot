package com.example.member.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.member.domain.UserInfoVo;

@Repository("userInfoDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	
	
	@Override
	public int checkIsUser(Map map) {
		return sqlSession.selectOne("Member.checkIsUser", map); 
	}


	@Override
	public UserInfoVo sendUserInfo(String user) { 
		return sqlSession.selectOne("Member.loginUser", user);
	}

}
