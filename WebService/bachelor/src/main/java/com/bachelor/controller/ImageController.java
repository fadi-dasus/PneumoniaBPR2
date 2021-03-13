package com.bachelor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {

	@Autowired
	ImgService imageService;
	@Autowired
	IJMSService jmsService;
	@Autowired
	ResponseBuilderUtil imgUtil;

	@Operation(summary = CONSTANTS.getImageSummary)
	@GetMapping("/getImage")
	public ResponseEntity<?> GetImageByIdWithLog(@RequestParam Integer id) {
		Optional<Image> img = imageService.aopGetImageById(id);
		return imgUtil.aopGetImageByIdResponseBuilder(img);
	}

	@Operation(summary = CONSTANTS.submitImageSummary)
	@PostMapping("/saubmitImage")
	public ResponseEntity<?> saubmitImage(@RequestBody Image image) {
		Image img = imageService.aopSubmitImage(image);
		img.setIssuer(image.getIssuer());
		jmsService.sendToService(img);
		return imgUtil.submitImageResponseBuilder(img);
	}

	@Operation(summary = CONSTANTS.getAllImagesSummary)
	@GetMapping("/getAllImages")
	public ResponseEntity<Flux<Image>> getAllImages() {
		Flux<Image> fluxImages = Flux.fromIterable(imageService.getAllImages());
		return imgUtil.aopGetAllImagesResponseBuilder(fluxImages);
	}

	@Operation(summary = CONSTANTS.loadDbSummary)
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<?> loadDB(@RequestBody ImageDirectory path) {
		List<Image> list = imageService.loadDB(path);
		if (list.size() == 0) {
			return new ResponseEntity<String>(" No files found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(list.size() + CONSTANTS.loadDB, HttpStatus.OK);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "409 ", description = "Conflict E-Tag does not match", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "500 ", description = "Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "302 ", description = "Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "Could not find an image with this Id", content = @Content), })
	@PutMapping("/updateStatus")
	@Operation(summary = CONSTANTS.updateStatusSummary)
	public ResponseEntity<?> updateImageStatus(@RequestBody Image image, @RequestHeader("If-Match") Integer ifMatch) {
		return imgUtil.updateImageHelper(image, ifMatch);
	}

}
