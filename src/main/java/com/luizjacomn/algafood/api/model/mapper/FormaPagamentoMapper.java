package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.FormaPagamentoInput;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
import com.luizjacomn.algafood.domain.model.FormaPagamento;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoMapper extends GenericMapper<FormaPagamento, FormaPagamentoInput, FormaPagamentoOutput> { }
