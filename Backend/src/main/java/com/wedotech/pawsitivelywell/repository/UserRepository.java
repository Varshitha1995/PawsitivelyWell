package com.wedotech.pawsitivelywell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wedotech.pawsitivelywell.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long>{

	@Query("select u from UserDetails u where u.EmailId = (:emailId)")
	UserDetails getUser(@Param("emailId") String emailId);
	
	@Query("select u.user_id from UserDetails u where u.EmailId = (:emailId)")
	Long getUserId(@Param("emailId") String emailId);

}
