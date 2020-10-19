package com.bachelor.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface ImgService {

	Optional<Image> getImageById(int id);

	Iterable<Image> getAllImages();

	void loadDB(ImageDirectory path);

//	Image update(Image img) throws NoSuchFileException ;
	
	boolean update(Image img);

	void removeAllImagesFromTheTable();

	Image saubmitImage(Image img);
}
