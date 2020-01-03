package com.codingchallenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * JPA zum speichern von Daten in eine (relationalen) Datenbank
 */

// Entity - zeigt an das es sich um eine JPA Entity für eine Tabelle handelt
@Entity
public class TestWertAusDb
{

  private static final Logger log = LoggerFactory.getLogger(TestWertAusDb.class);

  // ID - ist die Id und wird über "GeneratedValue" automatisch erstellt
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // gemappt Variablen (und Spalten der Tabelle)
  @Column(name = "stringvalueA")
  private String stringvalueA;

  @Column(name = "stringvalueB")
  private String stringvalueB;


  // Default-Constructor - ohne den gibt es eine HibernateSystemException
  protected TestWertAusDb()
  {

    log.info("-- TestWertAusDb.java - Konstuktor leer");
  }

  // Konstruktor der gleich den Parameter in die DB schreibt
  public TestWertAusDb(String stringvalueA, String stringvalueB)
  {
    log.info("-- TestWertAusDb.java - Konstruktor mit 2 Parametern - Schreibe in die DB die Werte A: " + stringvalueA + " Wert B: " + stringvalueB);
    this.stringvalueA = stringvalueA;
    this.stringvalueB = stringvalueB;
  }

  // Getter und Setter

  public String getStringvalueA()
  {
    return this.stringvalueA;
  }


  public void setStringvalueA(String stringvalueA)
  {
    this.stringvalueA = stringvalueA;
  }


  public String getStringvalueB()
  {
    return this.stringvalueB;
  }


  public void setStringvalueB(String stringvalueB)
  {
    this.stringvalueB = stringvalueB;
  }

  public Long getId()
  {
    return id;
  }

  // Methoden

  // zur Ausgabe der Werte/Properties
  @Override
  public String toString()
  {
    String result = "";
    result += String.format("%n-- Customer.java - toString() - Stringvalue[id=%d]", this.id);
    result += String.format("%n-- Customer.java - toString() - Stringvalue: %s ", this.getStringvalueA());
    result += String.format("%n-- Customer.java - toString() - Stringvalue: %s ", this.getStringvalueB());
    return result;
  }


}
