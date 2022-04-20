package com.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.models.ForgotPasswordModel;
import com.ashokit.services.UserService;

@RestController
@CrossOrigin
public class ForgotPasswordController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/recover-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordModel email){
		String frgtPass = service.forgotPassword(email);
		return new ResponseEntity<String>(frgtPass,HttpStatus.OK);
	}
}	
