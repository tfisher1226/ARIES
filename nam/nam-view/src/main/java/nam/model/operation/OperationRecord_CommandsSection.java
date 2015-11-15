package nam.model.operation;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Operation;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("operationCommandsSection")
public class OperationRecord_CommandsSection extends AbstractWizardPage<Operation> {

	private Operation operation;

	
	public OperationRecord_CommandsSection() {
		//setTitle("Specify operation information.");
		setName("Commands");
		setUrl("commands");
		//setOwner(owner);
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public void initialize(Operation operation) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setOperation(operation);
	}
	
	public void validate() {
		if (operation == null) {
			validator.missing("Operation");
		} else {
			
		}
	}

}
