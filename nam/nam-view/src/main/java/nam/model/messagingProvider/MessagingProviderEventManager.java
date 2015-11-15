package nam.model.messagingProvider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.MessagingProvider;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingProviderEventManager")
public class MessagingProviderEventManager extends AbstractEventManager<MessagingProvider> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public MessagingProvider getInstance() {
		return selectionContext.getSelection("messagingProvider");
	}
	
	public void removeMessagingProvider() {
		MessagingProvider messagingProvider = getInstance();
		fireRemoveEvent(messagingProvider);
	}
	
}
