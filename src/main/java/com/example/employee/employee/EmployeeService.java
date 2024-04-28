package com.example.employee.employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    public Employee findEmployeeById(int id);
    public Employee save(Employee employee);
    public void deleteById(Integer id);
    void deleteAll();
}
