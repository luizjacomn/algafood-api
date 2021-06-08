package com.luizjacomn.algafood.domain.service.reports;

import com.luizjacomn.algafood.api.v1.filter.VendaDiariaFilter;

public interface VendaRelatorioService {

    byte[] emitir(VendaDiariaFilter filter, String timeOffset);

}
