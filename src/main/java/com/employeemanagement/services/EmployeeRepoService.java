package com.employeemanagement.services;

import com.employeemanagement.adapter.PayrollEmployeeAdapter;
import com.employeemanagement.adapter.RequestEmployeeAdapter;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRepoService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    public EmployeeRequest saveAndGetEmployee(EmployeeRequest employeeRequest){
        String newName = employeeService.getNewName(employeeRequest.getName());
        employeeRequest.setName(newName);
        try{
            employeeRepository.save(RequestEmployeeAdapter.adaptEmployeeRequest(employeeRequest));
        }catch (Exception e){
            return  saveAndGetEmployee(employeeRequest);
        }
        return employeeRequest;
    }

    public Employee saveEmployee(PayrollEmployee payrollEmployee) {
        try {
           return  employeeRepository.save(PayrollEmployeeAdapter.adaptPayrollEmployee(payrollEmployee));
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Employee> fetchEmployeesByName(String name) {
        return employeeRepository.findAllByNameContains(name);
    }

    public List<Employee> fetchEmployeesByStartName(String name) {
        return employeeRepository.findAllByNameStartsWith(name);
    }

    public List<Employee> fetchEmployeesByAge(int age) {
        return employeeRepository.findAllByAge(age);
    }


}
