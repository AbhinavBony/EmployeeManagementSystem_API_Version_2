package com.springbootapplication.employeemanagementsystem.interfaces;

import com.springbootapplication.employeemanagementsystem.dto.RequestDTO;
import com.springbootapplication.employeemanagementsystem.dto.ResponseDTO;

import java.util.List;

public interface EmployeeService {
    List<ResponseDTO> getAllEmployees();
    ResponseDTO addEmployees(RequestDTO dto);
    ResponseDTO updateEmployee(Integer Id, RequestDTO dto);
    void deleteEmployee(Integer Id);
    ResponseDTO getEmployeeByEmpId(Integer empId);
}
