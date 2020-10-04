package com.bachelor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.model.Image;

@Transactional

public interface ImageRepository extends JpaRepository<Image, Integer> {

//	void updateStatus(Image img);
//	
////	   @Modifying
////	    @Query("UPDATE Company c SET c.address = :address WHERE c.id = :companyId.")
////	    int updateStatus(@Param("companyId") String companyId, @Param("address") String address);

}