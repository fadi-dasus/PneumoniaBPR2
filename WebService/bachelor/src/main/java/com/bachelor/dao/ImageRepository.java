package com.bachelor.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bachelor.model.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {

//	@Query(value = " SELECT imageguid FROM images where imageId =?", nativeQuery = true)
//	String getImageById(int n);

//	@Modifying
//	@Transactional
//	@Query(value = "BEGIN TRAN INSERT INTO dbo.images (imageblob)(SELECT * FROM OPENROWSET (BULK 'C:\\pic\\NORMAL2-IM-1257-0001.jpeg', SINGLE_BLOB) imageblol) SELECT CAST(scope_identity() AS int) COMMIT;", nativeQuery = true)
//	Image insertImage(@Param("path") String path);
}

//'C:\\pic\\NORMAL2-IM-1257-0001.jpeg'
