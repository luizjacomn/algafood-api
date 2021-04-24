package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.GrupoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.Grupo;
import com.luizjacomn.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw GrupoNaoEncontradoException.nomeMasculino("Grupo", id);
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeMasculino("Grupo");
        }
    }

    public Grupo buscar(Long id) {
        return grupoRepository.findById(id).orElseThrow(() -> GrupoNaoEncontradoException.nomeMasculino("Grupo", id));
    }
}
