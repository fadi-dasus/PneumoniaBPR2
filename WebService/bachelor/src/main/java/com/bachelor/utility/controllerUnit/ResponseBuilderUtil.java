package com.bachelor.utility.controllerUnit;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.controller.ImageController;
import com.bachelor.model.Image;
import com.bachelor.service.image.ImgService;

@Component
public class ResponseBuilderUtil {
	@Autowired
	ImgService imageService;

	private static final Logger logger = LogManager.getLogger(ImageController.class);

	public ResponseEntity<?> getImageByIdResponseBuilder(Optional<Image> optional) {
		return optional.map(image -> {
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

	public ResponseEntity<?> submitImageResponseBuilder(Image img) {

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
	
	
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = NoSuchElementException.class,value ="jpaTransactionManager")
	public
	 ResponseEntity<?> updateImageHelper(Image image, Integer ifMatch) {
		 try {

				Optional<Image> existingImage = imageService.getImageById(image.getId());
		
				if (!existingImage.get().getVersion().equals(ifMatch)) {
					return ResponseEntity.status(HttpStatus.CONFLICT).build();
				}
				Image updated = imageService.update(existingImage.get());
				
				try {
					if (updated != null) {
						return ResponseEntity.ok().location(new URI("/getImage/" + updated.getId()))
								.eTag(Integer.toString(updated.getVersion())).body(updated);
					}
				} catch (URISyntaxException e) {
					logger.error(e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} 
		 // for the optional object 
		 catch (NoSuchElementException e) {
				logger.error(e.getMessage());
			}
			
			 	return ResponseEntity.notFound().build();

	}
	
	
	
	
	
	
}
