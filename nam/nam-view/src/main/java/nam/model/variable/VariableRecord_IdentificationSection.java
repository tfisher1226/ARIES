package nam.model.variable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Variable;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("variableIdentificationSection")
public class VariableRecord_IdentificationSection extends AbstractWizardPage<Variable> {

	private Variable variable;


	public VariableRecord_IdentificationSection() {
		//setTitle("Specify Variable information.");
		setName("Identification");
		setUrl("identification");
		//setOwner(owner);
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	
	public void initialize(Variable variable) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setVariable(variable);
	}
	
	public void validate() {
		if (variable == null) {
			validator.missing("Variable");
		} else {
			if (StringUtils.isEmpty(variable.getType()))
				validator.missing("Type");
			if (StringUtils.isEmpty(variable.getName()))
				validator.missing("Name");
		}
	}
	
}
