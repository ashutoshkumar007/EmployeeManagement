package com.employeemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayrollEmployeeResponse {
    String status;

    @JsonProperty("data")
    PayrollEmployeeDetails payrollEmployeeDetails;
}
