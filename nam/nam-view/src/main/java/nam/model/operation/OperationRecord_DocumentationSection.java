package nam.model.operation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Operation;
import nam.model.util.OperationUtil;


@SessionScoped
@Named("operationDocumentationSection")
public class OperationRecord_DocumentationSection extends AbstractWizardPage<Operation> implements Serializable {
	
	private Operation operation;

	
	public OperationRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}

	
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Override
	public void initialize(Operation operation) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setOperation(operation);
	}
	
	@Override
	public void validate() {
		if (operation == null) {
			validator.missing("Operation");
		} else {
		}
	}

}
