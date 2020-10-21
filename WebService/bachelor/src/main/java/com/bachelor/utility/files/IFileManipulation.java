package com.bachelor.utility.files;

import java.nio.file.NoSuchFileException;
import java.util.List;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface IFileManipulation {
	List<Image> getAllImagesInThePath(ImageDirectory dir);

	Image moveImageToItsAppropriateDirectory(Image img) throws NoSuchFileException;
}
