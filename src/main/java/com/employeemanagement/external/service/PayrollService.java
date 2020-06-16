package com.employeemanagement.external.service;
import com.employeemanagement.dto.response.*;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.external.ApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    ApiManager apiManager;


    public List<PayrollEmployeeDetails> getAllEmployees(){
        return apiManager.get(payrollBaseUrl,payrollGetAllEndpoint,null
                , PayrollAllEmployeeResponse.class).getPayrollEmployeeDetailsList();
    }

    public PayrollEmployeeDetails getEmployeeById(int id){
        return apiManager.get(payrollBaseUrl,"/api/v1/employee/"+id,null
                , PayrollEmployeeResponse.class).getPayrollEmployeeDetails();
    }

    public PayrollEmployee createEmployee(EmployeeRequest employeeRequest){
           return apiManager.post(payrollBaseUrl, payrollCreateEndpoint, employeeRequest
                   , null, PayrollCreateResponse.class).getPayrollEmployee();
    }
}

