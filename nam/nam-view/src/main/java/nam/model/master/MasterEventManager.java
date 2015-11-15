package nam.model.master;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Master;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("masterEventManager")
public class MasterEventManager extends AbstractEventManager<Master> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Master getInstance() {
		return selectionContext.getSelection("master");
	}
	
	public void removeMaster() {
		Master master = getInstance();
		fireRemoveEvent(master);
	}
	
}
