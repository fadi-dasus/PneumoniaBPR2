package com.bachelor.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bachelor.model.Admin;
import com.bachelor.model.Image;

@Transactional
@Repository

public class TestManager {

	@PersistenceContext
	@Autowired
	public EntityManager entityManager;

	public void insertNewImage(String path) {
//		Image image = (Image) entityManager
//				.createNativeQuery("select * from Customer as a WHERE a.username = ?0", Image.class)
//				.setParameter(0, path).getSingleResult();
		int name = 1;
		Image image = (Image) entityManager.createNativeQuery("select * from image as a WHERE a.id = ?0", Image.class)
				.setParameter(0, name).getSingleResult();
		System.out.println("%%%%%%%%%%%%%%%");
//		System.out.println(image.toString());
		System.out.println("%%%%%%%%%%%%%%%");

	}

//	public boolean customerExist(String username) {
//
//		String jpql = "from Customer as a WHERE a.username = ?0 ";
//		int count = entityManager.createQuery(jpql).setParameter(0, username).getResultList().size();
//		return count > 0;
//
//	}

//	public void addCustomer(Customer customer) {
//
//		entityManager.persist(customer);
//
//	}
//
//	@Override
//	public Customer getCustomerByUsername(String username) {
//		Customer customer = (Customer) entityManager
//				.createNativeQuery("select * from Customer as a WHERE a.username = ?0", Customer.class)
//				.setParameter(0, username).getSingleResult();
//		return customer;
//	}

}