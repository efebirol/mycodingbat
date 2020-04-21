package com.codingchallenge.controller;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.codingchallenge.entity.TestWertAusDb;
import com.codingchallenge.repository.CoderRepository;
import com.codingchallenge.webservice.CoderService;

import lombok.extern.slf4j.Slf4j;


/*
 * Mock - simuliere eine Schicht (z. B. die Datenbank)
 * - Unittests teste nur Funktionalität des Codes
 * -- Info: alle anderen Abhängigkeiten/Dependencies werden gemockt (hier: im Controller)
 * -- Trennung: Habe Controller als für REST, und Services/Webservices für die Logik
 * -- Info: Testen der DB, 3rd Parties Anbieter oder Netzwerk gehört in den Integrationtest
 * Quelle: https://stackabuse.com/how-to-test-a-spring-boot-application/
 * Vorteil:
 * - muss keine tatsächliche Verbindung (z. B. zur DB) aufbauen, sondern mocke/simuliere diese.
 * - Polymorphismus - binde mich nicht an eine bestimmte Klasse (Stichwort: keine/wenig Abhängigkeiten)
 */


// Spring Boot Tests + Junit
@Slf4j
@RunWith(SpringRunner.class)
public class CoderControllerUnitTest
{

  /*
   * Info:
   * MockBean Beispiel
   * MockBean - Überschreibt/mockt eine Bean im SpringContext-Pool wenn es gestartet wird
   * InjectMocks - erzeugt eine Klasse und alle dazugehörigen "@Mock"-Mocks
   */


  /*
   * @Mock - Simuliert eine Klasse oder ein Objekt. Muss initialisiert werden und ein "when-thenReturn"-Binding haben
   * Info: ohne Mocking mit "Mockito.when().thenReturn" erhalten wird null zurück, da Mock-Objekt "null" ist
   * Info: @Mock vs. @InjectMocks - bei @InjectMocks wird die Methode tatsächlich aufgerufen, bei @Mock nicht (liefert "null" ohne mocking).
   * -- @InjectMocks erspart mir das Erstellen des Objektes mit "new". Injizieren die eine Klasse in ein Mock
   * -- Szenario: @InjectMocks auf eine Klasse die instanziert werden soll, und @Mock auf alle die Properties des @InjectMocks-Klasse (@Autowired, Attribute, Methoden etc.)
   */
  @Mock
  CoderController coderControllerMock;

  @Mock
  CoderService coderControllerServiceMock;

  // Mock/Simuliere den Zugriff auf die DB
  @MockBean
  CoderRepository coderRepository;

  /*
   * Nicht "@autowired", sondern von Mockito @InjectMocks verwenden
   */
  @InjectMocks
  CoderController coderControllerCode;

  @InjectMocks
  CoderService coderControllerService;


  @Before
  public void setup()
  {
    // Variablen

    // Initializiere alle anhängenden Dependencies (@Mock) an der eigentlichen Instanz (hier: @InjectMocks)
    MockitoAnnotations.initMocks(this);

    // Mocks
    Mockito.when(this.coderControllerMock.testAMock(anyString())).thenReturn("dies ist simulierter Mock, returniert gewünschten String, NICHT den echten von der Funktion");

    // Mock - Binding für den "testStringMatch"-Testfall
    TestWertAusDb testWertePaar = new TestWertAusDb("testWert1", "testWert2");
    // wird die DB mit "findById" aufgerufen, returniert er eine festen Wert über das "coderRepository"-Mock-Objekt
    Mockito.when(coderRepository.findById(1L)).thenReturn(testWertePaar);
  }

  // JUnit 5
  @BeforeAll
  static void goBeforeAll()
  {
    log.info("-- @BeforeAll - EINMALIG ausgeführt bevor alle Tests beginnen");
  }

  // JUnit 5
  // Aufgerufen vor jedem einzelen Test
  @BeforeEach
  public void goBeforeEach()
  {
    log.info("-- @BeforeEach - Wird vor jedem einzelnen Test ausgeführt:");
  }

  @Test
  public void testADummyMock()
  {
    String expectedString = "dies ist simulierter Mock, returniert gewünschten String, NICHT den echten von der Funktion";
    Assert.assertEquals(expectedString, this.coderControllerMock.testAMock("NichtRelevanterText"));
  }

