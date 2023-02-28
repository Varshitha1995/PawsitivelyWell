package com.wedotech.pawsitivelywell.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wedotech.pawsitivelywell.model.DogDetails;

@Repository
public interface DogRepository extends JpaRepository<DogDetails, Long> {
	
	@Query("select d from DogDetails d where d.dog_id = (:dogId)")
	DogDetails getDog(@Param("dogId") Long dogId);
	
//	@Query("select d from DogDetails d where d.users.E")
//	DogDetails[] getDogsByUser(@Param("userId") Long userId);
	
//	@Query("select * from DogDetails d where d.users in (select u from UserDetails u where u.EmailId = (:emailId))")
//	DogDetails[] getDogsByEmail(@Param("emailId") String emailId);
	
//	@Query("select d from DogDetails d JOIN d.dogs u where u.EmailId = (:emailId)")
//	DogDetails[] getDogsByEmail(@Param("emailId") String emailId);
	
//	Set<DogDetails> findByUsers_EmailId(String EmailId);

}
