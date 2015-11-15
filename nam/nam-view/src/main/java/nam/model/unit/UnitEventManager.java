package nam.model.unit;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Unit;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("unitEventManager")
public class UnitEventManager extends AbstractEventManager<Unit> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Unit getInstance() {
		return selectionContext.getSelection("unit");
	}
	
	public void removeUnit() {
		Unit unit = getInstance();
		fireRemoveEvent(unit);
	}
	
}
