package com.luizjacomn.algafood.api.v2.controller;

import com.luizjacomn.algafood.api.v2.util.AlgaLinksV2;
import com.luizjacomn.algafood.core.web.RootEntryPointModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class RootEntryPointControllerV2 {

    @Autowired
    private AlgaLinksV2 algaLinksV2;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinksV2.linkFor(CidadeControllerV2.class));

        rootEntryPointModel.add(algaLinksV2.linkFor(CozinhaControllerV2.class));

        return rootEntryPointModel;
    }

}
