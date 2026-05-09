package com.springbootapplication.employeemanagementsystem.repository;

import com.springbootapplication.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmpId(Integer empId);
}
