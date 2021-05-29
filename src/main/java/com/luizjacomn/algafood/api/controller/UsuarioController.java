package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.SenhaInput;
import com.luizjacomn.algafood.api.model.input.UsuarioInput;
import com.luizjacomn.algafood.api.model.input.UsuarioSenhaInput;
import com.luizjacomn.algafood.api.model.mapper.UsuarioMapper;
import com.luizjacomn.algafood.api.model.output.UsuarioOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.repository.UsuarioRepository;
import com.luizjacomn.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public CollectionModel<UsuarioOutput> listar() {
        return usuarioMapper.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioOutput buscar(@PathVariable Long id) {
        return usuarioMapper.toModel(usuarioService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutput salvar(@RequestBody @Valid UsuarioSenhaInput usuarioSenhaInput) {
        Usuario usuario = usuarioMapper.toEntity(usuarioSenhaInput);

        return usuarioMapper.toModel(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioOutput atualizar(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) throws Exception {
        Usuario usuario = usuarioService.buscar(id);

        usuarioMapper.copyToEntity(usuarioInput, usuario);

        return usuarioMapper.toModel(usuarioService.salvar(usuario));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @PutMapping("/{id}/alterar-senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
