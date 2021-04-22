package com.luizjacomn.algafood.api.controller;

import com.luizjacomn.algafood.api.model.mapper.CidadeMapper;
import com.luizjacomn.algafood.api.model.input.CidadeInput;
import com.luizjacomn.algafood.api.model.output.CidadeOutput;
import com.luizjacomn.algafood.domain.exception.EstadoNaoEncontradoException;
import com.luizjacomn.algafood.domain.exception.NegocioException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @GetMapping
    public List<CidadeOutput> listar() {
        return cidadeMapper.toOutputDTOList(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeOutput buscar(@PathVariable Long id) {
        return cidadeMapper.toOutputDTO(cidadeService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeMapper.toEntity(cidadeInput);

            return cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeOutput atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) throws Exception {
        try {
            Cidade cidade = cidadeService.buscar(id);
            cidadeMapper.copyToEntity(cidadeInput, cidade);

            return cidadeMapper.toOutputDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cidadeService.excluir(id);
    }

}
