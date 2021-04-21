package com.luizjacomn.algafood.api.model.converter;

import com.luizjacomn.algafood.api.model.input.CozinhaInput;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class CozinhaConverter extends GenericConverter<Cozinha, CozinhaInput, CozinhaOutput> { }
