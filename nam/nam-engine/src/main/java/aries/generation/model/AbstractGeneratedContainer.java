package aries.generation.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nam.model.Command;
import nam.model.Parameter;
import nam.model.Type;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;


public abstract class AbstractGeneratedContainer extends AbstractGeneratedObject {

	protected Map<String, String> importedClasses = new LinkedHashMap<String, String>();
	
	protected Map<String, String> staticImportedClasses = new LinkedHashMap<String, String>();
	

	public Collection<String> getImportedClasses() {
		return getSortedClasses(importedClasses);
	}
	
	public Collection<String> getStaticImportedClasses() {
		return getSortedClasses(staticImportedClasses);
	}

	public Collection<String> getSortedClasses(Map<String, String> classes) {
		List<String> sortedClasses = NameUtil.sortPackageNames(classes.keySet());
		return sortedClasses;
	}

	public void addImportedClass(String type) {
		if (type.startsWith("null"))
			throw new RuntimeException("CODE PROBLEM");
//		if (type.equals("bookshop2.map.BookOrdersBookMapper"))
//			System.out.println();
		addClass(importedClasses, type);
	}

	public void addStaticImportedClass(String type) {
		addClass(staticImportedClasses, type);
	}

	public void addClass(Map<String, String> classes, String type) {
		if (type != null) {
			int indexOf = type.indexOf("<");
			if (indexOf != -1)
				type = type.substring(0, indexOf);
			if (TypeUtil.isImportedClassRequired(type)) {
				classes.put(type, type);
			}
		}
	}

	public void addImportedClass(Type type) {
		addClass(importedClasses, type);
	}
	
	public void addStaticImportedClass(Type type) {
		addClass(staticImportedClasses, type);
	}
	
	public void addClass(Map<String, String> classes, Type type) {
		if (type != null) {
			String className = TypeUtil.getClassName(type.getType());
			String packageName = TypeUtil.getPackageName(type.getType());
			String typeName = packageName + "." + className;
			addClass(classes, typeName);
		}
	}

	public void addImportedClasses(Collection<String> newClasses) {
		addClasses(importedClasses, newClasses);
	}
	
	public void addStaticImportedClasses(Collection<String> newClasses) {
		addClasses(staticImportedClasses, newClasses);
	}
	
	public void addClasses(Map<String, String> classes, Collection<String> newClasses) {
		Iterator<String> iterator = newClasses.iterator();
		while (iterator.hasNext()) {
			String newClass = iterator.next();
			addClass(classes, newClass);
		}
	}

	public void addImportedClass(ModelField modelField) {
		addClass(importedClasses, modelField);
	}
	
	public void addStaticImportedClass(ModelField modelField) {
		addClass(staticImportedClasses, modelField);
	}
	
	public void addClass(Map<String, String> classes, ModelField modelField) {
		String type = modelField.getPackageName()+"."+modelField.getClassName();
		if (TypeUtil.isImportedClassRequired(type))
			addClass(classes, type);
	}

	public void addImportedClass(ModelParameter modelParameter) {
		addClass(importedClasses, modelParameter);
		addImportedClassForConstruct(modelParameter);
	}
	
	public void addStaticImportedClass(ModelParameter modelParameter) {
		addClass(staticImportedClasses, modelParameter);
	}
	
	public void addImportedClassForConstruct(ModelParameter modelParameter) {
		addImportedClassForConstruct(modelParameter.getConstruct());
	}

	public void addImportedClassForConstruct(String construct) {
		if (construct.equals("list")) {
			addImportedClass("java.util.List");
		} else if (construct.equals("set")) {
			addImportedClass("java.util.Set");
		} else if (construct.equals("map")) {
			addImportedClass("java.util.Map");
		}
	}
	
	public void addClass(Map<String, String> classes, ModelParameter modelParameter) {
		String type = modelParameter.getPackageName()+"."+modelParameter.getClassName();
		if (TypeUtil.isImportedClassRequired(type))
			addClass(classes, type);
	}

}
