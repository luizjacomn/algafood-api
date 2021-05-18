package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiConsumer;

@Service
public class AlteracaoStatusPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Value("${api.pedido.habilita-erro.alteracao-para-o-mesmo-status:false}")
    private boolean habilitaErroAlteracaoMesmoStatus;

    @Transactional
    public void confirmarPedido(String codigo) {
        alterarStatus(codigo, Pedido::confirmar);
    }

    @Transactional
    public void entregarPedido(String codigo) {
        alterarStatus(codigo, Pedido::entregar);
    }

    @Transactional
    public void cancelarPedido(String codigo) {
        alterarStatus(codigo, Pedido::cancelar);
    }

    private void alterarStatus(String codigo, BiConsumer<Pedido, Boolean> consumer) {
        Pedido pedido = pedidoService.buscar(codigo);

        consumer.accept(pedido, habilitaErroAlteracaoMesmoStatus);

        pedidoRepository.save(pedido);
    }

}
