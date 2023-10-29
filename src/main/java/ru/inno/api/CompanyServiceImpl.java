package ru.inno.api;

import com.github.javafaker.Faker;
import io.restassured.common.mapper.TypeRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.model.Company;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

@Component
public class CompanyServiceImpl implements CompanyService {

    @Value("${base_url}")
    protected String uri;

    @Value("${company.endpoint}")
    protected String path;

    @Value("${company.delete.endpoint}")
    protected String deletePath;

    @Value("${names.prefix}")
    protected String prefix;

    Faker faker = new Faker();

    @Override
    public Company getRandomCompany() {

        int companyId = faker.random().nextInt(1000, 10000);
        String companyName = prefix + faker.twinPeaks().character();
        String companyDescription = prefix + faker.twinPeaks().quote();

        return new Company(companyId, companyName, companyDescription);
    }


    @Override
    public List<Company> getAll() {
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .extract()
                .response()
                .then()
                .extract()
                .body().as(new TypeRef<List<Company>>() {
                });
    }

    @Override
    public List<Company> getAll(boolean isActive) {
        step("Получить список всех активных компаний по API");
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .param("active", isActive)
                .when()
                .get()
                .then()
                .extract()
                .response()
                .then()
                .extract()
                .body().as(new TypeRef<List<Company>>() {
                });
    }

    @Override
    public Company getById(int id) {
        return null;
    }


    @Override
    public int create(String name, String description, String token) {
        step("Создать по API компанию с именем и описанием");
        return given()
                .baseUri(uri + path)
                .header("accept", "application/json")
                .header("x-client-token", token)
                .log().ifValidationFails()
                .contentType("application/json")
                .body("{\"name\": \"" + name + "\",\"description\": \"" + description + "\"}")
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
    }

    @Override
    public void deleteById(int id, String token) {
        step("Удалить компанию по id", ()->{
            given()
                    .log().ifValidationFails()
                    .baseUri(uri + path + id)
                    .header("accept", "application/json")
                    .header("x-client-token", token)
                    .log().ifValidationFails()
                    .contentType("application/json")
                    .when()
                    .get()
                    .then()
                    .log().ifValidationFails()
                    .statusCode(200);
        });
    }
}
