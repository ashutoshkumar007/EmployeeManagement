package com.employeemanagement.utils;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.modal.request.Employee;

public class EmployeeDataProviderUtil {
    public static Employee getEmployee() {
        return new Employee().setId(1).setAge(28).setName("Ashu").setPayrollId(14);
    }

    public static EmployeeRequest getEmployeerequest() {
        return new EmployeeRequest().setAge(28).setName("Ashu").setSalary(5000);
    }

    public static PayrollEmployee getPayrollEmployee(){
        return new PayrollEmployee().setId(14).setName("Ashu").setAge(28).setSalary(5000);
    }

    public static EmployeeRequest getInvalidEmployeerequest() {
        return new EmployeeRequest().setAge(-28).setName("Ashu").setSalary(5000);
    }


}
