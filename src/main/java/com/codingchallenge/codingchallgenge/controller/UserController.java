package com.codingchallenge.codingchallgenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.codingchallgenge.webservice.UserService;
import org.slf4j.Logger;

@RestController
@RequestMapping("/testresults")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {

		System.out.println("-- Usercontroller.java - Usercontroller() Konstruktor");
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUserWebpage() {
		System.out.println("-- Usercontroller.java - getUserWebpage()");
		return "testresultpage";
	}

}
