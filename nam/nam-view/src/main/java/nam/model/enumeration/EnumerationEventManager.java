package nam.model.enumeration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Enumeration;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("enumerationEventManager")
public class EnumerationEventManager extends AbstractEventManager<Enumeration> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Enumeration getInstance() {
		return selectionContext.getSelection("enumeration");
	}
	
	public void removeEnumeration() {
		Enumeration enumeration = getInstance();
		fireRemoveEvent(enumeration);
	}
	
}
