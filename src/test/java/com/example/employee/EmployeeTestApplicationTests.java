package com.example.employee;

import com.example.employee.employee.EmployeeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeTestApplicationTests {

	@Autowired
	private EmployeeController employeeController;
	@Test
	void contextLoads() {
		Assertions.assertThat(employeeController).isNotNull();
	}

}
