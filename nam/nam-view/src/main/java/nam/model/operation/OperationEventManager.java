package nam.model.operation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Operation;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("operationEventManager")
public class OperationEventManager extends AbstractEventManager<Operation> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Operation getInstance() {
		return selectionContext.getSelection("operation");
	}
	
	public void removeOperation() {
		Operation operation = getInstance();
		fireRemoveEvent(operation);
	}
	
}
