package com.codingchallenge.controller;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@RunWith(SpringRunner.class)
public class CoderControllerUnitTest
{

  /*
   * ToDo:
   * MockBean Beispiel
   * MockBean - Überschreibt/mockt eine Bean im SpringContext-Pool wenn es gestartet wird
   * InjectMocks - erzeugt eine Klasse und alle dazugehörigen "@Mock"-Mocks
   */


  /* Mock - Simuliert eine Klasse oder ein Objekt. Muss initialisiert werden und ein "when-thenReturn"-Binding haben */
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
}
