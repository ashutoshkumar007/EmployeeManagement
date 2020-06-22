package com.employeemanagement.external.service;
import com.employeemanagement.dto.response.*;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.external.RestApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PayrollService {

    @Value("${payroll.service.base.url}")
    private String payrollBaseUrl;

    @Value("${payroll.service.endpoint.create}")
    private String payrollCreateEndpoint;

    @Value("${payroll.service.endpoint.getAll}")
    private String payrollGetAllEndpoint;

    @Autowired
    RestApiManager restApiManager;


    public List<PayrollEmployeeDetails> getAllEmployees(){
        return restApiManager.get(payrollBaseUrl,payrollGetAllEndpoint, getRequestHeader()
                , PayrollAllEmployeeResponse.class).getPayrollEmployeeDetailsList();
    }

    public PayrollEmployeeDetails getEmployeeById(int id){
        return restApiManager.get(payrollBaseUrl,"/api/v1/employee/"+id, getRequestHeader()
                , PayrollEmployeeResponse.class).getPayrollEmployeeDetails();
    }

    public PayrollEmployee createEmployee(EmployeeRequest employeeRequest){
           return restApiManager.post(payrollBaseUrl, payrollCreateEndpoint, employeeRequest
                   , getRequestHeader(), PayrollCreateResponse.class).getPayrollEmployee();
    }

    private HttpHeaders getRequestHeader(){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
        return requestHeaders;
    }
}

