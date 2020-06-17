package com.employeemanagement.contollers;

import com.employeemanagement.controllers.EmployeeController;
import com.employeemanagement.dto.request.EmployeeRequest;
import com.employeemanagement.modal.request.Employee;
import com.employeemanagement.services.EmployeeService;
import com.employeemanagement.utils.EmployeeDataProviderUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private static final String BASE_URL = "/api/v1/employee";

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void setUp(){
         objectMapper =new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void createEmployee() throws Exception {
        EmployeeRequest employeeRequest = EmployeeDataProviderUtil.getEmployeerequest();
        Employee employee = EmployeeDataProviderUtil.getEmployee();

        when(this.employeeService.createEmployee(employeeRequest)).thenReturn(employee);

        mockMvc.perform(post(BASE_URL+"/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(employeeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(employee.getId()))
                .andExpect(jsonPath("age").value(employee.getAge()))
                .andExpect(jsonPath("name").value(employee.getName()));
    }

    @Test
    public void createInvalidEmployee() throws Exception {
        EmployeeRequest employeeRequest = EmployeeDataProviderUtil.getInvalidEmployeerequest();

        mockMvc.perform(post(BASE_URL+"/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(employeeRequest)))
                .andExpect(status().isBadRequest());
        verifyZeroInteractions(this.employeeService);
    }

    @Test
    public void getEmployeesByName() throws Exception {
        Employee employee = EmployeeDataProviderUtil.getEmployee();
        List<Employee> employeeList =new ArrayList<>(Arrays.asList(employee));


        when(this.employeeService.getEmployeesByName(employee.getName())).thenReturn(employeeList);

        mockMvc.perform((get(BASE_URL+"/name"+"/{name}",employee.getName())
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());

    }

    @Test
    public void getEmployeesByAge() throws Exception {
        Employee employee = EmployeeDataProviderUtil.getEmployee();
        List<Employee> employeeList =new ArrayList<>(Arrays.asList(employee));


        when(this.employeeService.getEmployeesByName(employee.getName())).thenReturn(employeeList);

        mockMvc.perform((get(BASE_URL+"/age"+"/{age}",employee.getAge())
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());

    }

    @Test
    public void getEmployeesByInvalidAge() throws Exception {

        mockMvc.perform((get(BASE_URL+"/age"+"/{age}",-4)
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getEmployeesByInvalidName() throws Exception {

        mockMvc.perform((get(BASE_URL+"/name"+"/{name}","12As54tx")
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());

    }

    private String createJson(EmployeeRequest employeeRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(employeeRequest);
    }

}
