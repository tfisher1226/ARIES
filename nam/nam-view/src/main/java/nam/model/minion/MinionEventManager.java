package nam.model.minion;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Minion;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("minionEventManager")
public class MinionEventManager extends AbstractEventManager<Minion> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Minion getInstance() {
		return selectionContext.getSelection("minion");
	}
	
	public void removeMinion() {
		Minion minion = getInstance();
		fireRemoveEvent(minion);
	}
	
}
