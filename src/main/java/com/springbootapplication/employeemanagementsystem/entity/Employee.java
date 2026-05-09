package com.springbootapplication.employeemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(name = "Employee_Id", nullable = false, unique = true)
    private Integer empId;
    @Column(name = "Employee_Name", nullable = false)
    private String name;
    @Column(name = "Employee_Department", nullable = false)
    private String department;
    @Column(name = "Employee_Salary", nullable = false)
    private Double salary;
}
