package nam.model.variable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Variable;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("variableOverviewSection")
public class VariableRecord_OverviewSection extends AbstractWizardPage<Variable> {

	private Variable variable;


	public VariableRecord_OverviewSection() {
		//setTitle("Specify Variable information.");
		setName("Overview");
		setUrl("overview");
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
	
}
