package com.example.employee.employee;

import com.example.employee.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        if(!employees.isEmpty()){
            return employees;
        } else {
            return new ArrayList<Employee>();
        }
    }

    @Override
    public Employee findEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee = employeeRepository.save(employee);
            return employee;
        } else {
            Optional<Employee> foundEmployee = employeeRepository.findById(employee.getId());
            if(foundEmployee.isPresent()) {
                Employee updatedEmployee = foundEmployee.get();
                updatedEmployee.setFirstName(employee.getFirstName());
                updatedEmployee.setLastName(employee.getLastName());
                updatedEmployee = employeeRepository.save(updatedEmployee);
                return updatedEmployee;
            } else {
                throw new RecordNotFoundException("Employee with id " + employee.getId()+" not found");
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        if(foundEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Employee with id " + id+" not found");
        }
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}


