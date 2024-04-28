package com.example.employee;

import com.example.employee.employee.Employee;
import com.example.employee.employee.TestH2EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeTestIntegrationTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2EmployeeRepository employeeRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp(){
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/v1/employees");
    }

    @Test
    public void Application_AddEmployee_ReturnCreated() {
        Employee employee = new Employee("Tom", "Cruise");
        Employee response = restTemplate.postForObject(baseUrl, employee, Employee.class);
        assertEquals("Tom", response.getFirstName());
        assertEquals("Cruise", response.getLastName());
        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO Employee (id, FIRST_NAME, LAST_NAME) VALUES (1, 'Tom', 'Cruise')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Employee WHERE FIRST_NAME = 'Tom'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void Application_GetAllEmployees_ReturnAllEmployees() {
        List<Employee> employees = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, employees.size());
        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO Employee (id, FIRST_NAME, LAST_NAME) VALUES (1, 'Tom', 'Cruise')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Employee WHERE FIRST_NAME = 'Tom'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void Application_FindEmployeeById_ReturnEmployee() {
        Employee employee = restTemplate.getForObject(baseUrl+"/{id}", Employee.class, 1);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(1, employee.getId()),
                () -> assertEquals("Tom", employee.getFirstName())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO Employee (id, FIRST_NAME, LAST_NAME) VALUES (1, 'Tom', 'Cruise')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Employee WHERE FIRST_NAME = 'Tomkumar'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void Application_UpdateEmployee_ReturnEmployee() {
        Employee employee = new Employee("Tomkumar", "Cruise");
        employee.setId(1);
        restTemplate.put(baseUrl, employee);
        Employee updatedEmployee = employeeRepository.findById(1).get();
        assertAll(
                () -> assertNotNull(updatedEmployee),
                () -> assertEquals(1, updatedEmployee.getId()),
                () -> assertEquals("Tomkumar", updatedEmployee.getFirstName())
        );
    }

    @Test
    @Sql(statements = "INSERT INTO Employee (id, FIRST_NAME, LAST_NAME) VALUES (1, 'Tom', 'Cruise')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void Application_DeleteEmployee_ReturnMessage() {
        int employeeCount = employeeRepository.findAll().size();
        assertEquals(1, employeeCount);
        restTemplate.delete(baseUrl+"/{id}", 1);
        assertEquals(0, employeeRepository.findAll().size());
    }
}


