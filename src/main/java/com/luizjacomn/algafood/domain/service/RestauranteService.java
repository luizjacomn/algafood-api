package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());

		restaurante.setCozinha(cozinha);

		if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade().getId() != null) {
			Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());

			restaurante.getEndereco().setCidade(cidade);
		}

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw EntidadeEmUsoException.nomeMasculino("Restaurante");
		}
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscar(id);

		restaurante.ativar();
	}

	@Transactional
	public void desativar(Long id) {
		Restaurante restaurante = buscar(id);

		// TODO verificar se há algum pedido em aberto, pois só poderia desativar um restaurante sem pedidos em aberto

		restaurante.desativar();
	}

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
