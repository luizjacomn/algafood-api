package com.luizjacomn.algafood.api.v1.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutput {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumeOutput cidade;
}
