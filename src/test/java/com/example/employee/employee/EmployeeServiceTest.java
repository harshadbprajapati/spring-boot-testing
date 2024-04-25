package com.example.employee.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void Employee_Save_ReturnsEmployee() throws Exception {
        //Arrange
        Employee employee = new Employee("Tom", "Cruise");
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        //Act
        Employee savedEmployee = employeeService.save(employee);

        //Assert
        Assertions.assertThat(savedEmployee).isNotNull();
    }

}


