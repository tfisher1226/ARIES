package nam.ui.design;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Service;
import nam.model.application.ApplicationPageManager;
import nam.model.element.ElementPageManager;
import nam.model.module.ModulePageManager;
import nam.model.project.ProjectPageManager;
import nam.model.service.ServicePageManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractPageManager;


@SessionScoped
@Named("pageManager")
public class PageManager extends AbstractPageManager<Service> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	@Inject
	private ApplicationPageManager applicationPageManager;

	@Inject
	private ModulePageManager modulePageManager;

	@Inject
	private ServicePageManager servicePageManager;

	@Inject
	private ElementPageManager elementPageManager;

	
	public PageManager() {
	}
	
	public String getMainPage() {
		return "/main.xhtml";
	}
	
	public String initializeMainPage() {
		setPageTitle("Overview");
		setPageIcon("/icons/nam/Overview16.gif");
		String pageLevelKey = "main";
		clearBreadcrumbs(pageLevelKey);
		addBreadcrumb(pageLevelKey, "Top", "showMainPage()");
		addBreadcrumb(pageLevelKey, "Services", "showServiceManagementPage()");
		String url = getMainPage();
		selectionContext.setCurrentArea("projectList");
		selectionContext.setSelectedArea(pageLevelKey);
		selectionContext.setMessageDomain(pageLevelKey);
		selectionContext.resetOrigin();
		selectionContext.setUrl(url);
		refresh("projectList");
		//sections.clear();
		return url;
	}
	
	public void refresh(String scope) {
		//ProjectPageManager projectPageManager = BeanContext.getFromSession("projectPageManager");
		//projectPageManager.refreshLocal(scope);
	}

}
