package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.List;


public interface OperationDescripter extends Serializable {

	public String getName();

	public String getDescription();

	public boolean isTransacted();

	public List<ParameterDescripter> getParameterDescripters();

	public List<ResultDescripter> getResultDescripters();

	public List<PropertyDescripter> getPropertyDescripters();

}
