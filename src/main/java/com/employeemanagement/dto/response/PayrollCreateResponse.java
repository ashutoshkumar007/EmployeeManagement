package com.employeemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PayrollCreateResponse {
    @JsonProperty("status")
    String status;

    @JsonProperty("data")
    PayrollEmployee payrollEmployee;
}
