package com.example.employee;

import com.example.employee.employee.Employee;
import com.example.employee.employee.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class EmployeeTestIntegrationTestcontainersTests {
    @LocalServerPort
    private Integer port;

    @Container
    static MySQLContainer mysql = new MySQLContainer("mysql:8.3.0");

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    EmployeeRepository employeeRepository;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeEach
    public void setUp(){
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/v1/employees");
    }

    @Test
    public void Application_GetAllEmployees_ReturnAllEmployees() {
        //Arrange
        List<Employee> employees = List.of(
                new Employee("Tom", "Cruise"),
                new Employee("Will", "Smith")
        );
        employeeRepository.saveAll(employees);

        //Act
        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {}
        );
        List<Employee> response = responseEntity.getBody();

        //Assert
        assertEquals(2, employees.size());
        assertEquals("Tom", response.get(0).getFirstName());
        assertEquals("Cruise", response.get(0).getLastName());
        assertEquals("Will", response.get(1).getFirstName());
        assertEquals("Smith", response.get(1).getLastName());
    }
}

