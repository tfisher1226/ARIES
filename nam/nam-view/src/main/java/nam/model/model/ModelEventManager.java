package nam.model.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Model;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("modelEventManager")
public class ModelEventManager extends AbstractEventManager<Model> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Model getInstance() {
		return selectionContext.getSelection("model");
	}
	
	public void removeModel() {
		Model model = getInstance();
		fireRemoveEvent(model);
	}
	
}
