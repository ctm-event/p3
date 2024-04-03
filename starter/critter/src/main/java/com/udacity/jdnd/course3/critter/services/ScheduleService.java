package com.udacity.jdnd.course3.critter.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repositoties.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositoties.PetRepository;
import com.udacity.jdnd.course3.critter.repositoties.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@Service
public class ScheduleService {
  private final ScheduleRepository scheduleRepository;
  private final PetRepository petRepository;
  private final EmployeeRepository employeeRepository;

  public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository,
      EmployeeRepository employeeRepository) {
    this.scheduleRepository = scheduleRepository;
    this.petRepository = petRepository;
    this.employeeRepository = employeeRepository;
  }

  public List<ScheduleDTO> getAll() {
    List<Schedule> schedules = this.scheduleRepository.findAll();
    List<ScheduleDTO> scheduleDTOs = schedules
        .stream()
        .map(schedule -> this.convertScheduleEntityToDTO(schedule))
        .collect(Collectors.toList());
    return scheduleDTOs;
  }

  public ScheduleDTO save(ScheduleDTO scheduleDTO) {
    Schedule schedule = this.convertScheduleDTOToEntity(scheduleDTO);
    this.scheduleRepository.save(schedule);
    return this.convertScheduleEntityToDTO(schedule);
  }

  public List<ScheduleDTO> getSchedulesByPetId(long petId) {
    Pet pet = new Pet();
    pet.setId(petId);
    List<Schedule> schedules = this.scheduleRepository.getAllByPetsContains(pet);
    List<ScheduleDTO> scheduleDTOs = schedules
        .stream()
        .map(schedule -> this.convertScheduleEntityToDTO(schedule))
        .collect(Collectors.toList());
    return scheduleDTOs;
  }

  public List<ScheduleDTO> getSchedulesByEmployeeId(long employeeId) {
    Employee employee = new Employee();
    employee.setId(employeeId);
    List<Schedule> schedules = this.scheduleRepository.getAllByEmployeesContains(employee);
    List<ScheduleDTO> scheduleDTOs = schedules
        .stream()
        .map(schedule -> this.convertScheduleEntityToDTO(schedule))
        .collect(Collectors.toList());
    return scheduleDTOs;
  }

  public List<ScheduleDTO> getSchedulesByCustomerId(long customerId) {
    Customer customer = new Customer();
    customer.setId(customerId);
    List<Pet> pets = this.petRepository.findByCustomer(customer);
    List<Schedule> schedules = this.scheduleRepository.getAllByPetsIn(pets);
    List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
    for (Schedule schedule : schedules) {
      scheduleDTOs.add(convertScheduleEntityToDTO(schedule));
    }
    return scheduleDTOs;
  }

  /**
   * @param schedule
   * @return
   */
  private ScheduleDTO convertScheduleEntityToDTO(Schedule schedule) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();
    scheduleDTO.setId(schedule.getId());
    scheduleDTO.setActivities(schedule.getActivities());
    scheduleDTO.setDate(schedule.getDate());
    List<Long> petIds = new ArrayList<>();
    for (Pet pet : schedule.getPets()) {
      petIds.add(pet.getId());
    }
    scheduleDTO.setPetIds(petIds);
    List<Long> employeeIds = new ArrayList<>();
    for (Employee employee : schedule.getEmployees()) {
      employeeIds.add(employee.getId());
    }
    scheduleDTO.setEmployeeIds(employeeIds);
    return scheduleDTO;
  }

  /**
   * @param scheduleDTO
   * @return
   */
  private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
    Schedule schedule = new Schedule();

    schedule.setId(scheduleDTO.getId());
    schedule.setActivities(scheduleDTO.getActivities());
    schedule.setDate(scheduleDTO.getDate());

    List<Long> petIds = scheduleDTO.getPetIds();
    List<Pet> pets = new ArrayList<>();
    if (!CollectionUtils.isEmpty(petIds)) {
      pets = this.petRepository.findByIdIn(petIds);
    }
    schedule.setPets(pets);

    List<Long> employeeIds = scheduleDTO.getEmployeeIds();
    List<Employee> employees = new ArrayList<>();
    if (!CollectionUtils.isEmpty(petIds)) {
      employees = this.employeeRepository.findByIdIn(employeeIds);
    }
    schedule.setEmployees(employees);

    return schedule;
  }

}
