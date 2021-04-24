package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

}
