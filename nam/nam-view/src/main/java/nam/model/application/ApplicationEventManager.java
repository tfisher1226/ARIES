package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("applicationEventManager")
public class ApplicationEventManager extends AbstractEventManager<Application> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Application getInstance() {
		return selectionContext.getSelection("application");
	}
	
	public void removeApplication() {
		Application application = getInstance();
		fireRemoveEvent(application);
	}
	
}
