package com.luizjacomn.algafood.infra.repository;

import com.luizjacomn.algafood.api.filter.VendaDiariaFilter;
import com.luizjacomn.algafood.api.model.dto.VendaDiaria;
import com.luizjacomn.algafood.domain.model.Pedido;
import com.luizjacomn.algafood.domain.model.StatusPedido;
import com.luizjacomn.algafood.domain.repository.PedidoRelatoriosRepository;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PedidoRelatoriosRepositoryImpl implements PedidoRelatoriosRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> pesquisarVendasDiarias(VendaDiariaFilter filter) {
        /*
        select
        date(p.data_criacao),
        count(p.id),
        sum(p.valor_total)
        from
        pedido p
        group by p.data_criacao
         */

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);
        Expression<Date> dataCriacaoTrunc = builder.function("date", Date.class, root.get("dataCriacao"));

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                dataCriacaoTrunc,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
        query.select(selection);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.in(root.get("status")).value(StatusPedido.CONFIRMADO).value(StatusPedido.ENTREGUE));

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
        }

        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
        }

        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(dataCriacaoTrunc);
        query.orderBy(new OrderImpl(dataCriacaoTrunc, false));

        return manager.createQuery(query).getResultList();
    }

}
