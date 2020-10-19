package com.bachelor.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bachelor.model.Image;
import com.bachelor.service.ImgService;

@Component
public class ImageControllerUtil {
	@Autowired
	ImgService imageService;

	private static final Logger logger = LogManager.getLogger(ImageController.class);

	ResponseEntity<?> getImageByIdResponseBuilder(Optional<Image> optiona) {
		return optiona.map(image -> {
			try {
				return ResponseEntity.ok()
						.eTag(Integer.toString(image.getVersion()))
						.location(new URI("/getImage/" + image.getId())).body(image);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}).orElse(ResponseEntity.notFound().build());

	}

	ResponseEntity<?> submitImageResponseBuilder(Image img) {

		logger.info("Creating new image with id: {}, path: {}", img.getId(), img.getPhysicalPath());
		try {
			// Build a created response
			return ResponseEntity.created(new URI("/getImage/" + img.getId()))
					.eTag(Integer.toString(img.getVersion()))
					.body(img);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
