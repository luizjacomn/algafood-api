package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> { }
