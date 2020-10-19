package com.bachelor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.model.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE image SET physical_path =?, status = ?,version=? WHERE Id =?", nativeQuery = true)
	void update(String physical_path, String status,Integer version, int Id);
	
}
