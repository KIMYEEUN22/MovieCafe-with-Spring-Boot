package com.example.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
	private String userId;
	private String userPwd;
	private String userNick;
	private String userEmail;
	private String userBirth;
	private String userContact;
	private String gender;
	private String exitdate;
	private String userName;
	private String photoOrigin;
	private int boardCount;
	private int comCount;
	private String exitType;
	private String joinDate;
	private String rankType;
	private int reportCount;
	
}

