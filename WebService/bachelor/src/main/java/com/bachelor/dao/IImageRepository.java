package com.bachelor.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bachelor.model.Admin;

public interface IImageRepository extends CrudRepository<Admin, Integer> {
	// System.out.println(dao.getImageById(1));

	@Query(value = " SELECT imageguid FROM images where imageId =?", nativeQuery = true)
	String getImageById(int n);

	@Query(value = "BEGIN TRAN\r\n" + "INSERT INTO dbo.images (imageblob)\r\n"
			+ "(SELECT * FROM OPENROWSET (BULK 'C:\\pic\\NORMAL2-IM-1257-0001.jpeg', SINGLE_BLOB) imageblol) \r\n"
			+ "--get the insertionid\r\n" + "SELECT CAST(scope_identity() AS int);\r\n" + "COMMIT", nativeQuery = true)
	String insertImage();
}
