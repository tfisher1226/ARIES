package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.Map;

public interface ProviderDescripter extends Serializable {

	public String getType();

	public String getName();

	public String getConnectionUrl();

	public String getContextFactory();

	public String getSecurityPrinciple();

	public String getSecurityCredentials();

	public Map<String, String> getProperties();

}
