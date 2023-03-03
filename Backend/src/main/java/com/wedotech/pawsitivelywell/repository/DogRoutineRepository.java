package com.wedotech.pawsitivelywell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wedotech.pawsitivelywell.model.DogRoutine;

public interface DogRoutineRepository  extends JpaRepository<DogRoutine, Long>{
	
	@Query("select d from DogRoutine d where d.dog = (select dog from DogDetails dog where dog.dog_id=(:dogId))")
	DogRoutine getRoutine(@Param("dogId") Long dogId);
}
