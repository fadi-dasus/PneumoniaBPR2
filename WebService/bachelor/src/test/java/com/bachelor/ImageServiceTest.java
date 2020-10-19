package com.bachelor;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.service.ImgService;



@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImageServiceTest {
	
 
    @Autowired
    private ImgService service;

    @MockBean
    private ImageRepository repository;

    
	Image mockImage = new Image(1, "mockPath", "Normal", 1);

    @Test
    @DisplayName("Test findById Success")
    void testgetImageByIdSuccess() {
    	Image mockImage = new Image(1, "mockPath", "Normal", 1);
        doReturn(Optional.of(mockImage)).when(repository).findById(1);
        Optional<Image> returnedImage = service.getImageById(1);
        Assertions.assertTrue(returnedImage.isPresent(), "Image was not found");
        Assertions.assertSame(returnedImage.get(), mockImage, "Image should be the same");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testgetImageByIdNotFound() {
        doReturn(Optional.empty()).when(repository).findById(1);
        Optional<Image> returnedImage = service.getImageById(1);
        Assertions.assertFalse(returnedImage.isPresent(), "Image was found, when it shouldn't be");
    }
    
    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        // Setup our mock
    	Image mockImage = new Image(1, "mockPath", "Normal", 1);
    	Image mockImage2 = new Image(2, "mockPath", "Pneumonia", 1);
        doReturn(Arrays.asList(mockImage, mockImage2)).when(repository).findAll();
        Iterable<Image> images =  service.getAllImages();
        Assertions.assertEquals(2, ((List<Image>) images).size(), "findAll should return 2 images");
    }
    
    
    @Test
    @DisplayName("Test save image")
    void testSave() {
    	Image mockImage = new Image(1, "mockPath", "Normal");
        doReturn(mockImage).when(repository).saveAndFlush(any());

        Image returnedImage = service.saubmitImage(mockImage);
        Assertions.assertNotNull(returnedImage, "The saved image should not be null");
        Assertions.assertEquals(0, returnedImage.getVersion().intValue(),
                "The version for a new product should be 0");
    }
    
    

//	void removeAllImagesFromTheTable();
}
