package com.luizjacomn.algafood.api.v1.controller;

import com.luizjacomn.algafood.api.v1.util.AlgaLinks;
import com.luizjacomn.algafood.core.web.RootEntryPointModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinks.linkFor(CidadeController.class));

        rootEntryPointModel.add(algaLinks.linkFor(EstadoController.class));

        rootEntryPointModel.add(algaLinks.linkFor(CozinhaController.class));

        rootEntryPointModel.add(algaLinks.linkFor(RestauranteController.class));

        rootEntryPointModel.add(algaLinks.linkFor(FormaPagamentoController.class, "formas-pagamento"));

        rootEntryPointModel.add(algaLinks.linkFor(PedidoController.class));

        rootEntryPointModel.add(algaLinks.linkFor(UsuarioController.class));

        rootEntryPointModel.add(algaLinks.linkFor(GrupoController.class));

        rootEntryPointModel.add(algaLinks.linkForRelatorioVendasDiarias());

        return rootEntryPointModel;
    }

}
