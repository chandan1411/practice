package com.example.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dao.EmployeeDAO;
import com.example.entity.Employee;
import com.example.entity.Employees;

@RestController
//@RequestMapping(path = "/employees")
public class EmployeeController {
	
	@Autowired
    private EmployeeDAO employeeDao;
     
    @GetMapping(path="/all", produces = "application/json")
    public Employees getEmployees() 
    {
        return employeeDao.getAllEmployees();
    }
    
    @GetMapping(path="/test", produces = "application/json")
    public String  test() 
    {
        return "Welcome";
    }
     
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) 
    {
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);
         
        employeeDao.addEmployee(employee);
         
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(employee.getId())
                                    .toUri();
         
        return ResponseEntity.created(location).build();
    }

}