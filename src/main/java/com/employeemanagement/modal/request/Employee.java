package com.employeemanagement.modal.request;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Accessors(chain = true)
public class Employee {

    private int Id;

    @Id
    @Column(length = 200, unique = true)
    private String name;

    private int age;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "created_at")
    private Instant createdAt;
}
