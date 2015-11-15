package nam.model.provider;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Provider;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("providerEventManager")
public class ProviderEventManager extends AbstractEventManager<Provider> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Provider getInstance() {
		return selectionContext.getSelection("provider");
	}
	
	public void removeProvider() {
		Provider provider = getInstance();
		fireRemoveEvent(provider);
	}
	
}
