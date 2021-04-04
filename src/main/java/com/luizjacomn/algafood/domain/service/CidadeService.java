package com.luizjacomn.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import com.luizjacomn.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade, Long id) {
		Estado estado = estadoRepository.findById(cidade.getEstado().getId())
										.orElseThrow(() -> new EntidadeNaoEncontradaException("Estado informado não foi encontrado."));

		cidade.setEstado(estado);

		if (id != null) {
			Cidade cidadeAtual = cidadeRepository.findById(id)
												.orElseThrow(() -> new EntidadeNaoEncontradaException("Cidade informada não foi encontrada."));

			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return cidadeRepository.save(cidadeAtual);
		}

		return cidadeRepository.save(cidade);
	}

	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Cidade informada não foi encontrada.");
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade está sendo utilizada e não pode ser excluída.");
		}
	}
}
