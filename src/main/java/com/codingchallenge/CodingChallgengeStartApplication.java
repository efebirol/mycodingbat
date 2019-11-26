package com.codingchallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Efe
 *         Startpunkt für Spring Boot
 */
@SpringBootApplication
public class CodingChallgengeStartApplication
{

  private static final Logger log = LoggerFactory.getLogger(CodingChallgengeStartApplication.class);

  /**
   * @param args
   *          Starte Spring Applikation
   */
  public static void main(String[] args)
  {
    SpringApplication.run(CodingChallgengeStartApplication.class, args);
  }

}
