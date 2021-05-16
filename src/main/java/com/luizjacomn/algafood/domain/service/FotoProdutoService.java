package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.repository.ProdutoRepository;
import com.luizjacomn.algafood.domain.service.storage.FotoStorageService;
import com.luizjacomn.algafood.domain.service.storage.FotoStorageService.Foto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {
        String nomeArquivoAntigo = null;

        Optional<FotoProduto> optional = produtoRepository.findFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId());

        if (optional.isPresent()) {
            produtoRepository.delete(optional.get());
            nomeArquivoAntigo = optional.get().getNomeArquivo();
        }

        String nomeArquivoResolvido = fotoStorageService.resolverNomeArquivo(fotoProduto.getNomeArquivo());

        fotoProduto.setNomeArquivo(nomeArquivoResolvido);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        Foto foto = Foto.builder()
                        .nomeArquivo(fotoProduto.getNomeArquivo())
                        .inputStream(dadosArquivo)
                        .build();

        fotoStorageService.armazenar(nomeArquivoAntigo, foto);

        return fotoProduto;
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoProduto(restauranteId, produtoId)
                                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.excluir(fotoProduto.getNomeArquivo());
    }
}
