package com.udacity.jdnd.course3.critter.repositoties;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.jdnd.course3.critter.entities.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
