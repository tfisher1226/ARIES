package nam.model.variable;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Variable;
import nam.model.util.VariableUtil;


@SessionScoped
@Named("variableHelper")
public class VariableHelper extends AbstractElementHelper<Variable> implements Serializable {
	
	@Override
	public boolean isEmpty(Variable variable) {
		return VariableUtil.isEmpty(variable);
	}
	
	@Override
	public String toString(Variable variable) {
		return VariableUtil.toString(variable);
	}
	
	@Override
	public String toString(Collection<Variable> variableList) {
		return VariableUtil.toString(variableList);
	}
	
	@Override
	public boolean validate(Variable variable) {
		return VariableUtil.validate(variable);
	}
	
	@Override
	public boolean validate(Collection<Variable> variableList) {
		return VariableUtil.validate(variableList);
	}
	
}
