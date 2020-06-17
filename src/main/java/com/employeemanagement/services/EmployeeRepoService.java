package com.employeemanagement.services;

import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.employeemanagement.constant.StringConstant.NAME_REGEX;
import static com.employeemanagement.constant.StringConstant.SEARCH_NAME_REGEX;

@Service
public class EmployeeRepoService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(PayrollEmployee payrollEmployee) {
        try {
            Employee employee = new Employee()
                    .setPayrollId(payrollEmployee.getId())
                    .setAge(payrollEmployee.getAge())
                    .setName(payrollEmployee.getName());
            employeeRepository.save(employee);
            return employee;
        } catch (Exception e) {
            throw e;
        }

    }

    public List<Employee> fetchAllEmployeesByName(String name) {
        return employeeRepository.findAllByNameContains(name);
    }

    public List<Employee> fetchEmployeesByAge(int age) {
        return employeeRepository.findAllByAge(age);
    }

    public List<Employee> fetchAllEmployeesByNameRegex(String name) {
        List<Employee> employeeList = employeeRepository.findAllByNameStartsWith(name);
        return employeeList;
    }

}
