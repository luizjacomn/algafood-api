package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.domain.service.AlteracaoStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class AlteracaoStatusPedidoController {

    @Autowired
    private AlteracaoStatusPedidoService alteracaoStatusPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.confirmarPedido(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.entregarPedido(pedidoId);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long pedidoId) {
        alteracaoStatusPedidoService.cancelarPedido(pedidoId);
    }
}
