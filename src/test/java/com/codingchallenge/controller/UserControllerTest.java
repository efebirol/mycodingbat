package com.codingchallenge.controller;

import static org.junit.Assert.*;
import org.junit.Test;

import com.codingchallenge.controller.UserController;
import com.codingchallenge.webservice.UserService;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;	
	
	
	@Test
	public void testGetUserWebpage() throws Exception
	{
		String shouldTestname = "TestLombokGetterName";
		//given(reservationService.getRoomReservationsForDate("2017-01-01")).willReturn(mockReservationList);
        //this.mockMvc.perform(get("/testresults")).andExpect(status().isOk()).andExpect(content().string(containsString("Test, JUnit")));
		given(userController.getUserWebpage()).willReturn(shouldTestname);
		this.mockMvc.perform(get("/testresults")).andExpect(status().isOk());
	}

}
