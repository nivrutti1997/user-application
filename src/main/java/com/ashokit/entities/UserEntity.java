package com.ashokit.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="USER_DTLS")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "FIRST_NAME", length = 25)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 25)
	private String lastName;
	
	@Column(name = "USER_EMAIL", length = 30)
	private String emailId;
	
	@Column(name = "USER_PWD", length = 30)
	private String password;
	
	@Column(name = "USER_MOBILE")
	private Long mobileNo;
	
	@Column(name = "DOB")
	private String dateOfBirth;
	
	@Column(name = "GENDER", length = 10)
	private String gender;
	
	@Column(name = "CITY_ID")
	private Integer cityId;
	
	@Column(name = "STATE_ID")
	private Integer stateId;
	
	@Column(name = "COUNTRY_ID")
	private Integer countryId;
	
	@Column(name = "ACC_STATUS")
	private String accLock_flag;
	
	@Column(name = "CREATED_DATE", updatable = false)
	@CreationTimestamp
	private LocalDate createDate;
	
	@Column(name = "UPDATED_DATE", insertable = false)
	@UpdateTimestamp
	private LocalDate updateDate;
			
}
