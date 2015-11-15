package nam.model.domain;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Domain;
import nam.model.Project;
import nam.model.util.DomainUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("domainWizard")
@SuppressWarnings("serial")
public class DomainWizard extends AbstractDomainElementWizard<Domain> implements Serializable {
	
	@Inject
	private DomainDataManager domainDataManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private DomainEventManager domainEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Domain";
	}
	
	@Override
	public String getUrlContext() {
		return domainPageManager.getDomainWizardPage();
	}
	
	@Override
	public void initialize(Domain domain) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(domainPageManager.getSections());
		super.initialize(domain);
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
		domainPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		domainPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		domainPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		domainPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Domain domain = getInstance();
		domainDataManager.saveDomain(domain);
		domainEventManager.fireSavedEvent(domain);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Domain domain = getInstance();
		//TODO take this out soon
		if (domain == null)
			domain = new Domain();
		domainEventManager.fireCancelledEvent(domain);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Domain domain = selectionContext.getSelection("domain");
		String name = domain.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("domainWizard");
			display.error("Domain name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
