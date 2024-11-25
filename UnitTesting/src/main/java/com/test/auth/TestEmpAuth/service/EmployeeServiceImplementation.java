package com.test.auth.TestEmpAuth.service;

import com.test.auth.TestEmpAuth.exception.InvalidRequestException;
import com.test.auth.TestEmpAuth.exception.ResourceNotFoundException;
import com.test.auth.TestEmpAuth.model.TestEmployee;
//import com.test.auth.TestEmpAuth.model.Users;
import com.test.auth.TestEmpAuth.repository.EmployeeRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService
{
//    private List<Employee> employees = new ArrayList<>();

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }



//    @Autowired
//    AuthenticationManager authManager;

//    @Autowired
//    private JWTService jwtService;

    private Long newId = 101L;



    @Override
    public List<TestEmployee> getAllEmployees()
    {


        if (employeeRepository.findAll().isEmpty())
        {
            throw new ResourceNotFoundException("Database is Empty", "Resource Not Found");
        }

        return employeeRepository.findAll();
    }

    @Override
    public void addNewEmployee(TestEmployee testEmployee)
    {
//        testEmployee.setEmployeeId(newId++);

        testEmployee.getNameOfEmployee().trim();
        testEmployee.getNameOfDepartment().trim();

        if (testEmployee.getEmployeeId() == null)
            throw new InvalidRequestException("Employee Id cannot be empty", "Invalid Request");
        else if (testEmployee.getEmployeeId() > 999)
            throw new InvalidRequestException("Employee Id can be between 1 and 1000", "Invalid Request");
        else if (!testEmployee.getNameOfEmployee().matches("^[a-zA-Z\\s]+$"))
            throw new InvalidRequestException("Employee Name must only contain alphabets", "Invalid Request");
        else if (!testEmployee.getNameOfDepartment().matches("^[a-zA-Z\\s]+$"))
            throw new InvalidRequestException("Employee Department must only contain alphabets", "Invalid Request");
        else if (employeeRepository.existsById(testEmployee.getEmployeeId()))
            throw new InvalidRequestException("Duplicate Employee Id is not allowed", "Invalid Request");


        employeeRepository.save(testEmployee);


    }

    @Override
    public TestEmployee updateExistingEmployee(TestEmployee testEmployee, Long employeeId)
    {

        TestEmployee savedTestEmployee = employeeRepository.findById(employeeId).orElse(null);

        if (savedTestEmployee == null)
            throw new ResourceNotFoundException("Employee with Id: " + employeeId + " not found", "Resource Not Found");

        if (testEmployee.getNameOfEmployee() != null)
        {
            savedTestEmployee.setNameOfEmployee(testEmployee.getNameOfEmployee().trim());
        }
        else if (testEmployee.getNameOfDepartment() != null)
        {
            savedTestEmployee.setNameOfDepartment(testEmployee.getNameOfDepartment().trim());
        }

        if (savedTestEmployee.getEmployeeId() == null)
            throw new InvalidRequestException("Employee Id cannot be null", "Invalid Request");
        else if (savedTestEmployee.getEmployeeId() > 999)
            throw new InvalidRequestException("Employee Id can be between 1 and 1000", "Invalid Request");
        else if (savedTestEmployee.getNameOfEmployee() == null || (!savedTestEmployee.getNameOfEmployee().matches("^[a-zA-Z\\s]+$")))
            throw new InvalidRequestException("Employee name must only contain alphabets", "Invalid Request");
        else if (savedTestEmployee.getNameOfDepartment() == null || (!savedTestEmployee.getNameOfDepartment().matches("^[a-zA-Z\\s]+$")))
            throw new InvalidRequestException("Employee Department must only contain alphabets", "Invalid Request");

        savedTestEmployee = employeeRepository.save(savedTestEmployee);

        return savedTestEmployee;
    }

    @Override
    public String deleteEmployee(Long employeeId)
    {

        TestEmployee testEmployee = employeeRepository.findById(employeeId).orElse(null);

        if (testEmployee == null)
            throw new ResourceNotFoundException("Employee with Id: " + employeeId + " not found", "Resource Not Found");

        employeeRepository.delete(testEmployee);

        return "Category with CategoryID " + employeeId + " deleted successfully";
    }



//    @Override
//    public String verify(Users user) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if (authentication.isAuthenticated())
//        {
//            return jwtService.generateToken(user.getUsername());
//        }
//
//        else
//        {
//            return "Fail";
//        }
//    }


}
