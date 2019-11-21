package com.codingchallenge.controller;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


/*
 * Mock - simuliere eine Schicht (z. B. die Datenbank)
 * Quelle: https://stackabuse.com/how-to-test-a-spring-boot-application/
 * Vorteil:
 * - muss keine tatsächliche Verbindung (z. B. zur DB) aufbauen, sondern mocke/simuliere diese.
 */


// Spring Boot Tests + Junit
@RunWith(SpringRunner.class)
public class CoderControllerUnitTest
{

  @MockBean
  CoderController coderControllerMock;

  /*
   * Nicht "@autowired", sondern von Mockito @InjectMocks verwenden
   */
  @InjectMocks
  CoderController coderControllerCode;

  CoderController coderController;

  @Before
  public void setup()
  {
    // Variablen

    // Mocks
    Mockito.when(this.coderControllerMock.testAMock(anyString())).thenReturn("dies ist simulierter Mock, returniert gewünschten String, NICHT den echten von der Funktion");

    // Initializiere alle anhängenden Dependencies (@Mock) an der eigentlichen Instanz (hier: @InjectMocks)
    MockitoAnnotations.initMocks(this);
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

    coderController = new CoderController();

    // Testing
    Assert.assertEquals(true, this.coderControllerCode.arrayFront9(actualArray));
    Assert.assertEquals(false, this.coderControllerCode.arrayFront9(actualArray2));
    Assert.assertEquals(false, this.coderControllerCode.arrayFront9(actualArray3));
    Assert.assertEquals(true, this.coderControllerCode.arrayFront9(actualArray4));
  }

}
