package com.bachelor.service;

import java.util.Optional;

import com.bachelor.model.Image;

public interface ImgService {

	Image insertImage(String path);

	Optional<Image> getImageById(int id);

	 Iterable<Image> getAllImages();
}
