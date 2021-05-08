package com.luizjacomn.algafood.api.controller.relatorios;

import com.luizjacomn.algafood.api.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.model.dto.VendaDiaria;
import com.luizjacomn.algafood.domain.repository.PedidoRelatoriosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class PedidoRelatoriosController {

    @Autowired
    private PedidoRelatoriosRepository pedidoRelatoriosRepository;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> pesquisar(VendaDiariaFilter filter,
                                       @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return pedidoRelatoriosRepository.pesquisarVendasDiarias(filter, timeOffset);
    }
}
