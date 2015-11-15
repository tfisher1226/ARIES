package nam.model.messagingProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.MessagingProvider;
import nam.model.Project;
import nam.model.util.MessagingProviderUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingProviderWizard")
@SuppressWarnings("serial")
public class MessagingProviderWizard extends AbstractDomainElementWizard<MessagingProvider> implements Serializable {
	
	@Inject
	private MessagingProviderDataManager messagingProviderDataManager;
	
	@Inject
	private MessagingProviderPageManager messagingProviderPageManager;
	
	@Inject
	private MessagingProviderEventManager messagingProviderEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "MessagingProvider";
	}
	
	@Override
	public String getUrlContext() {
		return messagingProviderPageManager.getMessagingProviderWizardPage();
	}
	
	@Override
	public void initialize(MessagingProvider messagingProvider) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(messagingProviderPageManager.getSections());
		super.initialize(messagingProvider);
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
		messagingProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		messagingProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		messagingProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		messagingProviderPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		MessagingProvider messagingProvider = getInstance();
		messagingProviderDataManager.saveMessagingProvider(messagingProvider);
		messagingProviderEventManager.fireSavedEvent(messagingProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		MessagingProvider messagingProvider = getInstance();
		//TODO take this out soon
		if (messagingProvider == null)
			messagingProvider = new MessagingProvider();
		messagingProviderEventManager.fireCancelledEvent(messagingProvider);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		MessagingProvider messagingProvider = selectionContext.getSelection("messagingProvider");
		String name = messagingProvider.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("messagingProviderWizard");
			display.error("MessagingProvider name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
