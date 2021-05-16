package com.luizjacomn.algafood.infra.service.storage;

import com.luizjacomn.algafood.domain.service.storage.FotoStorageService;
import com.luizjacomn.algafood.infra.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${api.service.storage.local.diretorio}")
    private Path diretorioLocal;

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

    private void excluir(String nomeArquivo) {
        if (nomeArquivo != null) {
            try {
                Files.deleteIfExists(montarDestino(nomeArquivo));
            } catch (Exception e) {
                throw new StorageException("Não foi possível excluir o arquivo antigo", e);
            }
        }
    }

    private Path montarDestino(String nomeArquivo) {
        return diretorioLocal.resolve(Paths.get(nomeArquivo));
    }

}
