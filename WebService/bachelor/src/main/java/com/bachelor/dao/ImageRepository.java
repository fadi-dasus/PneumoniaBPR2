package com.bachelor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bachelor.model.Image;

@Transactional

public interface ImageRepository extends CrudRepository<Image, Integer> {

}