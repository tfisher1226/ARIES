package org.aries.ui.table;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Column implements Serializable {

	private String label;

	private Boolean enabled;

	private Boolean visible;


	public Column(String label, Boolean enabled, Boolean visible) {
		this.label = label;
		this.enabled = enabled;
		this.visible = visible;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Column other = (Column) object;
		if (this.getLabel() == null || other.getLabel() == null)
			return this == other;
		if (!this.getLabel().equals(other.getLabel()))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (getLabel() != null)
			return getLabel().hashCode();
		return 0;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}

}
