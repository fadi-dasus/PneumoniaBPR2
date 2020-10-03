package com.bachelor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;

@Service
public class ImageService implements ImgService {
	@Autowired
	public ImageRepository dao;

	@Override
	public Image insertImage(String path) {

		return dao.save(new Image(path, "Unknown"));

	}

	public Optional<Image> getImageById(int id) {
		return dao.findById(id);
	}

	public Iterable<Image> getAllImages() {
		return dao.findAll();
	}

	public int loadDB(String path) {
		List<File> filesInFolder = null;
		try {
			filesInFolder = Files.walk(Paths.get(path)).filter(Files::isRegularFile)
					.map(Path::toFile).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		filesInFolder.forEach(System.out::println);
		return 100;
	}

}
