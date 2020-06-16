package com.employeemanagement.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() {
        super();
    }
    public EmployeeNotFoundException(Exception ex) {
        super(ex);
    }


}
