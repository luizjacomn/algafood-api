package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {

    @Query("SELECT MAX(dataAtualizacao) FROM FormaPagamento")
    Optional<OffsetDateTime> findMaxDataAtualizacao();

}
