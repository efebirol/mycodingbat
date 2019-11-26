package com.codingchallenge.webservice;

import java.util.Arrays;

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
    coderRepository.save(new TestWertAusDb("xxcaazz", "xxbaaz"));

    // lese Daten aus der DB
    coderRepository.findById(1L);

    return result;

  }

}
