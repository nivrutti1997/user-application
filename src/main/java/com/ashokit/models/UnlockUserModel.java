package com.ashokit.models;

import lombok.Data;

@Data
public class UnlockUserModel {
	private String emailId;
	private String temporaryPass;
	private String chooseNewPass;
	private String confirmPass;
}
