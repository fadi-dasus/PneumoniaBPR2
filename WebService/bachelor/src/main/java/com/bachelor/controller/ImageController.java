package com.bachelor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.service.IJMSService;
import com.bachelor.service.ImgService;
import com.bachelor.utility.CONSTANTS;
import com.bachelor.utility.controllerUnit.ResponseBuilderUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {


	@Autowired
	ImgService imageService;
	@Autowired
	ResponseBuilderUtil imgUtil;
	@Autowired
	IJMSService jmsService;

	@Operation(summary = CONSTANTS.getImageSummary)
	@GetMapping("/getImage/{id}")
	public ResponseEntity<?> getImageById(@PathVariable Integer id) {
		Optional<Image> img = imageService.getImageById(id);
		return imgUtil.getImageByIdResponseBuilder(img);
	}

	@Operation(summary = CONSTANTS.saubmitImageSummary)
	@PostMapping("/saubmitImage")
	public ResponseEntity<?> saubmitImage(@RequestBody Image image) {
		Image img = imageService.saubmitImage(image);
		jmsService.send(img);
		return imgUtil.submitImageResponseBuilder(img);
	}

	@Operation(summary = CONSTANTS.getAllImagesSummary)
	@GetMapping("/getAllImages")
	public ResponseEntity<Iterable<Image>> getAllImages() {
		Iterable<Image> img = imageService.getAllImages();
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.FOUND);
	}

	@Operation(summary = CONSTANTS.loadDbSummary)
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<?> loadDB(@RequestBody ImageDirectory path) {
		List<Image> list = imageService.loadDB(path);
		if (list.size() == 0) {
			new ResponseEntity<String>(" No files found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(list.size() + CONSTANTS.loadDB, HttpStatus.OK);
	}

	@PutMapping("/updateStatus")
	@Operation(summary = CONSTANTS.updateStatusSummary)
	public ResponseEntity<?> updateImageStatus(@RequestBody Image image, @RequestHeader("If-Match") Integer ifMatch) {
		return imgUtil.updateImageHelper(image, ifMatch);

	}

	@DeleteMapping("/cleardb")
	@Operation(summary = CONSTANTS.deleteSummary)
	public ResponseEntity<String> cleanDatabaseTotally() {
		imageService.removeAllImagesFromTheTable();
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
