package com.codingchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.webservice.UserService;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.server.PathParam;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/testresults")
@Getter
@Setter // <--- Benutze Lombok Bibliothek (für Getter und Setter)
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

    @RequestMapping(value = "/getuserwebpage", method = RequestMethod.GET)
    public String getUserWebpage() {
        logger.info("-- Usercontroller.java - getUserWebpage()");
        return this.getName();
    }

    /**
     * Test in Konsole mit "curl
     * http://localhost:8080/testresults/stringe/IrgendeinString"
     */
    @RequestMapping(value = "/stringe/{inputstring}", method = RequestMethod.GET)
    public boolean stringE(@PathVariable("inputstring") String inputstring) {

        char c='e';
        logger.info("-- UserController -- stringE() - inputstring: " + inputstring);
        int count = checkLetter(inputstring, 0, c);


        return (count >= 1 && count <= 3) ? true : false;
    }

    private int checkLetter(String input, int index, char checkFor) {
        return (index >= input.length()) ? 0 : (input.charAt(index++) == checkFor ? 1 : 0) + checkLetter(input, index, checkFor);
    }

}
