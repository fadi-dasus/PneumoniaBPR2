package com.bachelor.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.After;
import org.junit.Before;
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
public class ImageIntegrationGetImageFound {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ImageRepository repo;
	private static final String URL = "http://localhost:8081/bachelor/image/";

	private Image img = new Image("mockPath","MockStatus",1);

	@Before
	public void addImage() {
		this.img = repo.save(img);
	}

	@Test
	@DisplayName("Test Get Image Success")
	public void testGetImageSuccess() throws Exception {
		ResponseEntity<Image> responseEntity = restTemplate.getForEntity(URL + "getImage/" + this.img.getId(),
				Image.class);
		int statusCode = responseEntity.getStatusCodeValue();
		Image image = responseEntity.getBody();
		assertEquals(this.img.getId(), image.getId());
		assertEquals(HttpStatus.OK.value(), statusCode);
		assertNotNull(image);
	}

	@After
	public void removeImage() {

		repo.delete(this.img);
	}


	

}
