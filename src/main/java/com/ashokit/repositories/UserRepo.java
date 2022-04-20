package com.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>{
	public UserEntity findByEmailIdAndPassword(String email,String pass);
	public UserEntity findByEmailId(String email);
}
