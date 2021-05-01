package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.model.*;
import com.luizjacomn.algafood.domain.repository.*;
import com.luizjacomn.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class AlterarStatusPedidoControllerTest {

    private static final String URI = "/pedidos";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String uuidGerado;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URI;

        databaseCleaner.clearTables();
        carregarDadosIniciais();
    }

    @Test
    public void deve_retornar_204_na_alteracao_status() {
        given()
                .when()
                .put(String.format("/%s/confirmacao", uuidGerado))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deve_retornar_400_na_alteracao_status() {
        given()
                .when()
                .put(String.format("/%s/entrega", uuidGerado))
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", is(String.format("Status do pedido %s não pode ser alterado de 'Criado' para 'Entregue'", uuidGerado)));
    }

    private void carregarDadosIniciais() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Logado");
        usuario.setEmail("usuario@logado.test");
        usuario.setSenha("senha");
        usuarioRepository.save(usuario);

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");
        cozinhaRepository.save(cozinha);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setDescricao("Dinheiro");
        formaPagamentoRepository.save(formaPagamento);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Ponto do Baião de Dois");
        restaurante.setTaxaFrete(BigDecimal.valueOf(5.99));
        restaurante.setCozinha(cozinha);
        restaurante.getFormasPagamento().add(formaPagamento);
        restauranteRepository.save(restaurante);

        Produto produto1 = new Produto();
        produto1.setNome("Baião de Dois");
        produto1.setDescricao("Baião de Dois tradicional com muito queijo, leite e cheiro verde");
        produto1.setPreco(BigDecimal.valueOf(5.99));
        produto1.setRestaurante(restaurante);
        produtoRepository.save(produto1);

        Produto produto2 = new Produto();
        produto2.setNome("Mistão de Carnes");
        produto2.setDescricao("300g de carne variada (porco, carne, frango ou linguiça)");
        produto2.setPreco(BigDecimal.valueOf(11.99));
        produto2.setRestaurante(restaurante);
        produtoRepository.save(produto2);

        ItemPedido item1 = new ItemPedido();
        item1.setQuantidade(1);
        item1.setProduto(produto1);
        item1.setPrecoUnitario(produto1.getPreco());
        item1.setObservacao("Não colocar cheiro verde");

        ItemPedido item2 = new ItemPedido();
        item2.setQuantidade(2);
        item2.setProduto(produto2);
        item2.setPrecoUnitario(produto2.getPreco());

        Estado estado = new Estado();
        estado.setNome("Ceará");
        estadoRepository.save(estado);

        Cidade cidade = new Cidade();
        cidade.setNome("Morada Nova");
        cidade.setEstado(estado);
        cidadeRepository.save(cidade);

        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setLogradouro("Rua Cipriano Maia");
        enderecoEntrega.setNumero("215");
        enderecoEntrega.setBairro("Centro");
        enderecoEntrega.setCep("62940000");
        enderecoEntrega.setCidade(cidade);

        Pedido pedido = new Pedido();
        pedido.setCodigo("f9981ca4-5a5e-4da3-af04-933861df3e55");
        pedido.setCliente(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getItens().add(item1);
        pedido.getItens().add(item2);
        pedido.setEnderecoEntrega(enderecoEntrega);

        item1.setPedido(pedido);
        item2.setPedido(pedido);
        pedido.calcularValorTotal();
        pedido = pedidoRepository.save(pedido);
        uuidGerado = pedido.getCodigo();
    }
}
