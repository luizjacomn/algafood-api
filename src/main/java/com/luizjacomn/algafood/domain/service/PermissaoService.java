package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Permissao;
import com.luizjacomn.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    public Permissao buscar(Long id) {
        return permissaoRepository.findById(id).orElseThrow(() -> PermissaoNaoEncontradaException.nomeFeminino("Permissão", id));
    }
}
