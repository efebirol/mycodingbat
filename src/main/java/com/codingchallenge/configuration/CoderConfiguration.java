package com.codingchallenge.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Efe
 *         Configuration wird gleich zu Anfang der Spring Boot Application initialisert und als Bean zur Verfügung gestellt.
 *         Über ConfigurationProperty-Annotation verknüpfe ich .properties mit dieser .java Klasse
 *         Hinweis: Getter und Setter sind nötig
 */

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class CoderConfiguration
{

  String testconfiguration;

  String testWert;

  private String description;

  private String uploadDir;

  private final Security security = new Security();

  private final Sammlung sammlung = new Sammlung();

  @Getter
  @Setter
  // für die Key-Properties die mir als einen "."-Punkt haben (e.g. "eins.zwei.drei")
  public static class Security
  {

    private String username;

    private String password;
  }

  @Getter
  @Setter
  // für die Key-Properties die mir als einen "."-Punkt haben (e.g. "app.sammlung.againTwo")
  public static class Sammlung
  {

    private String again;

    private String againTwo;
  }
}
