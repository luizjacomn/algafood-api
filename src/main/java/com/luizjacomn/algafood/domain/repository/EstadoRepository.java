package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

}
