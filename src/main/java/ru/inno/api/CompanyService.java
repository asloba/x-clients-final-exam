package ru.inno.api;

import ru.inno.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAll();

    List<Company> getAll(boolean isActive);

    void deleteById(int id, String token);
}
