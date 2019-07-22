package com.codingchallenge.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.codingchallenge.helper.ZweiWerte;
import com.codingchallenge.webservice.CoderService;


/**
 * @author Efe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CoderController.class)
public class CoderControllerTest
{

  @MockBean
  private CoderService userService;

  @MockBean
  private CoderController userController;

  @Autowired
  private MockMvc mockMvc;

  /**
   * Prüfen das Response für Mock etwas züruck gibt (nicht "null"), sonst Aufruf der echten Methode.
   */
  @Before
  public void beforeTest()
  {
    // "
    when(userController.stringE(anyString())).thenCallRealMethod();
    when(userController.lastdigit(anyInt(), anyInt())).thenCallRealMethod();
    when(userController.endup(anyString())).thenCallRealMethod();
    when(userController.everynth(anyString(), anyInt())).thenCallRealMethod();
  }

  /**
   * 
   */
  @Test
  public void testGetUserWebpage() throws Exception
  {
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
  public void testWhenTheParameter1Or2Or3eThenReturnTrue() throws Exception
  {

    check(new ArrayList<>(Arrays.asList(new String[]{"hello", "eee", "axel", "jelaldo"})), Boolean.TRUE);
  }

  /**
   * 
   */
  @Test
  public void testWhenTheParameterlesThen1OrMoreThen3EThenReturnTrue() throws Exception
  {
    check(new ArrayList<>(Arrays.asList(new String[]{"Tamr", "Flasch", "32343", "birol", "do"})), Boolean.FALSE);
  }

  private void check(ArrayList<String> a, Boolean excepted)
  {
    a.stream().forEach(x -> {
      String content = null;
      try
      {
        content = invokeControllerStringe(x);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Assert.fail();
      }

      Assert.assertEquals(excepted.toString(), content);
    });
  }

  private String invokeControllerStringe(String param) throws Exception
  {
    MvcResult result = this.mockMvc.perform(get("/testresults/stringe/{inputstring}", param).accept("application/json")).andExpect(status().isOk()).andReturn();
    return result.getResponse().getContentAsString();
  }

  /**
   * Given two non-negative int values, return true if they have the same last
   * digit, such as with 27 and 57. Note that the % "mod" operator computes
   * remainders, so 17 % 10 is 7. Testfälle: lastDigit(7, 17) → true, lastDigit(6,
   * 17) → false, lastDigit(3, 113) → true.
   */
  // Der eigentliche Test
  @Test
  public void testLastdigit()
  {
    final List<ZweiWerte> numberListTrue = new ArrayList<ZweiWerte>();
    final List<ZweiWerte> numberListFalse = new ArrayList<ZweiWerte>();

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
  private void checkLastDigit(List<ZweiWerte> numberList, Boolean excepted)
  {
    numberList.stream().forEach(x -> {
      String content = null;
      try
      {
        content = invokeControllerLastdigit(x);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Assert.fail();
      }

      Assert.assertEquals(excepted.toString(), content);
    });
  }

  /* Aufruf des CoderController-Controllers für die lastdigit Methode */
  private String invokeControllerLastdigit(ZweiWerte listTwoValues) throws Exception
  {
    MvcResult result = this.mockMvc.perform(get("/testresults/lastdigit/{inputnumberone}/{inputnumberone}",
                                                listTwoValues.getValueOne(),
                                                listTwoValues.getValueTwo()).accept("application/json"))
                                   .andExpect(status().isOk())
                                   .andReturn();
    return result.getResponse().getContentAsString();
  }

  /**
   * Given a string, return a new string where the last 3 chars are now in upper
   * case. If the string has less than 3 chars, uppercase whatever is there. Note
   * that str.toUpperCase() returns the uppercase version of a string.
   * endUp("Hello") → "HeLLO" endUp("hi there") → "hi thERE" endUp("hi") → "HI"
   */

  // Der eigentlich Test mit den Testwerten
  @Test
  public void TestEndUp() throws Exception
  {

    // iteriere über Werte und rüfe Controller-Klasse auf
    Assert.assertEquals(invokeControllerEndUp("Hello"), "HeLLO");
    Assert.assertEquals(invokeControllerEndUp("hi there"), "hi thERE");
    Assert.assertEquals(invokeControllerEndUp("hi"), "HI");
    Assert.assertEquals(invokeControllerEndUp("woo hoo"), "woo HOO");
    Assert.assertEquals(invokeControllerEndUp("xyz12"), "xyZ12");
    Assert.assertEquals(invokeControllerEndUp(" "), " ");

  }

  /* Aufruf des CoderController-Controllers für die lastdigit Methode */
  private String invokeControllerEndUp(String testinput) throws Exception
  {
    MvcResult result = this.mockMvc.perform(get("/testresults/testendup/{teststringparam}", testinput).accept("application/json")).andExpect(status().isOk()).andReturn();

    return result.getResponse().getContentAsString();
  }

  /**
   * Given a non-empty string and an int N, return the string made starting with
   * char 0, and then every Nth char of the string. So if N is 3, use char 0, 3,
   * 6, ... and so on. N is 1 or more.
   * everyNth("Miracle", 2) → "Mrce"
   * everyNth("abcdefg", 2) → "aceg"
   * everyNth("abcdefg", 3) → "adg"
   * everyNth("Chocolate", 3) → "Cca"
   * everyNth("Chocolates", 3) → "Ccas"
   * everyNth("Chocolates", 4) → "Coe"
   * everyNth("Chocolates", 100) → "C"
   */

  @Test
  public void TestEveryNth()
  {
    Assert.assertEquals("Mrce", this.userController.everynth("Miracle", 2));
    Assert.assertEquals("aceg", this.userController.everynth("abcdefg", 2));
    Assert.assertEquals("adg", this.userController.everynth("abcdefg", 3));
    Assert.assertEquals("Cca", this.userController.everynth("Chocolate", 3));
    Assert.assertEquals("Ccas", this.userController.everynth("Chocolates", 3));
    Assert.assertEquals("Coe", this.userController.everynth("Chocolates", 4));
    Assert.assertEquals("C", this.userController.everynth("Chocolates", 100));
  }


}
