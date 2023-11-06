package ru.inno.api;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.parsing.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.model.Employee;

import java.util.List;

import static io.restassured.RestAssured.given;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${base_url}")
    protected String URI;

    @Value("${employee.endpoint}")
    protected String PATH;

    @Override
    public List<Employee> getAll(int companyId) {
        RestAssured.defaultParser = Parser.JSON;
        return given()
                .baseUri(URI + PATH)
                .header("accept", "application/json")
                .param("company", companyId)
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .extract()
                .response()
                .then()
                .extract().body().as(new TypeRef<List<Employee>>() {
                });
    }

    @Override
    public Employee getById(int id) {
        RestAssured.defaultParser = Parser.JSON;
        return given()
                .baseUri(URI + PATH + "/" + id)
                .header("accept", "application/json")
                .contentType("application/json; charset=utf-8")
                .when()
                .get()
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().body().as(new TypeRef<Employee>() {
                });
    }

    @Override
    public int create(Employee employee, String token) {
        return given()
                .baseUri(URI + PATH)
                .log().ifValidationFails()
                .header("accept", "application/json")
                .contentType("application/json; charset=utf-8")
                .header("x-client-token", token)
                .body(employee)
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
    }
}
