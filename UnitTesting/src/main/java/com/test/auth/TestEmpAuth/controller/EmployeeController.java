package com.test.auth.TestEmpAuth.controller;

import com.test.auth.TestEmpAuth.model.TestEmployee;
//import com.test.auth.TestEmpAuth.model.Users;
import com.test.auth.TestEmpAuth.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

//    public EmployeeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }

    @GetMapping("/public/employees")
    public ResponseEntity<List<TestEmployee>> getAllEmployees() {
        List<TestEmployee> allTestEmployees = employeeService.getAllEmployees();

        return new ResponseEntity<>(allTestEmployees, HttpStatus.OK);
    }

    @PostMapping("/admin/employee")
    public ResponseEntity<String> addNewEmployee(@RequestBody TestEmployee testEmployee) {


        employeeService.addNewEmployee(testEmployee);

        return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/admin/employees/{employeeId}")
    public ResponseEntity<String> updateExistingEmployee(@RequestBody TestEmployee testEmployee, @PathVariable Long employeeId)
    {
            employeeService.updateExistingEmployee(testEmployee, employeeId);

            return new ResponseEntity<>("Updated Employee with employeeId: " + employeeId + " successfully", HttpStatus.OK);

    }

    @DeleteMapping("/admin/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId)
    {
        String status = employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<>(status, HttpStatus.OK);

    }

//    @PostMapping("/login")
//    public String login(@RequestBody Users user)
//    {
//        return employeeService.verify(user);
//    }



}
