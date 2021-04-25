package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.output.PermissaoOutput;
import com.luizjacomn.algafood.domain.model.Permissao;
import org.springframework.stereotype.Component;

@Component
public class PermissaoMapper extends GenericMapper<Permissao, Void, PermissaoOutput> { }
