package nam.model.adapter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Adapter;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("adapterEventManager")
public class AdapterEventManager extends AbstractEventManager<Adapter> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Adapter getInstance() {
		return selectionContext.getSelection("adapter");
	}
	
	public void removeAdapter() {
		Adapter adapter = getInstance();
		fireRemoveEvent(adapter);
	}
	
}
