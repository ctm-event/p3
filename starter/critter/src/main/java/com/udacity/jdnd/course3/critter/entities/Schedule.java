package com.udacity.jdnd.course3.critter.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToMany()
  @JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
  private List<Employee> employees;

  @ManyToMany()
  @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "pet_id"))
  private List<Pet> pets;

  private LocalDate date;

  @ElementCollection
  private Set<EmployeeSkill> activities;
}
