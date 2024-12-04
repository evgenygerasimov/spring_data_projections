package com.spring_data_projections.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_data_projections.entity.Department;
import com.spring_data_projections.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDepartmentSuccessfully() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");

        when(departmentService.createDepartment(Mockito.any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("HR"));

        verify(departmentService, times(1)).createDepartment(Mockito.any(Department.class));
    }

    @Test
    void shouldReturnAllDepartments() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");
        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("HR2");
        List<Department> departments = Arrays.asList(
                department,
                department2
        );

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/api/departments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("HR2"));

        verify(departmentService, times(1)).getAllDepartments();
    }
}
