package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.FormaPagamento;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {
}
