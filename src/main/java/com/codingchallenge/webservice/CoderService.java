package com.codingchallenge.webservice;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingchallenge.entity.TestWertAusDb;
import com.codingchallenge.repository.CoderRepository;


/**
 * @author Efe
 *         Methoden die aus dem Klassen (z. B. Controller) extrahiert werden
 *         Vorteil:
 *         - Übersichtlichkeit - alles dorthin, wohin es gehört
 */
@Service("coderService")
public class CoderService
{

  private static final Logger LOGGER = LoggerFactory.getLogger(CoderService.class);

  // Verbindung zur DB
  @Autowired
  CoderRepository coderRepository;

  public CoderService()
  {
    LOGGER.info("--CoderService.java - Konstruktor");
  }

  public boolean array123Service(int[] nums)
  {
    boolean result = false;

    LOGGER.info("nums Array: " + Arrays.toString(nums));

    for ( int i = 0 ; i < nums.length - 2 ; i++ )
    {
      LOGGER.info("nums.length: " + nums.length + " Position: " + i + " mit Wert: " + nums[i]);
      if (nums[i] == 1 && nums[i + 1] == 2 && nums[i + 2] == 3)
      {
        LOGGER.info("true :-)");
        return true;

      }
    }
    LOGGER.info(result + " :-)");

    return result;
  }

  /*
   * Given 2 strings, a and b, return the number of the positions where they contain the same length 2 substring. So "xxcaazz" and "xxbaaz" yields 3, since the "xx", "aa", and "az"
   * substrings appear in the same place in both strings.
   * stringMatch("xxcaazz", "xxbaaz") → 3
   * stringMatch("abc", "abc") → 2
   * stringMatch("abc", "axc") → 0
   */
  public int stringMatchService(String a, String b)
  {
    LOGGER.info("-- CoderService.java - stringMatchService() - Speichere Daten über JPA in die DB. Siehe http://localhost:8080/h2-console");

    int result = 0;

    LOGGER.info("-- CoderService.java - a: " + a + " b: " + b);

    // schreibe Daten in die DB (entsprechend dem Konstruktor des JPA-Repository, hier : TestWertAusDb.java)
    coderRepository.save(new TestWertAusDb(a, b));

    // lese Daten aus der DB
    TestWertAusDb dbEintrag = getDbEntry(1L);
    LOGGER.info(dbEintrag.toString());

    // Logik schreiben

    return result;
  }

  /* Hilfsklasse um DB zu durchsuchen */
  public TestWertAusDb getDbEntry(Long id)
  {
    Optional<TestWertAusDb> optionalDbWert = coderRepository.findById(id);
    String output = (optionalDbWert.isPresent() == true) ? "-- CoderService.java - getDbEntry - optionalDbWert ist TRUE"
      : "-- CoderService.java - getDbEntry - optionalDbWert ist FALSE";
    LOGGER.info(output);
    return optionalDbWert.get();
  }

  /*
   * Given a string, return a version where all the "x" have been removed. Except an "x" at the very start or end should not be removed.
   * stringX("xxHxix") → "xHix"
   * stringX("abxxxcd") → "abcd"
   * stringX("xabxxxcdx") → "xabcdx"
   */
  public String stringXService(String str)
  {
    String result = "";

    if (str.length() == 0)
      return "";
    if (str.length() == 1)
      return str.charAt(0) + "";

    LOGGER.info("-- CoderService.java - stringXService() - str: " + str);
    for ( int i = 0 ; i < str.length() ; i++ )
    {
      if ((i == 0) || (i == str.length() - 1))
      {
        LOGGER.info("-- CoderService.java - stringXService() - füge in result (erstes oder letzes Zeichen): " + str.charAt(i));
        result += str.charAt(i);
      }
      else if ((str.charAt(i) != 'x'))
      {
        LOGGER.info("-- CoderService.java - stringXService() - füge in result String: " + str.charAt(i));
        result += str.charAt(i);
      }
    }

    return result;
  }

  /*
   * Given a string, return a string made of the chars at indexes 0,1, 4,5, 8,9 ... so "kittens" yields "kien".
   * altPairs("kitten") → "kien"
   * altPairs("Chocolate") → "Chole"
   * altPairs("CodingHorror") → "Congrr"
   */
  public String altPairsService(String inputstring)
  {
    int[] indexes = {0, 1, 4, 5, 8, 9, 12, 13};
    int strlength = inputstring.length();
    // fallback, wenn kein String übergeben wurde
    String result = (inputstring.isEmpty()) ? "" : "";

    LOGGER.info("-- CoderController.java - inputstring: " + inputstring);
    LOGGER.info("-- CoderController.java - indexes Array Länge: " + indexes.length);
    LOGGER.info("-- CoderController.java - strlength: " + strlength);


    for ( int i = 0 ; i < indexes.length ; i++ )
    {
      if ((strlength >= 0) && (indexes[i]) < strlength)
      {
        LOGGER.info("-- CoderController.java - füge ins result die Stelle: " + indexes[i]);
        result = result + inputstring.charAt(indexes[i]);
      }
    }

    LOGGER.info("-- CoderController.java -  result: " + result);

    return result;
  }


}
