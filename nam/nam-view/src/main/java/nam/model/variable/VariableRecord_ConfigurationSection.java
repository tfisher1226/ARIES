package nam.model.variable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Variable;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("variableConfigurationSection")
public class VariableRecord_ConfigurationSection extends AbstractWizardPage<Variable> {

	private Variable variable;

	
	public VariableRecord_ConfigurationSection() {
		//setTitle("Specify desired configuration.");
		setName("Configuration");
		setUrl("configuration");
		//setOwner(owner);
	}
	
	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public void initialize(Variable variable) {
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setVariable(variable);
	}
	
	public void validate() {
		if (variable == null) {
			validator.missing("Variable");
		} else {
//			if (variable.getConfiguration() == null)
//				validator.missing("Configuration");
		}
	}
	
}
