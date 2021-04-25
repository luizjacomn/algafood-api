package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    Optional<Produto> findByIdAndRestauranteId(Long id, Long restauranteId);

}
