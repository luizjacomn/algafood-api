package com.luizjacomn.algafood.infra.service.reports;

import com.luizjacomn.algafood.api.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.model.dto.VendaDiaria;
import com.luizjacomn.algafood.domain.repository.reports.PedidoRelatoriosRepository;
import com.luizjacomn.algafood.domain.service.reports.VendaRelatorioService;
import com.luizjacomn.algafood.infra.exception.ReportException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class VendaRelatorioPDFService implements VendaRelatorioService {

    @Autowired
    private PedidoRelatoriosRepository pedidoRelatoriosRepository;

    @Override
    public byte[] emitir(VendaDiariaFilter filter, String timeOffset) {
        try {
            InputStream vendasDiariasJasper = this.getClass().getResourceAsStream("/reports/vendas-diarias.jasper");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            List<VendaDiaria> vendasDiarias = pedidoRelatoriosRepository.pesquisarVendasDiarias(filter, timeOffset);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(vendasDiariasJasper, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Erro ao emitir relatório de vendas diárias", e);
        }
    }
}
