package com.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.models.LoginModel;
import com.ashokit.services.UserService;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginModel loginModel) {
		String loginUser = userService.loginUser(loginModel);
		return new ResponseEntity<String>(loginUser,HttpStatus.OK);
	}
	
	
}
