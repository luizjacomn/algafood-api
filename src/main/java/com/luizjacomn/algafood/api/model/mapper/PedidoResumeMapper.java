package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumeMapper extends GenericMapper<Pedido, PedidoInput, PedidoResumeOutput> {
}
