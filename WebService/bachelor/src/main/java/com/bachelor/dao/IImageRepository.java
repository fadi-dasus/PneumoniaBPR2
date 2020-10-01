package com.bachelor.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bachelor.model.Image;

public interface IImageRepository extends CrudRepository<Image, Integer> {
	// System.out.println(dao.getImageById(1));

	@Query(value = " SELECT imageguid FROM images where imageId =?", nativeQuery = true)
	String getImageById(int n);

	@Modifying
	@Transactional
	@Query(value = "BEGIN TRAN INSERT INTO dbo.images (imageblob)(SELECT * FROM OPENROWSET (BULK 'C:\\pic\\NORMAL2-IM-1257-0001.jpeg', SINGLE_BLOB) imageblol) SELECT CAST(scope_identity() AS int) COMMIT;"

			, nativeQuery = true)
	Integer insertImage(@Param("path") String path);
}

//'C:\\pic\\NORMAL2-IM-1257-0001.jpeg'
