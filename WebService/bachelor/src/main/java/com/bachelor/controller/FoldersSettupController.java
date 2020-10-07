package com.bachelor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.model.Image;
import com.bachelor.service.ImageJMSService;
import com.bachelor.utility.FoldersPathtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/bachelor/folderPath")
public class FoldersSettupController {

	@Autowired
	FoldersPathtUtil foldersPathtUtil;
	@Autowired
	ImageJMSService jms;
	
	@Operation(summary = "Get all the directories used for saving images")
	@GetMapping("/getFoldersPath")
	public ResponseEntity<String> insertNewImage() {
		jms.send(new Image(null,"asd","fsfaf"));
		return new ResponseEntity<String>(foldersPathtUtil.toString(), HttpStatus.OK);
	}

}
