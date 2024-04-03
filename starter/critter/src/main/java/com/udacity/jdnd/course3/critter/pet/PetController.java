package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.services.PetService;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Transactional
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return this.petService.save(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return this.petService.getOne(petId);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return this.petService.getAll();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getPetsByOwner(ownerId);
    }
}
