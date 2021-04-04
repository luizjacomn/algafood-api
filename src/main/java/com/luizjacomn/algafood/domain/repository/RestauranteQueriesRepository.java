package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteQueriesRepository {

    List<Restaurante> buscarPorNomeEIntervaloDeTaxas(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> buscarComFreteGratis(String nome);

}
