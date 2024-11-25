package com.test.auth.TestEmpAuth.service;

import com.test.auth.TestEmpAuth.model.TestEmployee;
//import com.test.auth.TestEmpAuth.model.Users;

import java.util.*;

public interface EmployeeService {
//    String verify(Users user);

    List<TestEmployee> getAllEmployees();

    void addNewEmployee(TestEmployee testEmployee);

    TestEmployee updateExistingEmployee(TestEmployee testEmployee, Long employeeId);

    String deleteEmployee(Long employeeId);


}
