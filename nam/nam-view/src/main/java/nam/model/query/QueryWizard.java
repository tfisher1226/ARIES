package nam.model.query;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Query;
import nam.model.util.QueryUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("queryWizard")
@SuppressWarnings("serial")
public class QueryWizard extends AbstractDomainElementWizard<Query> implements Serializable {
	
	@Inject
	private QueryDataManager queryDataManager;
	
	@Inject
	private QueryPageManager queryPageManager;
	
	@Inject
	private QueryEventManager queryEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Query";
	}
	
	@Override
	public String getUrlContext() {
		return queryPageManager.getQueryWizardPage();
	}
	
	@Override
	public void initialize(Query query) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(queryPageManager.getSections());
		super.initialize(query);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		queryPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		queryPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		queryPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		queryPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Query query = getInstance();
		queryDataManager.saveQuery(query);
		queryEventManager.fireSavedEvent(query);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Query query = getInstance();
		//TODO take this out soon
		if (query == null)
			query = new Query();
		queryEventManager.fireCancelledEvent(query);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Query query = selectionContext.getSelection("query");
		String name = query.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("queryWizard");
			display.error("Query name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
