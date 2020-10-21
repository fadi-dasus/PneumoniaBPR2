package com.bachelor;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
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
import com.bachelor.model.ImageDirectory;
import com.bachelor.service.ImgService;
import com.bachelor.utility.IFileManipulation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImageServiceTest {

	@Autowired
	private ImgService service;

	@MockBean
	private ImageRepository repository;

	@MockBean
	private IFileManipulation fileManipulater;

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
		Iterable<Image> images = service.getAllImages();
		Assertions.assertEquals(2, ((List<Image>) images).size(), "findAll should return 2 images");
	}

	@Test
	@DisplayName("Test save image")
	void testSave() {
		Image mockImage = new Image(1, "mockPath", "Normal", 1);
		doReturn(mockImage).when(repository).saveAndFlush(any());

		Image returnedImage = service.saubmitImage(mockImage);
		Assertions.assertNotNull(returnedImage, "The saved image should not be null");
		Assertions.assertEquals(0, returnedImage.getVersion().intValue(), "The version for a new product should be 0");
	}

	@Test
	@DisplayName("Test update image success")
	void testUpdateSuccess() throws NoSuchFileException {
		Image mockImage = new Image(1, "mockPath", "Normal", 0);
		Image updatedImage = new Image(1, "new edited mockPath", "Normal", 0);
		doReturn(updatedImage).when(fileManipulater).moveImageToItsAppropriateDirectory(any());
		Image returnedImage = service.update(mockImage);

		Assertions.assertNotNull(returnedImage, "The updated image should not be null");
		Assertions.assertEquals(1, returnedImage.getVersion().intValue(),
				"The version for the updated image should be 1");
	}

	@Test
	@DisplayName("Test update image throws exception")
	void testUpdate() throws NoSuchFileException {
		Image mockImage = new Image(1, "mockPath", "Normal", 0);
		doThrow(NoSuchFileException.class).when(fileManipulater).moveImageToItsAppropriateDirectory(any());
		Image returnedImage = service.update(mockImage);
		Assertions.assertNull(returnedImage, "we should not update if there an exception occurs ");
	}

	@Test
	@DisplayName("Test insert images to the database Success")
	void testLoadDBSuccess() throws NoSuchFileException {
		List<Image> mockList = new ArrayList<>();
		Image mockImage1 = new Image(1, "mockPath", "Normal", 0);
		Image mockImage2 = new Image(2, "mockPath", "Normal", 0);
		mockList.add(mockImage1);
		mockList.add(mockImage2);

		doReturn(mockList).when(fileManipulater).getAllImagesInThePath(any());
		doReturn(mockList).when(repository).saveAll(mockList);

		List<Image> loadedList = service.loadDB(new ImageDirectory("path", "Normal"));
		Assertions.assertNotNull(loadedList);
		Assertions.assertEquals(2, loadedList.size());

	}

//	

}
