package com.bachelor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void loadDB(String path) {

		dao.saveAll(getIAllImagesInThePath(path));
	}

	private List<Image> getIAllImagesInThePath(String path) {
		List<String> filesInFolder = null;

		try {
			filesInFolder = Files.walk(Paths.get(path)).filter(Files::isRegularFile).map(Path::toString)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getImageList(filesInFolder);
	}

	private List<Image> getImageList(List<String> filesInFolder) {
		List<Image> images = new ArrayList<Image>();

		for (int i = 0; i < filesInFolder.size(); i++) {
			images.add(new Image(filesInFolder.get(i), "Normal"));
		}
		return images;
	}

}
