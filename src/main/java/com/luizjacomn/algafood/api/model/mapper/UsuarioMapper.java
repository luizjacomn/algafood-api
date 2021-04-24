package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.UsuarioInput;
import com.luizjacomn.algafood.api.model.output.UsuarioOutput;
import com.luizjacomn.algafood.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends GenericMapper<Usuario, UsuarioInput, UsuarioOutput> { }
