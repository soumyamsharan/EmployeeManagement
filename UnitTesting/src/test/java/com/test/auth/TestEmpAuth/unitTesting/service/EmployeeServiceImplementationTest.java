package com.test.auth.TestEmpAuth.unitTesting.service;

import com.test.auth.TestEmpAuth.exception.InvalidRequestException;
import com.test.auth.TestEmpAuth.exception.ResourceNotFoundException;
import com.test.auth.TestEmpAuth.model.TestEmployee;
import com.test.auth.TestEmpAuth.repository.EmployeeRepository;
import com.test.auth.TestEmpAuth.service.EmployeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplementationTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImplementation employeeServiceImplementation;


    private TestEmployee testEmployee;

    @BeforeEach
    void setUp()
    {
        testEmployee = new TestEmployee(101L, "Soumyam", "MAMA");

    }

    @Test
    void testAddNewEmployee()
    {

        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        employeeServiceImplementation.addNewEmployee(testEmployee);

        verify(employeeRepository, times(1)).save(testEmployee);

        assertEquals(101L, testEmployee.getEmployeeId());
        assertEquals("Soumyam", testEmployee.getNameOfEmployee());
        assertEquals("MAMA", testEmployee.getNameOfDepartment());
    }

    @Test
    void testGetAllEmployees()
    {
        TestEmployee testEmployee = new TestEmployee(101L, "Soumyam", "MAMA");
        TestEmployee testEmployee2 = new TestEmployee(102L, "Sharan", "MAMA");

        List<TestEmployee> employees = Arrays.asList(testEmployee, testEmployee2);


        when(employeeRepository.findAll()).thenReturn(employees);

        List<TestEmployee> allEmployees = employeeServiceImplementation.getAllEmployees();

        assertEquals(101L, allEmployees.get(0).getEmployeeId());

    }


    @Test
    void testDeleteEmployee()
    {
        when(employeeRepository.findById(testEmployee.getEmployeeId())).thenReturn(Optional.ofNullable(testEmployee));

        String result = employeeServiceImplementation.deleteEmployee(testEmployee.getEmployeeId());

        assertEquals("Category with CategoryID " + testEmployee.getEmployeeId() + " deleted successfully", result);
    }

    @Test
    void testUpdateExistingEmployee()
    {
        when(employeeRepository.findById(testEmployee.getEmployeeId())).thenReturn(Optional.ofNullable(testEmployee));

        testEmployee.setNameOfDepartment("WE");

        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        TestEmployee updatedEmployee = employeeServiceImplementation.updateExistingEmployee(testEmployee, testEmployee.getEmployeeId());

        assertEquals("WE", updatedEmployee.getNameOfDepartment());
    }

    @Test
    void testUpdateExistingEmployee_EmployeeIdNotPresent_ThrowsException()
    {
        testEmployee.setEmployeeId(12L);

        assertThrows(ResourceNotFoundException.class,
                () -> { employeeServiceImplementation.updateExistingEmployee(testEmployee, testEmployee.getEmployeeId());});

        verify(employeeRepository, times(0)).save(testEmployee);

    }

    @Test
    void testAddNewEmployee_IdAbove999_ThrowsException() {

        testEmployee.setEmployeeId(1001L);

        assertThrows(InvalidRequestException.class, () -> {
            employeeServiceImplementation.addNewEmployee(testEmployee);
        });

        verify(employeeRepository, times(0)).save(testEmployee);

    }

    @Test
    void testAddNewEmployee_InvalidName_ThrowsException()
    {
        testEmployee.setNameOfEmployee("Soumyam123");

        assertThrows(InvalidRequestException.class,
                () -> {employeeServiceImplementation.addNewEmployee(testEmployee);});

        verify(employeeRepository, times(0)).save(testEmployee);

    }

    @Test
    void testAddNewEmployee_InvalidDepartment_ThrowsException()
    {
        testEmployee.setNameOfEmployee("MAMA123");

        assertThrows(InvalidRequestException.class,
                () -> {employeeServiceImplementation.addNewEmployee(testEmployee);});

        verify(employeeRepository, times(0)).save(testEmployee);

    }

    @Test
    void testDeleteEmployee_EmployeeIdNotPresent_ThrowsException()
    {
        testEmployee.setEmployeeId(12L);

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeServiceImplementation.deleteEmployee(testEmployee.getEmployeeId());
        });

        verify(employeeRepository, times(0)).save(testEmployee);
    }

    @Test
    void testAddNewEmployee_DuplicateEmployeeId_ThrowsException()
    {
        when(employeeRepository.existsById(testEmployee.getEmployeeId())).thenReturn(true);

        TestEmployee duplicateEmployee = new TestEmployee(101L, "Madhu", "MAMA");

        assertThrows(InvalidRequestException.class, () ->
        {
           employeeServiceImplementation.addNewEmployee(duplicateEmployee);
        });
    }

    @Test
    void testGetAllEmployees_EmptyDatabase_ThrowsException()
    {
        assertThrows(ResourceNotFoundException.class, () ->
        {
            employeeServiceImplementation.getAllEmployees();
        });
    }

    @Test
    void testAddNewEmployee_EmptyNameOfEmployee_ThrowsException()
    {
        testEmployee.setNameOfEmployee("");

        assertThrows(InvalidRequestException.class, () ->
        {
           employeeServiceImplementation.addNewEmployee(testEmployee);
        });

    }

    @Test
    void testAddNewEmployee_EmptyNameOfDepartment_ThrowsException()
    {
        testEmployee.setNameOfDepartment("");

        assertThrows(InvalidRequestException.class, () ->
        {
           employeeServiceImplementation.addNewEmployee(testEmployee);
        });
    }
}



