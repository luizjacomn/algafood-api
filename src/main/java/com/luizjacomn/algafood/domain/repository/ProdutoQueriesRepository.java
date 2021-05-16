package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.FotoProduto;

import java.util.Optional;

public interface ProdutoQueriesRepository {

    Optional<FotoProduto> findFotoProduto(Long restauranteId, Long produtoId);

    FotoProduto save(FotoProduto fotoProduto);

    void delete(FotoProduto fotoProduto);
}
