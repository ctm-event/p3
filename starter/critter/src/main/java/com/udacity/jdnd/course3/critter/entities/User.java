package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.Nationalized;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
  @Id
  @GeneratedValue
  private Long id;

  @Nationalized
  private String name;
}