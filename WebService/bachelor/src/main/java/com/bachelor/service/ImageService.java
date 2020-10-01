package com.bachelor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bachelor.dao.IImageRepository;
import com.bachelor.dao.TestManager;

@Service
public class ImageService implements IImageService {
	@Autowired
	public IImageRepository idao;

	@Autowired
	public TestManager t;

	@Override
	public Integer insertImage(String path) {
		t.insertNewImage("");
		return idao.insertImage("C:\\pic\\NORMAL2-IM-1257-0001.jpeg");

	}

}
