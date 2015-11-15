package nam.model.dependency;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Dependency;
import nam.model.Project;
import nam.model.util.DependencyUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("dependencyWizard")
@SuppressWarnings("serial")
public class DependencyWizard extends AbstractDomainElementWizard<Dependency> implements Serializable {
	
	@Inject
	private DependencyDataManager dependencyDataManager;
	
	@Inject
	private DependencyPageManager dependencyPageManager;
	
	@Inject
	private DependencyEventManager dependencyEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Dependency";
	}
	
	@Override
	public String getUrlContext() {
		return dependencyPageManager.getDependencyWizardPage();
	}
	
	@Override
	public void initialize(Dependency dependency) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(dependencyPageManager.getSections());
		super.initialize(dependency);
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
		dependencyPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		dependencyPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		dependencyPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		dependencyPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Dependency dependency = getInstance();
		dependencyDataManager.saveDependency(dependency);
		dependencyEventManager.fireSavedEvent(dependency);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Dependency dependency = getInstance();
		//TODO take this out soon
		if (dependency == null)
			dependency = new Dependency();
		dependencyEventManager.fireCancelledEvent(dependency);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Dependency dependency = selectionContext.getSelection("dependency");
		String name = dependency.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("dependencyWizard");
			display.error("Dependency name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
