package com.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entities.Country;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
