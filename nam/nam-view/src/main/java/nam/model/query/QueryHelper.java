package nam.model.query;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Query;
import nam.model.util.QueryUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("queryHelper")
public class QueryHelper extends AbstractElementHelper<Query> implements Serializable {
	
	@Override
	public boolean isEmpty(Query query) {
		return QueryUtil.isEmpty(query);
	}
	
	@Override
	public String toString(Query query) {
		return QueryUtil.toString(query);
	}
	
	@Override
	public String toString(Collection<Query> queryList) {
		return QueryUtil.toString(queryList);
	}
	
	@Override
	public boolean validate(Query query) {
		return QueryUtil.validate(query);
	}
	
	@Override
	public boolean validate(Collection<Query> queryList) {
		return QueryUtil.validate(queryList);
	}
	
	public DataModel<Serializable> getConditionsAndCriterias(Query query) {
		if (query == null)
			return null;
		return getConditionsAndCriterias(query.getConditionsAndCriterias());
	}
	
	public DataModel<Serializable> getConditionsAndCriterias(Collection<Serializable> conditionsAndCriteriasList) {
		//SerializableListManager serializableListManager = BeanContext.getFromSession("serializableListManager");
		//DataModel<Serializable> dataModel = serializableListManager.getDataModel(conditionsAndCriteriasList);
		//return dataModel;
		return null;
	}
	
}
