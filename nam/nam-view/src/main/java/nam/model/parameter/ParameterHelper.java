package nam.model.parameter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Parameter;
import nam.model.util.ParameterUtil;


@SessionScoped
@Named("parameterHelper")
public class ParameterHelper extends AbstractElementHelper<Parameter> implements Serializable {
	
	@Override
	public boolean isEmpty(Parameter parameter) {
		return ParameterUtil.isEmpty(parameter);
	}
	
	@Override
	public String toString(Parameter parameter) {
		return ParameterUtil.toString(parameter);
	}
	
	@Override
	public String toString(Collection<Parameter> parameterList) {
		return ParameterUtil.toString(parameterList);
	}
	
	@Override
	public boolean validate(Parameter parameter) {
		return ParameterUtil.validate(parameter);
	}
	
	@Override
	public boolean validate(Collection<Parameter> parameterList) {
		return ParameterUtil.validate(parameterList);
	}
	
}
