package com.luizjacomn.algafood.api.model.mapper;

import com.luizjacomn.algafood.api.model.input.RestauranteInput;
import com.luizjacomn.algafood.api.model.output.RestauranteOutput;
import com.luizjacomn.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper extends GenericMapper<Restaurante, RestauranteInput, RestauranteOutput> {

    @Override
    public String[] getRelationshipAttributes() {
        return new String[] { "cozinha" };
    }
}
