package com.bachelor.service;

import java.util.Optional;

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

}
