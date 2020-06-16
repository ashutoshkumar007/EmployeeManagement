package com.employeemanagement.external;

import com.employeemanagement.exception.EmployeeNotFoundException;
import com.employeemanagement.exception.PayrollServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ApiManager {

    @Autowired
    RestTemplate restTemplate;

    public <T> T get(String baseUrl,String endPoint, HttpHeaders requestHeaders, Class<T> responseClassType) {
        ResponseEntity<T> responseEntity = null;
        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(requestHeaders);
            responseEntity = restTemplate.exchange(buildUrl(baseUrl,endPoint), HttpMethod.GET, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
        } catch (Exception e) {
            handleException(responseEntity, e);
        }
        return null;
    }

    public <T> T post(String baseUrl,String endPoint, Object body, HttpHeaders requestHeaders, Class<T> responseClassType) {
        ResponseEntity<T> responseEntity = null;
        try {
            HttpEntity<Object> requestEntity = new HttpEntity<>(body,requestHeaders);
            responseEntity = restTemplate.exchange(buildUrl(baseUrl,endPoint), HttpMethod.POST, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
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
}
