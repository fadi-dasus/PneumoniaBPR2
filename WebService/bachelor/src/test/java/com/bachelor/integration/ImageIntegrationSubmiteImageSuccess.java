package com.bachelor.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.After;
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
public class ImageIntegrationSubmiteImageSuccess {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ImageRepository repo;
	private static final String URL = "http://localhost:8081/bachelor/image/";
	private Image image = new Image("test Path", "test Status");
	private int id = 0;

	@Test
	@DisplayName("Test Submit Image")
	public void testSubmitImage() throws Exception {
		//Arrange
		ResponseEntity<Image> responseEntity = restTemplate.postForEntity(URL + "/saubmitImage", this.image,
				Image.class);	
		//Act
		int statusCode = responseEntity.getStatusCodeValue();
		Image resultImage = responseEntity.getBody();
		this.id = resultImage.getId();
		//Assert
		assertNotNull(resultImage);
		assertEquals(this.image.getPhysicalPath(),
				resultImage.getPhysicalPath());
		assertEquals(0, resultImage.getVersion());
		assertEquals(HttpStatus.CREATED.value(), statusCode);
	}
	//Tear down 
	@After
	public void removeImage() {
		repo.deleteById(this.id);
	}
}
