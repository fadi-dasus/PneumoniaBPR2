package com.bachelor.service.image;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.dao.ImageRepository;
import com.bachelor.model.Image;
import com.bachelor.model.ImageDirectory;
import com.bachelor.utility.files.IFileManipulation;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, value = "jpaTransactionManager")
public class ImageService implements ImgService {
	
	@Autowired
	IFileManipulation fileManipulater;
	
	@Autowired
	ImageRepository dao;
	
	public Image aopSubmitImage(Image img) {
		return dao.saveAndFlush(new Image(img.getPhysicalPath(), img.getStatus(), 0));
	}
	
	public Optional<Image> aopGetImageById(int id) {
		return dao.findById(id);

	}

	public Iterable<Image> getAllImages() {
		return dao.findAll();
	}
	
	@Transactional(rollbackFor = NoSuchFileException.class)
	public Image aopUpdate(Image img) throws NoSuchFileException {
		Image updatedImage = new Image();
			updatedImage = fileManipulater.moveImageToItsAppropriateDirectory(img);
			updatedImage.setVersion(updatedImage.getVersion() + 1);
			dao.update(updatedImage.getPhysicalPath(), updatedImage.getStatus(), updatedImage.getVersion() +1,
					updatedImage.getId());
				return updatedImage;

	}

	public List<Image> loadDB(ImageDirectory dir) {
		List<Image> list = fileManipulater.getAllImagesInThePath(dir);
		if (list.size() > 0) 
			  dao.saveAll(list);
		return list;
	}


}
