package com.codingchallenge.webservice;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingchallenge.entity.TestWertAusDb;
import com.codingchallenge.repository.CoderRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Efe
 *         Methoden die aus dem Klassen (z. B. Controller) extrahiert werden
 *         Vorteil:
 *         - Übersichtlichkeit - alles dorthin, wohin es gehört
 */
@Slf4j
@Service("coderService")
public class CoderService
{


  // Verbindung zur DB
  @Autowired
  CoderRepository coderRepository;

  public CoderService()
  {
    log.info("--CoderService.java - Konstruktor");
  }

  public boolean array123Service(int[] nums)
  {
    boolean result = false;

    log.info("nums Array: " + Arrays.toString(nums));

    for ( int i = 0 ; i < nums.length - 2 ; i++ )
    {
      log.info("nums.length: " + nums.length + " Position: " + i + " mit Wert: " + nums[i]);
      if (nums[i] == 1 && nums[i + 1] == 2 && nums[i + 2] == 3)
      {
        log.info("true :-)");
        return true;

      }
    }
    log.info(result + " :-)");

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
    log.info("-- CoderService.java - stringMatchService() - Speichere Daten über JPA in die DB. Siehe http://localhost:8080/h2-console");

    int result = 0;

    log.info("-- CoderService.java - a: " + a + " b: " + b);

    // schreibe Daten in die DB (entsprechend dem Konstruktor des JPA-Repository, hier : TestWertAusDb.java)
    coderRepository.save(new TestWertAusDb(a, b));

    // lese Daten aus der DB
    TestWertAusDb dbEintrag = getDbEntry(1L);
    log.info(dbEintrag.toString());

    // Logik schreiben

    return result;
  }

  /* Hilfsklasse um DB zu durchsuchen */
  public TestWertAusDb getDbEntry(Long id)
  {
    Optional<TestWertAusDb> optionalDbWert = coderRepository.findById(id);
    String output = (optionalDbWert.isPresent() == true) ? "-- CoderService.java - getDbEntry - optionalDbWert ist TRUE"
      : "-- CoderService.java - getDbEntry - optionalDbWert ist FALSE";
    log.info(output);
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

    log.info("-- CoderService.java - stringXService() - str: " + str);
    for ( int i = 0 ; i < str.length() ; i++ )
    {
      if ((i == 0) || (i == str.length() - 1))
      {
        log.info("-- CoderService.java - stringXService() - füge in result (erstes oder letzes Zeichen): " + str.charAt(i));
        result += str.charAt(i);
      }
      else if ((str.charAt(i) != 'x'))
      {
        log.info("-- CoderService.java - stringXService() - füge in result String: " + str.charAt(i));
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

    log.info("-- CoderController.java - inputstring: " + inputstring);
    log.info("-- CoderController.java - indexes Array Länge: " + indexes.length);
    log.info("-- CoderController.java - strlength: " + strlength);


    for ( int i = 0 ; i < indexes.length ; i++ )
    {
      if ((strlength >= 0) && (indexes[i]) < strlength)
      {
        log.info("-- CoderController.java - füge ins result die Stelle: " + indexes[i]);
        result = result + inputstring.charAt(indexes[i]);
      }
    }

    log.info("-- CoderController.java -  result: " + result);

    return result;
  }

  /**
   * Suppose the string "yak" is unlucky. Given a string, return a version where all the "yak" are removed, but the "a" can be any char. The "yak" strings will not overlap.
   * stringYak("yakpak") → "pak"
   * stringYak("pakyak") → "pak"
   * stringYak("yak123ya") → "123ya"
   */
  public String stringYakService(String str)
  {
    String suchbegriff = "yak";

    if (str.contains(suchbegriff) == false)
    {
      return str;
    }
    else
    {

      // prüfe wie oft Suchbegriff im String vorkommt und filtere diesen raus, sooft wie der "suchbegriff" in String "str" vorkommt
      while (str.contains(suchbegriff))
      {
        str = rausfilternString(suchbegriff, str);
      }

    }

    return str;
  }

  // Suche den String einmal aus dem String raus
  private String rausfilternString(String suchbegriff, String str)
  {

    int strl = str.length() - 1;
    int occurenceStart, occurenceEnd;

    String result = "";
    // an welcher Stelle kommt der Suchstring vor
    occurenceStart = str.indexOf(suchbegriff);
    occurenceEnd = occurenceStart + suchbegriff.length() - 1;

    log.info("CoderController.java - stringYak() - occurenceStart: " + occurenceStart + " occurenceEnd: " + occurenceEnd);
    for ( int i = 0 ; i <= strl ; i++ )

    {
      // Iteriere den String und ignoriere den Suchbegriff ""
      if ((i < occurenceStart || i > occurenceEnd))
      {
        // Anfang des String
        log.info("CoderController.java - stringYak() - für char in result hinzu: " + str.charAt(i));
        result = result + str.charAt(i);
      }
    }
    return result;
  }


  public String stringBitsService(String input)
  {
    String result = "";

    log.info("input: {}", input);

    for ( int i = 0 ; i < input.length() ; i = i + 2 )
    {
      result = result + input.charAt(i);
    }

    return result;
  }
}
