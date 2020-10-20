package com.bachelor;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.aspectj.lang.annotation.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.utility.FoldersPathtUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	ImageRepository repo;
	private static final String URL ="http://localhost:8080/bachelor/image/";
	
	@Test
	public void testGetImageSuccess () throws Exception{
		ResponseEntity<Image> responseEntity = restTemplate.getForEntity(URL+ "getImage/2", Image.class);
		int statusCode = responseEntity.getStatusCodeValue();
		Image image = responseEntity.getBody();
		assertEquals(2,image.getId());
		assertEquals(HttpStatus.OK.value(), statusCode);
		assertNotNull(image);
	}
	@Test
	public void testGetImageNotFound () throws Exception{
		ResponseEntity<Image> responseEntity = restTemplate.getForEntity(URL+ "getImage/1000", Image.class);
		int statusCode = responseEntity.getStatusCodeValue();
		Image image = responseEntity.getBody();
		assertNull(image);
		assertEquals(HttpStatus.NOT_FOUND.value(), statusCode);
	}
	
	@Test
	public void testSubmitImage() throws Exception{
		Image image = new Image("test Path","test Status");
		ResponseEntity<Image> responseEntity = restTemplate.postForEntity(URL+ "/saubmitImage",image, Image.class);
		int statusCode = responseEntity.getStatusCodeValue();
		Image resultImage = responseEntity.getBody();
		assertNotNull(resultImage);
		System.out.println(resultImage.getPhysicalPath());
		assertEquals(FoldersPathtUtil.temporaryFolderDestination.concat(image.getPhysicalPath()), resultImage.getPhysicalPath());
		assertEquals(0, resultImage.getVersion());
		assertEquals(HttpStatus.CREATED.value(), statusCode);
		repo.deleteById(resultImage.getId());
	}	

	
}
