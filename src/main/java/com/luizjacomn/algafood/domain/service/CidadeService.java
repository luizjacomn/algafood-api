package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.CidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Estado;
import com.luizjacomn.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade está sendo utilizada e não pode ser excluída.";

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Transactional
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

	@Transactional
	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_CIDADE_EM_USO);
		}
	}

	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
}
