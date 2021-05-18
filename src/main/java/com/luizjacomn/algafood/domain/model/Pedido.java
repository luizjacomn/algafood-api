package com.luizjacomn.algafood.domain.model;

import com.luizjacomn.algafood.domain.event.PedidoCanceladoEvent;
import com.luizjacomn.algafood.domain.event.PedidoConfirmadoEvent;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String codigo;

    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "data_entrega")
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido status = StatusPedido.CRIADO;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        setTaxaFrete(getRestaurante().getTaxaFrete());

        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subTotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subTotal.add(this.taxaFrete);
    }

    public void confirmar(boolean habilitaErroAlteracaoMesmoStatus) {
        paraProximoStatus(StatusPedido.CONFIRMADO, p -> !p.getStatus().equals(StatusPedido.CRIADO), habilitaErroAlteracaoMesmoStatus);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar(boolean habilitaErroAlteracaoMesmoStatus) {
        paraProximoStatus(StatusPedido.ENTREGUE, p -> !p.getStatus().equals(StatusPedido.CONFIRMADO), habilitaErroAlteracaoMesmoStatus);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar(boolean habilitaErroAlteracaoMesmoStatus) {
        paraProximoStatus(StatusPedido.CANCELADO, p -> p.getStatus().equals(StatusPedido.CONFIRMADO) || p.getStatus().equals(StatusPedido.ENTREGUE), habilitaErroAlteracaoMesmoStatus);
        setDataCancelamento(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    private void paraProximoStatus(StatusPedido statusPedido, Predicate<Pedido> lancaExcecaoSe, boolean habilitaErroAlteracaoMesmoStatus) {
        if (this.getStatus().equals(statusPedido)) {
            if (habilitaErroAlteracaoMesmoStatus) {
                throw new NegocioException(String.format("Este pedido foi %s anteriormente", statusPedido.getDescricao().toLowerCase()));
            }
        } else {
            validar(lancaExcecaoSe, statusPedido);

            setStatus(statusPedido);
        }
    }

    private void validar(Predicate<Pedido> lancaExcecaoSe, StatusPedido proximoStatus) {
        if (lancaExcecaoSe.test(this)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de '%s' para '%s'",
                    this.getCodigo(), this.getStatus().getDescricao(), proximoStatus.getDescricao()));
        }
    }

    @PrePersist
    private void gerarUUID() {
        setCodigo(UUID.randomUUID().toString());
    }

    private void setStatus(StatusPedido status) {
        this.status = status;
    }
}

