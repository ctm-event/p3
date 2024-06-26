package com.udacity.jdnd.course3.critter.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repositoties.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositoties.PetRepository;

@Service
@Transactional
public class PetService {
  private final PetRepository petRepository;
  private final CustomerRepository customerRepository;

  public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
    this.petRepository = petRepository;
    this.customerRepository = customerRepository;
  }

  public List<PetDTO> getAll() {
    List<Pet> pets = this.petRepository.findAll();
    List<PetDTO> petDTOs = pets
        .stream()
        .map(pet -> this.convertEntityToDTO(pet))
        .collect(Collectors.toList());
    return petDTOs;
  }

  public PetDTO save(PetDTO petDTO) {
    Pet pet = convertDTOToEntity(petDTO);
    return convertEntityToDTO(petRepository.save(pet));
  }

  public List<PetDTO> getPetsByOwner(long ownerId) {
    Customer customer = new Customer();
    customer.setId(ownerId);
    List<Pet> pets = this.petRepository.findByCustomer(customer);
    List<PetDTO> petDTOs = pets
        .stream()
        .map(pet -> this.convertEntityToDTO(pet))
        .collect(Collectors.toList());
    return petDTOs;
  }

  public PetDTO getOne(long petId) {
    Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
    return this.convertEntityToDTO(pet);
  }

  private PetDTO convertEntityToDTO(Pet pet) {
    PetDTO petDTO = new PetDTO();
    petDTO.setId(pet.getId());
    petDTO.setName(pet.getName());
    petDTO.setNotes(pet.getNotes());
    petDTO.setBirthDate(pet.getBirthDate());
    petDTO.setType(pet.getType());
    petDTO.setOwnerId(pet.getCustomer().getId());
    return petDTO;
  }

  private Pet convertDTOToEntity(PetDTO petDTO) {
    Pet pet = new Pet();
    pet.setName(petDTO.getName());
    pet.setNotes(petDTO.getNotes());
    pet.setType(petDTO.getType());
    pet.setBirthDate(petDTO.getBirthDate());
    Long ownerId = petDTO.getOwnerId();
    if (ownerId != null) {
      Customer customer = this.customerRepository.findById(ownerId)
          .orElseThrow(() -> new RuntimeException("Owner not found"));
      pet.setCustomer(customer);
    }
    return pet;
  }
}
