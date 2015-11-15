package nam.model.attribute;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Attribute;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("attributeEventManager")
public class AttributeEventManager extends AbstractEventManager<Attribute> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Attribute getInstance() {
		return selectionContext.getSelection("attribute");
	}
	
	public void removeAttribute() {
		Attribute attribute = getInstance();
		fireRemoveEvent(attribute);
	}
	
}
