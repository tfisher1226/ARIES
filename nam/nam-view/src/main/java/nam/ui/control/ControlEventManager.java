package nam.ui.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Control;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("controlEventManager")
public class ControlEventManager extends AbstractEventManager<Control> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Control getInstance() {
		return selectionContext.getSelection("control");
	}
	
	public void removeControl() {
		Control control = getInstance();
		fireRemoveEvent(control);
	}
	
}
