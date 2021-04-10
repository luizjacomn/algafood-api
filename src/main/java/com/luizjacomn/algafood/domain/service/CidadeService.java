package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade informada não foi encontrada.";
	private static final String MSG_CIDADE_EM_USO = "Cidade está sendo utilizada e não pode ser excluída.";

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeRepository cidadeRepository;

	public Cidade salvar(Cidade cidade, Long id) {
		Estado estado = estadoService.buscar(cidade.getEstado().getId());

		cidade.setEstado(estado);

		if (id != null) {
			Cidade cidadeAtual = buscar(id);

			BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			return cidadeRepository.save(cidadeAtual);
		}

		return cidadeRepository.save(cidade);
	}

	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_CIDADE_EM_USO);
		}
	}

	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA));
	}
}
