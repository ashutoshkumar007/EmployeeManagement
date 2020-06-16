package com.employeemanagement.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.jdo.annotations.Index;

@Data
@Accessors(chain = true)
public class EmployeeRequest {


    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 200, unique = true)
    //@Pattern(regexp = VALID_NAME_REGEX)
    @Index
    public String name;

    @Range(min=1, max=100000, message = "Salary is not in range")
    private int salary;

    @Index
    @Range(min=18, max=100, message = "Age is not in range")
    private int age;

}
