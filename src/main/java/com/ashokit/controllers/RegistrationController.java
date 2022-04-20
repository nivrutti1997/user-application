package com.ashokit.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.models.RegistrationModel;
import com.ashokit.services.UserService;
import com.ashokit.services.UserServiceImpl;

@RestController
@CrossOrigin
public class RegistrationController {

	@Autowired
	UserService serviceImpl;
	
	@PostMapping("/registration")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationModel registrationModel) {
		String registerUser = serviceImpl.registerUser(registrationModel);
		return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/countries")
	public ResponseEntity<Map<Integer,String>> getContries(){
		Map<Integer, String> countries = serviceImpl.getCountries();
		return new ResponseEntity<Map<Integer,String>>(countries,HttpStatus.OK);
	}
	
	@GetMapping("/states/{countryId}")
	public ResponseEntity<Map<Integer,String>> getStates(@PathVariable("countryId") Integer countryId){
		Map<Integer, String> states = serviceImpl.getStates(countryId);
		return new ResponseEntity<Map<Integer,String>>(states, HttpStatus.OK);
	}
	
	@GetMapping("/cities/{stateId}")
	public ResponseEntity<Map<Integer,String>> getCities(@PathVariable("stateId") Integer stateId){
		Map<Integer, String> cities = serviceImpl.getCities(stateId);
		return new ResponseEntity<Map<Integer,String>>(cities, HttpStatus.OK);
	}
	
	@GetMapping("/check-email/{email}")
	public ResponseEntity<String> checkEmail(@PathVariable("email") String email){
		String checkEmail = serviceImpl.checkEmail(email);
		return new ResponseEntity<String>(checkEmail,HttpStatus.OK);
	}
}
