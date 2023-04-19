package com.hrs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hrs.app.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	@Query( value = "select * from users where email = :email", nativeQuery = true)
	User findbyEmail(@Param("email") String email);

//	@Query( value = "select * from users where usertype = 'student'", nativeQuery = true)
//	List<User> findAllStudents();
//
//	@Query( value = "select * from users where usertype = 'houseowner'", nativeQuery = true)
//	List<User> findAllOwners();

//	@Query( value = "delete from users where email = :userMail", nativeQuery = true)
//	void deleteByUserMail(@Param("userMail") String userMail);
}
