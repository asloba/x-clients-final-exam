package ru.inno.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.inno.model.EmployeeEntity;

import java.util.List;

@Repository
public interface EmployeeRepositorySpring extends CrudRepository<EmployeeEntity, Integer> {

    List<EmployeeEntity> findAll();

    List<EmployeeEntity> findAllByCompanyId(int id);

    void deleteByFirstNameStartingWith(String name);
}
