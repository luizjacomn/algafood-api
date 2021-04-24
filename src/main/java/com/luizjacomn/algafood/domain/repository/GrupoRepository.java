package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Grupo;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long> {
}
