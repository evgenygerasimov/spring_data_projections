package com.spring_data_projections.service;

import com.spring_data_projections.dto.EmployeeProjection;
import com.spring_data_projections.entity.Employee;
import com.spring_data_projections.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeProjection> getAllEmployeesProjection() {
        return employeeRepository.findAllEmployeeProjection();
    }

    public EmployeeProjection getEmployeeProjectionById(Long id) {
        return employeeRepository.findEmployeeProjectionById(id);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setPosition(updatedEmployee.getPosition());
            employee.setSalary(updatedEmployee.getSalary());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeProjection> getAllEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}

