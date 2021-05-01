package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper extends GenericMapper<Pedido, PedidoInput, PedidoOutput> {
}
