package aries.generation.model;

import java.util.ArrayList;
import java.util.List;


public class ModelLiteral {

	private String name;
	
	private int value;

	private String text;
	
	private List<ModelAnnotation> annotations;

	
	public ModelLiteral() {
		annotations = new ArrayList<ModelAnnotation>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public List<ModelAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<ModelAnnotation> annotations) {
		this.annotations = annotations;
	}

	public void addAnnotation(ModelAnnotation annotation) {
		annotations.add(annotation);;
	}
	
	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelLiteral other = (ModelLiteral) object;
		if (this.getName() == null || other.getName() == null)
			return this == other;
		if (!this.getName().equals(other.getName()))
			return false;
		return true;
	}

	public int hashCode() {
		if (getName() != null)
			return getName().hashCode();
		return 0;
	}
	
}