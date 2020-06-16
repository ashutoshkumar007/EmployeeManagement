package com.employeemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PayrollAllEmployeeResponse {
    String status;

    @JsonProperty("data")
    List<PayrollEmployeeDetails> payrollEmployeeDetailsList;
}
