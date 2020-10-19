package com.bachelor.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
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
import com.bachelor.service.ImgService;

import io.swagger.v3.oas.annotations.Operation;
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
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.FOUND);
	}


	@Operation(summary = loadDbSummary)
	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<String> loadDB(@RequestBody ImageDirectory path) {
		imageService.loadDB(path);
		return new ResponseEntity<String>("the database has been loaded with all the files from the provided path ",
				HttpStatus.OK);
	}

	

	
	

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Images has been found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Image.class)) }),
			@ApiResponse(responseCode = "404", description = "did not find an image with this Id", content = @Content), })
	@PutMapping("/updateStatus")
	@Operation(summary = updateStatusSummary)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<?> updateImageStatus(@RequestBody Image image,@RequestHeader("If-Match") Integer ifMatch) {
		Optional<Image> existingImage = imageService.getImageById(image.getId());
	      return existingImage.map(p -> {
	            // Compare the etags
	    	  System.out.println(p.getVersion().equals(ifMatch));
	            if (!p.getVersion().equals(ifMatch)) {
	                return ResponseEntity.status(HttpStatus.CONFLICT).build();
	            }
	            // Update the image
	            p.setPhysicalPath(image.getPhysicalPath());
	            p.setStatus(image.getStatus());
	            p.setVersion(p.getVersion() + 1);	          

	            try {
	                // Update the product and return an ok response
	                if (imageService.update(p)!=null) {
	                    return ResponseEntity.ok()
	                            .location(new URI("/getImage/" + p.getId()))
	                            .eTag(Integer.toString(p.getVersion()))
	                            .body(p);
	                } else {
	                    return ResponseEntity.notFound().build();
	                }
	            } catch (URISyntaxException e) {
	                // An error occurred trying to create the location URI, return an error
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	            }

	        }
	      ).orElse(ResponseEntity.notFound().build());
		
		
		
		
		
		
		
		
		
		
//		
//		Image updated;
//		try {
//			updated = imageService.updateStatus(img);
//			return new ResponseEntity<Image>(updated, HttpStatus.OK);
//		} catch (NoSuchFileException e) {
//			e.printStackTrace();
//			return new ResponseEntity<String>("no such file in the provided directory", HttpStatus.OK);
//		}
		
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
