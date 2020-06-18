package com.employeemanagement.adapter;

import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;

public class RequestEmployeeAdapter {

    public static Employee adaptEmployeeRequest(EmployeeRequest employeeRequest){
        return new Employee().setAge(employeeRequest.getAge())
                .setName(employeeRequest.getName());
    }
}
