package ru.inno.api;

import ru.inno.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll(int companyId);

    Employee getById(int id);

    int create(Employee employee, String token);

    Employee update(Employee employee, String token);
}