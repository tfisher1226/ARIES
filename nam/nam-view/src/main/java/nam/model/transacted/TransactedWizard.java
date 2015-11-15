package nam.model.transacted;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Project;
import nam.model.Transacted;
import nam.model.TransactionScope;
import nam.model.TransactionUsage;
import nam.model.util.TransactedUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transactedWizard")
@SuppressWarnings("serial")
public class TransactedWizard extends AbstractDomainElementWizard<Transacted> implements Serializable {
	
	@Inject
	private TransactedDataManager transactedDataManager;
	
	@Inject
	private TransactedPageManager transactedPageManager;
	
	@Inject
	private TransactedEventManager transactedEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Transacted";
	}
	
	@Override
	public String getUrlContext() {
		return transactedPageManager.getTransactedWizardPage();
	}
	
	@Override
	public void initialize(Transacted transacted) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(transactedPageManager.getSections());
		super.initialize(transacted);
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
		transactedPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		transactedPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		transactedPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		transactedPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Transacted transacted = getInstance();
		transactedDataManager.saveTransacted(transacted);
		transactedEventManager.fireSavedEvent(transacted);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Transacted transacted = getInstance();
		//TODO take this out soon
		if (transacted == null)
			transacted = new Transacted();
		transactedEventManager.fireCancelledEvent(transacted);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Transacted transacted = selectionContext.getSelection("transacted");
		TransactionScope scope = transacted.getScope();
		TransactionUsage use = transacted.getUse();
		
		Project project = selectionContext.getSelection("project");
		
		return getUrl();
	}
	
}
