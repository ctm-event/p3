package com.udacity.jdnd.course3.critter.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositoties.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositoties.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositoties.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

import javassist.NotFoundException;

@Service
public class UserService {
  private final CustomerRepository customerRepository;
  private final EmployeeRepository employeeRepository;
  private final PetRepository petRepository;

  public UserService(
      CustomerRepository customerRepository,
      EmployeeRepository employeeRepository,
      PetRepository petRepository) {
    this.customerRepository = customerRepository;
    this.employeeRepository = employeeRepository;
    this.petRepository = petRepository;
  }

  public List<CustomerDTO> getAllCustomers() {
    List<Customer> customers = this.customerRepository.findAll();
    List<CustomerDTO> customerDTOs = customers
        .stream()
        .map(customer -> this.convertCustomerEntityToDTO(customer))
        .collect(Collectors.toList());
    return customerDTOs;
  }

  public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
    Customer customer = convertCustomerDTOToEntity(customerDTO);
    return convertCustomerEntityToDTO(customerRepository.save(customer));
  }

  public CustomerDTO getCustomer(long customerId) {
    Customer customer = this.customerRepository.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
    return this.convertCustomerEntityToDTO(customer);
  }

  public CustomerDTO getCustomerByPetId(long petId) {
    Pet pet = this.petRepository.getOne(petId);
    return this.convertCustomerEntityToDTO(pet.getCustomer());
  }

  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    Employee employee = this.employeeRepository.save(this.convertEmployeeDTOToEntity(employeeDTO));
    return this.convertEmployeeEntityToDTO(employee);
  }

  public void addEmployeeSchedule(Set<DayOfWeek> daysAvailable, long employeeId) {
    Employee employee = this.employeeRepository.findById(employeeId)
        .orElseThrow(() -> new RuntimeException("Employee not found"));
    employee.setDaysAvailable(daysAvailable);
    employeeRepository.save(employee);
  }

  public EmployeeDTO getEmployee(long employeeId) {
    Employee employee = this.employeeRepository.findById(employeeId)
        .orElseThrow(() -> new RuntimeException("Employee not found"));
    return this.convertEmployeeEntityToDTO(employee);
  }

  public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
    List<Employee> employees = this.employeeRepository.getAllByDaysAvailableContains(
        employeeRequestDTO.getDate().getDayOfWeek());
    return (List<EmployeeDTO>) employees
        .stream()
        .filter(employee -> employee.getSkills().containsAll(employeeRequestDTO.getSkills()))
        .map(employee -> this.convertEmployeeEntityToDTO(employee))
        .collect(Collectors.toList());
  }

  /**
   * 
   * @param customer
   * @return
   */
  private CustomerDTO convertCustomerEntityToDTO(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(customer.getId());
    customerDTO.setName(customer.getName());
    customerDTO.setPhoneNumber(customer.getPhoneNumber());
    customerDTO.setNotes(customer.getNotes());
    List<Long> petIds = customer.getPets()
        .stream()
        .map(pet -> pet.getId())
        .collect(Collectors.toList());
    customerDTO.setPetIds(petIds);
    return customerDTO;
  }

  /**
   * 
   * @param customerDTO
   * @return
   */
  private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    customer.setName(customerDTO.getName());
    customer.setPhoneNumber(customerDTO.getPhoneNumber());
    customer.setNotes(customerDTO.getNotes());
    List<Long> petIds = customerDTO.getPetIds();
    List<Pet> pets = new ArrayList<>();
    if (!CollectionUtils.isEmpty(petIds)) {
      pets = petRepository.findByIdIn(petIds);
    }
    customer.setPets(pets);
    return customer;
  }

  /**
   * 
   * @param employee
   * @return
   */
  private EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setId(employee.getId());
    employeeDTO.setName(employee.getName());
    employeeDTO.setSkills(employee.getSkills());
    employeeDTO.setDaysAvailable(employee.getDaysAvailable());
    return employeeDTO;
  }

  /**
   * 
   * @param employeeDTO
   * @return
   */
  private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    employee.setId(employeeDTO.getId());
    employee.setName(employeeDTO.getName());
    employee.setSkills(employeeDTO.getSkills());
    employee.setDaysAvailable(employeeDTO.getDaysAvailable());
    return employee;
  }

}
