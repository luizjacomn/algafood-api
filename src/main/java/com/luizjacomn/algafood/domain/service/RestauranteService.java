package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.luizjacomn.algafood.domain.model.Cidade;
import com.luizjacomn.algafood.domain.model.Cozinha;
import com.luizjacomn.algafood.domain.model.Restaurante;
import com.luizjacomn.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante está sendo utilizado e não pode ser excluído.";

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private RestauranteRepository restauranteRepository;

	public Restaurante salvar(Restaurante restaurante, Long id) {
		Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());

		restaurante.setCozinha(cozinha);

		if (restaurante.getEndereco() != null && restaurante.getEndereco().getCidade().getId() != null) {
			Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());

			restaurante.getEndereco().setCidade(cidade);
		}

		if (id != null) {
			Restaurante restauranteAtual = buscar(id);

			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "endereco", "formasPagamento", "dataCadastro");

			return restauranteRepository.save(restauranteAtual);
		}

		return restauranteRepository.save(restaurante);
	}

	public void excluir(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_RESTAURANTE_EM_USO);
		}
	}

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
}
