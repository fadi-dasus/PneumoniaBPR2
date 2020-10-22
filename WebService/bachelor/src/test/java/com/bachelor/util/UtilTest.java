package com.bachelor.util;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bachelor.model.Image;
import com.bachelor.utility.CONSTANTS;
import com.bachelor.utility.files.FileManipulationUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilTest {
	
	@Autowired
	 FileManipulationUtil fmu;
	
	
	
	
	@Test
	@DisplayName("Test Concatinate Path To The Normal Folder Path Success")
	void testConcaNormalSuccess() {
		
		Image mockImage = new Image(1,"\\mockPath","Normal",1);
		String result =fmu.concatinateString(mockImage);
		String expected  = CONSTANTS.normalImagesFolderPath + mockImage.getPhysicalPath();

		Assertions.assertEquals(result, expected,"image path should match this path");
		Assertions.assertNotSame(mockImage.getPhysicalPath(), result, "the Path should change");
	}	
	
	@Test
	@DisplayName("Test Concatinate Path To The Pneumonia Folder Path Success")
	void testConcatPneumoniaSuccess() {
		
		Image mockImage = new Image(1,"\\mockPath","Pneumonia",1);
		String result =fmu.concatinateString(mockImage);
		String expected  = CONSTANTS.pneumoniaImagesFolderPath + mockImage.getPhysicalPath();

		Assertions.assertEquals(result, expected,"image path should match this path");
		Assertions.assertNotSame(mockImage.getPhysicalPath(), result, "the Path should change");
	}
	
	

}
