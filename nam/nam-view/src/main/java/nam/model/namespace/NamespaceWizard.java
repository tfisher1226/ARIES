package nam.model.namespace;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.NamespaceUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("namespaceWizard")
@SuppressWarnings("serial")
public class NamespaceWizard extends AbstractDomainElementWizard<Namespace> implements Serializable {

	@Inject
	private NamespaceDataManager namespaceDataManager;
	
	@Inject
	private NamespacePageManager namespacePageManager;

	@Inject
	private NamespaceEventManager namespaceEventManager;

	@Inject
	private SelectionContext selectionContext;


	@Override
	public String getName() {
		return "Namespace";
	}

	@Override
	public String getUrlContext() {
		return namespacePageManager.getNamespaceWizardPage();
	}
	
	@Override
	public void initialize(Namespace namespace) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(namespacePageManager.getSections());
		super.initialize(namespace);
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
		namespacePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		namespacePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		namespacePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		namespacePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Namespace namespace = getInstance();
		namespaceDataManager.saveNamespace(namespace);
		namespaceEventManager.fireSavedEvent(namespace);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Namespace namespace = getInstance();
		//TODO take this out soon
		if (namespace == null)
			namespace = new Namespace();
		namespaceEventManager.fireCancelledEvent(namespace);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Namespace namespace = selectionContext.getSelection("namespace");
		String name = namespace.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("namespaceWizard");
			display.error("Namespace name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
