package com.bachelor.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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

	ResponseEntity<?> getImageByIdResponseBuilder(Optional<Image> optiona) {
		return optiona.map(image -> {
			try {
				return ResponseEntity.ok().location(new URI("/getImage/" + image.getId())).body(image);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}).orElse(ResponseEntity.notFound().build());

	}
	ResponseEntity<?> submitImageResponseBuilder(Image img){
		//TODO CHECK IF THE IMAGE IN JSON IS THE SAME AS THE IMAGE WE ARE EXPECTING
//		if (!(img instanceof Image)) {
//			return new ResponseEntity<String>("Could not insert the image to the database please check the payload:" ,HttpStatus.NOT_ACCEPTABLE);
//		}
		return new ResponseEntity<String>("The image has been submitted successfully: " + img.getId().toString(),HttpStatus.CREATED);
		
	}

}
