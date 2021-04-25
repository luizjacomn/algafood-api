package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.*;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade().getId() != null) {
            Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());

            restaurante.getEndereco().setCidade(cidade);
        }

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw RestauranteNaoEncontradoException.nomeMasculino("Restaurante", id);
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeMasculino("Restaurante");
        }
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.associar(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.desassociar(formaPagamento);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);

        restaurante.associar(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);

        restaurante.desassociar(usuario);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscar(id);

        restaurante.ativar();
    }

    @Transactional
    public void desativar(Long id) {
        Restaurante restaurante = buscar(id);

        // TODO verificar se há algum pedido em aberto, pois só poderia desativar um restaurante sem pedidos em aberto

        restaurante.desativar();
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = buscar(id);

        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = buscar(id);

        // TODO verificar se há algum pedido em aberto, pois só poderia desativar um restaurante sem pedidos em aberto

        restaurante.fechar();
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> RestauranteNaoEncontradoException.nomeMasculino("Restaurante", restauranteId));
    }
}
