package ru.inno.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.model.CompanyEntity;
import ru.inno.testData.CompanyTestData;

import java.util.List;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    @Autowired
    CompanyRepositorySpring companyRepositorySpring;
    @Autowired
    CompanyEntity companyEntity;
    @Autowired
    CompanyTestData companyTestData;

    /* Для получения списка всех компаний использую метод "findAllByDeletedAtNull",
    т.к. апи запрос "get all" возвращает только не удаленные компании */
    @Override
    public List<CompanyEntity> getAll() {
        return companyRepositorySpring.findAllByDeletedAtNull();
    }

    /* Для получения списка только активных компаний использую метод "findAllByIsActiveAndDeletedAtIsNull",
    т.к. апи запрос на получение только активных компаний возвращает именнро активные не удаленные компании */
    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        return companyRepositorySpring.findAllByIsActiveAndDeletedAtIsNull(isActive);
    }

    @Override
    public CompanyEntity getById(int id) {
        return companyRepositorySpring.findById(id);
    }

    @Override
    public int create() {
        return companyRepositorySpring.save(companyTestData.getCompanyDBData()).getId();
    }

    @Override
    public void clean(String prefix) {
        companyRepositorySpring.deleteByNameStartingWith(prefix);
    }
}
