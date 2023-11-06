package ru.inno.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.model.EmployeeEntity;

import java.util.List;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    EmployeeRepositorySpring employeeRepositorySpring;

    @Override
    public List<EmployeeEntity> getAll() {
        return employeeRepositorySpring.findAll();
    }

    @Override
    public List<EmployeeEntity> getAllByCompanyId(int companyId) {
        return employeeRepositorySpring.findAllByCompanyId(companyId);
    }

    @Override
    public void clean(String prefix) {
        employeeRepositorySpring.deleteByFirstNameStartingWith(prefix);
    }
}
