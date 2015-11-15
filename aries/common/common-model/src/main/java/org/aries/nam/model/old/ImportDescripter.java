package org.aries.nam.model.old;

import java.io.Serializable;


public interface ImportDescripter extends Serializable {

	public String getDomain();

	public String getName();

	public String getVersion();

}
