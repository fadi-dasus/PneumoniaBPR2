package com.bachelor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.service.image.ImgService;
import com.bachelor.service.jms.IJMSService;
import com.bachelor.utility.CONSTANTS;
import com.bachelor.utility.controllerUnit.ResponseBuilderUtil;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Flux;

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
	@GetMapping("/getImage")
	public ResponseEntity<?> getImageById(@RequestParam Integer id) {
				Optional<Image> img = imageService.getImageById(id);
		return imgUtil.getImageByIdResponseBuilder(img);
	}

	
	@Operation(summary = CONSTANTS.saubmitImageSummary)
	@PostMapping("/saubmitImage")
	public ResponseEntity<?> saubmitImage(@RequestBody Image image) {
		Image img = imageService.saubmitImage(image);
		if(img!=null) {
		jmsService.send(img);
		}
		return imgUtil.submitImageResponseBuilder(img);
	}

	@Operation(summary = CONSTANTS.getAllImagesSummary)
	@GetMapping("/getAllImages")
	public ResponseEntity<Flux<Image>> getAllImages() {
		Flux<Image> fluxImages = Flux.fromIterable(imageService.getAllImages());
		return new ResponseEntity<Flux<Image>>(fluxImages, HttpStatus.FOUND);
	}

	@Operation(summary = CONSTANTS.loadDbSummary)
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<?> loadDB(@RequestBody ImageDirectory path) {
		List<Image> list = imageService.loadDB(path);
		if (list.size() == 0 ) {
			return new ResponseEntity<String>(" No files found", HttpStatus.NO_CONTENT);
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
