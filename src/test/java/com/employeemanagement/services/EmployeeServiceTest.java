package com.employeemanagement.services;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.external.service.PayrollService;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.utils.EmployeeDataProviderUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

   @Mock
   private PayrollService payrollService;

   @Mock
   private EmployeeRepoService employeeRepoService;

   @InjectMocks
   private EmployeeService employeeService;

    @Test
    public void createEmployee(){
        PayrollEmployee payrollEmployee = EmployeeDataProviderUtil.getPayrollEmployee();
        EmployeeRequest employeeRequest =EmployeeDataProviderUtil.getEmployeerequest();
        Employee employee = EmployeeDataProviderUtil.getEmployee();

        when(this.employeeRepoService.saveAndGetEmployee(employeeRequest)).thenReturn(employeeRequest);
        when(this.payrollService.createEmployee(employeeRequest)).thenReturn(payrollEmployee);
        when(this.employeeRepoService.saveEmployee(payrollEmployee)).thenReturn(employee);

        Employee actualEmployee = this.employeeService.createEmployee(employeeRequest);
        Assert.assertEquals(actualEmployee.getName(), "Ashu");

    }

    @Test
    public void getEmployeesByAge(){
        PayrollEmployeeDetails payrollEmployeeDetails = EmployeeDataProviderUtil.getPayrollEmployeeDetails();
        Employee employee = EmployeeDataProviderUtil.getEmployee();
        List<PayrollEmployeeDetails> payrollEmployeeList =new ArrayList<>(Arrays.asList(payrollEmployeeDetails));
        List<Employee> employeeList =new ArrayList<>(Arrays.asList(employee));

        when(this.payrollService.getAllEmployees()).thenReturn(payrollEmployeeList);
        when(this.employeeRepoService.fetchEmployeesByAge(28)).thenReturn(employeeList);

        List<Employee> actualEmployees = this.employeeService.getEmployeesByAge(28);
        Assert.assertEquals(actualEmployees.get(0).getName(),"Ashu");

    }

    @Test
    public void getEmployeesByName(){
        PayrollEmployeeDetails payrollEmployeeDetails = EmployeeDataProviderUtil.getPayrollEmployeeDetails();
        Employee employee = EmployeeDataProviderUtil.getEmployee();
        List<PayrollEmployeeDetails> payrollEmployeeList =new ArrayList<>(Arrays.asList(payrollEmployeeDetails));
        List<Employee> employeeList =new ArrayList<>(Arrays.asList(employee));

        when(this.payrollService.getAllEmployees()).thenReturn(payrollEmployeeList);
        when(this.employeeRepoService.fetchEmployeesByName("Ashu")).thenReturn(employeeList);

        List<Employee> actualEmployees = this.employeeService.getEmployeesByName("Ashu");
        Assert.assertEquals(actualEmployees.get(0).getAge(),28);

    }

}
