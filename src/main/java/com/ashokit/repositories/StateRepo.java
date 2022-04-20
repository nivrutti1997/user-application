package com.ashokit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entities.State;

public interface StateRepo extends JpaRepository<State, Integer> {
	public List<State> findByCountryId(Integer countryId);
}	
