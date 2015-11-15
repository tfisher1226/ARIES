package nam.model.parameter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


@SessionScoped
@Named("parameterDocumentationSection")
public class ParameterRecord_DocumentationSection extends AbstractWizardPage<Parameter> implements Serializable {
	
	private Parameter parameter;
	
	
	public ParameterRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Parameter getParameter() {
		return parameter;
	}
	
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public void initialize(Parameter parameter) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setParameter(parameter);
	}
	
	@Override
	public void validate() {
		if (parameter == null) {
			validator.missing("Parameter");
		} else {
		}
	}
	
}
