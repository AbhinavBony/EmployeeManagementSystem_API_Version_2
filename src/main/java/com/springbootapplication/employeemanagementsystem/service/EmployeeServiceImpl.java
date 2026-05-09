package com.springbootapplication.employeemanagementsystem.service;
import com.springbootapplication.employeemanagementsystem.dto.RequestDTO;
import com.springbootapplication.employeemanagementsystem.dto.ResponseDTO;
import com.springbootapplication.employeemanagementsystem.entity.Employee;
import com.springbootapplication.employeemanagementsystem.exception.DuplicateEmployeeFoundException;
import com.springbootapplication.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.springbootapplication.employeemanagementsystem.interfaces.EmployeeService;
import com.springbootapplication.employeemanagementsystem.repository.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo repo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ResponseDTO> getAllEmployees() {
        log.info("Fetching All Employees...");
        List<ResponseDTO> dtoList = repo.findAll().stream().map(employee ->mapper.map(employee, ResponseDTO.class)).toList();
        if (dtoList.isEmpty()){
            log.warn("No Employees Details Found in DB..");
        }else {
            log.debug("Employee Details Fetched: {}", dtoList.size());
        }
        return dtoList;
    }

    @Override
    public ResponseDTO addEmployees(RequestDTO dto) {
        log.info("Adding Employee With Id: {}", dto.getEmpId());
        repo.findByEmpId(dto.getEmpId()).ifPresent(emp-> {
            log.error("duplicate Employee Found With Id: {}", dto.getEmpId());
            throw new DuplicateEmployeeFoundException("Employee Already Exists With Emp Id: "+ dto.getEmpId());
        });
        Employee employee = this.mapper.map(dto, Employee.class);
        Employee saved = repo.save(employee);
        log.debug("Saved Employee Entity: {}", saved);
        log.info("Employee Added Successfully with Id: {}", saved.getEmpId());
        return mapper.map(saved, ResponseDTO.class);
    }

    @Override
    public ResponseDTO updateEmployee(Integer Id, RequestDTO dto) {
        log.info("Updating Employee with Id: {}", Id);
        Employee found = repo.findById(Id).orElseThrow(()-> {
            log.error("Employee Not Found With Id: {}", Id);
            return new EmployeeNotFoundException("Employee Not found With Id: " + Id);
        });
        this.mapper.map(dto, found);
        Employee updated = repo.save(found);
        log.info("Employee Details Updated Successfully: {}", updated.getEmpId());
        return this.mapper.map(updated, ResponseDTO.class);
    }

    @Override
    public void deleteEmployee(Integer Id) {
        log.info("Deleting Employee Details with Id: {}", Id);
        repo.findById(Id).orElseThrow(()-> {
            log.error("Employee Not Found With Id: {}", Id);
            return new EmployeeNotFoundException("Employee Not Found With Id: "+ Id);
        });
        repo.deleteById(Id);
        log.info("Employee Details Deleted Successfully: {}", Id);
    }

    @Override
    public ResponseDTO getEmployeeByEmpId(Integer empId) {
        log.info("Fetching Employee By Id: {}", empId);
        Employee existsById = repo.findByEmpId(empId).orElseThrow(()-> {
            log.error("Employee not Found With EmpId: {}", empId);
            return new EmployeeNotFoundException("Employee Not found With EmpId: "+ empId);
        });
        log.info("Employee Fetched Successfully with EmpId: {}", empId);
        return this.mapper.map(existsById, ResponseDTO.class);
    }
}