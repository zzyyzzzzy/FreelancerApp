package com.mercury.FreelancerApp.http;


import com.mercury.FreelancerApp.vo.UserVO;
import lombok.Data;

@Data
public class AuthenticationSuccessResponse extends Response {

	private UserVO userVO;

	public AuthenticationSuccessResponse(boolean success, int code, String message, UserVO userVO) {
		super(success, code, message);
		this.userVO = userVO;
	}

}
