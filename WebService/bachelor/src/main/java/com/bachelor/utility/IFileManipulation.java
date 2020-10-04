package com.bachelor.utility;

import java.util.List;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

public interface IFileManipulation {
	List<Image> getAllImagesInThePath(ImageDirectory dir);
}
