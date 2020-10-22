package com.bachelor.integration;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageInegrationGetNotFound {


	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ImageRepository repo;
	private static final String URL = "http://localhost:8080/bachelor/image/";

	
	@Test
	@DisplayName("Test Get Image Not Found")
	public void testGetImageNotFound() throws Exception {
		ResponseEntity<Image> responseEntity = restTemplate.getForEntity(URL + "getImage/0" , Image.class);
		int statusCode = responseEntity.getStatusCodeValue();
		Image image = responseEntity.getBody();
		assertNull(image);
		assertEquals(HttpStatus.NOT_FOUND.value(), statusCode);
	}
}
