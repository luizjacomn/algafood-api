package com.luizjacomn.algafood.api.model.converter;

import com.luizjacomn.algafood.api.model.input.RestauranteInput;
import com.luizjacomn.algafood.api.model.output.RestauranteOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteConverter extends GenericConverter<Restaurante, RestauranteInput, RestauranteOutput> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "cozinha" };
    }
}
