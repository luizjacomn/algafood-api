package com.luizjacomn.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizjacomn.algafood.domain.model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoOutput {

    private Long id;

    private BigDecimal subTotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    private String status;

    private EnderecoOutput enderecoEntrega;

    private RestauranteResumeOutput restaurante;

    private UsuarioOutput cliente;

    private FormaPagamentoOutput formaPagamento;

    private List<ItemPedidoOutput> itens;
}
