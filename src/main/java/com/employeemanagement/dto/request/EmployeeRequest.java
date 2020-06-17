package com.employeemanagement.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.jdo.annotations.Index;
import javax.validation.constraints.Pattern;

import static com.employeemanagement.constant.StringConstant.CREATE_NAME_REGEX;
@Data
@Accessors(chain = true)
public class EmployeeRequest {


    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 200, unique = true)
    @Pattern(regexp = CREATE_NAME_REGEX)
    @Index
    public String name;

    @Range(min=1, message = "Salary is not positive")
    private int salary;

    @Index
    @Range(min=14, message = "Age is less than minimum age")
    private int age;

}
