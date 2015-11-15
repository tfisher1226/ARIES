package nam.model.messaging;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Messaging;
import nam.model.Project;
import nam.model.util.MessagingUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingWizard")
@SuppressWarnings("serial")
public class MessagingWizard extends AbstractDomainElementWizard<Messaging> implements Serializable {
	
	@Inject
	private MessagingDataManager messagingDataManager;
	
	@Inject
	private MessagingPageManager messagingPageManager;
	
	@Inject
	private MessagingEventManager messagingEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Messaging";
	}
	
	@Override
	public String getUrlContext() {
		return messagingPageManager.getMessagingWizardPage();
	}
	
	@Override
	public void initialize(Messaging messaging) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(messagingPageManager.getSections());
		super.initialize(messaging);
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
		messagingPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		messagingPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		messagingPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		messagingPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Messaging messaging = getInstance();
		messagingDataManager.saveMessaging(messaging);
		messagingEventManager.fireSavedEvent(messaging);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Messaging messaging = getInstance();
		//TODO take this out soon
		if (messaging == null)
			messaging = new Messaging();
		messagingEventManager.fireCancelledEvent(messaging);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Messaging messaging = selectionContext.getSelection("messaging");
		String name = messaging.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("messagingWizard");
			display.error("Messaging name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
