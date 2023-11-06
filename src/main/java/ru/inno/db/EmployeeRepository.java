package ru.inno.db;

import ru.inno.model.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository {

    List<EmployeeEntity> getAll();

    List<EmployeeEntity> getAllByCompanyId(int companyId);

    void clean(String prefix);
}