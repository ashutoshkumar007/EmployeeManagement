package com.employeemanagement.controllers;

import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(value = "")
    public String healthCheck() {
        employeeRepository.save(new Employee().setName("Test"));
        return HttpStatus.OK.getReasonPhrase();
    }
}
