package com.luizjacomn.algafood.core.modelmapper;

import com.luizjacomn.algafood.api.model.input.ItemPedidoInput;
import com.luizjacomn.algafood.api.model.output.EnderecoOutput;
import com.luizjacomn.algafood.domain.model.Endereco;
import com.luizjacomn.algafood.domain.model.ItemPedido;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class).addMappings(item -> item.skip(ItemPedido::setId));

        var enderecoModelMapper = modelMapper.createTypeMap(Endereco.class, EnderecoOutput.class);
        enderecoModelMapper.<String>addMapping(endereco -> endereco.getCidade().getEstado().getNome(),
                (enderecoOutput, value) -> enderecoOutput.getCidade().setEstado(value));

        return modelMapper;
    }

}
