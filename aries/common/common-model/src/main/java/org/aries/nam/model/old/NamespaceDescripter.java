package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.List;


public interface NamespaceDescripter extends Serializable {

	public String getUri();

	public String getPrefix();

	public String getFilename();

	public List<String> getTypes();
	
}
