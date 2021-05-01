package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.mapper.PedidoMapper;
import com.luizjacomn.algafood.api.model.mapper.PedidoResumeMapper;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.repository.PedidoRepository;
import com.luizjacomn.algafood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{codigoPedido}")
    public PedidoOutput buscar(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);

        return pedidoMapper.toOutputDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutput criar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoMapper.toEntity(pedidoInput);
            // TODO pegar usuário autenticado
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            return pedidoMapper.toOutputDTO(pedidoService.emitir(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
