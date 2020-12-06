package com.app.springsocialloginapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleLoginController {
	
	@GetMapping("/")
	public String showLogin() {
		return "login";
		
		
	}

}
