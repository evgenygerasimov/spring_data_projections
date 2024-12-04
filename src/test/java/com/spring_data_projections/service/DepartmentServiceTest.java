package com.spring_data_projections.service;

import com.spring_data_projections.entity.Department;
import com.spring_data_projections.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department1 = new Department();
        department1.setId(1L);
        department1.setName("HR Department");

        department2 = new Department();
        department2.setId(2L);
        department2.setName("Finance Department");
    }

    @Test
    void shouldCreateDepartment() {
        when(departmentRepository.save(department1)).thenReturn(department1);

        Department createdDepartment = departmentService.createDepartment(department1);

        assertEquals(department1.getId(), createdDepartment.getId());
        assertEquals(department1.getName(), createdDepartment.getName());
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void shouldReturnAllDepartments() {
        List<Department> mockDepartments = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(mockDepartments);

        List<Department> departments = departmentService.getAllDepartments();

        assertEquals(2, departments.size());
        assertEquals(department1.getName(), departments.get(0).getName());
        assertEquals(department2.getName(), departments.get(1).getName());
        verify(departmentRepository, times(1)).findAll();
    }
}
