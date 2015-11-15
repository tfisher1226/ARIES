package nam.model.query;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Query;
import nam.model.util.QueryUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("queryListManager")
public class QueryListManager extends AbstractDomainListManager<Query, QueryListObject> implements Serializable {
	
	@Inject
	private QueryDataManager queryDataManager;
	
	@Inject
	private QueryEventManager queryEventManager;
	
	@Inject
	private QueryInfoManager queryInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "queryList";
	}
	
	@Override
	public String getTitle() {
		return "Query List";
	}
	
	@Override
	public Object getRecordKey(Query query) {
		return QueryUtil.getKey(query);
	}
	
	@Override
	public String getRecordName(Query query) {
		return QueryUtil.toString(query);
	}
	
	@Override
	protected Class<Query> getRecordClass() {
		return Query.class;
	}
	
	@Override
	protected Query getRecord(QueryListObject rowObject) {
		return rowObject.getQuery();
	}
	
	@Override
	public Query getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? QueryUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Query query) {
		super.setSelectedRecord(query);
		fireSelectedEvent(query);
	}
	
	protected void fireSelectedEvent(Query query) {
		queryEventManager.fireSelectedEvent(query);
	}
	
	public boolean isSelected(Query query) {
		Query selection = selectionContext.getSelection("query");
		boolean selected = selection != null && selection.equals(query);
		return selected;
	}
	
	@Override
	protected QueryListObject createRowObject(Query query) {
		QueryListObject listObject = new QueryListObject(query);
		listObject.setSelected(isSelected(query));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Query> createRecordList() {
		try {
			Collection<Query> queryList = queryDataManager.getQueryList();
			if (queryList != null)
				return queryList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewQuery() {
		return viewQuery(selectedRecordKey);
	}
	
	public String viewQuery(Object recordKey) {
		Query query = recordByKeyMap.get(recordKey);
		return viewQuery(query);
	}
	
	public String viewQuery(Query query) {
		String url = queryInfoManager.viewQuery(query);
		return url;
	}
	
	public String editQuery() {
		return editQuery(selectedRecordKey);
	}
	
	public String editQuery(Object recordKey) {
		Query query = recordByKeyMap.get(recordKey);
		return editQuery(query);
	}
	
	public String editQuery(Query query) {
		String url = queryInfoManager.editQuery(query);
		return url;
	}
	
	public void removeQuery() {
		removeQuery(selectedRecordKey);
	}
	
	public void removeQuery(Object recordKey) {
		Query query = recordByKeyMap.get(recordKey);
		removeQuery(query);
	}
	
	public void removeQuery(Query query) {
		try {
			if (queryDataManager.removeQuery(query))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelQuery(@Observes @Cancelled Query query) {
		try {
			//Object key = QueryUtil.getKey(query);
			//recordByKeyMap.put(key, query);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("query");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateQuery(Collection<Query> queryList) {
		return QueryUtil.validate(queryList);
	}
	
	public void exportQueryList(@Observes @Export String tableId) {
		//String tableId = "pageForm:queryListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
