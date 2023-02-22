package com.wedotech.pawsitivelywell.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wedotech.pawsitivelywell.model.DogDetails;
import com.wedotech.pawsitivelywell.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long>{

	@Query("select u from UserDetails u where u.emailId = (:emailId)")
	UserDetails getUser(@Param("emailId") String emailId);
	
	@Query("select u.user_id from UserDetails u where u.emailId = (:emailId)")
	Long getUserId(@Param("emailId") String emailId);
	
	@Query("select ud.dog_id from UserDetails u join u.dogs ud where u.user_id=(:userId)")
	Set<Long> getDogIds(@Param("userId") Long userId);

	@Query("select d from DogDetails d where d.dog_id in (:dogIds)")
	Set<DogDetails> getDogsByIds(@Param("dogIds") Set<Long> dogIds);
	
	@Query("select d from UserDetails d JOIN d.dogs ud where ud.dog_id = (:userId)")
	Set<UserDetails> getDogsByUserId(@Param("userId") Long userId);
	
}
