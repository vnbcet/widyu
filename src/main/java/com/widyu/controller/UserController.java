package com.widyu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.widyu.service.UserService;

@RestController
@RequestMapping("/user/")
public class UserController {

	@Autowired
	UserService userservice;

	@GetMapping("check")
	public String test() {
		return "ok";
	}

	@PostMapping("saveuser")
	public ResponseEntity<?> saveUserDetails(@RequestBody String request) {
		return userservice.saveuserdetails(request);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody String request){
		return this.userservice.loginServ(request);
	}

}
