package com.luizjacomn.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado", 404),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso", 409),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio", 400),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível", 400),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido", 400),
    METODO_HTTP_NA0_SUPORTADO("/metodo-http-nao-suportado", "Método HTTP não suportado", 400),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos", 400),
    TAMANHO_REQUISICAO_EXCEDIDO("/tamanho-requisicao-excedido", "Tamanho máximo da requisição foi excedido", 400),
    PROBLEMA_NO_SISTEMA("/problema-no-sistema", "Problema no sistema", 500);

    private String uri;

    private String title;

    private int status;

    ProblemType(String path, String title, int status) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
        this.status = status;
    }
}
