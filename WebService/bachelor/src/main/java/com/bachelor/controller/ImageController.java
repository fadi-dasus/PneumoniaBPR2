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
import com.bachelor.service.ImageService;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {
	@Autowired
	ImageService imageService;

	@PostMapping("/insert")
	public ResponseEntity<Image> insertNewImage(@RequestBody ImageDirectory dir) {

		Image img = imageService.insertImage(dir.getPath());
		return new ResponseEntity<Image>(img, HttpStatus.OK);
	}

	@GetMapping("/getImage")
	public ResponseEntity<Optional<Image>> getImageById(@RequestParam int id) {
		Optional<Image> img = imageService.getImageById(id);
		return new ResponseEntity<Optional<Image>>(img, HttpStatus.OK);
	}

	@GetMapping("/getAllImage")
	public ResponseEntity<Iterable<Image>> getAllImages() {
		Iterable<Image> img = imageService.getAllImages();
		return new ResponseEntity<Iterable<Image>>(img, HttpStatus.OK);
	}

	@PostMapping("/loadPicturesIntoDB")
	public ResponseEntity<String> loadDB(@RequestBody ImageDirectory path) {
		imageService.loadDB(path);
		return new ResponseEntity<String>("the database has been loaded with files from the provided path ",
				HttpStatus.OK);
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<Image> updateImageStatus(@RequestBody Image img) {
		Image updated = imageService.updateStatus(img);
		return new ResponseEntity<Image>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/cleardb")
	public ResponseEntity<String> cleanDatabaseTotally() {
		imageService.removeAllImagesFromTheTable();
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
