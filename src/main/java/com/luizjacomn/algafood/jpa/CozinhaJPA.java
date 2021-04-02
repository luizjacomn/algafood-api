package com.luizjacomn.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.luizjacomn.algafood.domain.model.Cozinha;

@Component
public class CozinhaJPA {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Cozinha> listarCozinhas() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	@Transactional
	public void deletar(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		manager.remove(cozinha);
	}
}
