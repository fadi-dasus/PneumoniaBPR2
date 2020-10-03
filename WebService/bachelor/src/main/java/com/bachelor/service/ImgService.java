package com.bachelor.service;

import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.model.Directory;

public interface ImgService {

	Image insertImage(String path);

	Optional<Image> getImageById(int id);

	Iterable<Image> getAllImages();

	void loadDB(Directory path);
}
