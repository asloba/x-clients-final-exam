package ru.inno.api;

import io.qameta.allure.Step;
import ru.inno.model.Company;

import java.util.List;

public interface CompanyService {

    Company getRandomCompany();

    @Step("Получить список всех компаний по API")
    List<Company> getAll();

    @Step("Получить список всех компаний по API с признаком isActive='{isActive}'")
    List<Company> getAll(boolean isActive);

    @Step("Получить компанию по API с ID='{id}'")
    Company getById(int id);

    @Step("Создать по API компанию с именем '{name}' и описанием {'description'}")
    int create(String name, String description, String token);

    @Step("Удалить по API компанию с id='{id}'")
    void deleteById(int id, String token);

}
