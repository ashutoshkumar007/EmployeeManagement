package com.employeemanagement.controllers;

import com.employeemanagement.annotation.MethodLog;
import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

import static com.employeemanagement.constant.StringConstant.SEARCH_NAME_REGEX;

@RestController
@RequestMapping(value = "/api/v1/employee/" , produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class EmployeeController {

   @Autowired
   EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeesByName(@PathVariable  @Pattern(regexp = SEARCH_NAME_REGEX) String name){
        return new ResponseEntity<>(employeeService.getEmployeesByName(name),HttpStatus.OK);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Employee>> getEmployeesByAge(@PathVariable @Min(14) int age){
        return new ResponseEntity<>(employeeService.getEmployeesByAge(age),HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public PayrollEmployeeDetails getEmployeesById(@PathVariable int id){
        return employeeService.getEmployeesById(id);
    }
}
