package com.ashokit.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.constants.AppConstants;
import com.ashokit.entities.City;
import com.ashokit.entities.Country;
import com.ashokit.entities.State;
import com.ashokit.entities.UserEntity;
import com.ashokit.models.ForgotPasswordModel;
import com.ashokit.models.LoginModel;
import com.ashokit.models.RegistrationModel;
import com.ashokit.models.UnlockUserModel;
import com.ashokit.props.AppProps;
import com.ashokit.repositories.CityRepo;
import com.ashokit.repositories.CountryRepo;
import com.ashokit.repositories.StateRepo;
import com.ashokit.repositories.UserRepo;
import com.ashokit.utils.EmailUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	CountryRepo countryRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	CityRepo cityRepo;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	AppProps appProps;
	
	@Override
	public String loginUser(LoginModel loginModel) {
		Map<String, String> messages = appProps.getMessages();
		UserEntity ue = userRepo.findByEmailIdAndPassword(loginModel.getEmailId(), loginModel.getPassword());
		if (ue == null) {
			return messages.get(AppConstants.USER_CRED_INVALID);
		}
		if (ue.getAccLock_flag().equals("LOCKED")) {
			return messages.get(AppConstants.USER_ACCT_LOCKED);
		}
		return messages.get(AppConstants.LOGIN_SUCCESS);
	}

	@Override
	public String checkEmail(String email) {
		Map<String, String> messages = appProps.getMessages();
		UserEntity userEntity = userRepo.findByEmailId(email);
		if (userEntity == null) {
			return messages.get(AppConstants.UNIQUE_EMAIL);
		}
		return messages.get(AppConstants.DUPLICATE_EMAIL);
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<Country> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<State> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> statesMap = new HashMap<>();
		states.forEach(state -> {
			statesMap.put(state.getStateId(), state.getStateName());
		});
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<City> cities = cityRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();
		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;
	}
	
	 private char[] generatePassword(int length) {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      Random random = new Random();
	      char[] password = new char[length];

	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return password;
	   }
	
	@Override
	public String registerUser(RegistrationModel registrationModel) {
		Map<String, String> messages = appProps.getMessages();
		UserEntity userEntity = new UserEntity();
		char[] generatePassword = generatePassword(6);
		String password = new String(generatePassword);
		userEntity.setPassword(password);
		userEntity.setAccLock_flag("LOCKED");
		BeanUtils.copyProperties(registrationModel, userEntity);
		UserEntity entity = userRepo.save(userEntity);
		if (entity == null) {
			return messages.get(AppConstants.REGISTRATION_ERROR);
		}
		String subject = "User registration - Ashok It";
		String emailBody = readUnlockAccEmailBody(registrationModel,password);
		emailUtil.sendMail(registrationModel.getEmailId(), subject, emailBody);
		return messages.get(AppConstants.REGISTRATION_SUCCESS);
	}
	
	private String readUnlockAccEmailBody(RegistrationModel registrationModel, String password) {
		String body = "";
		StringBuffer sb = new StringBuffer();
		Path pathBody = Paths.get("Registration-Mail-Body.txt");
		try (Stream<String> stream = Files.lines(pathBody)){
			stream.forEach(line->{
				sb.append(line);
			});
			body = sb.toString();
			body = body.replace("{FNAME}", registrationModel.getFirstName());
			body = body.replace("{LNAME}", registrationModel.getLastName());
			body = body.replace("{TEMP-PWD}", password);
			body = body.replace("{EMAIL}", registrationModel.getEmailId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	@Override
	public String unlockAccount(UnlockUserModel unlockUserModel) {
		Map<String, String> messages = appProps.getMessages();
		UserEntity userEntity = userRepo.findByEmailIdAndPassword(unlockUserModel.getEmailId(),unlockUserModel.getTemporaryPass());
		if (userEntity == null) {
			return messages.get(AppConstants.WRONG_TEMP_PASS);
		}else {
			String confirmPass = unlockUserModel.getConfirmPass();
			userEntity.setPassword(confirmPass);
			userEntity.setAccLock_flag("UNLOCKED");
			userRepo.save(userEntity);
			return messages.get(AppConstants.UNLOCK_ACC_SUCC);
		}
		
	}
	
	@Override
	public String forgotPassword(ForgotPasswordModel email) {
		Map<String, String> messages = appProps.getMessages();
		UserEntity userEntity = userRepo.findByEmailId(email.getEmailId());
		if (userEntity == null) {
			return messages.get(AppConstants.FORGOT_PASS_EMAIL_WRONG);
		}
		String subject = "Forgot Password - Ashok It";
		String body = forgotPasswordEmailBody(userEntity);
		emailUtil.sendMail(email.getEmailId(),subject, body);
		return messages.get(AppConstants.FORGOT_PASS_SUCC);
	}

	private String forgotPasswordEmailBody(UserEntity userEntity) {
		String emailBody = "";
		try {
			StringBuffer body = new StringBuffer();
			File file = new File("Forgot-Password-Email-Body.txt");
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				body = body.append(line);
				emailBody = body.toString();
				emailBody = emailBody.replace("{FNAME}",userEntity.getFirstName());
				emailBody = emailBody.replace("{LNAME}",userEntity.getLastName());
				emailBody = emailBody.replace("{PWD}",userEntity.getPassword());			
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return emailBody;
	}
	
	

}
