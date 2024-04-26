package com.example.employee.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

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
        Employee oldEmployee = new Employee("Tom", "Smith");
        oldEmployee.setId(1);
        Employee employee = new Employee("Tom", "Cruise");
        employee.setId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(oldEmployee));
        when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //Act
        Employee savedEmployee = employeeService.save(employee);

        //Assert
        Assertions.assertThat(savedEmployee).isNotNull();
//        assertEquals(employee.getFirstName(), savedEmployee.getFirstName());
//        assertEquals(employee.getLastName(), savedEmployee.getLastName());
        Assertions.assertThat(savedEmployee).isEqualToComparingFieldByField(employee);
    }

}


