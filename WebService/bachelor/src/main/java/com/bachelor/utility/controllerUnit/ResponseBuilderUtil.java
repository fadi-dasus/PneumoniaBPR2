package com.bachelor.utility.controllerUnit;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
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
import com.bachelor.service.jms.IJMSService;

@Component
public class ResponseBuilderUtil {
	@Autowired
	ImgService imageService;
	@Autowired
	IJMSService jmsService;

	private static final Logger logger = LogManager.getLogger(ImageController.class);

	public ResponseEntity<?> submitImageResponseBuilder(Image img) {
		try {
			return submitImageSuccessfulResponseBuilder(img);
		} catch (URISyntaxException e) {
			return serverErrorResponseGenerator(e);
		}
	}

	
	public ResponseEntity<?> getImageByIdResponseBuilder(Optional<Image> optional) {
		return optional.map(image -> {
			try {
				return ResponseEntity.ok().eTag(Integer.toString(image.getVersion()))
						.location(new URI("/getImage/" + image.getId())).body(image);
			} catch (URISyntaxException e) {
				return serverErrorResponseGenerator(e);
			}
		}).orElse(ResponseEntity.notFound().build());
	}

	
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = NoSuchElementException.class, value = "jpaTransactionManager")
	public ResponseEntity<?> updateImageHelper(Image image, Integer ifMatch)  {
		
		try {
			Optional<Image> existingImage = imageService.getImageById(image.getId());

			if (!existingImage.get().getVersion().equals(ifMatch)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			existingImage.get().setStatus(image.getStatus());
			Image updated = imageService.update(existingImage.get());

			try {
				if (updated != null) {
					updated.setIssuer(image.getIssuer());
					jmsService.sendToUser(updated);
					return ResponseEntity.ok().location(new URI("/getImage/" + updated.getId()))
							.eTag(Integer.toString(updated.getVersion())).body(updated);
				}
			} catch (URISyntaxException e) {
				return serverErrorResponseGenerator(e);
			}
		}
		catch (NoSuchElementException | NoSuchFileException e) {
			logger.error(e.getMessage());
		}

		return ResponseEntity.notFound().build();

	}

	private ResponseEntity<Image> submitImageSuccessfulResponseBuilder(Image img) throws URISyntaxException {
		return ResponseEntity.created(new URI("/getImage/" + img.getId())).eTag(Integer.toString(img.getVersion()))
				.body(img);
	}

	private ResponseEntity<Object> serverErrorResponseGenerator(URISyntaxException e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
