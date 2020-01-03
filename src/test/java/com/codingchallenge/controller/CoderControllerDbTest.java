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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.codingchallenge.entity.TestWertAusDb;
import com.codingchallenge.repository.CoderRepository;
import com.codingchallenge.webservice.CoderService;


/*
 * IntegrationTest für die DB
 * DB Test sollten Integration Test sein, da wir mehrere Schichten testen.
 * Mock - simuliere eine Schicht (z. B. die Datenbank)
 * - Integrationstest - mehr als nur eine Unittest, und daher nicht nur die Funktionalität des Codes
 * -- brauche daher die DB, dennoch mocke/simuliere diese hier erstmal nur, anstelle sie "real" zu nutzen
 * -- Info: Anwendung bei einer typischen Controller -> Service-> Persistence Schicht Applikation
 * -- Info: alle anderen Abhängigkeiten/Dependencies werden gemockt (hier: im Controller)
 * -- Trennung: Habe Controller für REST, und Services/Webservices für die Logik
 * -- Info: Testen der DB, 3rd Parties Anbieter oder Netzwerk gehört in den Integrationtest
 * Quelle: https://stackabuse.com/how-to-test-a-spring-boot-application/
 * Vorteil:
 * - muss keine tatsächliche Verbindung (z. B. zur DB) aufbauen, sondern mocke/simuliere diese.
 * - Polymorphismus - binde mich nicht an eine bestimmte Klasse (Stichwort: keine/wenig Abhängigkeiten)
 * Info: DataJpaTest
 * - Konfiguration für H2, Hibernate, Spring Data etc.
 */


// Spring Boot Tests + Junit + DataJpaTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class CoderControllerDbTest
{

  /*
   * Info:
   * MockBean Beispiel
   * MockBean - Überschreibt/mockt eine Bean im SpringContext-Pool wenn es gestartet wird
   * InjectMocks - erzeugt eine Klasse und alle dazugehörigen "@Mock"-Mocks
   */


  /* Mock - Simuliert eine Klasse oder ein Objekt. Muss initialisiert werden und ein "when-thenReturn"-Binding haben */
  @Mock
  CoderController coderControllerMock;

  /*
   * Nicht "@autowired", sondern von Mockito @InjectMocks verwenden
   */
  @InjectMocks
  CoderController coderControllerCode;

  @InjectMocks
  CoderService coderControllerService;

  // Hier wird absichtlich die DB genutzt und nicht gemockt
  @Autowired
  private TestEntityManager entityManager; // "TestEntityManager" für die JPA Nutzung

  @Autowired
  private CoderRepository coderRepository;

  @Before
  public void setup()
  {
    // Variablen

    // Initializiere alle anhängenden Dependencies (@Mock) an der eigentlichen Instanz (hier: @InjectMocks)
    MockitoAnnotations.initMocks(this);

    // Mocks
    Mockito.when(this.coderControllerMock.testAMock(anyString())).thenReturn("dies ist simulierter Mock, returniert gewünschten String, NICHT den echten von der Funktion");


  }


  /*
   * Given 2 strings, a and b, return the number of the positions where they contain the same length 2 substring. So "xxcaazz" and "xxbaaz" yields 3, since the "xx", "aa", and "az"
   * substrings appear in the same place in both strings.
   * stringMatch("xxcaazz", "xxbaaz") → 3
   * stringMatch("abc", "abc") → 2
   * stringMatch("abc", "axc") → 0
   **/
  @Test
  public void testDbEntry()
  {
    // schreibe in die DB
    TestWertAusDb stringMatchEntity = new TestWertAusDb("TestWert1", "TestWert2");
    entityManager.persist(stringMatchEntity); // speichere in der Test-DB
    entityManager.flush();

    // lese aus der Test-Db "entityManager"
    TestWertAusDb gefundenerEntry = coderRepository.findById(1L);

    Assert.assertEquals("TestWert1", gefundenerEntry.getStringvalueA());
    Assert.assertEquals("TestWert2", gefundenerEntry.getStringvalueB());
  }
}
