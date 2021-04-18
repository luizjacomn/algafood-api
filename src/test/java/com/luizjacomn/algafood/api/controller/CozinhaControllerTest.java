package com.luizjacomn.algafood.api.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CozinhaControllerTest {

    private static final String URI = "/cozinhas";

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @Before
    public void setup() {
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URI;
        flyway.migrate();
    }

    @Test
    public void deve_retornar_200_na_listagem_de_cozinha() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deve_validar_corpo_da_resposta_na_listagem_de_cozinha() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(4))
            .body("nome", hasItems("Tailandesa", "Brasileira"));
    }

    @Test
    public void deve_retornar_201_no_cadastro_de_cozinha() {
        given()
            .body(" { \"nome\": \"Moradanovense\" } ")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
}
