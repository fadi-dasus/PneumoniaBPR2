package com.bachelor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bachelor.dao.AdminRepository;
import com.bachelor.model.Admin;

@Service
public class AdminService implements IAdminService {
	@Autowired
	public AdminRepository dao;

	public List<Admin> getAdminByUsername(String username) {

		return dao.findByUsername(username);
	}

}