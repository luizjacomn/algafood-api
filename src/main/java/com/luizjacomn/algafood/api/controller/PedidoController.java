package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.filter.PedidoFilter;
import com.luizjacomn.algafood.api.model.input.PedidoInput;
import com.luizjacomn.algafood.api.model.mapper.PedidoMapper;
import com.luizjacomn.algafood.api.model.mapper.PedidoResumeMapper;
import com.luizjacomn.algafood.api.model.output.PedidoOutput;
import com.luizjacomn.algafood.api.model.output.PedidoResumeOutput;
import com.luizjacomn.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.luizjacomn.algafood.domain.exception.generics.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.generics.NegocioException;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.model.Usuario;
import com.luizjacomn.algafood.domain.repository.PedidoRepository;
import com.luizjacomn.algafood.domain.service.PedidoService;
import com.luizjacomn.algafood.infra.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoResumeMapper pedidoResumeMapper;

    @Override
    @GetMapping
    public Page<PedidoResumeOutput> pesquisar(PedidoFilter filter, Pageable pageable) {
        return pedidoResumeMapper.toOutputDTOPage(pageable, pedidoRepository.findAll(PedidoSpecs.filtrandoPor(filter), pageable));
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
