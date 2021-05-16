package com.luizjacomn.algafood.infra.service.storage;

import com.luizjacomn.algafood.domain.service.storage.FotoStorageService;
import com.luizjacomn.algafood.infra.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Service
@Slf4j
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${api.service.storage.local.diretorio}")
    private Path diretorioLocal;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            return Files.newInputStream(montarDestino(nomeArquivo));
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo");
        }
    }

    @Override
    public void armazenar(String nomeArquivoAntigo, Foto foto) {
        try {
            Path destino = montarDestino(foto.getNomeArquivo());

            FileCopyUtils.copy(foto.getInputStream(), Files.newOutputStream(destino));

            excluir(nomeArquivoAntigo);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo informado", e);
        }

    }

    @Override
    public void excluir(String nomeArquivo) {
        if (nomeArquivo != null) {
            try {
                Files.deleteIfExists(montarDestino(nomeArquivo));
            } catch (Exception e) {
                throw new StorageException("Não foi possível excluir o arquivo antigo", e);
            }
        }
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            log.info("Limpando diretório local");
            FileSystemUtils.deleteRecursively(diretorioLocal);
            log.info("Limpeza realizada com sucesso");

            log.info("Recriando diretório local");
            Files.createDirectories(diretorioLocal);
            log.info("Diretório local criado com sucesso");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new StorageException("Erro ao limpar diretório local", e);
        }
    }

    private Path montarDestino(String nomeArquivo) {
        return diretorioLocal.resolve(Paths.get(nomeArquivo));
    }

}
