package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.v1.controller.FormaPagamentoController;
import com.luizjacomn.algafood.api.v1.model.input.FormaPagamentoInput;
import com.luizjacomn.algafood.api.v1.model.output.FormaPagamentoOutput;
import com.luizjacomn.algafood.domain.model.FormaPagamento;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class FormaPagamentoMapper extends GenericRepresentationModelMapper<FormaPagamento, FormaPagamentoInput, FormaPagamentoOutput, FormaPagamentoController> {

    @Override
    public Serializable getIdentifier(FormaPagamentoOutput output) {
        return output.getId();
    }
}
