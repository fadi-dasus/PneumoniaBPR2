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
public class FileManipulationUtil implements IFileManipulation{

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
	public boolean moveImageToItsAppropriateDirectory(Image img) {
	if(img.getStatus().equalsIgnoreCase("Normal")) 
		img.setPhysicalPath(FoldersPathtUtil.normalImagesFolderPath.concat(img.getPhysicalPath().substring(img.getPhysicalPath().lastIndexOf("\\"))));
	else 
		img.setPhysicalPath(FoldersPathtUtil.pneumoniaImagesFolderPath.concat(img.getPhysicalPath().substring(img.getPhysicalPath().lastIndexOf("\\"))));
	
	System.out.println("////////////////////////");
	System.out.println(img.getPhysicalPath());
	System.out.println("////////////////////////");

		return true;
	}
}
