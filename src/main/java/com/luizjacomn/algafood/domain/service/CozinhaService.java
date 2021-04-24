package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw CozinhaNaoEncontradaException.nomeFeminino("Cozinha", id);
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeFeminino("Cozinha");
        }
    }

    public Cozinha buscar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> CozinhaNaoEncontradaException.nomeFeminino("Cozinha", cozinhaId));
    }
}
