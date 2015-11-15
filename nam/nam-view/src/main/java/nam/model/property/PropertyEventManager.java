package nam.model.property;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Property;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("propertyEventManager")
public class PropertyEventManager extends AbstractEventManager<Property> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Property getInstance() {
		return selectionContext.getSelection("property");
	}
	
	public void removeProperty() {
		Property property = getInstance();
		fireRemoveEvent(property);
	}
	
}
