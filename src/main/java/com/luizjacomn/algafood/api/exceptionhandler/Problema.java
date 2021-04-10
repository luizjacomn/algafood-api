package com.luizjacomn.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Problema {

    private LocalDateTime dataHorario;

    private String mensagem;
}
