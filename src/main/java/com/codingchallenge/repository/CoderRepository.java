package com.codingchallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingchallenge.entity.TestWertAusDb;


/**
 * Info: Erzeugt automatisch eine Query.
 * Quelle: https://stackabuse.com/integrating-h2-database-with-spring-boot/
 * - wir erstellen hier ein Interface uns als "JpaRepository"-Persistence (alternativ "CrudRepository") Layer dient
 * -- CRUD oder JPA um mit der Datenbank zu kommunizieren
 * - CrudRepository gibt mir Methode um in der DB zu suchen, speichern und zu löschen (hier: innerhalb der Customer-Entity-Klasse)
 * - hier innerhalb des Tabelle "TestWerte"
 */

public interface CoderRepository extends JpaRepository<TestWertAusDb, Long>
{

  TestWertAusDb findById(long id);
}
