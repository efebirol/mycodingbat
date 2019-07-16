package com.codingchallenge.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.codingchallenge.helper.ZweiWerte;
import com.codingchallenge.webservice.CoderService;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CoderController.class)
public class CoderControllerTest {

	@MockBean
	private CoderService userService;
	@MockBean
	private CoderController userController;
	@Autowired
	private MockMvc mockMvc;

	@Before
	public void beforeTest() {
		// prüfen auf "null"
		when(userController.stringE(anyString())).thenCallRealMethod();
		when(userController.lastdigit(anyInt(), anyInt())).thenCallRealMethod();
		when(userController.endup(anyString())).thenCallRealMethod();
	}

	@Test
	public void testGetUserWebpage() throws Exception {
		String shouldTestname = "TestLombokGetterName";
		given(userController.getUserWebpage()).willReturn(shouldTestname);
		this.mockMvc.perform(get("/testresults/getuserwebpage")).andExpect(status().isOk());
	}

	/**
	 * Return true if the given string contains between 1 and 3 'e' chars.
	 * Testfälle: stringE("Hello") → true stringE("Heelle") → true
	 * stringE("Heelele") → false
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWhenTheParameter1Or2Or3eThenReturnTrue() throws Exception {

		check(new ArrayList<>(Arrays.asList((new String[] { "hello", "eee", "axel", "jelaldo" }))), Boolean.TRUE);
	}

	@Test
	public void testWhenTheParameterlesThen1OrMoreThen3EThenReturnTrue() throws Exception {
		check(new ArrayList<>(Arrays.asList((new String[] { "Tamr", "Flasch", "32343", "birol", "do" }))),
				Boolean.FALSE);
	}

	private void check(ArrayList<String> a, Boolean excepted) {
		a.stream().forEach(x -> {
			String content = null;
			try {
				content = invokeControllerStringe(x);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

			Assert.assertEquals(excepted.toString(), content);
		});
	}

	private String invokeControllerStringe(String param) throws Exception {
		MvcResult result = this.mockMvc
				.perform(get("/testresults/stringe/{inputstring}", param).accept("application/json"))
				.andExpect(status().isOk()).andReturn();
		return result.getResponse().getContentAsString();
	}

	private void testSimpleMethodeStringe() throws Exception {
		String param = "Axeldo";
		MvcResult result = this.mockMvc
				.perform(get("/testresults/stringe/{inputstring}", param).accept("application/json"))
				.andExpect(status().isOk()).andReturn();
		String retVal = result.getResponse().getContentAsString();
		Assert.assertEquals("true", retVal);

	}

	/**
	 * Given two non-negative int values, return true if they have the same last
	 * digit, such as with 27 and 57. Note that the % "mod" operator computes
	 * remainders, so 17 % 10 is 7. Testfälle: lastDigit(7, 17) → true, lastDigit(6,
	 * 17) → false, lastDigit(3, 113) → true.
	 */
	// Der eigentliche Test
	@Test
	public void testLastdigit() {
		List<ZweiWerte> numberListTrue = new ArrayList<ZweiWerte>();
		List<ZweiWerte> numberListFalse = new ArrayList<ZweiWerte>();

		// check TRUE Scenario
		numberListTrue.add(new ZweiWerte(7, 17));
		numberListTrue.add(new ZweiWerte(3, 113));
		numberListTrue.add(new ZweiWerte(114, 4));
		numberListTrue.add(new ZweiWerte(10, 0));

		// check FALSE Scenario
		checkLastDigit(numberListTrue, Boolean.TRUE);
		numberListFalse.add(new ZweiWerte(6, 17));
		numberListTrue.add(new ZweiWerte(114, 113));
		numberListTrue.add(new ZweiWerte(11, 0));
		checkLastDigit(numberListFalse, Boolean.FALSE);
	}

	// Aufruf mehrerer Tests
	private void checkLastDigit(List<ZweiWerte> numberList, Boolean excepted) {
		numberList.stream().forEach(x -> {
			String content = null;
			try {
				content = invokeControllerLastdigit(x);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}

			Assert.assertEquals(excepted.toString(), content);
		});
	}

	/* Aufruf des CoderController-Controllers für die lastdigit Methode */
	private String invokeControllerLastdigit(ZweiWerte listTwoValues) throws Exception {
		MvcResult result = this.mockMvc.perform(get("/testresults/lastdigit/{inputnumberone}/{inputnumberone}",
				listTwoValues.getValueOne(), listTwoValues.getValueTwo()).accept("application/json"))
				.andExpect(status().isOk()).andReturn();
		return result.getResponse().getContentAsString();
	}

	/**
	 * Given a string, return a new string where the last 3 chars are now in upper
	 * case. If the string has less than 3 chars, uppercase whatever is there. Note
	 * that str.toUpperCase() returns the uppercase version of a string.
	 * 
	 * endUp("Hello") → "HeLLO" endUp("hi there") → "hi thERE" endUp("hi") → "HI"
	 */
	
	
	/* Aufruf des CoderController-Controllers für die lastdigit Methode*/
	@Test
	public void TestEndUp() throws Exception
	{
		String testinput = "Hello";
		MvcResult result = this.mockMvc
				.perform(get("/testresults/testendup/{teststringparam}", testinput).accept("application/json"))
				.andExpect(status().isOk()).andReturn();
		
		Assert.assertEquals("xyz", result.getResponse().getContentAsString());
		
	}

}
