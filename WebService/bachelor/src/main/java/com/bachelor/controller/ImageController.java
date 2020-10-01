package com.bachelor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.service.ImageService;

@RestController
@RequestMapping("/bachelor/image")
public class ImageController {
	@Autowired
	ImageService imageService;

	@PostMapping("/insert")
	@ResponseBody
	public String getAdminByUsername(@RequestBody String path) {
	
		 Integer i = imageService.insertImage(path);
		

		return "Hello world";
	}

}
