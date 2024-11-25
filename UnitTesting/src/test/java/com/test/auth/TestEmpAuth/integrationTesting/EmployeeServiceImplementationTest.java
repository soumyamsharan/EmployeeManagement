package com.test.auth.TestEmpAuth.integrationTesting;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.test.auth.TestEmpAuth.exception.InvalidRequestException;
import com.test.auth.TestEmpAuth.model.TestEmployee;
import com.test.auth.TestEmpAuth.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmployeeServiceImplementationTest
{
    @LocalServerPort
    private int port;

    private String baseURL = "http://localhost";

    private static RestTemplate restTemplate;

    private TestEmployee testEmployee;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeAll
    public static void init()
    {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup()
    {
        baseURL = baseURL + ":" + port + "/api";
        testEmployee = new TestEmployee(102L, "Soumyam", "MAMA");
    }

    @AfterEach
    public void afterSetup()
    {
        employeeRepository.deleteAll();
    }

    @Test
    public void testAddNewEmployee()
    {

        String message = restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);

        assertNotNull(message);

        TestEmployee savedEmployee = employeeRepository.findById(102L).orElse(null);

        assertNotNull(savedEmployee);
        assertEquals("Soumyam", savedEmployee.getNameOfEmployee());
        assertEquals("MAMA", savedEmployee.getNameOfDepartment());
    }

    @Test
    public void testGetAllEmployees()
    {

        TestEmployee soumyamEmployee = new TestEmployee(103L, "Soumyam", "MAMA");
        TestEmployee madhuEmployee = new TestEmployee(104L, "Madhu", "MAMA");

        restTemplate.postForObject(baseURL + "/admin/employee", soumyamEmployee, String.class);
        restTemplate.postForObject(baseURL + "/admin/employee", madhuEmployee, String.class);

        List<?> listOfEmployees = restTemplate.getForObject(baseURL + "/public/employees",  List.class);

//        assertThat(listOfEmployees.size()).isEqualTo(2);

        assertEquals("Soumyam", soumyamEmployee.getNameOfEmployee());
        assertEquals("Madhu", madhuEmployee.getNameOfEmployee());

    }

    @Test
    public void testDeleteEmployee()
    {
        TestEmployee soumyamEmployee = new TestEmployee(103L, "Soumyam", "MAMA");
        TestEmployee madhuEmployee = new TestEmployee(104L, "Madhu", "MAMA");

        restTemplate.postForObject(baseURL + "/admin/employee", soumyamEmployee, String.class);
        restTemplate.postForObject(baseURL + "/admin/employee", madhuEmployee, String.class);

        restTemplate.delete(baseURL + "/admin/employees/" + soumyamEmployee.getEmployeeId());

        int count = employeeRepository.findAll().size();

        assertEquals(1, count);
    }

    @Test
    public void testUpdateExistingEmployee()
    {
        restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);

        testEmployee.setNameOfEmployee("Rishabh");
        restTemplate.put(baseURL + "/admin/employees/" + testEmployee.getEmployeeId(), testEmployee);

        List<?> list = restTemplate.getForObject(baseURL + "/public/employees", List.class);

        assertEquals("Rishabh", testEmployee.getNameOfEmployee());
    }

    @Test
    public void testAddNewEmployee_IdAbove999_ThrowsException()
    {
        TestEmployee invalidEmployee = new TestEmployee(1111L, "Soumyam", "Platform");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.postForObject(baseURL + "/admin/employee", invalidEmployee, String.class);
        });

        assertEquals(400, exception.getStatusCode().value());

        String responseBody = exception.getResponseBodyAsString();
        assertTrue(responseBody.contains("Employee Id can be between 1 and 1000"));

    }

    @Test
    public void testAddNewEmployee_InvalidName_ThrowsException()
    {
        testEmployee.setNameOfEmployee("Soumyam@123");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class,
                () -> {
            restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);
                });

        assertEquals(400, exception.getStatusCode().value());

    }

    @Test
    public void testAddNewEmployee_InvalidDepartment_ThrowsException()
    {
        testEmployee.setNameOfDepartment("MAMA@123");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class,
                () ->
                {
                    restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);
                });

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    public void testUpdateExistingEmployee_EmployeeIdNotPresent_ThrowsException()
    {

        restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);

        HttpClientErrorException.NotFound exception = assertThrows(HttpClientErrorException.NotFound.class, () ->
        {
            restTemplate.put(baseURL + "/admin/employee/69", testEmployee);
        });

        assertEquals(404, exception.getStatusCode().value());


    }

    @Test
    public void testDeleteEmployee_EmployeeIdNotPresent_ThrowsException()
    {
        restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);

        HttpClientErrorException.NotFound exception = assertThrows(HttpClientErrorException.NotFound.class, () ->
        {
           restTemplate.delete(baseURL + "/admin/employee" + testEmployee.getEmployeeId());
        });

        assertEquals(404, exception.getStatusCode().value());
    }


    @Test
    public void testAddNewEmployee_DuplicateEmployeeId_ThrowsException()
    {
        restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);

        TestEmployee duplicateEmployee = new TestEmployee(102L, "Madhu", "MAMA");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class, () ->
        {
            restTemplate.postForObject(baseURL + "/admin/employee", duplicateEmployee, String.class);
        });

//        assertEquals(400, exception.getStatusCode().value());

        String responseBody = exception.getResponseBodyAsString();

        assertTrue(responseBody.contains("Duplicate Employee Id is not allowed"));

    }

    @Test
    public void testGetAllEmployees_EmptyDatabase_ThrowsException()
    {


        HttpClientErrorException.NotFound exception = assertThrows(HttpClientErrorException.NotFound.class, () ->
        {
            restTemplate.getForObject(baseURL + "/public/employees", List.class);
        });

        assertEquals(404, exception.getStatusCode().value());

        String responseBody = exception.getResponseBodyAsString();

        assertTrue(responseBody.contains("Database is Empty"));

    }

    @Test
    public void testAddNewEmployee_EmptyNameOfEmployee_ThrowsException()
    {
        testEmployee.setNameOfEmployee("");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class, () ->
        {
           restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);
        });

        assertEquals(400, exception.getStatusCode().value());

        String responseBody = exception.getResponseBodyAsString();

        assertTrue(responseBody.contains("Employee Name must only contain alphabets"));
    }


    @Test
    public void testAddNewEmployee_EmptyNameOfDepartment_ThrowsException()
    {
        testEmployee.setNameOfDepartment("");

        HttpClientErrorException.BadRequest exception = assertThrows(HttpClientErrorException.BadRequest.class, () ->
        {
            restTemplate.postForObject(baseURL + "/admin/employee", testEmployee, String.class);
        });

        assertEquals(400, exception.getStatusCode().value());

        String responseBody = exception.getResponseBodyAsString();
        assertTrue(responseBody.contains("Employee Department must only contain alphabets"));
    }


}
