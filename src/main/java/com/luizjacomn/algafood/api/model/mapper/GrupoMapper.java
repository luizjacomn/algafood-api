package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.GrupoInput;
import com.luizjacomn.algafood.api.model.output.GrupoOutput;
import com.luizjacomn.algafood.domain.model.Grupo;
import org.springframework.stereotype.Component;

@Component
public class GrupoMapper extends GenericMapper<Grupo, GrupoInput, GrupoOutput> {
}
