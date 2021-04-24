package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Estado estado = estadoService.buscar(cidade.getEstado().getId());

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw CidadeNaoEncontradaException.nomeFeminino("Cidade", id);
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeFeminino("Cidade");
        }
    }

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> CidadeNaoEncontradaException.nomeFeminino("Cidade", cidadeId));
    }
}
