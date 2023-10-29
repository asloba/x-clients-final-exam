package ru.inno.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.model.CompanyEntity;

import java.util.List;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    @Autowired
    CompanyRepositorySpring companyRepositorySpring;


    @Override
    public List<CompanyEntity> getAll() {
        return companyRepositorySpring.findAllByDeletedAtNull();
    }

    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        return companyRepositorySpring.findAllByIsActive(isActive);
    }

    @Override
    public CompanyEntity getLast() {
        return null;
    }

    @Override
    public CompanyEntity getById(int id) {
        return null;
    }

    @Override
    public int create(String name) {
        return 0;
    }

    @Override
    public int create(String name, String description) {
        return 0;
    }

    @Override
    public void deleteById(int id) {

    }
}
