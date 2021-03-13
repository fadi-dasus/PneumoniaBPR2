package com.bachelor.utility.controllerUnit;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.model.Image;
import com.bachelor.service.image.ImgService;
import com.bachelor.service.jms.IJMSService;

import reactor.core.publisher.Flux;

@Component
public class ResponseBuilderUtil {
	@Autowired
	ImgService imageService;
	@Autowired
	IJMSService jmsService;

	public ResponseEntity<?> submitImageResponseBuilder(Image img) {
		try {
			return aopSubmitImageSuccessfulResponseBuilder(img);
		} catch (URISyntaxException e) {
			return errorServerErrorResponseGenerator(e);
		}
	}
	
	private ResponseEntity<Image> aopSubmitImageSuccessfulResponseBuilder(Image img) throws URISyntaxException {
		return ResponseEntity.created(new URI("/getImage/" + img.getId())).eTag(Integer.toString(img.getVersion()))
				.body(img);
	}

	private ResponseEntity<Object> errorServerErrorResponseGenerator(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	public ResponseEntity<?> aopGetImageByIdResponseBuilder(Optional<Image> optional) {
		return optional.map(image -> {
			try {
				return ResponseEntity.ok().eTag(Integer.toString(image.getVersion()))
						.location(new URI("/getImage/" + image.getId())).body(image);
			} catch (URISyntaxException e) {
				return errorServerErrorResponseGenerator(e);
			}
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = NoSuchElementException.class, value = "jpaTransactionManager")
	public ResponseEntity<?> updateImageHelper(Image image, Integer ifMatch)  {
		
		try {
			Optional<Image> existingImage = imageService.aopGetImageById(image.getId());

			if (!existingImage.get().getVersion().equals(ifMatch)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			existingImage.get().setStatus(image.getStatus());
			Image updated = imageService.aopUpdate(existingImage.get());

			try {
				if (updated != null) {
					updated.setIssuer(image.getIssuer());
					jmsService.sendToUser(updated);
					return ResponseEntity.ok().location(new URI("/getImage/" + updated.getId()))
							.eTag(Integer.toString(updated.getVersion())).body(updated);
				}
			} catch (URISyntaxException e) {
				return errorServerErrorResponseGenerator(e);
			}
		}
		catch (NoSuchElementException | NoSuchFileException e) {
			errorServerErrorResponseGenerator(e);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Flux<Image>> aopGetAllImagesResponseBuilder(Flux<Image> fluxImages) {
		return new ResponseEntity<Flux<Image>>(fluxImages, HttpStatus.FOUND);
	}


}
