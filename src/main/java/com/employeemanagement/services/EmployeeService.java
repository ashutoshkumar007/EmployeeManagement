package com.employeemanagement.services;

import com.employeemanagement.annotation.MethodLog;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.external.service.PayrollService;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    PayrollService payrollService;

    @Autowired
    EmployeeRepoService employeeRepoService;

    @MethodLog
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        String newName = getNewName(employeeRequest.getName());
        employeeRequest.setName(newName);
        PayrollEmployee payrollEmployee = payrollService.createEmployee(employeeRequest);
        return employeeRepoService.saveEmployee(payrollEmployee);
    }

    @MethodLog
    public List<Employee> getEmployeesByName(String name) {
        List<Integer> payrollEmpIds = Optional.ofNullable(payrollService.getAllEmployees())
                .filter(CollectionUtils::isNotEmpty)
                .map(payrollIds -> payrollIds.stream()
                        .map(PayrollEmployeeDetails::getId).collect(Collectors.toList()))
                .orElseThrow(EmployeeNotFoundException::new);

        return Optional.ofNullable(employeeRepoService.fetchAllEmployeesByName(name))
                .filter(CollectionUtils::isNotEmpty)
                .map(employees -> employees.stream()
                        .filter(employee -> payrollEmpIds.contains(employee.getPayrollId())).collect(Collectors.toList()))
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(EmployeeNotFoundException::new);

    }

    @MethodLog
    public List<Employee> getEmployeesByAge(Integer age) {
        List<Integer> payrollEmpIds = Optional.ofNullable(payrollService.getAllEmployees())
                .filter(CollectionUtils::isNotEmpty)
                .map(payrollIds -> payrollIds.stream()
                        .map(PayrollEmployeeDetails::getId).collect(Collectors.toList()))
                .orElseThrow(EmployeeNotFoundException::new);

        return Optional.ofNullable(employeeRepoService.fetchEmployeesByAge(age))
                .filter(CollectionUtils::isNotEmpty)
                .map(employees -> employees.stream()
                        .filter(employee -> payrollEmpIds.contains(employee.getPayrollId())).collect(Collectors.toList()))
                .filter(CollectionUtils::isNotEmpty)
                .orElseThrow(EmployeeNotFoundException::new);

    }

    private String getNewName(String name) {
        int length = name.length();
       List<Employee> employeeList =  Optional.ofNullable(employeeRepoService.fetchAllEmployeesByName(name))
               .map(employees -> employees.stream()
               .filter(employee -> employee.getName().length() >= length)
                       .collect(Collectors.toList())).get();

       if(employeeList.size()>0)
           return name+employeeList.size();
        return name;
    }

    public PayrollEmployeeDetails getEmployeesById(int id){
       return  payrollService.getEmployeeById(id);
    }
}
