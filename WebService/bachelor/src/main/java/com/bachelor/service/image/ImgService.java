package com.bachelor.service.image;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface ImgService {

	Optional<Image> getImageById(int id);

	Iterable<Image> getAllImages();

	List<Image> loadDB(ImageDirectory path);

	Image update(Image img) throws NoSuchFileException;

	void removeAllImagesFromTheTable();

	Image saubmitImage(Image img);
}
