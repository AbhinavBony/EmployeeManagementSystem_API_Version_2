package com.springbootapplication.employeemanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestDTO {
    @Positive(message = "EmpId Can't be negative")
    @NotNull(message = "EmpId Can't be empty/blank")
    private Integer empId;
    @NotBlank(message = "Emp Name Can't be empty/blank")
    private String name;
    @NotBlank(message = "Emp Department Can't be empty/blank")
    private String department;
    @Positive(message = "Emp Salary Can't be negative")
    @NotNull(message = "Emp Salary Can't be empty/blank")
    private Double salary;
}
