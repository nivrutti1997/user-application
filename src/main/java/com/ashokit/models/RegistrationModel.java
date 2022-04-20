package com.ashokit.models;

import java.time.LocalDate;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class RegistrationModel {
	private String firstName;
	private String lastName;
	private String emailId;
	private Long mobileNo;
	private String dateOfBirth;
	private String gender;
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
			
}
