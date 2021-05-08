package com.luizjacomn.algafood.domain.repository;

import com.luizjacomn.algafood.api.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.model.dto.VendaDiaria;

import java.util.List;

public interface PedidoRelatoriosRepository {

    List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter);

}
