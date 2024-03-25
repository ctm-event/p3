package com.udacity.jdnd.course3.critter.entities;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Employee extends User {
  @ElementCollection
  private Set<EmployeeSkill> skills;

  @ElementCollection
  private Set<DayOfWeek> daysAvailable;
}
