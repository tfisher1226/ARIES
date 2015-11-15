package nam.model.operation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Operation;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("operationIdentificationSection")
public class OperationRecord_IdentificationSection extends AbstractWizardPage<Operation> implements Serializable {
	
	private Operation operation;

	
	public OperationRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setOperation(operation);
	}
	
	@Override
	public void validate() {
		if (operation == null) {
			validator.missing("Operation");
		} else {
			if (StringUtils.isEmpty(operation.getName()))
				validator.missing("Name");
		}
	}

}
