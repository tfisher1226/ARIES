package nam.model.variable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Variable;

import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("variableDocumentationSection")
public class VariableRecord_DocumentationSection extends AbstractWizardPage<Variable> {
	
	private Variable variable;

	
	public VariableRecord_DocumentationSection() {
		//setTitle("Specify variable information.");
		setName("Documentation");
		setUrl("documentation");
		//setOwner(owner);
	}

	public Variable getvariable() {
		return variable;
	}

	public void setvariable(Variable variable) {
		this.variable = variable;
	}

	public void initialize(Variable variable) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(true);
		setvariable(variable);
	}
	
	public void validate() {
		if (variable == null) {
			validator.missing("Variable");
		} else {
//			if (StringUtils.isEmpty(variable.getDe()))
//				validator.missing("Name");
		}
	}

}
