package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.CozinhaInput;
import com.luizjacomn.algafood.api.model.output.CozinhaOutput;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class CozinhaMapper extends GenericMapper<Cozinha, CozinhaInput, CozinhaOutput> { }
