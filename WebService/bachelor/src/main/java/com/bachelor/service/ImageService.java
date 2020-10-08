package com.bachelor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.utility.FoldersPathtUtil;
import com.bachelor.utility.IFileManipulation;

@Service
public class ImageService implements ImgService {
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

	public Image updateStatus(Image img) throws Exception {
		Image updatedImage = fileManipulater.moveImageToItsAppropriateDirectory(img);
		dao.update(updatedImage.getPhysicalPath(), updatedImage.getStatus(), updatedImage.getId());
		return updatedImage;

	}

	@Transactional
	public void loadDB(ImageDirectory dir) {
		if (fileManipulater.getAllImagesInThePath(dir) != null) {
			dao.saveAll(fileManipulater.getAllImagesInThePath(dir));
		} else
			System.out.println("check file distination ");
	}

	public void removeAllImagesFromTheTable() {
		dao.deleteAll();

	}

	public Image saubmitImage(ImageDirectory dir) {
		//TODO update the sending message to a directory instead of image 
		jms.send(new Image(1,"E:\\7 semester\\BP\\dataset","Normal"));
		System.out.println("we will notify the ML with the path in here");

		return dao.save(new Image(FoldersPathtUtil.temporaryFolderDestination.concat(dir.getSourceImagePath()),
				dir.getStatus()));
	}

}
