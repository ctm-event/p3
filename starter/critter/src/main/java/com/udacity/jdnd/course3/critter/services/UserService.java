package com.udacity.jdnd.course3.critter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositoties.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositoties.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@Service
public class UserService {
  private CustomerRepository customerRepository;
  private PetRepository petRepository;

  public UserService(CustomerRepository customerRepository, PetRepository petRepository) {
    this.customerRepository = customerRepository;
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

}
