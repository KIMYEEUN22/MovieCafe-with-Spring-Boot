package com.example.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.member.service.UserService;
import com.example.member.vo.UserInfoVo;

@Controller
public class UserInfoController {
	@Autowired
	private UserService userService;
	
	//회원가입 버튼 클릭시 작성 폼으로 이동
	@GetMapping("/joinUser")
	public String joinUser() {
		return "/views/member/joinUserForm";
	}

	@RequestMapping(value = "/requestlogin", method = RequestMethod.POST)
	public String loginController(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "userPwd") String userPwd, HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		map.put("userId", userId);
		map.put("userPwd", userPwd);
		

		int isCheckUser = this.userService.isCheckUserCount(map);
		if (isCheckUser == 1) {
			HttpSession session = req.getSession();
			UserInfoVo user = userService.uploadUserInfo(userId);
			session.setAttribute("userInfo", user);
			// 로그인한 상태로 메인페이지
			url = "/views/main";
			
			UserInfoVo user1 = (UserInfoVo) session.getAttribute("userInfo");
		} else {
			// 리다이렉트로 로그인 페이지 다시
			url = "/redirect:/views/main";
		}
	
		return 
				url;
	
	}
	//회원 가입
	@PostMapping(value="/joinUserRequest")
	public String joinUserRequest(
			@RequestParam(value="userId1") String userId,
			@RequestParam(value="userPwd1") String userPwd,
			@RequestParam(value="userEmail") String userEmail,
			@RequestParam(value="birthYear") String tempYear,
			@RequestParam(value="birthMonth") String tempMonth,
			@RequestParam(value="birthDate") String tempDate,
			@RequestParam(value="contact1") String tempCon1,
			@RequestParam(value="contact2") String tempCon2,
			@RequestParam(value="contact3") String tempCon3,
			@RequestParam(value="userNick") String userNick,
			@RequestParam(value="userName") String userName,	
			@RequestParam(value="pickGender") String gender,
			Model model) {
		
		UserInfoVo user = new UserInfoVo();
		
		user.setUserId(userId);
		user.setUserPwd(userPwd);
		user.setUserEmail(userEmail);
		
		String userBirth = tempYear + "-" + tempMonth + "-" + tempDate;
		user.setUserBirth(userBirth);
		
		String userContact = tempCon1+"-"+ tempCon2 + "-" + tempCon3;
		user.setUserContact(userContact);
		
		user.setUserNick(userNick);
		user.setUserName(userName);
		user.setGender(gender);
		this.userService.insertUserInfo(user);
		return "views/main";
		

	}

	
	// 회원가입 과정에서 아이디 중복 체크
	@RequestMapping(value="/checkId", method=RequestMethod.POST)
	public @ResponseBody String checkIdAjax(@RequestParam("userId") String reqId) {
		String inputId = reqId.trim();
		int isCheckId = userService.isCheckId(inputId);
		String checkResult = "";
		// 아이디가 중복이 아니면 0 = 가입 가능
		// 아이디가 중복이면 1 = 사용 불가
		if (isCheckId == 0) {
			checkResult = "false";
			
		} else if(isCheckId == 1) {
			checkResult = "true";
		}
		return checkResult;
		
		
	}
	// 회원가입 과정에서 닉네임 중복 체크
	@RequestMapping(value="/checkNick", method=RequestMethod.POST)
	public @ResponseBody String checkNickAjax(@RequestParam("userNick") String reqNick) {
		String inputId = reqNick.trim();
		int isCheckNick = userService.isCheckNick(inputId);
		String checkResult = "";
		// 닉네임이 중복이 아니면 0 = 가입 가능
		// 넥니엠이 중복이면 1 = 사용 불가
		if (isCheckNick == 0) {
			checkResult = "false";
			
		} else if(isCheckNick == 1) {
			checkResult = "true";
		}
		return checkResult;
		
		
	}
	@GetMapping(value="/gomypage")
	public ModelAndView myPage(HttpServletRequest req, ModelAndView model) {
		HttpSession session = req.getSession();
		UserInfoVo userInfo = (UserInfoVo)session.getAttribute("userInfo");
		System.out.println("세션 정보 : " + userInfo.toString());
		
		model.setViewName("/views/member/mypage");
		model.addObject("userId",userInfo.getUserId());
		model.addObject("userNick",userInfo.getUserNick());
		model.addObject("joinDate",userInfo.getJoinDate());
		

		return model;
	
	}
	
	
 // @GetMapping("/mypage")
//	public String mypage() {
	//	return "views/member/mypage";
	//}

}
