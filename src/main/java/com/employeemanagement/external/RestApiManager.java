package com.employeemanagement.external;

import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.exception.PayrollServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestApiManager {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RetryPolicy retryPolicy;

    public <T> T get(String baseUrl,String endPoint, HttpHeaders requestHeaders, Class<T> responseClassType) {
        ResponseEntity<T> responseEntity = null;
        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(requestHeaders);
//            responseEntity = restTemplate.exchange(buildUrl(baseUrl,endPoint), HttpMethod.GET, requestEntity, responseClassType);
            return getResponse(buildUrl(baseUrl,endPoint), HttpMethod.GET, requestEntity, responseClassType);

        } catch (Exception e) {
            handleException(responseEntity, e);
        }
        return null;
    }


    public <T> T post(String baseUrl,String endPoint, Object body, HttpHeaders requestHeaders, Class<T> responseClassType) {
        ResponseEntity<T> responseEntity = null;
        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(body),requestHeaders);
//            responseEntity = restTemplate.exchange(buildUrl(baseUrl,endPoint), HttpMethod.POST, requestEntity, responseClassType);
            return getResponse(buildUrl(baseUrl,endPoint), HttpMethod.POST, requestEntity, responseClassType);
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                return responseEntity.getBody();
//            }
        } catch (Exception e) {
            handleException(responseEntity, e);
        }
        return null;
    }

    private String buildUrl(String baseUrl, String endpoint) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(endpoint);
        return builder.toString();
    }

    private void handleException(ResponseEntity responseEntity, Exception e) {
        log.error("failed to access payroll service  message {}" ,e.getMessage());
        if (e instanceof HttpClientErrorException) {
            throw new EmployeeNotFoundException();
        }else
            throw new PayrollServiceException();
    }

    private <T> T getResponse(String url, HttpMethod httpMethod ,HttpEntity<Object> requestEntity, Class<T> responseClassType ){
        return Failsafe.with(retryPolicy)
                .onFailedAttempt((r,ex,ctx) -> log.warn("failed attempt to connect to payroll service, attempt: {}, duration {}"
                        ,ctx.getExecutions(),ctx.getElapsedTime().toMillis(),ex))
                .onFailure((r,ex,ctx) -> log.error("failed  to connect to payroll service, attempt: {}, duration {}"
                        ,ctx.getExecutions(),ctx.getElapsedTime().toMillis(),ex))
                .onSuccess((r, ctx) -> log.debug("successfully connected to payroll service , duration: {}",ctx.getElapsedTime().toMillis()))
                .get(() -> restTemplate.exchange(url, httpMethod, requestEntity, responseClassType).getBody());
    }
}