  /* JUnit Test mit Mock */
  @Test
  public void testArrayFront9()
  {
    // Setup Tests
    int[] actualArray = new int[]{1, 2, 9, 3, 4};
    int[] actualArray2 = new int[]{1, 2, 3, 4, 9};
    int[] actualArray3 = new int[]{1, 2, 3, 4, 5};
    int[] actualArray4 = new int[]{9, 2, 3};

    // Testing
    Assert.assertEquals(true, this.coderControllerCode.arrayFront9(actualArray));
    Assert.assertEquals(false, this.coderControllerCode.arrayFront9(actualArray2));
    Assert.assertEquals(false, this.coderControllerCode.arrayFront9(actualArray3));
    Assert.assertEquals(true, this.coderControllerCode.arrayFront9(actualArray4));
  }


  /*
   * Given an array of ints, return true if the sequence of numbers 1, 2, 3 appears in the array somewhere.
   * array123([1, 1, 2, 3, 1]) → true
   * array123([1, 1, 2, 4, 1]) → false
   * array123([1, 1, 2, 1, 2, 3]) → true
   **/
  @Test
  public void testArray123()
  {
    // Setup Tests
    int[] actualArray = new int[]{1, 1, 2, 3, 1};
    int[] actualArray2 = new int[]{1, 1, 2, 4, 1};
    int[] actualArray3 = new int[]{1, 1, 2, 1, 2, 3};

    // Testing - Teste nur die Methode
    Assert.assertEquals(true, this.coderControllerService.array123Service(actualArray));
    Assert.assertEquals(false, this.coderControllerService.array123Service(actualArray2));
    Assert.assertEquals(true, this.coderControllerService.array123Service(actualArray3));
  }

  /* Info: Testen hier die Service (Logik) mit einem Datenbank-Mock (Persistenz) */
  /*
   * Given 2 strings, a and b, return the number of the positions where they contain the same length 2 substring. So "xxcaazz" and "xxbaaz" yields 3, since the "xx", "aa", and "az"
   * substrings appear in the same place in both strings.
   * stringMatch("xxcaazz", "xxbaaz") → 3
   * stringMatch("abc", "abc") → 2
   * stringMatch("abc", "axc") → 0
   */
  @Test
  public void testStringMatch()
  {
    Long testId = 1L;
    String valueA = "xxcaazz";
    String valueB = "xxbaaz";

    // Einfaches @Mock (nicht @InjectMocks), da nur die Methode simuliert wird ohne seine Verbindungen/Depedencies (z. B. DB, 3rd Parties)
    Mockito.when(this.coderControllerServiceMock.stringMatchService(anyString(), anyString())).thenReturn(3);

    // Aufruf des Service (DB gehört hier gemockt, da nur Interesse an Service besteht
    int result = this.coderControllerServiceMock.stringMatchService(valueA, valueB);
    Assert.assertEquals(3, result);
  }

  /*
   * Given a string, return a version where all the "x" have been removed. Except an "x" at the very start or end should not be removed.
   * stringX("xxHxix") → "xHix"
   * stringX("abxxxcd") → "abcd"
   * stringX("xabxxxcdx") → "xabcdx"
   */
  @Test
  public void TestStringX()
  {
    List<String> actualResults = new ArrayList<String>();

    // Testwerte
    List<String> testval = new ArrayList<String>();
    testval.add("xxHxix");
    testval.add("abxxxcd");
    testval.add("xabxxxcdx");
    testval.add("xKittenx");
    testval.add("Hello");
    testval.add("xx");
    testval.add("x");
    testval.add("");

    List<String> expected = new ArrayList<String>();
    expected.add("xHix");
    expected.add("abcd");
    expected.add("xabcdx");
    expected.add("xKittenx");
    expected.add("Hello");
    expected.add("xx");
    expected.add("x");
    expected.add("");

    // Prüfung des Testergebnisses
    for ( int i = 0 ; i < testval.size() ; i++ )
    {
      log.info("-- CoderControllerUnitTest.java - TestStringX - i: " + i + " mit dem Wert: " + testval.get(i));
      // Aufruf der Funktion/Service
      actualResults.add(i, this.coderControllerService.stringXService(testval.get(i)));
    }

    Assert.assertArrayEquals(expected.toArray(), actualResults.toArray());
  }

