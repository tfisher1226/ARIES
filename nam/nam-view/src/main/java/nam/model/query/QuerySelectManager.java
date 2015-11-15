package nam.model.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Query;
import nam.model.util.QueryUtil;


@SessionScoped
@Named("querySelectManager")
public class QuerySelectManager extends AbstractSelectManager<Query, QueryListObject> implements Serializable {
	
	@Inject
	private QueryDataManager queryDataManager;
	
	@Inject
	private QueryHelper queryHelper;
	
	
	@Override
	public String getClientId() {
		return "querySelect";
	}
	
	@Override
	public String getTitle() {
		return "Query Selection";
	}
	
	@Override
	protected Class<Query> getRecordClass() {
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
	
	protected QueryHelper getQueryHelper() {
		return BeanContext.getFromSession("queryHelper");
	}
	
	protected QueryListManager getQueryListManager() {
		return BeanContext.getFromSession("queryListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshQueryList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Query> recordList) {
		QueryListManager queryListManager = getQueryListManager();
		DataModel<QueryListObject> dataModel = queryListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshQueryList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Query> refreshRecords() {
		try {
			Collection<Query> records = queryDataManager.getQueryList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Query> queryList) {
		Collections.sort(queryList, new Comparator<Query>() {
			public int compare(Query query1, Query query2) {
				String text1 = QueryUtil.toString(query1);
				String text2 = QueryUtil.toString(query2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
