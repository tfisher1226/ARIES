package aries.generation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class ModelParameter implements Comparable<ModelParameter> {

	private String name;

	private String refName;

	private String construct;
	
	private String className;

	private String packageName;

	private String keyClassName;

	private String keyPackageName;

	private boolean isFinal;

	private List<ModelAnnotation> annotations;

	
	public ModelParameter() {
		construct = "item";
		annotations = new ArrayList<ModelAnnotation>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getConstruct() {
		return construct;
	}

	public void setConstruct(String construct) {
		if (construct != null)
			this.construct = construct;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getKeyClassName() {
		return keyClassName;
	}

	public void setKeyClassName(String keyClassName) {
		this.keyClassName = keyClassName;
	}

	public String getKeyPackageName() {
		return keyPackageName;
	}

	public void setKeyPackageName(String keyPackageName) {
		this.keyPackageName = keyPackageName;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(ModelParameter other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(packageName, other.packageName);
		if (status != 0)
			return status;
		status = compare(className, other.className);
		if (status != 0)
			return status;
		status = compare(construct, other.construct);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelParameter other = (ModelParameter) object;
		if (!this.getName().equals(other.getName()))
			return false;
		String thisPackageName = this.getPackageName();
		String otherPackageName = other.getPackageName();
		if (thisPackageName != null && otherPackageName != null) {
			if (!thisPackageName.equals(otherPackageName))
				return false;
		}
		if (!this.getClassName().equals(other.getClassName()))
			return false;
		if (!this.getConstruct().equals(other.getConstruct()))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		if (construct.equals("list")) {
			return "List<"+className+">";
		} else if (construct.equals("set")) {
			return "Set<"+className+">";
		} else if (construct.equals("map")) {
			return "Map<"+keyClassName+", "+className+">";
		}
		return className;
	}
	
}