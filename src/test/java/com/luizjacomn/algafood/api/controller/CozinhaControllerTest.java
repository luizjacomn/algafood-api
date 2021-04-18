package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.util.DatabaseCleaner;
import com.luizjacomn.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    private List<Cozinha> cozinhasCargaDados = new ArrayList<>();

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
            .body("", hasSize(cozinhasCargaDados.size()))
            .body("nome", hasItems("Tailandesa", "Brasileira"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_cozinha() {
        int parametro = cozinhasCargaDados.size();
        given()
            .pathParam("id", parametro)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Brasileira"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_cozinha_inexistente() {
        int parametro = cozinhasCargaDados.size() + 1;
        given()
            .pathParam("id", parametro)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("detail", equalTo(String.format("Cozinha com id %d não foi encontrada", parametro)));
    }

    @Test
    public void deve_retornar_201_no_cadastro_de_cozinha() {
        String json = ResourceUtils.getContentFromResource("/data/success/cozinha/cadastro_test.json");

        given()
            .body(json)
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
        cozinhasCargaDados.add(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Brasileira");
        cozinhasCargaDados.add(cozinha2);

        cozinhaRepository.saveAll(cozinhasCargaDados);
    }
}
