package nam.model.variable;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Variable;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("variableEventManager")
public class VariableEventManager extends AbstractEventManager<Variable> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Variable getInstance() {
		return selectionContext.getSelection("variable");
	}
	
	public void removeVariable() {
		Variable variable = getInstance();
		fireRemoveEvent(variable);
	}
	
}
