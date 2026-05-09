package com.springbootapplication.employeemanagementsystem.config;

import com.springbootapplication.employeemanagementsystem.dto.RequestDTO;
import com.springbootapplication.employeemanagementsystem.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(RequestDTO.class, Employee.class)
                .addMappings(m -> m.skip(Employee::setId));
        return mapper;
    }
}
