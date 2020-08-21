package com.widyu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@Autowired
	UserRepository userrepository;

	public ResponseEntity<?> saveuserdetails(String request) {
		ResponseBean response=new ResponseBean();
		response.setStatus(HttpStatus.OK);
		response.setMessage("data saved");
		try {
			ObjectMapper mapper=new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			User user=mapper.readValue(request, User.class);
			userrepository.save(user);	
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("data not saved");
			response.setData(new ArrayList<>());
		}
		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
