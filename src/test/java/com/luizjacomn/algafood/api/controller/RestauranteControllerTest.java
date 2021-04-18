package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteControllerTest {

    private static final String URI = "/restaurantes";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private List<Restaurante> restaurantesCargaDados = new ArrayList<>();

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URI;

        databaseCleaner.clearTables();
        carregarDadosIniciais();
    }

    @Test
    public void deve_retornar_200_na_listagem_de_restaurante() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deve_validar_corpo_da_resposta_na_listagem_de_restaurante() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(restaurantesCargaDados.size()))
            .body("nome", hasItems("Cantinho da Tailândia", "Frango Dourado", "Mac Lanches"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_restaurante() {
        int parametro = restaurantesCargaDados.size();
        given()
            .pathParam("id", parametro)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Mac Lanches"))
            .body("cozinha.nome", equalTo("Brasileira"));
    }

    @Test
    public void deve_validar_status_e_corpo_da_resposta_na_busca_de_restaurante_inexistente() {
        int parametro = restaurantesCargaDados.size() + 1;
        given()
            .pathParam("id", parametro)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("detail", equalTo(String.format("Restaurante com id %d não foi encontrado", parametro)));
    }

    @Test
    public void deve_retornar_201_no_cadastro_de_restaurante() {
        String json = ResourceUtils.getContentFromResource("/data/success/restaurante/cadastro_test.json");

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
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Cantinho da Tailândia");
        restaurante1.setTaxaFrete(BigDecimal.valueOf(5.99));
        restaurante1.setCozinha(cozinhaTailandesa);
        restaurantesCargaDados.add(restaurante1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Frango Dourado");
        restaurante2.setTaxaFrete(BigDecimal.ZERO);
        restaurante2.setCozinha(cozinhaBrasileira);
        restaurantesCargaDados.add(restaurante2);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNome("Mac Lanches");
        restaurante3.setTaxaFrete(BigDecimal.valueOf(2));
        restaurante3.setCozinha(cozinhaBrasileira);
        restaurantesCargaDados.add(restaurante3);

        restauranteRepository.saveAll(restaurantesCargaDados);
    }
}
