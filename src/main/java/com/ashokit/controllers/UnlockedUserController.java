package com.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.models.UnlockUserModel;
import com.ashokit.services.UserService;

@RestController
@CrossOrigin
public class UnlockedUserController {
	
	@Autowired
	UserService service;
	
	@PostMapping("/unlock-user/{email}")
	public ResponseEntity<String> unlockedUserAcct(@RequestBody UnlockUserModel model){
		String unlockAccount = service.unlockAccount(model);
		return new ResponseEntity<String>(unlockAccount,HttpStatus.OK);
	}
}
