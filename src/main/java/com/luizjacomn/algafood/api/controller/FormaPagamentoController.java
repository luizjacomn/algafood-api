package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.input.FormaPagamentoInput;
import com.luizjacomn.algafood.api.model.mapper.FormaPagamentoMapper;
import com.luizjacomn.algafood.api.model.output.FormaPagamentoOutput;
import com.luizjacomn.algafood.domain.model.FormaPagamento;
import com.luizjacomn.algafood.domain.repository.FormaPagamentoRepository;
import com.luizjacomn.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<List<FormaPagamentoOutput>> listar(ServletWebRequest request) {
        String eTag = geteTag(request);

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamentoOutput> formasPagamento = formaPagamentoMapper.toOutputDTOList(formaPagamentoRepository.findAll());
        return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                    .eTag(eTag)
                    .body(formasPagamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutput> buscar(@PathVariable Long id, ServletWebRequest request) {
        String eTag = geteTag(request);

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoOutput formaPagamento = formaPagamentoMapper.toOutputDTO(formaPagamentoService.buscar(id));
        
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamento);
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

    private String geteTag(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        Optional<OffsetDateTime> maxDataAtualizacao = formaPagamentoRepository.findMaxDataAtualizacao();

        if (maxDataAtualizacao.isPresent()) {
            return String.valueOf(maxDataAtualizacao.get().toEpochSecond());
        } else {
            return "0"; // default
        }
    }
}
