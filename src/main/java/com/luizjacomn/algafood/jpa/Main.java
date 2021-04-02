package com.luizjacomn.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.luizjacomn.algafood.AlgafoodApiApplication;
import com.luizjacomn.algafood.domain.model.Cozinha;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
																.web(WebApplicationType.NONE)
																.run(args);
		
		CozinhaJPA jpa = context.getBean(CozinhaJPA.class);
		
		Cozinha brasileira = new Cozinha("Brasileira");
		jpa.salvar(brasileira);
		
		List<Cozinha> listarCozinhas = jpa.listarCozinhas();
		listarCozinhas.forEach(cozinha -> System.out.println(cozinha.getNome()));
		
//		Cozinha cozinha = jpa.buscar(1L);
//		System.out.println(cozinha.getNome());
//		
//		cozinha.setNome("Tailandeza/Australiana");
//		Cozinha atualizada = jpa.salvar(cozinha);
//		System.out.println(atualizada.getNome());
//		
//		jpa.deletar(cozinha);
	}
	
}
