package ru.inno.api;

import io.restassured.filter.log.LogDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${base_url}")
    protected String URI;

    @Value("${auth.endpoint}")
    protected String path;

    @Value("${username}")
    protected String username;

    @Value("${password}")
    protected String password;

    @Override
    public String getToken() {
        String token = given()
                .baseUri(URI + path)
                .log().ifValidationFails(LogDetail.ALL)
                .contentType("application/json; charset=utf-8")
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post()
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .extract().path("userToken");
        return token;
    }
}
