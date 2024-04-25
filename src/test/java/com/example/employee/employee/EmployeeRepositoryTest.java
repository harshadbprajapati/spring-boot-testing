package com.example.employee.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void EmployeeRepository_Save_ReturnEmployees() throws Exception {
        //Arrange
        Employee employee = new Employee("Tom", "Cruise");

        //Act
        Employee savedEmployee = employeeRepository.save(employee);

        //Assert
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void EmployeeRepository_FindAll_ReturnMoreThanOneEmployee() throws Exception {
        //Arrange
        Employee employee1 = new Employee("Tom", "Cruise");
        Employee employee2 = new Employee("Will", "Smith");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //Act
        List<Employee> employees = (List<Employee>)employeeRepository.findAll();

        //Assert
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

}

