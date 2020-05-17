package com.codingchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codingchallenge.configuration.CoderConfiguration;
import com.codingchallenge.webservice.CoderService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Efe
 */

@Slf4j
@Getter
@Setter // <--- Benutze Lombok Bibliothek (für Getter und Setter)
@RestController
@RequestMapping("/testresults")
public class CoderController
{

  private static final int MODNUMBER = 10;

  //

  @Autowired
  private CoderService coderService;

  // zum auslesen der .properties
  @Autowired
  private CoderConfiguration coderConf;

  private String name;

  // public CoderController()
  // {
  // this.coderService = new CoderService();
  //
  // }

  /*
   * Initializiere das Service (mit Methoden) in den Controller
   * @Autowired - Wird automatisch instanziert/befüllt beim Start
   */
  @Autowired
  public CoderController(CoderService coderService, CoderConfiguration coderConf)
  {

    log.info("-- CoderController.java - CoderController() - Wird automatisch instanziert/befüllt beim Start");

    this.coderService = coderService;
    this.setName("TestLombokGetterName");

    this.coderConf = coderConf;
  }

  @RequestMapping(value = "/hellorestendpoint", method = RequestMethod.GET)
  public String helloRestResponse()
  {
    return "hello";
  }

  @RequestMapping(value = "/getuserwebpage", method = RequestMethod.GET)
  public String getUserWebpage()
  {
    // log.info("-- CoderController.java - getUserWebpage()");
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
    // log.info("-- CoderController -- stringE() - inputstring: " + inputstring);
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
    // log.info("-- CoderController -- lastdigit() - inputstring Wert 1: " +
    // inputnumberone);
    // log.info("-- CoderController -- lastdigit() - inputstring Wert 2: " +
    // inputnumbertwo);

    int modValueOne = inputnumberone % MODNUMBER;
    int modValueTwo = inputnumbertwo % MODNUMBER;

    // log.info("-- CoderController -- lastdigit() - modValueOne Wert 1: " +
    // modValueOne);
    // log.info("-- CoderController -- lastdigit() - modValueTwo Wert 2: " +
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
    // log.info("-- CoderController -- everynth() - Variable str:" + str);
    // log.info("-- CoderController -- everynth() - Variable n:" + n);
    String result = "";
    if (!("".equals(str)))
    {
      for ( int i = 0 ; i <= str.length() - 1 ; i++ )
      {
        // log.info("-- CoderController -- everynth() - (i == 0):"+(i == 0));
        // log.info("-- CoderController -- everynth() - (i % n == 0):"+(i % n == 0));

        // check if Nth number
        if (i % n == 0)
        {
          // log.info("-- CoderController -- everynth() - str.charAt(i):" + str.charAt(i));
          result = result + str.charAt(i);
        }
      }
      // log.info("-- CoderController -- everynth() - Variable result:"+result);
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

    // log.info("-- CoderController -- stringTimes() - str: "+str);
    // log.info("-- CoderController -- stringTimes() - n: "+n);

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
          // log.info("-- CoderController -- testfronttimes() - str: "+str.charAt(i));
          // log.info("-- CoderController -- testfronttimes() - i : "+i+" j "+j);
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
      // log.info("-- CoderController -- countXX() - str: " + str);
      result = ((str.charAt(i) == 'x') && ((i != 0) && (str.charAt(i - 1) == 'x'))) ? ++result : result;
      // log.info("-- CoderController -- countXX() - RESULT: " + result);
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
      // log.info("-- CoderController -- doublex() - str.charAt(i): " + str.charAt(i));
      // log.info("-- CoderController -- doublex() - if-Abfrage: "+ ( !( "".equals(str)) && (str.length() >= 1) && (str.charAt(i) == 'x') && str.charAt(i + 1) == 'x') );
      if (!("".equals(str)) && (str.length() >= 1) && (str.charAt(i) == 'x') && str.charAt(i + 1) == 'x')
      {
        result = true;
        break;
      }
      // prüfe ob "x", gleich ein weiteres folgt
      else if ((str.charAt(i) == 'x') && !(str.charAt(i + 1) == 'x'))
      {
        // log.info("-- CoderController -- doublex() - elseif");
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

    // log.info("-- CoderController.java - Stringsplosion - result:"+result);

    return result;
  }

  /**
   * Ich: Diese Funktion nimmt als URL Parameter eine Array an Integern an
   * Given an array of ints, return the number of 9's in the array.
   * arrayCount9([1, 2, 9]) → 1
   * arrayCount9([1, 9, 9]) → 2
   * arrayCount9([1, 9, 9, 3, 9]) → 3
   * Info: Beispielaufruf:
   * http://localhost:8090/testresults/arrayCount9/1,2,9
   * - die Werte "1,2,9" werden in das Paramter "nums"-Array übergeben
   */
  @RequestMapping(value = "/arrayCount9/{nums}", method = RequestMethod.GET)
  public int arrayCount9(@PathVariable("nums") int[] nums)
  {
    int result = 0;
    // log.info("\"-- CoderController.java - arrayCount9()");

    for ( int i = 0 ; i < nums.length ; i++ )
    {
      // log.info("\"-- CoderController.java - arrayCount9() - nums:" + nums[i]);
      result = (nums[i] == 9) ? (result + 1) : result;
    }

    return result;
  }

  /**
   * Dummy Test für JUnit Mocks
   */
  @RequestMapping(value = "/birolsmock")
  public String testAMock(String text)
  {
    return "echte Methode, nicht mock";
  }

  /**
   * Given an array of ints, return true if one of the first 4 elements in the array is a 9. The array length may be less than 4.
   * arrayFront9([1, 2, 9, 3, 4]) → true
   * arrayFront9([1, 2, 3, 4, 9]) → false
   * arrayFront9([1, 2, 3, 4, 5]) → false
   */
  @RequestMapping(value = "/arrayFront9/{nums}", method = RequestMethod.GET)
  public boolean arrayFront9(@PathVariable("nums") int[] nums)
  {
    boolean result = false;

    for ( int i = 0 ; i < nums.length ; i++ )
    {
      log.info("nums.length: " + nums.length + " Position: " + i + " mit Wert: " + nums[i]);
      if (nums[i] == 9 && i < 4)
      {
        log.info("True :-)");
        result = true;
      }
    }
    log.info("False:-)");
    return result;

  }

  /*
   * Hinweis: Lege Logik ab hier in sein eigene Service Package (webservice)
   * Given an array of ints, return true if the sequence of numbers 1, 2, 3 appears in the array somewhere.
   * array123([1, 1, 2, 3, 1]) → true
   * array123([1, 1, 2, 4, 1]) → false
   * array123([1, 1, 2, 1, 2, 3]) → true
   * Beispielaufruf: http://localhost:8090/testresults/testStringMatch/worteins/wortzwei
   * - Port 8090
   * - 1. Parameter "worteins"
   * - 2. Parameter "wortzwei"
   **/
  @RequestMapping(value = "/testarray123/{nums}")
  public boolean array123(@PathVariable("nums") int[] nums)
  {
    return this.coderService.array123Service(nums);
  }

  @RequestMapping(value = "/testStringMatch/{a}/{b}")
  public int stringMatch(@PathVariable("a") String a, @PathVariable("b") String b)
  {
    return this.coderService.stringMatchService(a, b);
  }

  /*
   * Given a string, return a version where all the "x" have been removed. Except an "x" at the very start or end should not be removed.
   * stringX("xxHxix") → "xHix"
   * stringX("abxxxcd") → "abcd"
   * stringX("xabxxxcdx") → "xabcdx"
   */
  @RequestMapping(value = "/testStringX/{inputstring}", method = RequestMethod.GET)
  public String stringX(@PathVariable("inputstring") String str)
  {
    String result;
    result = this.coderService.stringXService(str);
    return result;
  }

  /*
   * Given a string, return a string made of the chars at indexes 0,1, 4,5, 8,9 ... so "kittens" yields "kien".
   * altPairs("kitten") → "kien"
   * altPairs("Chocolate") → "Chole"
   * altPairs("CodingHorror") → "Congrr"
   */
  @RequestMapping(value = "/testAltPairs/{urlString}")
  public String altPairs(@PathVariable("urlString") String str)
  {

    String result = this.coderService.altPairsService(str);

    return result;
  }

  /**
   * Suppose the string "yak" is unlucky. Given a string, return a version where all the "yak" are removed, but the "a" can be any char. The "yak" strings will not overlap.
   * stringYak("yakpak") → "pak"
   * stringYak("pakyak") → "pak"
   * stringYak("yak123ya") → "123ya"
   */
  @RequestMapping(value = "/stringYak/{inputstring}")
  public String stringYak(@PathVariable("inputstring") String str)
  {
    return this.coderService.stringYakService(str);
  }

  /***
   * Given a string, return a new string made of every other char starting with the first, so "Hello" yields "Hlo".
   * stringBits("Hello") → "Hlo"
   * stringBits("Hi") → "H"
   * stringBits("Heeololeo") → "Hello"
   * 
   * @param input
   * @return result
   */
  @RequestMapping(value = "/stringBits/{inputUrlString}")
  public String stringBits(@PathVariable(name = "inputUrlString") String input)
  {
    return this.coderService.stringBitsService(input);
  }

  /***
   * Given a string, return the count of the number of times that a substring length 2 appears in the string and also as the last 2 chars of the string, so "hixxxhi" yields 1 (we
   * won't count the end substring).
   * last2("hixxhi") → 1
   * last2("xaxxaxaxx") → 1
   * last2("axxxaaxx") → 2
   */


  /**
   * Gebe die application.properties Werte zurück, die in der CoderConfiguration.java gemappt sind
   */
  @RequestMapping(value = "/ausgabePunktProperties")
  public void printProperties()
  {
    log.info("testconfiguration von application.properties: " + coderConf.getTestconfiguration());
    log.info("getTestWert: " + coderConf.getTestWert());
    // Info: Nutze Camel Case um .properties "app.testWert.again"-Key zu nutzen (also "." weglassen, und ersten Bruchstaben danach Grosschreiben)
    log.info("getTestWertAgain: " + coderConf.getSammlung().getAgain());
    log.info("getAgainTwo: " + coderConf.getSammlung().getAgainTwo());
    log.info("getDescription: " + coderConf.getDescription());
    log.info("getUploadDir: " + coderConf.getUploadDir());
    log.info("getSecurity getUsername: " + coderConf.getSecurity().getUsername());
    log.info("getSecurity getPassword: " + coderConf.getSecurity().getPassword());

  }
}
