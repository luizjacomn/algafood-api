package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.util.DatabaseCleaner;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaControllerTest {

    private static final String URI = "/cozinhas";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URI;

        databaseCleaner.clearTables();
        carregarDadosIniciais();
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
            .body("", hasSize(2))
            .body("nome", hasItems("Tailandesa", "Brasileira"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_cozinha() {
        given()
            .pathParam("id", 2)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Brasileira"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_cozinha_inexistente() {
        given()
            .pathParam("id", 200)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("detail", equalTo("Cozinha com id 200 não foi encontrada"));
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

    private void carregarDadosIniciais() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Brasileira");

        cozinhaRepository.saveAll(Arrays.asList(cozinha1, cozinha2));
    }
}
