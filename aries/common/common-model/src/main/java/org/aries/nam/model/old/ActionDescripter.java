package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.List;


public interface ActionDescripter extends Serializable {

	public String getActionName();

	public String getClassName();

	public String getDescription();

	public List<PropertyDescripter> getPropertyDescripters();

	public List<ParameterDescripter> getParameterDescripters();

	public ResultDescripter getResultDescripter();
	
}
