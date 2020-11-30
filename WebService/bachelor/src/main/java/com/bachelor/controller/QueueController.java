package com.bachelor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bachelor/queue")
public class QueueController {
	@PostMapping("/registerQueue")
	public ResponseEntity<?> saubmitImage(@RequestBody String nickname) {
		System.out.println("Hello" + nickname);
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

}
