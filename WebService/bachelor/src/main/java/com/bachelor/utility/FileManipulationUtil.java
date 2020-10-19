package com.bachelor.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;

@Component
public class FileManipulationUtil implements IFileManipulation {

	@Override
	public List<Image> getAllImagesInThePath(ImageDirectory dir) {
		List<Image> images = null;
		try {
			images = Files.walk(Paths.get(dir.getSourceImagePath())).filter(Files::isRegularFile)
					.map(p -> new Image(p.toString(), dir.getStatus())).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public Image moveImageToItsAppropriateDirectory(Image img) {
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
		if (img.getStatus().equalsIgnoreCase("Normal"))
			return concatNormal(img.getPhysicalPath());
		else
			return concatPneumonia(img.getPhysicalPath());

	}

	private String concatPneumonia(String physicalPath) {
		return FoldersPathtUtil.pneumoniaImagesFolderPath
				.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}

	private String concatNormal(String physicalPath) {
		return FoldersPathtUtil.normalImagesFolderPath.concat(physicalPath.substring(physicalPath.lastIndexOf("\\")));
	}
}
