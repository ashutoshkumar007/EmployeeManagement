package com.employeemanagement.respository;

import com.employeemanagement.modal.request.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    List<Employee> findAllByNameContains(String name);
    List<Employee> findAllByAge(Integer age);
    List<Employee> findAllByNameStartsWith(String name);




}
