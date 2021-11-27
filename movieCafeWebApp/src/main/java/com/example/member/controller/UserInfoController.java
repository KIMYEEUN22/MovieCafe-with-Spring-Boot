package com.example.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.member.domain.UserInfoVo;
import com.example.member.service.UserService;

@Controller
public class UserInfoController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/requestlogin", method = RequestMethod.POST)
	public String loginController(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "userPwd") String userPwd, HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		map.put("userId", userId);
		map.put("userPwd", userPwd);

		int isCheckUser = this.userService.isCheckUserCount(map);
		System.out.println(isCheckUser);
		if (isCheckUser == 1) {
			HttpSession session = req.getSession();
			UserInfoVo user = userService.uploadUserInfo(userId);
			session.setAttribute("userInfo", user);
			// 로그인한 상태로 메인페이지
			url = "/views/main";
			
			UserInfoVo user1 = (UserInfoVo) session.getAttribute("userInfo");
			System.out.println("세션 유저 정보 : " + user1.toString());
		} else {
			// 리다이렉트로 로그인 페이지 다시
			url = "/redirect:/views/main";
		}
		System.out.println("controller return : "+url);
		return 
				url;
	}

}
