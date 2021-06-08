package com.luizjacomn.algafood.api.v1.model.mapper;

import com.luizjacomn.algafood.api.v1.controller.RestauranteProdutoController;
import com.luizjacomn.algafood.api.v1.model.input.FotoProdutoInput;
import com.luizjacomn.algafood.api.v1.model.output.FotoProdutoOutput;
import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.model.Produto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class FotoProdutoMapper extends GenericRepresentationModelMapper<FotoProduto, FotoProdutoInput, FotoProdutoOutput, RestauranteProdutoController> {

    @Override
    public FotoProduto toEntity(FotoProdutoInput input) {
        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setNomeArquivo(input.getArquivo().getOriginalFilename());
        fotoProduto.setDescricao(input.getDescricao());
        fotoProduto.setContentType(input.getArquivo().getContentType());
        fotoProduto.setTamanho(input.getArquivo().getSize());

        return fotoProduto;
    }

    public FotoProduto toEntity(FotoProdutoInput fotoProdutoInput, Produto produto) {
        FotoProduto fotoProduto = this.toEntity(fotoProdutoInput);
        fotoProduto.setProduto(produto);

        return fotoProduto;
    }

    @Override
    public FotoProdutoOutput toModel(FotoProduto entity) {
        FotoProdutoOutput output = modelMapper.map(entity, outputClass);

        output.add(linkTo(controllerClass, entity.getRestauranteId()).slash(getIdentifier(output)).withSelfRel());

        return output;
    }

    @Override
    public Serializable getIdentifier(FotoProdutoOutput output) {
        return "/foto";
    }
}
