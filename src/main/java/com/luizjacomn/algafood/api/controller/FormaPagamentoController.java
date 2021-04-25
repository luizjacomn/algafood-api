package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.FormaPagamentoInput;
import com.luizjacomn.algafood.api.model.mapper.FormaPagamentoMapper;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
import com.luizjacomn.algafood.domain.model.FormaPagamento;
import com.luizjacomn.algafood.domain.repository.FormaPagamentoRepository;
import com.luizjacomn.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @GetMapping
    public List<FormaPagamentoOutput> listar() {
        return formaPagamentoMapper.toOutputDTOList(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoOutput buscar(@PathVariable Long id) {
        return formaPagamentoMapper.toOutputDTO(formaPagamentoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutput salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoMapper.toEntity(formaPagamentoInput);

        return formaPagamentoMapper.toOutputDTO(formaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    public FormaPagamentoOutput atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) throws Exception {
        FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

        formaPagamentoMapper.copyToEntity(formaPagamentoInput, formaPagamento);

        return formaPagamentoMapper.toOutputDTO(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        formaPagamentoService.excluir(id);
    }
}
