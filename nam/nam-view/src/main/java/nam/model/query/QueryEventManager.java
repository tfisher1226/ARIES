package nam.model.query;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Query;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("queryEventManager")
public class QueryEventManager extends AbstractEventManager<Query> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Query getInstance() {
		return selectionContext.getSelection("query");
	}
	
	public void removeQuery() {
		Query query = getInstance();
		fireRemoveEvent(query);
	}
	
}
