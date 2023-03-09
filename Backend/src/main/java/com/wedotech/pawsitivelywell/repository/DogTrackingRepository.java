package com.wedotech.pawsitivelywell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wedotech.pawsitivelywell.model.Tracking;

public interface DogTrackingRepository  extends JpaRepository<Tracking, Long>{

	@Query("select t from Tracking t where t.type=(:type) and t.dog=(select dog from DogDetails dog where dog.dog_id=(:dogId))")
	List<Tracking> getTracking(@Param("dogId") Long dogId, @Param("type") String type);
}
