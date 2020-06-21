package com.employeemanagement.errorhandlers;

import com.employeemanagement.constant.StringConstant;
import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.exception.PayrollServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class SimpleControllerAdvice {
     public static String EMPLOYEE_NOT_FOUND_MESSAGE = "No employee found for this request";
     public static String PAYROLL_EXCEPTION_MESSAGE = "Error in payroll service";
     public static String INVALID_ARGUMENT_MESSAGE ="Invalid argument";
     public static String NUMBER_FORMAT_MESSAGE = "Expecting number in place of string";

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFound(EmployeeNotFoundException exception, HttpServletRequest request){
        log.warn("Employee not found for request {} ",request.getRequestURI(),exception);
        return EMPLOYEE_NOT_FOUND_MESSAGE;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormat(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.warn("Expecting number in place of string{} ",request.getRequestURI(),exception);
        return NUMBER_FORMAT_MESSAGE;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){
        log.warn("Invalid argument passed for request {} ",request.getRequestURI(),exception);
        return StringConstant.CONSTRAINT_VIOLATION_MESSAGE;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request){
        log.warn("Rest api failed for request {} ",request.getRequestURI(),exception);
        return INVALID_ARGUMENT_MESSAGE;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request){
        log.error("Constraint violated for request {} ",request.getRequestURI(),exception);
        return StringConstant.CONSTRAINT_VIOLATION_MESSAGE;
    }

    @ExceptionHandler(PayrollServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlePayrollServiceException(PayrollServiceException exception, HttpServletRequest request){
        log.error("Payroll service failed for request {} ",request.getRequestURI(),exception);
        return PAYROLL_EXCEPTION_MESSAGE;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception, HttpServletRequest request){
        log.error("Rest api failed for request {} ",request.getRequestURI(),exception);
        return exception.getMessage();
    }


}
