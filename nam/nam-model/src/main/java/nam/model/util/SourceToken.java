package nam.model.util;

import java.io.Serializable;


@SuppressWarnings("serial")
public class SourceToken implements Serializable {

	private Long id;

	private String token;

	private String value;

	private Boolean enabled;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		SourceToken other = (SourceToken) object;
		if (this.getId() == null ||  other.getId() == null)
			return false;
		if (this.getId() != other.getId())
			return false;
		return true;
	}

	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}
	
}
