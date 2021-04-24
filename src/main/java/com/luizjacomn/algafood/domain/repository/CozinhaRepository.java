package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

}
