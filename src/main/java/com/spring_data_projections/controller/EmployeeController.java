package com.spring_data_projections.controller;

import com.spring_data_projections.dto.EmployeeProjection;
import com.spring_data_projections.entity.Employee;
import com.spring_data_projections.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee-projections")
    public List<EmployeeProjection> getAllEmployeesProjections() {
        return employeeService.getAllEmployeesProjection();
    }

    @GetMapping("/employee-projections/{id}")
    public EmployeeProjection getEmployeeProjectionById(@PathVariable Long id) {
        return employeeService.getEmployeeProjectionById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<EmployeeProjection> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getAllEmployeesByDepartment(departmentId);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
