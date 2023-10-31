package ru.inno.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.model.Employee;
import ru.inno.model.EmployeeEntity;

import java.util.List;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository{

    @Autowired
    EmployeeRepositorySpring employeeRepositorySpring;

    @Override
    public List<EmployeeEntity> getAll() {
        return employeeRepositorySpring.findAll();
    }

    @Override
    public EmployeeEntity getById(int id) {
        return null;
    }

    @Override
    public int create(Employee employee) {
        return 0;
    }

    @Override
    public int update(EmployeeEntity e) {
        return 0;
    }

    @Override
    public void deleteById(int id) {

    }
}
