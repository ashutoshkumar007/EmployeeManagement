package com.employeemanagement.services;

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

import static com.employeemanagement.constant.StringConstant.NAME_REGEX;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    PayrollService payrollService;

    @Autowired
    EmployeeRepoService employeeRepoService;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        String newName = getNewName(employeeRequest.getName());
        employeeRequest.setName(newName);
        PayrollEmployee payrollEmployee = payrollService.createEmployee(employeeRequest);
        return employeeRepoService.saveEmployee(payrollEmployee);
    }

    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employeeList = employeeRepoService.fetchAllEmployeesByName(name);
        if (employeeList.size() > 0) {
            List<PayrollEmployeeDetails> payrollEmployeeDetailsList = payrollService.getAllEmployees();

            return employeeList;
        } else
            return null;

    }

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
        String nameRegex = name+NAME_REGEX;
        List<Employee> employeeRequestList = employeeRepoService.fetchAllEmployeesByNameRegex(nameRegex);
        if(employeeRequestList.size() >0)
         return name+employeeRequestList.size();
        return name;
    }


    // temp util
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public PayrollEmployeeDetails getEmployeesById(int id){
       return  payrollService.getEmployeeById(id);
    }
}
