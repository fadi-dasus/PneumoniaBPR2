package com.bachelor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bachelor.model.Admin;


public interface AdminRepository extends CrudRepository<Admin, Integer> {
	public List<Admin> findByUsername(String username);
}
