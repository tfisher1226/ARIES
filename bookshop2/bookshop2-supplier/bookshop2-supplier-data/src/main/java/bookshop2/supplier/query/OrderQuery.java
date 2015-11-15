package bookshop2.supplier.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import bookshop2.OrderCriteria;
import bookshop2.supplier.entity.AbstractOrderEntity;


public class OrderQuery<T> extends AbstractQuery<T> {

	public OrderQuery(EntityManager entityManager) {
		super(entityManager);
	}

	public void integrateCriteria(OrderCriteria orderCriteria) {
		query = createCriteriaQuery(orderCriteria);
	}

	protected CriteriaQuery<T> createCriteriaQuery(OrderCriteria orderCriteria) {
		CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
		query.distinct(true);
		root = query.from(entityClass);
		CriteriaQuery<T> select = query.select(root);
		List<Order> orderByList = new ArrayList<Order>();
		orderByList.add(criteriaBuilder.asc(root.get("id")));
		select = select.orderBy(orderByList);
		Predicate predicate = buildPredicates(orderCriteria);
		select.where(predicate);
		return select;
	}

	protected Predicate buildPredicates(OrderCriteria OrderCriteria) {
		Predicate predicate = criteriaBuilder.conjunction();
		//integrateOrganizationPredicate(predicate, OrderCriteria.getOrganizationSet());
		//integrateDivisionPredicate(predicate, OrderCriteria.getDivisionSet());
		return predicate;
	}

//	protected void integrateDivisionPredicate(Predicate predicate, Set<Division> divisionSet) {
//		if (divisionSet != null && divisionSet.size() > 0) {
//			Path<Object> path = root.get("division");
//			Predicate divisionPredicate = path.in(divisionSet);
//			predicate.getExpressions().add(divisionPredicate);
//		}
//	}

}
