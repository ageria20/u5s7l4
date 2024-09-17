package ageria.u5s7l2.controllers;

import ageria.u5s7l2.dto.EmployeeDTO;
import ageria.u5s7l2.entities.Employee;
import ageria.u5s7l2.exception.BadRequestException;
import ageria.u5s7l2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    // 1. GET
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int pages,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeeService.getAllEmployee(pages, size, sortBy);
    }

    // 1.1 GET BY ID
    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)

    public Employee findById(@PathVariable Long employeeId) {
        return this.employeeService.findById(employeeId);
    }


    // 1.2 GET ME's

    @GetMapping("/me")
    public Employee getEmployeeProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        return currentAuthenticatedEmployee;
    }


    //2. POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = (String) bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.employeeService.saveEmployee(body);
    }

    // 2.1 POST UPLOAD AVATAR
    @PostMapping("/avatar/{employeeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadEmployeeAvatar(@RequestParam("avatar") MultipartFile avatar, @PathVariable Long employeeId) {
        this.employeeService.uploadImage(avatar, employeeId);
    }

    // POST
    @PostMapping("/me/avatar")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadEmployeeAvatarAuth(@RequestParam("avatar") MultipartFile avatar, @AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        this.employeeService.uploadImage(avatar, currentAuthenticatedEmployee.getId());
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@PathVariable Long employeeId, @RequestBody Employee body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = (String) bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.employeeService.findByIdAndUpdate(employeeId, body);
    }


    // PUT ME endpoint
    @PutMapping("/me")
    public Employee updateEmployeeProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody Employee body) {
        return this.employeeService.findByIdAndUpdate(currentAuthenticatedEmployee.getId(), body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteEmploye(@PathVariable Long employeeId) {
        try {
            this.employeeService.findByIdAndDelete(employeeId);
            return "Employee Correctly DELETED";
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("YOU CANNOT DELETE AN EMPLOYEE THAT IS LINKED TO A BOOKING");
        }
    }

    // DELETE ME endpoint
    @DeleteMapping("/me")
    public void deleteEmployeeProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        this.employeeService.findByIdAndDelete(currentAuthenticatedEmployee.getId());
    }

}
