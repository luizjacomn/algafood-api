package com.luizjacomn.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada", 404),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso", 409),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio", 400);

    private String uri;

    private String title;

    private int status;

    ProblemType(String path, String title, int status) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
        this.status = status;
    }
}
