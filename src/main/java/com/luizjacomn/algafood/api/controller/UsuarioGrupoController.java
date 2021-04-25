package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.GrupoMapper;
import com.luizjacomn.algafood.api.model.output.GrupoOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoMapper grupoMapper;

    @GetMapping
    public List<GrupoOutput> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        return grupoMapper.toOutputDTOList(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.adicionarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.removerGrupo(usuarioId, grupoId);
    }
}
