package com.luizjacomn.algafood.domain.service;

import com.luizjacomn.algafood.domain.exception.EntidadeEmUsoException;
import com.luizjacomn.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.luizjacomn.algafood.domain.model.Cozinha;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CozinhaServiceIT {

	@Autowired
	private CozinhaService service;

	@Test
	public void deve_cadastrar_cozinha() {
		// arrange
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Cearense");

		// act
		cozinha = service.salvar(cozinha);

		// assert
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}

	@Test(expected = ConstraintViolationException.class)
	public void deve_falhar_ao_cadastrar_cozinha_sem_nome() {
		// arrange
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		// act
		service.salvar(cozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deve_falhar_ao_excluir_cozinha_em_uso() {
		// act
		service.excluir(2L);
	}

	@Test(expected = EntidadeNaoEncontradaException.class)
	public void deve_falhar_ao_excluir_cozinha_inexistente() {
		// act
		service.excluir(300L);
	}

}
