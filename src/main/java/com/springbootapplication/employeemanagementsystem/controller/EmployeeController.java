package com.springbootapplication.employeemanagementsystem.controller;

import com.springbootapplication.employeemanagementsystem.dto.RequestDTO;
import com.springbootapplication.employeemanagementsystem.dto.ResponseDTO;
import com.springbootapplication.employeemanagementsystem.interfaces.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/EmployeeManagementSystem/v2")
@Validated
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ResponseDTO>> getAll(){
        List<ResponseDTO> list = this.service.getAllEmployees();
        if (list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDTO> add(@Valid @RequestBody RequestDTO dto) {
        ResponseDTO saved = this.service.addEmployees(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping(value = "/update/{Id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable @Positive(message = "Id Can't be negative") Integer Id, @Valid @RequestBody RequestDTO dto){
        ResponseDTO updated = this.service.updateEmployee(Id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping(value = "/delete/{Id}")
    public ResponseEntity<?>delete(@PathVariable @Positive(message = "Id Can't be negative") Integer Id){
        this.service.deleteEmployee(Id);
        return ResponseEntity.ok("Employee Details Deleted Successfully");
    }
    @GetMapping(value = "/getById/{Id}")
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable(name = "Id") @Positive(message = "EmpId Can't be negative") Integer empId){
        ResponseDTO found = this.service.getEmployeeByEmpId(empId);
        return ResponseEntity.ok(found);
    }
}
