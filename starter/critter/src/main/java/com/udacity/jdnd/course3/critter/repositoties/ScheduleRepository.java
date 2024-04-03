package com.udacity.jdnd.course3.critter.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import java.util.List;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  List<Schedule> getAllByPetsIn(List<Pet> pets);

  List<Schedule> getAllByPetsContains(Pet pet);

  List<Schedule> getAllByEmployeesContains(Employee employee);

}
