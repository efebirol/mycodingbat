package com.codingchallenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.helper.ZweiWerte;
import com.codingchallenge.webservice.CoderService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // <--- Benutze Lombok Bibliothek (für Getter und Setter)
@RestController
@RequestMapping("/testresults")
public class CoderController {

	private static final Logger logger = LoggerFactory.getLogger(CoderController.class);
	private final CoderService userService;
	private String name;

	@Autowired
	public CoderController(CoderService userService) {

		// logger.info("-- Usercontroller.java - Usercontroller() Konstruktor");

		this.userService = userService;
		this.setName("TestLombokGetterName");
	}

	@RequestMapping(value = "/getuserwebpage", method = RequestMethod.GET)
	public String getUserWebpage() {
		// logger.info("-- Usercontroller.java - getUserWebpage()");
		return this.getName();
	}

	/**
	 * Test in Konsole mit "curl" oder Browser.
	 * http://localhost:8080/testresults/stringe/IrgendeinString
	 */
	@RequestMapping(value = "/stringe/{inputstring}", method = RequestMethod.GET)
	public boolean stringE(@PathVariable("inputstring") String inputstring) {

		char c = 'e';
		// logger.info("-- UserController -- stringE() - inputstring: " + inputstring);
		int count = checkLetter(inputstring, 0, c);

		return (count >= 1 && count <= 3) ? true : false;
	}

	private int checkLetter(String input, int index, char checkFor) {
		return (index >= input.length()) ? 0
				: (input.charAt(index++) == checkFor ? 1 : 0) + checkLetter(input, index, checkFor);
	}

	/**
	 * Test in Konsole mit "curl" oder Browser.
	 * http://localhost:8080/testresults/lastdigit/IrgendeineNummer?id=8,3 Info:
	 * "PathVariable"-Annotation liest Werte aus URL, "RequestParam"-Annotation
	 * liest es über die URL-Variablen aus (".../?id=1,2,3" oder ".../?id=1&id=2")
	 */
	@RequestMapping(value = "/lastdigit/{inputnumberone}/{inputnumbertwo}", method = RequestMethod.GET)
	public boolean lastdigit(@PathVariable("inputnumberone") int inputnumberone,
			@PathVariable("inputnumbertwo") int inputnumbertwo) {
		// logger.info("-- UserController -- lastdigit() - inputstring Wert 1: " +
		// inputnumberone);
		// logger.info("-- UserController -- lastdigit() - inputstring Wert 2: " +
		// inputnumbertwo);

		int modValueOne = inputnumberone % 10;
		int modValueTwo = inputnumbertwo % 10;

		// logger.info("-- UserController -- lastdigit() - modValueOne Wert 1: " +
		// modValueOne);
		// logger.info("-- UserController -- lastdigit() - modValueTwo Wert 2: " +
		// modValueTwo);

		return (modValueOne == modValueTwo) ? true : false;
	}

	/**
	 * Given a string, return a new string where the last 3 chars are now in upper
	 * case. If the string has less than 3 chars, uppercase whatever is there. Note
	 * that str.toUpperCase() returns the uppercase version of a string.
	 * 
	 * endUp("Hello") → "HeLLO" endUp("hi there") → "hi thERE" endUp("hi") → "HI"
	 */
	@RequestMapping(value = "/testendup/{inputparamstring}", method = RequestMethod.GET)
	public String endup(@PathVariable ("inputparamstring") String inputstring ) {
		String result ="";
		int strlen = inputstring.length();
		int lastchars = 3;
		
		if (strlen >= lastchars) {
			result = inputstring.substring(0,strlen-lastchars)+(inputstring.substring(strlen-lastchars, strlen)).toUpperCase();
		}
		else {
			result = inputstring.toUpperCase();
		}
		
		return result;
	}

}
