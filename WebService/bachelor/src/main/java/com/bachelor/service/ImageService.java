package com.bachelor.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bachelor.controller.ImageController;
import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.utility.FoldersPathtUtil;
import com.bachelor.utility.IFileManipulation;

@Service
public class ImageService implements ImgService {
	private static final Logger logger = LogManager.getLogger(ImageController.class);

	@Autowired
	ImageRepository dao;
	@Autowired
	IFileManipulation fileManipulater;
	@Autowired
	ImageJMSService jms;

	public Optional<Image> getImageById(int id) {
		return dao.findById(id);
	}

	public Iterable<Image> getAllImages() {
		return dao.findAll();
	}

	public Image updateStatus(Image img) throws NoSuchFileException {
		Image updatedImage = null;
		try {
			updatedImage  = fileManipulater.moveImageToItsAppropriateDirectory(img);
			dao.update(updatedImage.getPhysicalPath(), updatedImage.getStatus(),updatedImage .getId(),updatedImage.getVersion());
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return updatedImage;

	}

	@Transactional
	public void loadDB(ImageDirectory dir) {
		if (fileManipulater.getAllImagesInThePath(dir) != null) {
			dao.saveAll(fileManipulater.getAllImagesInThePath(dir));
		} else
			logger.info("check file distination");

	}

	public void removeAllImagesFromTheTable() {
		dao.deleteAll();

	}

	public Image saubmitImage(Image img) {

		Image image = dao
				.saveAndFlush(new Image(FoldersPathtUtil.temporaryFolderDestination.concat(img.getPhysicalPath()),
						img.getStatus(), img.getVersion()));
		jms.send(image);
		return image;
	}

}
