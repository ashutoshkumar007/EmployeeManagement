package com.employeemanagement.controllers;

import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

import static com.employeemanagement.constant.StringConstant.SEARCH_NAME_REGEX;

@RestController
@RequestMapping(value = "/api/v1/employee/" , produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

   @Autowired
   EmployeeService employeeService;

   @Value("${employee.age.min}")
   private int  minAge;

    @Value("${employee.age.max}")
    private int maxAge;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);

    }

    @GetMapping("/name/{name}")
    public List<Employee> getEmployeesByName(@PathVariable  @Pattern(regexp = SEARCH_NAME_REGEX) String name){
        return Optional.ofNullable(employeeService.getEmployeesByName(name))
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @GetMapping("/age/{age}")
    public List<Employee> getEmployeesByAge(@PathVariable @Min(18) int age){
        return employeeService.getEmployeesByAge(age);
    }

    @GetMapping("/id/{id}")
    public PayrollEmployeeDetails getEmployeesById(@PathVariable int id){
        return employeeService.getEmployeesById(id);
    }
}
