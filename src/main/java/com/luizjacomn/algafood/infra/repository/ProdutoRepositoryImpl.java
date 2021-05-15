package com.luizjacomn.algafood.infra.repository;

import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.repository.ProdutoQueriesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoQueriesRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }
}
