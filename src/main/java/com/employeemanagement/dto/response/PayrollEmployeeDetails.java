package com.employeemanagement.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayrollEmployeeDetails {
    private String employee_name;
    private int employee_salary;
    private int employee_age;
    private int id;
    private String profile_image;
}
