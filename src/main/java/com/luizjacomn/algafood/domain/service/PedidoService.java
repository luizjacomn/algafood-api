package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.core.enums.Genero;
import com.luizjacomn.algafood.domain.exception.PedidoNaoEncontradoException;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido", id, Genero.MASCULINO));
    }

}
