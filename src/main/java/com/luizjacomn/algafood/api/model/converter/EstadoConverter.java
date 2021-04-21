package com.luizjacomn.algafood.api.model.converter;

import com.luizjacomn.algafood.api.model.input.EstadoInput;
import com.luizjacomn.algafood.api.model.output.EstadoOutput;
import com.luizjacomn.algafood.domain.model.Estado;
import org.springframework.stereotype.Component;

@Component
public class EstadoConverter extends GenericConverter<Estado, EstadoInput, EstadoOutput> { }
