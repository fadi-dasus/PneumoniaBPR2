package com.bachelor.service;

import java.util.List;

import com.bachelor.model.Admin;


public interface IAdminService {


	public List<Admin> getAdminByUsername(String username);

}