package nam.model.element;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Element;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("elementEventManager")
public class ElementEventManager extends AbstractEventManager<Element> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Element getInstance() {
		return selectionContext.getSelection("element");
	}
	
	public void removeElement() {
		Element element = getInstance();
		fireRemoveEvent(element);
	}
	
}
