package org.aries.nam.model.old;

import java.util.ArrayList;
import java.util.List;


public class NamespaceDefinition implements NamespaceDescripter {

	private static final long serialVersionUID = 1L;
	
	private String uri;
	
	private String prefix;

	private String filename;
	
	private List<String> types = new ArrayList<String>();

	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

}
