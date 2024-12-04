package com.spring_data_projections.service;

import com.spring_data_projections.dto.EmployeeProjection;
import com.spring_data_projections.entity.Employee;
import com.spring_data_projections.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setPosition("Manager");

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setPosition("Engineer");
    }

    @Test
    void shouldCreateEmployee() {
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee createdEmployee = employeeService.createEmployee(employee1);

        assertEquals(employee1.getId(), createdEmployee.getId());
        assertEquals(employee1.getFirstName(), createdEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void shouldReturnAllEmployees() {
        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        assertEquals(employee1.getFirstName(), employees.get(0).getFirstName());
        assertEquals(employee2.getFirstName(), employees.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void shouldGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        Optional<Employee> employee = employeeService.getEmployeeById(1L);

        assertEquals(employee1.getFirstName(), employee.get().getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Updated");
        updatedEmployee.setLastName("Name");
        updatedEmployee.setPosition("Updated Position");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertEquals("Updated", result.getFirstName());
        assertEquals("Updated Position", result.getPosition());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentEmployee() {
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(3L, employee1);
        });

        verify(employeeRepository, times(1)).findById(3L);
    }

    @Test
    void shouldDeleteEmployee() {
        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnAllEmployeesByDepartment() {
        EmployeeProjection projection1 = mock(EmployeeProjection.class);
        EmployeeProjection projection2 = mock(EmployeeProjection.class);

        List<EmployeeProjection> projections = Arrays.asList(projection1, projection2);

        when(employeeRepository.findByDepartmentId(1L)).thenReturn(projections);

        List<EmployeeProjection> result = employeeService.getAllEmployeesByDepartment(1L);

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findByDepartmentId(1L);
    }

    @Test
    void shouldReturnAllEmployeeProjections() {
        EmployeeProjection projection1 = mock(EmployeeProjection.class);
        EmployeeProjection projection2 = mock(EmployeeProjection.class);

        List<EmployeeProjection> projections = Arrays.asList(projection1, projection2);
        when(employeeRepository.findAllEmployeeProjection()).thenReturn(projections);

        List<EmployeeProjection> result = employeeService.getAllEmployeesProjection();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAllEmployeeProjection();
    }

    @Test
    void shouldReturnEmployeeProjectionById() {
        EmployeeProjection projection = mock(EmployeeProjection.class);

        when(employeeRepository.findEmployeeProjectionById(1L)).thenReturn(projection);

        EmployeeProjection result = employeeService.getEmployeeProjectionById(1L);

        assertEquals(projection, result);
        verify(employeeRepository, times(1)).findEmployeeProjectionById(1L);
    }
}
