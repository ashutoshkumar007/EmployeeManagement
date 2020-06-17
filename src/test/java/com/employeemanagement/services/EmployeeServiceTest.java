package com.employeemanagement.services;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.external.service.PayrollService;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.respository.EmployeeRepository;
import com.employeemanagement.utils.EmployeeDataProviderUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

        when(this.payrollService.createEmployee(employeeRequest)).thenReturn(payrollEmployee);
        when(this.employeeRepoService.saveEmployee(payrollEmployee)).thenReturn(employee);

        Employee actualEmployee = this.employeeService.createEmployee(employeeRequest);
        Assert.assertEquals(actualEmployee.getName(), "Ashu");

    }

}
