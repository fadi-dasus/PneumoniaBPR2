package com.bachelor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bachelor.utility.CONSTANTS;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/bachelor/folderPath")
public class FoldersSettupController {


	@Operation(summary = CONSTANTS.folderSummary)
	@GetMapping("/getFoldersPath")
	public ResponseEntity<String> insertNewImage() {

		return new ResponseEntity<String>(CONSTANTS.temporaryFolderDestination, HttpStatus.OK);
	}

}
