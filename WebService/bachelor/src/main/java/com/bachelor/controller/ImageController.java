package com.bachelor.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.service.ImgService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {
	@Autowired
	ImgService imageService;
	

	@PostMapping("/saubmitImage")
	@Operation(summary = "Submit an image to be validated as Pneumonia or Normal")
	public ResponseEntity<String> saubmitImage(
	@Parameter(description = "Provide the directory in which the image can be found, optionally you can provide an preliminary diagnosis ")
	@RequestBody ImageDirectory dir) {
		Image img = imageService.saubmitImage(dir);
		return new ResponseEntity<String>(
				"thanks you will be notidfies when the prediction is ready your image Id is : "
						+ img.getId().toString(),
				HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "Get image by its Id from the database")
	@GetMapping("/getImage")
	public ResponseEntity<Optional<Image>> getImageById(@Parameter(description = "image Id")@RequestParam int id) {
		Optional<Image> img = imageService.getImageById(id);
		return new ResponseEntity<Optional<Image>>(img, HttpStatus.OK);
	}

	@Operation(summary = "Get all images from the database")
	@GetMapping("/getAllImage")
	public ResponseEntity<Iterable<Image>> getAllImages() {
		Iterable<Image> img = imageService.getAllImages();
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.OK);
	}

	@Operation(summary = "Insert all images from a specific directory into the database in one go, Note: images with unknown status must be submitted individually")
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<String> loadDB(
			@Parameter(description = "Provide the directory where the images can be found and make sure to specify the status for each directory i.e. Pneumonia or Normal")
			@RequestBody ImageDirectory path) {
		imageService.loadDB(path);
		return new ResponseEntity<String>("the database has been loaded with files from the provided path ",
				HttpStatus.OK);
	}

	@PutMapping("/updateStatus")
	@Operation(summary = "Update an image and move it from the temporary directory to its final destination based on its predicted status ")
	public ResponseEntity<Image> updateImageStatus(
			@Parameter(description = "Provide the image after the prediction process")
			@RequestBody Image img) throws Exception {
		Image updated = imageService.updateStatus(img);
		return new ResponseEntity<Image>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/cleardb")
	@Operation(summary = "REMOVE ALL THE DATA FROM THE DATABASE")
	public ResponseEntity<String> cleanDatabaseTotally() {
		imageService.removeAllImagesFromTheTable();
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
