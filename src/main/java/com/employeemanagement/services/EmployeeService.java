package com.employeemanagement.services;

import com.employeemanagement.annotation.MethodLog;
import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.external.service.PayrollService;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.employeemanagement.constant.StringConstant.NAME_REGEX;

@Service
public class EmployeeService {

    @Autowired
    PayrollService payrollService;

    @Autowired
    EmployeeRepoService employeeRepoService;

    @Autowired
    EmployeeRepository employeeRepository;

    @MethodLog
    @Transactional
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return employeeRepoService.saveEmployee(payrollService
                .createEmployee(employeeRepoService.saveAndGetEmployee(employeeRequest)));
    }

    @MethodLog
    public List<Employee> getEmployeesByName(String name) {
        List<Integer> empIds = Optional.ofNullable(payrollService.getAllEmployees())
                .filter(CollectionUtils::isNotEmpty)
                .map(payrollIds -> payrollIds.stream()
                        .map(PayrollEmployeeDetails::getId).collect(Collectors.toList()))
                .orElseThrow(EmployeeNotFoundException::new);

        return Optional.ofNullable(employeeRepoService.fetchEmployeesByName(name))
                .filter(CollectionUtils::isNotEmpty)
                .map(employees -> employees.stream()
                        .filter(employee -> empIds.contains(employee.getId())).collect(Collectors.toList()))
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(EmployeeNotFoundException::new);

    }

    @MethodLog
    public List<Employee> getEmployeesByAge(Integer age) {
        List<Integer> empIds = Optional.ofNullable(payrollService.getAllEmployees())
                .filter(CollectionUtils::isNotEmpty)
                .map(payrollIds -> payrollIds.stream()
                        .map(PayrollEmployeeDetails::getId).collect(Collectors.toList()))
                .orElseThrow(EmployeeNotFoundException::new);

        return Optional.ofNullable(employeeRepoService.fetchEmployeesByAge(age))
                .filter(CollectionUtils::isNotEmpty)
                .map(employees -> employees.stream()
                        .filter(employee -> empIds.contains(employee.getId())).collect(Collectors.toList()))
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(EmployeeNotFoundException::new);

    }

     String getNewName(String name) {
        String regex = "^"+name+NAME_REGEX;
        List<Employee> employeeList =  Optional.ofNullable(employeeRepoService.fetchEmployeesByStartName(name))
                .filter(CollectionUtils::isNotEmpty)
                .map(employees -> employees.stream()
                        .filter(employee -> employee.getName().matches(regex)).collect(Collectors.toList())).get();


        if (employeeList != null && employeeList.size() > 0)
            return name + employeeList.size();
        return name;
    }

   // Utility Methods
    public PayrollEmployeeDetails getEmployeesById(int id) {
        return payrollService.getEmployeeById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
