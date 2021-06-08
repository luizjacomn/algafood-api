package com.luizjacomn.algafood.api.v1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiaria {

    private Date dataCriacao;

    private Long quantidade;

    private BigDecimal valorFaturado;
}
