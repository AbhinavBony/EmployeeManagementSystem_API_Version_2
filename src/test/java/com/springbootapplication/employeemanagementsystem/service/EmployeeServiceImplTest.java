package com.springbootapplication.employeemanagementsystem.service;

import com.springbootapplication.employeemanagementsystem.dto.RequestDTO;
import com.springbootapplication.employeemanagementsystem.dto.ResponseDTO;
import com.springbootapplication.employeemanagementsystem.entity.Employee;
import com.springbootapplication.employeemanagementsystem.exception.DuplicateEmployeeFoundException;
import com.springbootapplication.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.springbootapplication.employeemanagementsystem.interfaces.EmployeeService;
import com.springbootapplication.employeemanagementsystem.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService service;
    @Autowired
    private EmployeeRepo repo;
    @Autowired
    private ModelMapper mapper;

    private RequestDTO requestDTO;
    @BeforeEach
    void setUp() {
        repo.deleteAll();
        requestDTO = new RequestDTO();
        requestDTO.setEmpId(199);
        requestDTO.setName("Leon");
        requestDTO.setDepartment("IT");
        requestDTO.setSalary(45789.25);

        Employee employee = mapper.map(requestDTO, Employee.class);
        repo.save(employee);
    }

    @Test
    void getAllEmployees() {
        List<ResponseDTO> empList = service.getAllEmployees();
        assertNotNull(empList);
        assertEquals(1, empList.size());
    }

    @Test
    void addEmployees() {
        RequestDTO dto = new RequestDTO(102, "Ada", "HR", 35689.48);
        ResponseDTO saved = service.addEmployees(dto);
        assertNotNull(saved);
        assertEquals(102, saved.getEmpId());
        assertEquals("Ada", saved.getName());
    }
    @Test
    void addEmployee_DuplicateEmployee(){
        assertThrows(DuplicateEmployeeFoundException.class, ()-> service.addEmployees(requestDTO));
    }

    @Test
    void updateEmployee() {
        RequestDTO updateDto = new RequestDTO(199, "Leon S Kennedy", "Information Technology", 65987.45);
        Employee existing = repo.findByEmpId(199).get();
        ResponseDTO responseDTO = service.updateEmployee(existing.getId(), updateDto);
        assertEquals("Leon S Kennedy", responseDTO.getName());
        assertEquals(65987.45, responseDTO.getSalary());
    }
    @Test
    void updateEmployee_NotFoundId(){
        RequestDTO dto = new RequestDTO();
        dto.setEmpId(200);
        assertThrows(EmployeeNotFoundException.class, ()-> service.updateEmployee(999, dto));
    }

    @Test
    void deleteEmployee() {
        Employee existing = repo.findByEmpId(199).get();
        service.deleteEmployee(existing.getId());
        assertTrue(repo.findById(existing.getId()).isEmpty());
    }
    @Test
    void deleteEmployee_NotFound(){
        assertThrows(EmployeeNotFoundException.class, ()-> service.deleteEmployee(200));
    }

    @Test
    void getEmployeeByEmpId() {
        Employee existsById = repo.findByEmpId(199).get();
        ResponseDTO found = service.getEmployeeByEmpId(existsById.getEmpId());
        assertEquals("Leon", found.getName());
        assertEquals(45789.25, found.getSalary());
    }
    @Test
    void getEmployeeByEmpId_NotFound(){
        assertThrows(EmployeeNotFoundException.class, ()-> service.getEmployeeByEmpId(200));
    }
}