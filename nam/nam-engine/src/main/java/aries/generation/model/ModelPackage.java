package aries.generation.model;

import java.util.ArrayList;
import java.util.List;


public class ModelPackage {

	protected String name;
	
	protected List<ModelPackage> imports;

	protected List<ModelClass> classes;

	protected List<ModelEnum> enums;

	protected List<ModelAnnotation> annotations;

	
	public ModelPackage() {
		imports = new ArrayList<ModelPackage>();
		classes = new ArrayList<ModelClass>();
		enums = new ArrayList<ModelEnum>();
		annotations = new ArrayList<ModelAnnotation>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ModelPackage> getImports() {
		return imports;
	}

	public void setImports(List<ModelPackage> imports) {
		this.imports = imports;
	}

	public List<ModelClass> getClasses() {
		return classes;
	}

	public void setClasses(List<ModelClass> classes) {
		this.classes = classes;
	}

	public List<ModelEnum> getEnums() {
		return enums;
	}

	public void setEnums(List<ModelEnum> enums) {
		this.enums = enums;
	}

	public List<ModelAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<ModelAnnotation> annotations) {
		this.annotations = annotations;
	}
	
}
