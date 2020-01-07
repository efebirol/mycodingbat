package com.codingchallenge.controller;

import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.codingchallenge.helper.ZweiWerte;
import com.codingchallenge.webservice.CoderService;


/**
 * @author Efe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CoderController.class)
public class CoderControllerRestTest
{

  /**
   * MockBean - Überschreibt/mockt eine Bean im SpringContext-Pool wenn es gestartet wird
   */

  // @MockBean
  // private CoderService userService;
  //
  // @MockBean
  // private CoderController coderController;

  //////

  @InjectMocks
  CoderController coderController;

  @MockBean
  CoderService userService;

  @Autowired
  private MockMvc mockMvc;

  /**
   * Prüfen das Response für Mock etwas züruck gibt (nicht "null"), sonst Aufruf der echten Methode.
   */
  @Before
  public void beforeTest()
  {
    // when(coderController.stringE(anyString())).thenCallRealMethod();
    // when(coderController.lastdigit(anyInt(), anyInt())).thenCallRealMethod();
    // when(coderController.endup(anyString())).thenCallRealMethod();
    // when(coderController.everynth(anyString(), anyInt())).thenCallRealMethod();
    // when(coderController.stringTimes(anyString(), anyInt())).thenCallRealMethod();
    // when(coderController.frontTimes(anyString(), anyInt())).thenCallRealMethod();
    // when(coderController.countXX(anyString())).thenCallRealMethod();
    // when(coderController.doublex(anyString())).thenCallRealMethod();
    // when(coderController.Stringsplosion(anyString())).thenCallRealMethod();
    // when(coderController.arrayCount9(any())).thenCallRealMethod();
    // when(coderController.stringX(anyString())).thenCallRealMethod();
    when(userService.stringXService(anyString())).thenCallRealMethod();


    // this must be called for the @Mock annotations above to be processed
    // and for the mock service to be injected into the controller under
    // test.
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(coderController).build();
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
    Assert.assertEquals("Mrce", this.coderController.everynth("Miracle", 2));
    Assert.assertEquals("aceg", this.coderController.everynth("abcdefg", 2));
    Assert.assertEquals("adg", this.coderController.everynth("abcdefg", 3));
    Assert.assertEquals("Cca", this.coderController.everynth("Chocolate", 3));
    Assert.assertEquals("Ccas", this.coderController.everynth("Chocolates", 3));
    Assert.assertEquals("Coe", this.coderController.everynth("Chocolates", 4));
    Assert.assertEquals("C", this.coderController.everynth("Chocolates", 100));
  }

  /*
   * Given a string and a non-negative int n, return a larger string that is n copies of the original string.
   * stringTimes("Hi", 2) → "HiHi"
   * stringTimes("Hi", 3) → "HiHiHi"
   * stringTimes("Hi", 1) → "Hi"
   */
  @Test
  public void TestStringTimes()
  {
    // einfache Test der Funktion (ohne Rest)
    Assert.assertEquals("HiHi", this.coderController.stringTimes("Hi", 2));
    Assert.assertEquals("HiHiHi", this.coderController.stringTimes("Hi", 3));
    Assert.assertEquals("Hi", this.coderController.stringTimes("Hi", 1));

    // Test der Funktion mit Überprüfung der Restschnittstelle
  }

  /**
   * Given a string and a non-negative int n, we'll say that the front of the string is the first 3 chars, or whatever is there if the string is less than length 3. Return n copies
   * of the front;
   * frontTimes("Chocolate", 2) → "ChoCho"
   * frontTimes("Chocolate", 3) → "ChoChoCho"
   * frontTimes("Abc", 3) → "AbcAbcAbc"
   * 
   * @param i
   * @param string
   */
  @Test
  public void TestFrontTimes()
  {
    // einfache Test der Funktion (ohne Rest)
    Assert.assertEquals("ChoCho", this.coderController.frontTimes("Chocolate", 2));
    Assert.assertEquals("ChoChoCho", this.coderController.frontTimes("Chocolate", 3));
    Assert.assertEquals("AbcAbcAbc", this.coderController.frontTimes("Abc", 3));
    Assert.assertEquals("AbAbAbAb", this.coderController.frontTimes("Ab", 4));
    Assert.assertEquals("AAAA", this.coderController.frontTimes("A", 4));
    Assert.assertEquals("", this.coderController.frontTimes("", 4));
  }

  @Test
  public void TestFrontTimesRestApi() throws Exception
  {
    // Test der Funktion mit Überprüfung der Restschnittstelle
    MvcResult result = this.mockMvc.perform(get("/testresults/testfronttimes/Chocolate/2").accept("application/json")).andExpect(status().isOk()).andReturn();
    result = this.mockMvc.perform(get("/testresults/testfronttimes/Chocolate/3").accept("application/json")).andExpect(status().isOk()).andReturn();
    result = this.mockMvc.perform(get("/testresults/testfronttimes/AbcAbcAbc/3").accept("application/json")).andExpect(status().isOk()).andReturn();
  }

  /**
   * Count the number of "xx" in the given string. We'll say that overlapping is allowed, so "xxx" contains 2 "xx".
   * countXX("abcxx") → 1
   * countXX("xxx") → 2
   * countXX("xxxx") → 3
   * 
   * @throws Exception
   */
  @Test
  public void TestCountXX() throws Exception
  {
    // einfacher Test
    Assert.assertEquals(1, this.coderController.countXX("abcxx"));
    Assert.assertEquals(2, this.coderController.countXX("xxx"));
    Assert.assertEquals(3, this.coderController.countXX("xxxx"));

    // RESTApi Schnittstellen Test
    MvcResult result = this.mockMvc.perform(get("/testresults/testcountxx/abcxx/").accept("application/json")).andExpect(status().isOk()).andReturn();
    result = this.mockMvc.perform(get("/testresults/testcountxx/xxx/").accept("application/json")).andExpect(status().isOk()).andReturn();
    result = this.mockMvc.perform(get("/testresults/testcountxx/xxxx/").accept("application/json")).andExpect(status().isOk()).andReturn();
  }

  /**
   * Given a string, return true if the first instance of "x" in the string is immediately followed by another "x".
   * doubleX("axxbb") → true
   * doubleX("axaxax") → false
   * doubleX("xxxxx") → true
   * 
   * @throws Exception
   */
  @Test
  public void Testdoublex() throws Exception
  {
    // einfacher Test
    Assert.assertEquals(true, this.coderController.doublex("axxbb"));
    Assert.assertEquals(false, this.coderController.doublex("axaxax"));
    Assert.assertEquals(true, this.coderController.doublex("xxxxx"));
    Assert.assertEquals(false, this.coderController.doublex("xaxxx"));
    Assert.assertEquals(false, this.coderController.doublex("aaaax"));
    Assert.assertEquals(false, this.coderController.doublex(""));
    Assert.assertEquals(false, this.coderController.doublex("abc"));
    Assert.assertEquals(false, this.coderController.doublex("x"));
    Assert.assertEquals(true, this.coderController.doublex("xx"));
    Assert.assertEquals(false, this.coderController.doublex("xax"));
    Assert.assertEquals(false, this.coderController.doublex("xaxx"));

    // REST Schnittstellen Tests der URL
    MvcResult result = this.mockMvc.perform(get("/testresults/testdoublex/axxbb/").accept("application/json")).andExpect(status().isOk()).andReturn();
    result = this.mockMvc.perform(get("/testresults/testdoublex/axaxax/").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("false", result.getResponse().getContentAsString());
    result = this.mockMvc.perform(get("/testresults/testdoublex/xxxxx/").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("true", result.getResponse().getContentAsString());
  }



  /**
   * Given a non-empty string like "Code" return a string like "CCoCodCode".
   * stringSplosion("Code") → "CCoCodCode"
   * stringSplosion("abc") → "aababc"
   * stringSplosion("ab") → "aab"
   * 
   * @param str
   * @return
   * @throws Exception
   */
  @Test
  public void Teststringsplosion() throws Exception
  {
    // einfacher Test
    Assert.assertEquals("CCoCodCode", this.coderController.Stringsplosion("Code"));
    Assert.assertEquals("aababc", this.coderController.Stringsplosion("abc"));
    Assert.assertEquals("aab", this.coderController.Stringsplosion("ab"));

    // REST API Test
    MvcResult result = this.mockMvc.perform(get("/testresults/stringsplosion/Code/").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("CCoCodCode", result.getResponse().getContentAsString());

    result = this.mockMvc.perform(get("/testresults/stringsplosion/abc/").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("aababc", result.getResponse().getContentAsString());

    result = this.mockMvc.perform(get("/testresults/stringsplosion/ab/").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("aab", result.getResponse().getContentAsString());
  }

  /**
   * Given an array of ints, return the number of 9's in the array.
   * arrayCount9([1, 2, 9]) → 1
   * arrayCount9([1, 9, 9]) → 2
   * arrayCount9([1, 9, 9, 3, 9]) → 3
   * 
   * @throws Exception
   */
  @Test
  public void TestArrayCount9() throws Exception
  {
    // Simple JUnit Test

    // Setup Tests
    int[] actualArray = new int[]{1, 2, 9};
    int[] actualArray2 = new int[]{1, 9, 9};
    int[] actualArray3 = new int[]{1, 9, 9, 3, 9};

    // Testing
    Assert.assertEquals(1, this.coderController.arrayCount9(actualArray));
    Assert.assertEquals(2, this.coderController.arrayCount9(actualArray2));
    Assert.assertEquals(3, this.coderController.arrayCount9(actualArray3));

    // Rest Endpoint Test (hier eine Collection, Array, etc. von integern)
    MvcResult result = this.mockMvc.perform(get("/testresults/arrayCount9/1,2,9").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("1", result.getResponse().getContentAsString());

    result = this.mockMvc.perform(get("/testresults/arrayCount9/1,9,9").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("2", result.getResponse().getContentAsString());

    result = this.mockMvc.perform(get("/testresults/arrayCount9/1,9,9,3,9").accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("3", result.getResponse().getContentAsString());
  }

  // Prüfe ob die Restschnittstelle aufrufbar
  /**
   * Problem: MockMVC liefert Nullpointerexception, wenn im Controller das Service aufgerufen wird
   * - Lösung:
   * -- Der Controller wird mit "@InjectMocks" erzeugt seine Klasse und alle dazugehörigen "@Mock"-Mocks im Test (also auch die Service Klasse)
   * -- benötige dazu den Controller als "@InjectMocks" und die Service als "@MockBean" annotiert
   */
  @Test
  public void TestStringX() throws Exception
  {
    String urlTestString = "xxtexstParaxxxx";

    // einfaches Testing
    Assert.assertEquals("xtestParax", this.coderController.stringX(urlTestString));

    // REST-Test
    MvcResult result = this.mockMvc.perform(get("/testresults/testStringX/{urlString}", urlTestString).accept("application/json")).andExpect(status().isOk()).andReturn();
    Assert.assertEquals("xtestParax", result.getResponse().getContentAsString());
  }

}
