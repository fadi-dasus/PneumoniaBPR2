package com.bachelor.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.service.ImgService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {
	final String getImageSummary = "Get image by its Id from the database";
	final String updateStatusSummary = "Update an image and move it from the temporary directory to its final destination based on its predicted status ";
	final String saubmitImageSummary = "Insert a new image, it accepts image directory,and the  preliminary diagnosis";
	final String loadDbSummary = "Insert all images from a specific directory into the database in one go, Note: images with unknown status must be submitted individually";

	@Autowired
	ImgService imageService;
	@Autowired
	ImageControllerUtil imgUtil;

	
	@Operation(summary = getImageSummary)
	@GetMapping("/getImage/{id}")
	public ResponseEntity<?> getImageById(@PathVariable Integer id) {
		Optional<Image> img = imageService.getImageById(id);
		return imgUtil.getImageByIdResponseBuilder(img);
	}

	@Operation(summary = saubmitImageSummary)
	@PostMapping("/saubmitImage")

	public ResponseEntity<?> saubmitImage(@RequestBody Image image) {
		
		Image img = imageService.saubmitImage(image);
		return imgUtil.submitImageResponseBuilder(img);
	}
	

	@Operation(summary = "Get all images from the database")
	@GetMapping("/getAllImage")
	public ResponseEntity<Iterable<Image>> getAllImages() {
		Iterable<Image> img = imageService.getAllImages();
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.OK);
	}

	// TODO Write a Test

	@Operation(summary = loadDbSummary)
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<String> loadDB(@RequestBody ImageDirectory path) {
		imageService.loadDB(path);
		return new ResponseEntity<String>("the database has been loaded with all the files from the provided path ",
				HttpStatus.OK);
	}

	// TODO Write a Test

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Images has been found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "did not find an image with this Id", content = @Content), })
	@PutMapping("/updateStatus")
	@Operation(summary = updateStatusSummary)
	public ResponseEntity<Image> updateImageStatus(
			@Parameter(description = "Provide the image after the prediction process") @RequestBody Image img)
			throws Exception {
		// TODO Match the file, using etag
		Image updated = imageService.updateStatus(img);
		return new ResponseEntity<Image>(updated, HttpStatus.OK);
	}

	// TODO Write a Test
	@ApiResponse(responseCode = "200", description = "Database has no records", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) })
	@DeleteMapping("/cleardb")
	@Operation(summary = "REMOVE ALL THE DATA FROM THE DATABASE")
	public ResponseEntity<String> cleanDatabaseTotally() {
		imageService.removeAllImagesFromTheTable();
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
