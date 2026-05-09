package com.springbootapplication.employeemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Integer empId;
    private String name;
    private String department;
    private Double salary;
}
