package com.employeemanagement.modal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Accessors(chain = true)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "payroll_id")
    private int payrollId;

   // @Pattern(regexp = CREATE_NAME_REGEX)
    @Column( length = 200,unique = true)
    private String name;

    private int age;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "created_at")
    private Instant createdAt;
}
