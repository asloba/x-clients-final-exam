package ru.inno.db;

import ru.inno.model.CompanyEntity;

import java.util.List;

public interface CompanyRepository {

    List<CompanyEntity> getAll();

    List<CompanyEntity> getAll(boolean isActive);

    CompanyEntity getById(int id);

    int create();

    void clean(String prefix);
}

