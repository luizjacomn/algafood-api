package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.controller.GrupoPermissaoController;
import com.luizjacomn.algafood.api.model.output.PermissaoOutput;
import com.luizjacomn.algafood.domain.model.Permissao;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissaoMapper extends GenericRepresentationModelMapper<Permissao, Void, PermissaoOutput, GrupoPermissaoController> {

    @Override
    public PermissaoOutput toModel(Permissao entity) {
        return modelMapper.map(entity, outputClass);
    }

    @Override
    public Serializable getIdentifier(PermissaoOutput output) {
        return output.getId();
    }
}
