package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteQueriesRepository,
        JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetweenOrderByTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal);

    @Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :id")
    List<Restaurante> listarPorNome(String nome, @Param("id") Long cozinhaId);

    int countByCozinhaId(Long id);

}
