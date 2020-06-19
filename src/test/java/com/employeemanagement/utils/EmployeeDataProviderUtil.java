package com.employeemanagement.utils;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.dto.response.PayrollEmployeeDetails;
import com.employeemanagement.modal.request.Employee;

public class EmployeeDataProviderUtil {
    public static Employee getEmployee() {
        return new Employee().setId(1).setAge(28).setName("Ashu").setId(14);
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

    public static PayrollEmployeeDetails getPayrollEmployeeDetails() {
        return new PayrollEmployeeDetails().setEmployee_age(28).setEmployee_name("Ashu")
                .setEmployee_salary(5000).setId(14);
    }


}
