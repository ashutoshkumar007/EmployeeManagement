package com.employeemanagement.exception;

public class PayrollServiceException extends RuntimeException {
    public PayrollServiceException() {
        super();
    }public PayrollServiceException(Exception ex) {
        super(ex);
    }


}
