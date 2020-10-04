package com.bachelor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.utility.FoldersPathtUtil;

@RestController
@RequestMapping("/bachelor/folderPath")
public class FoldersSettupController {

	@Autowired
	FoldersPathtUtil foldersPathtUtil;

	@GetMapping("/getFoldersPath")
	public ResponseEntity<String> insertNewImage() {

		return new ResponseEntity<String>(foldersPathtUtil.toString(), HttpStatus.OK);
	}

}
