package com.widyu.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.widyu.entities.User;
import com.widyu.repository.UserRepository;
import com.widyu.utility.ResponseBean;

@Service
public class UserService {

	private static final PasswordEncoder pwencode = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Autowired
	UserRepository userrepository;

	public ResponseEntity<?> saveuserdetails(String request) {
		ResponseBean response = new ResponseBean();
		response.setStatus(HttpStatus.OK);
		response.setMessage("data saved");
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			User user = mapper.readValue(request, User.class);
			String username = user.getUserName();
			User userData = userrepository.findByUserName(username);
			if (userData != null) {
				response.setStatus(HttpStatus.EXPECTATION_FAILED);
				response.setMessage("username already exists");
			}
			String number = user.getMobileNumber();
			User data = userrepository.findByMobileNumber(number);
			if (data != null) {
				response.setStatus(HttpStatus.EXPECTATION_FAILED);
				response.setMessage("mobilenumber already exists");
			}
			String email = user.getEmail();
			User useremail = userrepository.findByEmail(email);
			if (useremail != null) {
				response.setStatus(HttpStatus.EXPECTATION_FAILED);
				response.setMessage("email already exists");
			} else {
				userrepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("data not saved");
			response.setData(new ArrayList<>());
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	public ResponseEntity<?> loginServ(String request) {
		ResponseBean response = new ResponseBean();
		response.setStatus(HttpStatus.OK);
		response.setMessage("login successfully");
		try {
			JsonObject object=Json.parse(request).asObject();
			String username = object.get("username").asString();
			String password = object.get("password").asString();
			
			if(username == null || username.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("username is empty");
				return ResponseEntity.status(response.getStatus()).body(response);
			}
			
			if(password == null || password.isEmpty()) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("password is empty");
				return ResponseEntity.status(response.getStatus()).body(response);
			}
			
			User user = userrepository.findByUserName(username);
			if(user == null) {
				response.setStatus(HttpStatus.EXPECTATION_FAILED);
				response.setMessage("invalid username");
				return ResponseEntity.status(response.getStatus()).body(response);
			}
			
			if (pwencode.matches(password, user.getPassword())) {
				response.setData(user);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("error while login");
			response.setData(new ArrayList<>());
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
