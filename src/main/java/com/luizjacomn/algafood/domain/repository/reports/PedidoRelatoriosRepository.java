package com.luizjacomn.algafood.domain.repository.reports;

import com.luizjacomn.algafood.api.v1.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.v1.model.dto.VendaDiaria;

import java.util.List;

public interface PedidoRelatoriosRepository {

    List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
