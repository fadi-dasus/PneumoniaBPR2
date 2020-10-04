//package com.bachelor.dao;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bachelor.model.Image;
//
//@Transactional
//@Repository
//public class TestManager {
//
//	@PersistenceContext
//	@Autowired
//	public EntityManager entityManager;
//
//	@Transactional
//	public void flush(Image img) {
//		Image image = entityManager.find(Image.class, 1);
//		entityManager.getTransaction().begin();
//		image.setPhysicalPath("somehingwehereeasdsa");
//		entityManager.getTransaction().commit();
//	}
//
//}