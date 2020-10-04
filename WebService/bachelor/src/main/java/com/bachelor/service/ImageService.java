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

	public Optional<Image> getImageById(int id) {
		return dao.findById(id);
	}

	public Iterable<Image> getAllImages() {
		return dao.findAll();
	}

	public Image updateStatus(Image img) throws Exception {
//TODO move the file 
//		System.out.println(" we need to move the image to the appropirate folder in this step before saving to db");
		if (fileManipulater.moveImageToItsAppropriateDirectory(img))
			return dao.saveAndFlush(img);
		else
			throw new Exception("we could not find the image ");

	}

	@Transactional
	public void loadDB(ImageDirectory dir) {
		// TODO choose what to do with this function

		System.out.println(
				"we need to move the files to their appropriat folder based on the status or it is the user resposability to put them in the correct folder");
		if (fileManipulater.getAllImagesInThePath(dir) != null) {
			dao.saveAll(fileManipulater.getAllImagesInThePath(dir));
		} else
			System.out.println("check file distination ");
	}

	public void removeAllImagesFromTheTable() {
		dao.deleteAll();

	}

	public Image saubmitImage(ImageDirectory dir) {
		// TODO send a message to the queue
		System.out.println("we will notify the ML with the path in here");

		return dao.save(new Image(FoldersPathtUtil.temporaryFolderDestination.concat(dir.getSourceImagePath()),
				dir.getStatus()));
	}

}
