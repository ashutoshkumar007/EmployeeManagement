package com.employeemanagement.adapter;

import com.employeemanagement.dto.response.PayrollEmployee;
import com.employeemanagement.modal.request.Employee;

public class PayrollEmployeeAdapter {

    public static Employee adaptPayrollEmployee(PayrollEmployee payrollEmployee){
        return new Employee().setName(payrollEmployee.getName())
                .setAge(payrollEmployee.getAge())
                .setId(payrollEmployee.getId());
    }
}
