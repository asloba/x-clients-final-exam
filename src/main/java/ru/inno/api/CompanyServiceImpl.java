package ru.inno.api;

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
    public void deleteById(int id, String token) {
        step("Удалить компанию по id", () -> {
            given()
                    .log().ifValidationFails()
                    .baseUri(uri + deletePath + id)
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
