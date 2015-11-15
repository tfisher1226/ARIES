package nam.model.parameter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


@SessionScoped
@Named("parameterIdentificationSection")
public class ParameterRecord_IdentificationSection extends AbstractWizardPage<Parameter> implements Serializable {
	
	private Parameter parameter;
	
	
	public ParameterRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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
