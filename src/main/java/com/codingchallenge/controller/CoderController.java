package com.codingchallenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.webservice.CoderService;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Efe
 */
@Getter
@Setter // <--- Benutze Lombok Bibliothek (für Getter und Setter)
@RestController
@RequestMapping("/testresults")
public class CoderController
{

  private static final int MODNUMBER = 10;

  private static final Logger LOGGER = LoggerFactory.getLogger(CoderController.class);

  private final CoderService userService;

  private String name;

  /**
   * @author Efe
   */
  @Autowired
  public CoderController(CoderService userService)
  {

    // logger.info("-- Usercontroller.java - Usercontroller() Konstruktor");

    this.userService = userService;
    this.setName("TestLombokGetterName");
  }

  @RequestMapping(value = "/getuserwebpage", method = RequestMethod.GET)
  public String getUserWebpage()
  {
    // logger.info("-- Usercontroller.java - getUserWebpage()");
    return this.getName();
  }

  /**
   * Test in Konsole mit "curl" oder Browser.
   * http://localhost:8080/testresults/stringe/IrgendeinString
   */
  @RequestMapping(value = "/stringe/{inputstring}", method = RequestMethod.GET)
  @SuppressWarnings("PMD.GuardLogStatement")
  public boolean stringE(@PathVariable("inputstring") String inputstring)
  {

    char c = 'e';
//    LOGGER.info("-- UserController -- stringE() - inputstring: " + inputstring);
    int count = checkLetter(inputstring, 0, c);

    final int maxVal = 3;
    final int minVal = 1;
    return (count >= minVal && count <= maxVal) ? true : false;
  }

  private int checkLetter(String input, int index, char checkFor)
  {
    return (index >= input.length()) ? 0 : (input.charAt(index++) == checkFor ? 1 : 0) + checkLetter(input, index, checkFor);
  }

