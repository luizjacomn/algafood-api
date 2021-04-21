package com.luizjacomn.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.luizjacomn.algafood.domain.model.*;
import com.luizjacomn.algafood.api.model.mixin.*;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 2741934226059976261L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }

}
