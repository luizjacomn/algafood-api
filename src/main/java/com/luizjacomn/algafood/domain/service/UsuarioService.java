package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.generics.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw UsuarioNaoEncontradoException.nomeMasculino("Usuário", id);
        } catch (DataIntegrityViolationException e) {
            throw EntidadeEmUsoException.nomeMasculino("Usuário");
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> UsuarioNaoEncontradoException.nomeMasculino("Usuário", id));
    }
}
