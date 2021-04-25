package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> { }
