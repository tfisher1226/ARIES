package nam.model.messaging;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Messaging;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("messagingEventManager")
public class MessagingEventManager extends AbstractEventManager<Messaging> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Messaging getInstance() {
		return selectionContext.getSelection("messaging");
	}
	
	public void removeMessaging() {
		Messaging messaging = getInstance();
		fireRemoveEvent(messaging);
	}
	
}
