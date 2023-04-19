package com.hrs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hrs.app.model.House;

public interface HouseRepo extends JpaRepository<House, Long>{

	@Query( value = "select * from houses where house_owner_mail = :email", nativeQuery = true)
	List<House> findAllByEmailId(@Param("email") String email);

	@Query( value = "select * from houses where id = :id", nativeQuery = true)
	House findHouseById(@Param("id") Long id);
	
	@Query( value = "select * from houses where house_name = :houseName", nativeQuery = true)
	House findbyHouseName(@Param("houseName") String houseName);
}
