package bookshop2.supplier.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import bookshop2.BookCriteria;
import bookshop2.supplier.entity.AbstractBookEntity;


public class BookQuery<T extends AbstractBookEntity> extends AbstractQuery<T> {

	public BookQuery(EntityManager entityManager) {
		super(entityManager);
	}


	public void integrateCriteria(BookCriteria bookCriteria) {
		query = createCriteriaQuery(bookCriteria);
	}

	public List<T> getResultList() {
		return executeCriteriaQuery(query);
	}

	protected CriteriaQuery<T> createCriteriaQuery(BookCriteria bookCriteria) {
		CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
		query.distinct(true);
		root = query.from(entityClass);
		CriteriaQuery<T> select = query.select(root);
		List<Order> orderByList = new ArrayList<Order>();
		orderByList.add(criteriaBuilder.asc(root.get("id")));
		select = select.orderBy(orderByList);
		Predicate predicate = buildPredicates(bookCriteria);
		select.where(predicate);
		return select;
	}

	protected Predicate buildPredicates(BookCriteria bookCriteria) {
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

	protected List<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}


}
