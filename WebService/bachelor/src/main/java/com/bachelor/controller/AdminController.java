package com.bachelor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bachelor.model.Admin;
import com.bachelor.service.AdminService;

@RestController
@RequestMapping("/bachelor/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@GetMapping("/username")
	public ResponseEntity<List<Admin>> getAdminByUsername(@RequestParam("username") String username) {
		List<Admin> admin = adminService.getAdminByUsername(username);
		return new ResponseEntity<List<Admin>>(admin, HttpStatus.OK);
	}

}
