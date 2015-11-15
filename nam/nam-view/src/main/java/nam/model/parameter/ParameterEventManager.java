package nam.model.parameter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Parameter;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("parameterEventManager")
public class ParameterEventManager extends AbstractEventManager<Parameter> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Parameter getInstance() {
		return selectionContext.getSelection("parameter");
	}
	
	public void removeParameter() {
		Parameter parameter = getInstance();
		fireRemoveEvent(parameter);
	}
	
}
