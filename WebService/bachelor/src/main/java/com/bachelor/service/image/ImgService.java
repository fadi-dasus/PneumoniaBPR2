package com.bachelor.service.image;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface ImgService {

	Optional<Image> aopGetImageById(int id);

	Iterable<Image> getAllImages();

	List<Image> loadDB(ImageDirectory path);

	Image aopUpdate(Image img) throws NoSuchFileException;

	Image aopSubmitImage(Image img);
}
