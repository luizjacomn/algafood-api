package com.luizjacomn.algafood.api.v1.controller;

import com.luizjacomn.algafood.domain.service.AlteracaoStatusPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class AlteracaoStatusPedidoController {

    @Autowired
    private AlteracaoStatusPedidoService alteracaoStatusPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigoPedido) {
        alteracaoStatusPedidoService.confirmarPedido(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigoPedido) {
        alteracaoStatusPedidoService.entregarPedido(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigoPedido) {
        alteracaoStatusPedidoService.cancelarPedido(codigoPedido);
    }
}
