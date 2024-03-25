package com.udacity.jdnd.course3.critter.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
