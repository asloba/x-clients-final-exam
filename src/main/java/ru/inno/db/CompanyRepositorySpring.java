package ru.inno.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.inno.model.CompanyEntity;

import java.util.List;

@Repository
public interface CompanyRepositorySpring extends CrudRepository<CompanyEntity, Integer> {

    List<CompanyEntity> findAll();

    List<CompanyEntity> findAllByIsActive(boolean isActive);


    List<CompanyEntity> findAllByIsActiveAndDeletedAtIsNotNull(boolean isActive);
    List<CompanyEntity> findAllByDeletedAtNull();
    void deleteByNameStartingWith(String name);
    CompanyEntity findFirstByOrderByIdDesc();

}
