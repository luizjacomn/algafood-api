package com.luizjacomn.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(String nomeArquivoAntigo, Foto foto);

    default String resolverNomeArquivo(String nomeArquivo) {
        return UUID.randomUUID().toString() + "_" + nomeArquivo.replaceAll("[^a-zA-Z\\d-.]", "-").toLowerCase();
    }

    @Builder
    @Getter
    class Foto {

        String nomeArquivo;

        InputStream inputStream;

    }

}
