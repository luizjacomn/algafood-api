package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.PedidoMapper;
import com.luizjacomn.algafood.api.model.mapper.PedidoResumeMapper;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.repository.PedidoRepository;
import com.luizjacomn.algafood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoResumeMapper pedidoResumeMapper;

    @GetMapping
    public List<PedidoResumeOutput> listar() {
        return pedidoResumeMapper.toOutputDTOList(pedidoRepository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoOutput buscar(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscar(id);

        return pedidoMapper.toOutputDTO(pedido);
    }

}
