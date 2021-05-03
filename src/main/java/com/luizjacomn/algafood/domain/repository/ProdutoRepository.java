package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Produto;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    Optional<Produto> findByIdAndRestauranteId(Long id, Long restauranteId);

    List<Produto> findByRestaurante(Restaurante restaurante);

    List<Produto> findByRestauranteAndAtivoTrue(Restaurante restaurante);
}
