package nam.model.transacted;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Transacted;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("transactedEventManager")
public class TransactedEventManager extends AbstractEventManager<Transacted> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Transacted getInstance() {
		return selectionContext.getSelection("transacted");
	}
	
	public void removeTransacted() {
		Transacted transacted = getInstance();
		fireRemoveEvent(transacted);
	}
	
}
