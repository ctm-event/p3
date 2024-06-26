package com.udacity.jdnd.course3.critter.user;

import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.services.UserService;

import javassist.NotFoundException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into
 * separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this
 * class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    @Transactional
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return this.userService.saveCustomer(customerDTO);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return this.userService.getAllCustomers();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return this.userService.getCustomerByPetId(petId);
    }

    @PostMapping("/employee")
    @Transactional
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return this.userService.saveEmployee(employeeDTO);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return this.userService.getEmployee(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    @Transactional
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.userService.addEmployeeSchedule(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return this.userService.findEmployeesForService(employeeDTO);
    }

}
