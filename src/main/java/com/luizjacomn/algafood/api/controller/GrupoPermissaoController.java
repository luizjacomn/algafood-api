package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.PermissaoMapper;
import com.luizjacomn.algafood.api.model.output.PermissaoOutput;
import com.luizjacomn.algafood.domain.model.Grupo;
import com.luizjacomn.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoMapper permissaoMapper;

    @GetMapping
    public List<PermissaoOutput> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        return permissaoMapper.toOutputDTOList(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.adicionarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.removerPermissao(grupoId, permissaoId);
    }
}
