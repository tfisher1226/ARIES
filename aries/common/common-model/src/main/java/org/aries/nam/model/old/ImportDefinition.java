package org.aries.nam.model.old;


public class ImportDefinition implements ImportDescripter {

	private static final long serialVersionUID = 1L;

	public String domain;

	public String name;
	
	public String version;

	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
