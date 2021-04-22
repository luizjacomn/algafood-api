package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.EstadoInput;
import com.luizjacomn.algafood.api.model.output.EstadoOutput;
import com.luizjacomn.algafood.domain.model.Estado;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper extends GenericMapper<Estado, EstadoInput, EstadoOutput> { }
