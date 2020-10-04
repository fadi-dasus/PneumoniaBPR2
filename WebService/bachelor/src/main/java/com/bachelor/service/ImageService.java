package com.bachelor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Directory;
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

	public Image updateStatus(Image img) {
		return dao.saveAndFlush(img);

	}

	@Transactional
	public void loadDB(Directory dir) {
		if (getIAllImagesInThePath(dir) != null) {
			dao.saveAll(getIAllImagesInThePath(dir));
		} else
			System.out.println("check file distination ");
	}

	private List<Image> getIAllImagesInThePath(Directory dir) {
		List<Image> images = null;
		try {
			images = Files.walk(Paths.get(dir.getPath())).filter(Files::isRegularFile)
					.map(p -> new Image(p.toString(), dir.getStatus())).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return images;
	}

	public void removeAllImagesFromTheTable() {
		dao.deleteAll();

	}

}
