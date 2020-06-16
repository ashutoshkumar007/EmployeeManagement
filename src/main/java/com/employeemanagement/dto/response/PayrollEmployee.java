package com.employeemanagement.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayrollEmployee {
    private String name;
    private int salary;
    private int age;
    private int id;
}
