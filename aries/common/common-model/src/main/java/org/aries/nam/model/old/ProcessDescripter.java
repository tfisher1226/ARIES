package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.List;


public interface ProcessDescripter extends Serializable {

	public String getProcessId();

	public String getProcessName();

	public String getResultName();

	public String getDescription();

	public boolean isTransacted();

	public List<ParameterDescripter> getParameterDescripters();

	public List<ActionDescripter> getActionDescripters();

	public void addActionDescripter(ActionDescripter actiondescripter);

	public List<PropertyDescripter> getPropertyDescripters();

	public void addPropertyDescripter(PropertyDescripter propertydescripter);

	public ResultDescripter getResultDescripter();

}
