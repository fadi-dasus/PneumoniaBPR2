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
			logger.info("No files in the directory");
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public Image moveImageToItsAppropriateDirectory(Image img) throws NoSuchFileException {
		String newPath = concatinateString(img);
		moveToTheCorrectFolder(img.getPhysicalPath(), newPath);
		img.setPhysicalPath(newPath);
		return img;
	}

	private void moveToTheCorrectFolder(String oldPath, String newPath) {
		try {
			Files.move(Paths.get(oldPath), Paths.get(newPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String concatinateString(Image img) {
		return img.getStatus().equalsIgnoreCase("Normal") ? concatNormal(img.getPhysicalPath()) : concatPneumonia(img.getPhysicalPath());
//		if (img.getStatus().equalsIgnoreCase("Normal"))
//			return concatNormal(img.getPhysicalPath());
//		else
//			return concatPneumonia(img.getPhysicalPath());

	}

	private String concatPneumonia(String physicalPath) {
		return FoldersPathtUtil.pneumoniaImagesFolderPath
				.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}

	private String concatNormal(String physicalPath) {
		return FoldersPathtUtil.normalImagesFolderPath.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}
}
