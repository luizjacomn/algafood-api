package com.luizjacomn.algafood.api.controller.relatorios;

import com.luizjacomn.algafood.api.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.model.dto.VendaDiaria;
import com.luizjacomn.algafood.domain.repository.reports.PedidoRelatoriosRepository;
import com.luizjacomn.algafood.domain.service.reports.VendaRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private VendaRelatorioService vendaRelatorioService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> pesquisar(VendaDiariaFilter filter,
                                       @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return pedidoRelatoriosRepository.pesquisarVendasDiarias(filter, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> emitirPDF(VendaDiariaFilter filter,
                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(vendaRelatorioService.emitir(filter, timeOffset));
    }
}
