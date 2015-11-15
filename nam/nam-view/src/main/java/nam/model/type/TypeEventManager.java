package nam.model.type;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Type;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("typeEventManager")
public class TypeEventManager extends AbstractEventManager<Type> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Type getInstance() {
		return selectionContext.getSelection("type");
	}
	
	public void removeType() {
		Type type = getInstance();
		fireRemoveEvent(type);
	}
	
}
