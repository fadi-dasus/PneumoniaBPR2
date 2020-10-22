package com.bachelor.service;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.controller.ImageController;
import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.utility.CONSTANTS;
import com.bachelor.utility.files.IFileManipulation;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, value = "jpaTransactionManager")
public class ImageService implements ImgService {
	private static final Logger logger = LogManager.getLogger(ImageController.class);

	@Autowired
	ImageRepository dao;
	@Autowired
	IFileManipulation fileManipulater;

	public Optional<Image> getImageById(int id) {
		return dao.findById(id);

	}

	public Iterable<Image> getAllImages() {
		return dao.findAll();
	}

	public Image update(Image img) {
		Image updatedImage = null;
		try {
			updatedImage = fileManipulater.moveImageToItsAppropriateDirectory(img);
			updatedImage.setVersion(updatedImage.getVersion() + 1);
			dao.update(updatedImage.getPhysicalPath(), updatedImage.getStatus(), updatedImage.getVersion(),
					updatedImage.getId());
		}
		 catch (Exception e) {
			logger.error(e);
		}
		return updatedImage;

	}

	public List<Image> loadDB(ImageDirectory dir) {
		List<Image> addedList = null;
		List<Image> list = fileManipulater.getAllImagesInThePath(dir);
		if (list != null) {
			addedList = dao.saveAll(list);
		} else
			logger.info("No images in the distination");
		return addedList;
	}

	public void removeAllImagesFromTheTable() {
		dao.deleteAll();

	}

	public Image saubmitImage(Image img) {
		img.setVersion(0);
		Image image = dao.saveAndFlush(new Image(CONSTANTS.temporaryFolderDestination.concat(img.getPhysicalPath()),
				img.getStatus(), img.getVersion()));
		return image;
	}

}
