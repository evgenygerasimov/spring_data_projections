package com.spring_data_projections.repository;

import com.spring_data_projections.dto.EmployeeProjection;
import com.spring_data_projections.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) AS fullName, " +
            "e.position AS position, " +
            "d.name AS departmentName " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "WHERE d.id = :departmentId")
    List<EmployeeProjection> findByDepartmentId(Long departmentId);

    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) AS fullName, " +
            "e.position AS position, " +
            "d.name AS departmentName " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "WHERE e.id = :employeeId")
    EmployeeProjection findEmployeeProjectionById(Long employeeId);

    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) AS fullName, " +
            "e.position AS position, " +
            "d.name AS departmentName " +
            "FROM Employee e " +
            "JOIN e.department d ")
    List<EmployeeProjection> findAllEmployeeProjection();
}
