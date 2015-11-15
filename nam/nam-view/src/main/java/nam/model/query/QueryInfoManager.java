package nam.model.query;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Query;
import nam.model.util.ProjectUtil;
import nam.model.util.QueryUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("queryInfoManager")
public class QueryInfoManager extends AbstractNamRecordManager<Query> implements Serializable {
	
	@Inject
	private QueryWizard queryWizard;
	
	@Inject
	private QueryDataManager queryDataManager;
	
	@Inject
	private QueryPageManager queryPageManager;
	
	@Inject
	private QueryEventManager queryEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private QueryHelper queryHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public QueryInfoManager() {
		setInstanceName("query");
	}
	
	
	public Query getQuery() {
		return getRecord();
	}
	
	public Query getSelectedQuery() {
		return selectionContext.getSelection("query");
	}
	
	@Override
	public Class<Query> getRecordClass() {
		return Query.class;
	}
	
	@Override
	public boolean isEmpty(Query query) {
		return queryHelper.isEmpty(query);
	}
	
	@Override
	public String toString(Query query) {
		return queryHelper.toString(query);
	}
	
	@Override
	public void initialize() {
		Query query = selectionContext.getSelection("query");
		if (query != null)
			initialize(query);
	}
	
	protected void initialize(Query query) {
		QueryUtil.initialize(query);
		queryWizard.initialize(query);
		setContext("query", query);
	}
	
	public void handleQuerySelected(@Observes @Selected Query query) {
		selectionContext.setSelection("query",  query);
		queryPageManager.updateState(query);
		queryPageManager.refreshMembers();
		setRecord(query);
	}
	
	@Override
	public String newRecord() {
		return newQuery();
	}
	
	public String newQuery() {
		try {
			Query query = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("query",  query);
			String url = queryPageManager.initializeQueryCreationPage(query);
			queryPageManager.pushContext(queryWizard);
			initialize(query);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Query create() {
		Query query = QueryUtil.create();
		return query;
	}
	
	@Override
	public Query clone(Query query) {
		query = QueryUtil.clone(query);
		return query;
	}
	
	@Override
	public String viewRecord() {
		return viewQuery();
	}
	
	public String viewQuery() {
		Query query = selectionContext.getSelection("query");
		String url = viewQuery(query);
		return url;
	}
	
	public String viewQuery(Query query) {
		try {
			String url = queryPageManager.initializeQuerySummaryView(query);
			queryPageManager.pushContext(queryWizard);
			initialize(query);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editQuery();
	}
	
	public String editQuery() {
		Query query = selectionContext.getSelection("query");
		String url = editQuery(query);
		return url;
	}
	
	public String editQuery(Query query) {
		try {
			//query = clone(query);
			selectionContext.resetOrigin();
			selectionContext.setSelection("query",  query);
			String url = queryPageManager.initializeQueryUpdatePage(query);
			queryPageManager.pushContext(queryWizard);
			initialize(query);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveQuery() {
		Query query = getQuery();
		if (validateQuery(query)) {
			if (isImmediate())
				persistQuery(query);
			outject("query", query);
		}
	}
	
	public void persistQuery(Query query) {
		saveQuery(query);
	}
	
	public void saveQuery(Query query) {
		try {
			saveQueryToSystem(query);
			queryEventManager.fireAddedEvent(query);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveQueryToSystem(Query query) {
		queryDataManager.saveQuery(query);
	}
	
	public void handleSaveQuery(@Observes @Add Query query) {
		saveQuery(query);
	}
	
	public void addQuery(Query query) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichQuery(Query query) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Query query) {
		return validateQuery(query);
	}
	
	public boolean validateQuery(Query query) {
		Validator validator = getValidator();
		boolean isValid = QueryUtil.validate(query);
		Display display = getFromSession("display");
		display.setModule("queryInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveQuery() {
		display = getFromSession("display");
		display.setModule("queryInfo");
		Query query = selectionContext.getSelection("query");
		if (query == null) {
			display.error("Query record must be selected.");
		}
	}
	
	public String handleRemoveQuery(@Observes @Remove Query query) {
		display = getFromSession("display");
		display.setModule("queryInfo");
		try {
			display.info("Removing Query "+QueryUtil.getLabel(query)+" from the system.");
			removeQueryFromSystem(query);
			selectionContext.clearSelection("query");
			queryEventManager.fireClearSelectionEvent();
			queryEventManager.fireRemovedEvent(query);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeQueryFromSystem(Query query) {
		if (queryDataManager.removeQuery(query))
			setRecord(null);
	}
	
	public void cancelQuery() {
		BeanContext.removeFromSession("query");
		queryPageManager.removeContext(queryWizard);
	}
	
}
