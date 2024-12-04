package com.spring_data_projections.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_data_projections.entity.Employee;
import com.spring_data_projections.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    private Employee employee2;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("HR");

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setPosition("Finance");

        employees = Arrays.asList(
                employee,
                employee2
        );
    }

    @Test
    void shouldCreateEmployeeSuccessfully() throws Exception {
        when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.position").value("HR"));

        verify(employeeService, times(1)).createEmployee(Mockito.any(Employee.class));
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("HR"));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() throws Exception {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Updated");
        updatedEmployee.setLastName("Doe");
        updatedEmployee.setPosition("Finance");

        when(employeeService.updateEmployee(eq(1L), Mockito.any(Employee.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Finance"));

        verify(employeeService, times(1)).updateEmployee(eq(1L), Mockito.any(Employee.class));
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}
