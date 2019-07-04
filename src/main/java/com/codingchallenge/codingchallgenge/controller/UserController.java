package com.codingchallenge.codingchallgenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.codingchallgenge.webservice.UserService;

import lombok.Getter;
import lombok.Setter;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/testresults")
@Getter @Setter // <--- Benutze Lombok Bibliothek (für Getter und Setter)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;
	private String name;

	@Autowired
	public UserController(UserService userService) {

		logger.info("-- Usercontroller.java - Usercontroller() Konstruktor");
		this.userService = userService;
		this.setName("TestLombokGetterName");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUserWebpage() {
		logger.info("-- Usercontroller.java - getUserWebpage()");
		return this.getName();
	}
	
	

}
