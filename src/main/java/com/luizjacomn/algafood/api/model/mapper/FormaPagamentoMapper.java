package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.FormaPagamentoController;
import com.luizjacomn.algafood.api.model.input.FormaPagamentoInput;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
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
