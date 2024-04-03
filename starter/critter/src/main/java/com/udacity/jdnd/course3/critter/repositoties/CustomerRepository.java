package com.udacity.jdnd.course3.critter.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Customer findByPetsContains(Pet pet);
}
