package org.aries.ui.tree;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ModelTreeObject<T> implements Serializable {

	private String label;

	private String type;

	private T object;

	
	public ModelTreeObject(String label) {
		this(null, label, "TEMP"); 
	}
	
	public ModelTreeObject(T object, String label) {
		this(object, label, object.getClass().getSimpleName()); 
	}

	public ModelTreeObject(T object, String label, String type) {
		this.object = object; 
		this.label = label; 
		this.type = type;
	}

	public String getLabel() {
		if (label != null)
			return label;
		return getType();
	}

	public T getObject() {
		return object;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type= type;
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ModelTreeObject<T> other = (ModelTreeObject) object;
		if (!other.type.equals(this.type))
			return false;
		if (other.object == null && this.object == null)
			return true;
		if (other.object.equals(this.object))
			return true;
		return false;
	}

}
