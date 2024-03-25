package com.udacity.jdnd.course3.critter.repositoties;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
  List<Pet> findByIdIn(List<Long> ids);
}
