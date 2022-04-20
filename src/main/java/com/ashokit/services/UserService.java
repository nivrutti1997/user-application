package com.ashokit.services;

import java.util.Map;

import com.ashokit.models.ForgotPasswordModel;
import com.ashokit.models.LoginModel;
import com.ashokit.models.RegistrationModel;
import com.ashokit.models.UnlockUserModel;

public interface UserService {
	public String loginUser(LoginModel loginModel);
	public String checkEmail(String email);
	public Map<Integer,String> getCountries();
	public Map<Integer,String> getStates(Integer countryId);
	public Map<Integer,String> getCities(Integer stateId);
	public String registerUser(RegistrationModel registrationModel);
	public String unlockAccount(UnlockUserModel unlockUserModel);
	public String forgotPassword(ForgotPasswordModel email);	
}
