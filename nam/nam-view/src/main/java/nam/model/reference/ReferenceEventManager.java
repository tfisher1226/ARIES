package nam.model.reference;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Reference;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("referenceEventManager")
public class ReferenceEventManager extends AbstractEventManager<Reference> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Reference getInstance() {
		return selectionContext.getSelection("reference");
	}
	
	public void removeReference() {
		Reference reference = getInstance();
		fireRemoveEvent(reference);
	}
	
}