  /*
   * Given a string, return a string made of the chars at indexes 0,1, 4,5, 8,9 ... so "kittens" yields "kien".
   * altPairs("kitten") → "kien"
   * altPairs("Chocolate") → "Chole"
   * altPairs("CodingHorror") → "Congrr"
   */

  // Einfacher Unittest
  @Test
  public void testAltPairs()
  {
    List<String> urlTestStringsActual = new ArrayList<>();
    List<String> expecteds = new ArrayList<>();
    List<String> actuals = new ArrayList<>();

    // befüllt Array mit Testwerten
    urlTestStringsActual.add("kitten");
    urlTestStringsActual.add("Chocolate");
    urlTestStringsActual.add("CodingHorror");
    urlTestStringsActual.add("yak");
    urlTestStringsActual.add("ya");
    urlTestStringsActual.add("y");
    urlTestStringsActual.add("");
    urlTestStringsActual.add("ThisThatTheOther");

    // befüllt Array mit erwarteten Testergebnissen
    expecteds.add("kien");
    expecteds.add("Chole");
    expecteds.add("Congrr");
    expecteds.add("ya");
    expecteds.add("ya");
    expecteds.add("y");
    expecteds.add("");
    expecteds.add("ThThThth");

    for ( int i = 0 ; i < urlTestStringsActual.size() ; i++ )
    {
      actuals.add(this.coderControllerService.altPairsService(urlTestStringsActual.get(i)));
    }

    // prüfe den erwarteten String mit den aktuellen String-Resultat
    Assert.assertArrayEquals(expecteds.toArray(), actuals.toArray());

  }

  /**
   * Suppose the string "yak" is unlucky. Given a string, return a version where all the "yak" are removed, but the "a" can be any char. The "yak" strings will not overlap.
   * stringYak("yakpak") → "pak"
   * stringYak("pakyak") → "pak"
   * stringYak("yak123ya") → "123ya"
   */
  @Test
  public void testStringYakService()
  {
    List<String> urlTestStringsActual = new ArrayList<>();
    List<String> expecteds = new ArrayList<>();
    List<String> actuals = new ArrayList<>();

    // befüllt Array mit Testwerten
    urlTestStringsActual.add("yakpak");
    urlTestStringsActual.add("pakyak");
    urlTestStringsActual.add("yak123ya");
    urlTestStringsActual.add("yakxxxyak");
    urlTestStringsActual.add("xxxyakyyyakzzz");
    urlTestStringsActual.add("HiyakHi");
    urlTestStringsActual.add("yak");

    // befüllt Array mit erwarteten Testergebnissen
    expecteds.add("pak");
    expecteds.add("pak");
    expecteds.add("123ya");
    expecteds.add("xxx");
    expecteds.add("xxxyyzzz");
    expecteds.add("HiHi");
    expecteds.add("");

    // führe die Service-Methode aus
    for ( int i = 0 ; i < urlTestStringsActual.size() ; i++ )
    {
      actuals.add(this.coderControllerService.stringYakService(urlTestStringsActual.get(i)));
    }

    // prüfe den erwarteten String mit den aktuellen String-Resultat
    Assert.assertArrayEquals(expecteds.toArray(), actuals.toArray());
  }

  // ToDo: Add JUnit 5 Juniper

  /***
   * Given a string, return a new string made of every other char starting with the first, so "Hello" yields "Hlo".
   * stringBits("Hello") → "Hlo"
   * stringBits("Hi") → "H"
   * stringBits("Heeololeo") → "Hello"
   * 
   * @param input
   * @return result
   */
  @Test
  public void TestStringBits()
  {
    List<String> actuals = new ArrayList<>();
    List<String> expecteds = new ArrayList<>();

    actuals.add(this.coderControllerService.stringBitsService("Hello"));
    expecteds.add("Hlo");

    actuals.add(this.coderControllerService.stringBitsService("Hi"));
    expecteds.add("H");

    actuals.add(this.coderControllerService.stringBitsService("Heeololeo"));
    expecteds.add("Hello");

    actuals.add(this.coderControllerService.stringBitsService("hxaxpxpxy"));
    expecteds.add("happy");

    // JUnit 5 "Jupiter"
    for ( int i = 0 ; i < expecteds.size() ; i++ )
    {
      Assert.assertArrayEquals(expecteds.toArray(), actuals.toArray());
    }

  }
}
