package com.bachelor.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {
	@Autowired
	ImgService imageService;
	private static final Logger logger = LogManager.getLogger(ImageController.class);

	// TODO WRITE THE TEST
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Image has been found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "Image not found", content = @Content) })
	@Operation(summary = "Get image by its Id from the database")

	
	@GetMapping("/getImage")
	public ResponseEntity<?> getImageById(@Parameter(description = "image Id") @RequestParam int id) {

		Optional<Image> img = imageService.getImageById(id);
		if (!img.isPresent()) {
			return new ResponseEntity<String>("there is no image with this id:" + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<Image>>(img, HttpStatus.FOUND);

	}

	// TODO WRITE THE TEST

	@Operation(summary = "Submit an image")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The image has been submitted successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid directory", content = @Content) })
	@PostMapping("/saubmitImage")
	public ResponseEntity<String> saubmitImage(
			@Parameter(description = "Provide image directory,and the  preliminary diagnosis ") @RequestBody ImageDirectory dir) {
		Image img = imageService.saubmitImage(dir);
		return new ResponseEntity<String>("Image submited correctly: " + img.getId().toString(), HttpStatus.OK);
	}

	// TODO WRITE THE TEST

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Images has been found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "database is empty", content = @Content) })
	@Operation(summary = "Get all images from the database")
	@GetMapping("/getAllImage")
	public ResponseEntity<Iterable<Image>> getAllImages() {
		Iterable<Image> img = imageService.getAllImages();
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.OK);
	}

	// TODO WRITE THE TEST

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "database has been loaded successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }) })
	@Operation(summary = "Insert all images from a specific directory into the database in one go, Note: images with unknown status must be submitted individually")
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<String> loadDB(
			@Parameter(description = "Provide the directory where the images can be found and make sure to specify the status for each directory i.e. Pneumonia or Normal") @RequestBody ImageDirectory path) {
		imageService.loadDB(path);
		return new ResponseEntity<String>("the database has been loaded with files from the provided path ",
				HttpStatus.OK);
	}

	// TODO WRITE THE TEST

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Images has been found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "did not find an image with this Id", content = @Content), })
	@PutMapping("/updateStatus")
	@Operation(summary = "Update an image and move it from the temporary directory to its final destination based on its predicted status ")
	public ResponseEntity<Image> updateImageStatus(
			@Parameter(description = "Provide the image after the prediction process") @RequestBody Image img)
			throws Exception {
		Image updated = imageService.updateStatus(img);
		return new ResponseEntity<Image>(updated, HttpStatus.OK);
	}

	// TODO WRITE THE TEST

	@ApiResponse(responseCode = "200", description = "Database has no records", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) })
	@DeleteMapping("/cleardb")
	@Operation(summary = "REMOVE ALL THE DATA FROM THE DATABASE")
	public ResponseEntity<String> cleanDatabaseTotally() {
		imageService.removeAllImagesFromTheTable();
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
