package com.luizjacomn.algafood.infra.repository;

import com.luizjacomn.algafood.domain.model.FotoProduto;
import com.luizjacomn.algafood.domain.repository.ProdutoQueriesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoQueriesRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<FotoProduto> findFotoProduto(Long restauranteId, Long produtoId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT fp FROM FotoProduto fp ");
        jpql.append("JOIN fp.produto p ");
        jpql.append("WHERE p.restaurante.id = :restaurante ");
        jpql.append("AND fp.produto.id = :produto");

        TypedQuery<FotoProduto> query = manager.createQuery(jpql.toString(), FotoProduto.class);
        query.setParameter("restaurante", restauranteId);
        query.setParameter("produto", produtoId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }

    @Override
    public void delete(FotoProduto fotoProduto) {
        manager.remove(fotoProduto);
    }
}
