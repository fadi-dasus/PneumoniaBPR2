package com.bachelor.service;

import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface ImgService {

	Optional<Image> getImageById(int id);

	Iterable<Image> getAllImages();

	void loadDB(ImageDirectory path);

	Image updateStatus(Image img) throws Exception;

	void removeAllImagesFromTheTable();

	Image saubmitImage(ImageDirectory dir);
}
