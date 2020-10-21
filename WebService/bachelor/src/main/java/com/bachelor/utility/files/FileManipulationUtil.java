package com.bachelor.utility.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bachelor.controller.ImageController;
import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.utility.CONSTANTS;

@Component
public class FileManipulationUtil implements IFileManipulation {
	private static final Logger logger = LogManager.getLogger(ImageController.class);

	@Override
	public List<Image> getAllImagesInThePath(ImageDirectory dir) {
		List<Image> images = null;
		try {
			images = Files.walk(Paths.get(dir.getSourceImagePath())).filter(Files::isRegularFile)
					.map(p -> new Image(p.toString(), dir.getStatus())).collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("No files in the directory");
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public Image moveImageToItsAppropriateDirectory(Image img) {
		try {
			String newPath = concatinateString(img);
			moveToTheCorrectFolder(img.getPhysicalPath(), newPath);
			img.setPhysicalPath(newPath);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return img;
	}

	private void moveToTheCorrectFolder(String oldPath, String newPath) throws IOException {
		try {
			Files.move(Paths.get(oldPath), Paths.get(newPath));
		} catch (NoSuchFileException e) {
			logger.error(e.getMessage());
		}

	}

	private String concatinateString(Image img) {
		return img.getStatus().equalsIgnoreCase("Normal") ? concatNormal(img.getPhysicalPath())
				: concatPneumonia(img.getPhysicalPath());

	}

	private String concatPneumonia(String physicalPath) {
		return CONSTANTS.pneumoniaImagesFolderPath.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}

	private String concatNormal(String physicalPath) {
		return CONSTANTS.normalImagesFolderPath.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}
}
