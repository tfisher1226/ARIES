package nam.model.domain;

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

import nam.model.Domain;
import nam.model.Project;
import nam.model.util.DomainUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("domainInfoManager")
public class DomainInfoManager extends AbstractNamRecordManager<Domain> implements Serializable {
	
	@Inject
	private DomainWizard domainWizard;
	
	@Inject
	private DomainDataManager domainDataManager;
	
	@Inject
	private DomainPageManager domainPageManager;
	
	@Inject
	private DomainEventManager domainEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private DomainHelper domainHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public DomainInfoManager() {
		setInstanceName("domain");
	}
	
	
	public Domain getDomain() {
		return getRecord();
	}
	
	public Domain getSelectedDomain() {
		return selectionContext.getSelection("domain");
	}
	
	@Override
	public Class<Domain> getRecordClass() {
		return Domain.class;
	}
	
	@Override
	public boolean isEmpty(Domain domain) {
		return domainHelper.isEmpty(domain);
	}
	
	@Override
	public String toString(Domain domain) {
		return domainHelper.toString(domain);
	}
	
	@Override
	public void initialize() {
		Domain domain = selectionContext.getSelection("domain");
		if (domain != null)
			initialize(domain);
	}
	
	protected void initialize(Domain domain) {
		DomainUtil.initialize(domain);
		domainWizard.initialize(domain);
		setContext("domain", domain);
	}
	
	public void handleDomainSelected(@Observes @Selected Domain domain) {
		selectionContext.setSelection("domain",  domain);
		domainPageManager.updateState(domain);
		domainPageManager.refreshMembers();
		setRecord(domain);
	}
	
	@Override
	public String newRecord() {
		return newDomain();
	}
	
	public String newDomain() {
		try {
			Domain domain = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("domain",  domain);
			String url = domainPageManager.initializeDomainCreationPage(domain);
			domainPageManager.pushContext(domainWizard);
			initialize(domain);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Domain create() {
		Domain domain = DomainUtil.create();
		return domain;
	}
	
	@Override
	public Domain clone(Domain domain) {
		domain = DomainUtil.clone(domain);
		return domain;
	}
	
	@Override
	public String viewRecord() {
		return viewDomain();
	}
	
	public String viewDomain() {
		Domain domain = selectionContext.getSelection("domain");
		String url = viewDomain(domain);
		return url;
	}
	
	public String viewDomain(Domain domain) {
		try {
			String url = domainPageManager.initializeDomainSummaryView(domain);
			domainPageManager.pushContext(domainWizard);
			initialize(domain);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editDomain();
	}
	
	public String editDomain() {
		Domain domain = selectionContext.getSelection("domain");
		String url = editDomain(domain);
		return url;
	}
	
	public String editDomain(Domain domain) {
		try {
			//domain = clone(domain);
			selectionContext.resetOrigin();
			selectionContext.setSelection("domain",  domain);
			String url = domainPageManager.initializeDomainUpdatePage(domain);
			domainPageManager.pushContext(domainWizard);
			initialize(domain);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveDomain() {
		Domain domain = getDomain();
		if (validateDomain(domain)) {
			if (isImmediate())
				persistDomain(domain);
			outject("domain", domain);
		}
	}
	
	public void persistDomain(Domain domain) {
		saveDomain(domain);
	}
	
	public void saveDomain(Domain domain) {
		try {
			saveDomainToSystem(domain);
			domainEventManager.fireAddedEvent(domain);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveDomainToSystem(Domain domain) {
		domainDataManager.saveDomain(domain);
	}
	
	public void handleSaveDomain(@Observes @Add Domain domain) {
		saveDomain(domain);
	}
	
	public void addDomain(Domain domain) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichDomain(Domain domain) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Domain domain) {
		return validateDomain(domain);
	}
	
	public boolean validateDomain(Domain domain) {
		Validator validator = getValidator();
		boolean isValid = DomainUtil.validate(domain);
		Display display = getFromSession("display");
		display.setModule("domainInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveDomain() {
		display = getFromSession("display");
		display.setModule("domainInfo");
		Domain domain = selectionContext.getSelection("domain");
		if (domain == null) {
			display.error("Domain record must be selected.");
		}
	}
	
	public String handleRemoveDomain(@Observes @Remove Domain domain) {
		display = getFromSession("display");
		display.setModule("domainInfo");
		try {
			display.info("Removing Domain "+DomainUtil.getLabel(domain)+" from the system.");
			removeDomainFromSystem(domain);
			selectionContext.clearSelection("domain");
			domainEventManager.fireClearSelectionEvent();
			domainEventManager.fireRemovedEvent(domain);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeDomainFromSystem(Domain domain) {
		if (domainDataManager.removeDomain(domain))
			setRecord(null);
	}
	
	public void cancelDomain() {
		BeanContext.removeFromSession("domain");
		domainPageManager.removeContext(domainWizard);
	}
	
}
