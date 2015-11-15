package nam.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import nam.entity.WorkspaceEntity;
import nam.model.Project;

import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.StreetAddress;
import org.aries.common.query.AbstractQuery;


public class WorkspaceQuery<T extends WorkspaceEntity> extends AbstractQuery<T> {

	public WorkspaceQuery(EntityManager entityManager) {
		super(entityManager);
	}

	
	public void integrateCriteria(WorkspaceCriteria workspaceCriteria) {
		query = createCriteriaQuery(workspaceCriteria);
	}

	public List<T> getResultList() {
		return executeCriteriaQuery(query);
	}
	
	protected CriteriaQuery<T> createCriteriaQuery(WorkspaceCriteria workspaceCriteria) {
		CriteriaQuery<WorkspaceEntity> criteriaQuery = criteriaBuilder.createQuery(WorkspaceEntity.class);
		criteriaQuery.distinct(false);
		root = criteriaQuery.from(entityClass);
		@SuppressWarnings("unchecked") CriteriaQuery<T> select = (CriteriaQuery<T>) criteriaQuery.select(root);
		List<Order> orderByList = new ArrayList<Order>();
		select = select.orderBy(orderByList);
		Predicate predicate = buildPredicates(workspaceCriteria);
		select.where(predicate);
		return select;
	}
	
	protected Predicate buildPredicates(WorkspaceCriteria workspaceCriteria) {
		Predicate predicate = criteriaBuilder.conjunction();
		integrateEnabledPredicate(predicate, workspaceCriteria.getEnabled());
		integrateWorkspaceNamePredicate(predicate, workspaceCriteria.getWorkspaceName());
		integratePersonNamePredicate(predicate, workspaceCriteria.getPersonName());
		integrateEmailAddressPredicate(predicate, workspaceCriteria.getEmailAddress());
		integrateStreetAddressPredicate(predicate, workspaceCriteria.getStreetAddress());
		return predicate;
	}
	
	protected void integrateEnabledPredicate(Predicate predicate, Boolean enabled) {
		Path<Object> path = root.get("enabled");
		Predicate enabledPredicate = path.in(enabled);
		predicate.getExpressions().add(enabledPredicate);
	}
	
	protected void integrateWorkspaceNamePredicate(Predicate predicate, String workspaceName) {
		Path<Object> path = root.get("workspaceName");
		Predicate workspaceNamePredicate = path.in(workspaceName);
		predicate.getExpressions().add(workspaceNamePredicate);
	}
	
	protected void integratePersonNamePredicate(Predicate predicate, PersonName personName) {
		Path<Object> path = root.get("personName");
		Predicate personNamePredicate = path.in(personName);
		predicate.getExpressions().add(personNamePredicate);
	}
	
	protected void integrateEmailAddressPredicate(Predicate predicate, EmailAddress emailAddress) {
		Path<Object> path = root.get("emailAddress");
		Predicate emailAddressPredicate = path.in(emailAddress);
		predicate.getExpressions().add(emailAddressPredicate);
	}
	
	protected void integrateStreetAddressPredicate(Predicate predicate, StreetAddress streetAddress) {
		Path<Object> path = root.get("streetAddress");
		Predicate streetAddressPredicate = path.in(streetAddress);
		predicate.getExpressions().add(streetAddressPredicate);
	}
	
	protected void integrateProjectsPredicate(Predicate predicate, Project projects) {
		Path<Object> path = root.get("projects");
		Predicate projectsPredicate = path.in(projects);
		predicate.getExpressions().add(projectsPredicate);
	}
	
	protected List<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> results = new ArrayList<T>();
		results.addAll(typedQuery.getResultList());
		return results;
	}
	
}