  /**
   * Test in Konsole mit "curl" oder Browser.
   * http://localhost:8080/testresults/lastdigit/IrgendeineNummer?id=8,3 Info:
   * "PathVariable"-Annotation liest Werte aus URL, "RequestParam"-Annotation
   * liest es über die URL-Variablen aus (".../?id=1,2,3" oder ".../?id=1&id=2")
   */
  @RequestMapping(value = "/lastdigit/{inputnumberone}/{inputnumbertwo}", method = RequestMethod.GET)
  public boolean lastdigit(@PathVariable("inputnumberone") int inputnumberone, @PathVariable("inputnumbertwo") int inputnumbertwo)
  {
    // logger.info("-- UserController -- lastdigit() - inputstring Wert 1: " +
    // inputnumberone);
    // logger.info("-- UserController -- lastdigit() - inputstring Wert 2: " +
    // inputnumbertwo);

    int modValueOne = inputnumberone % MODNUMBER;
    int modValueTwo = inputnumbertwo % MODNUMBER;

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
   * endUp("Hello") → "HeLLO" endUp("hi there") → "hi thERE" endUp("hi") → "HI"
   */
  @RequestMapping(value = "/testendup/{inputparamstring}", method = RequestMethod.GET)
  public String endup(@PathVariable("inputparamstring") String inputstring)
  {
    String result = "";
    final int strlen = inputstring.length();
    final int lastchars = 3;

    if (strlen >= lastchars)
    {
      result = inputstring.substring(0, strlen - lastchars) + (inputstring.substring(strlen - lastchars, strlen)).toUpperCase();
    }
    else
    {
      result = inputstring.toUpperCase();
    }

    return result;
  }

  /**
   * Given a non-empty string and an int N, return the string made starting with
   * char 0, and then every Nth char of the string. So if N is 3, use char 0, 3,
   * 6, ... and so on. N is 1 or more.
   * everyNth("Miracle", 2) → "Mrce"
   * everyNth("abcdefg", 2) → "aceg"
   * everyNth("abcdefg", 3) → "adg"
   */
  @RequestMapping(value = "/testeverynth/{str}/{n}", method = RequestMethod.GET)
  public String everynth(@PathVariable("str") String str, @PathVariable("n") int n)
  {
//    LOGGER.info("-- UserController -- everynth() - Variable str:" + str);
//    LOGGER.info("-- UserController -- everynth() - Variable n:" + n);
    String result = "";
    if (!("".equals(str)))
    {
      for ( int i = 0 ; i <= str.length() - 1 ; i++ )
      {
        // LOGGER.info("-- UserController -- everynth() - (i == 0):"+(i == 0));
        // LOGGER.info("-- UserController -- everynth() - (i % n == 0):"+(i % n == 0));

        // check if Nth number
        if (i % n == 0)
        {
          // LOGGER.info("-- UserController -- everynth() - str.charAt(i):" + str.charAt(i));
          result = result + str.charAt(i);
        }
      }
      // LOGGER.info("-- UserController -- everynth() - Variable result:"+result);
    }

    return result;
  }

  /*
   * Given a string and a non-negative int n, return a larger string that is n copies of the original string.
   * stringTimes("Hi", 2) → "HiHi"
   * stringTimes("Hi", 3) → "HiHiHi"
   * stringTimes("Hi", 1) → "Hi"
   */
  @RequestMapping(value = "/teststringtimes/{str}/{n}", method = RequestMethod.GET)
  public String stringTimes(@PathVariable("str") String str, @PathVariable("n") int n)
  {
    String result = "";
    final String word = str;

    // LOGGER.info("-- UserController -- stringTimes() - str: "+str);
    // LOGGER.info("-- UserController -- stringTimes() - n: "+n);

    if (n > 0)
    {
      for ( int i = 0 ; i < n ; i++ )
      {
        result += word;
      }
    }

    return result;
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
  @RequestMapping(value = "/testfronttimes/{str}/{n}", method = RequestMethod.GET)
  public String frontTimes(@PathVariable("str") String str, @PathVariable("n") int n)
  {
    String result = "";
    if (n > 0)
    {
      for ( int i = 0 ; i < n ; i++ )
      {
        for ( int j = 0 ; (j < str.length()) && (j < 3) ; j++ )
        {
          // LOGGER.info("-- UserController -- testfronttimes() - str: "+str.charAt(i));
          // LOGGER.info("-- UserController -- testfronttimes() - i : "+i+" j "+j);
          result += str.charAt(j);
        }
      }
    }
    return result;
  }

  /**
   * Count the number of "xx" in the given string. We'll say that overlapping is allowed, so "xxx" contains 2 "xx".
   * countXX("abcxx") → 1
   * countXX("xxx") → 2
   * countXX("xxxx") → 3
   */
  @RequestMapping(value = "/testcountxx/{str}", method = RequestMethod.GET)
  public int countXX(@PathVariable("str") String str)
  {
    int result = 0;


    for ( int i = 0 ; i < str.length() ; i++ )
    {
      // LOGGER.info("-- UserController -- countXX() - str: " + str);
      result = ((str.charAt(i) == 'x') && ((i != 0) && (str.charAt(i - 1) == 'x'))) ? ++result : result;
      // LOGGER.info("-- UserController -- countXX() - RESULT: " + result);
    }

    return result;
  }

  /**
   * Given a string, return true if the first instance of "x" in the string is immediately followed by another "x".
   * doubleX("axxbb") → true
   * doubleX("axaxax") → false
   * doubleX("xxxxx") → true
   */
  @RequestMapping(value = "/testdoublex/{str}", method = RequestMethod.GET)
  public boolean doublex(@PathVariable("str") String str)
  {
    boolean result = false;

    for ( int i = 0 ; i < str.length() - 1 ; i++ )
    {
      // LOGGER.info("-- UserController -- doublex() - str.charAt(i): " + str.charAt(i));
      // LOGGER.info("-- UserController -- doublex() - if-Abfrage: "+ ( !( "".equals(str)) && (str.length() >= 1) && (str.charAt(i) == 'x') && str.charAt(i + 1) == 'x') );
      if (!("".equals(str)) && (str.length() >= 1) && (str.charAt(i) == 'x') && str.charAt(i + 1) == 'x')
      {
        result = true;
        break;
      }
      // prüfe ob "x", gleich ein weiteres folgt
      else if ((str.charAt(i) == 'x') && !(str.charAt(i + 1) == 'x'))
      {
        // LOGGER.info("-- UserController -- doublex() - elseif");
        break;
      }
    }

    return result;
  }


  /**
   * Given a non-empty string like "Code" return a string like "CCoCodCode".
   * stringSplosion("Code") → "CCoCodCode"
   * stringSplosion("abc") → "aababc"
   * stringSplosion("ab") → "aab"
   * 
   * @param str
   * @return
   */
  @RequestMapping(value = "/stringsplosion/{str}", method = RequestMethod.GET)
  public String Stringsplosion(@PathVariable("str") String str)
  {
    String result = "";
    for ( int i = 0 ; i <= str.length() ; i++ )
    {
      result = result + str.substring(0, i);
    }

    // LOGGER.info("-- CoderController.java - Stringsplosion - result:"+result);

    return result;
  }

  /**
   * Given a string, return the count of the number of times that a substring length 2 appears in the string and also as the last 2 chars of the string, so "hixxxhi" yields 1 (we
   * won't count the end substring).
   * last2("hixxhi") → 1
   * last2("xaxxaxaxx") → 1
   * last2("axxxaaxx") → 2
   */

}
