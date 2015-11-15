package aries.generation.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nam.model.Type;
import nam.model.util.TypeUtil;
import aries.codegen.util.CodeUtil;


/**
 * Class modeling an operation of an instance. An operation is simply
 * a method of the instance that is not a constructor nor an accessor method.
 */
public class ModelOperation implements Comparable<ModelOperation> {

	private int modifiers;
	
	private String name;

	private int index = Integer.MAX_VALUE;

	private boolean isSynchronous = false;
	
	private Map<String, String> importedClasses;

	private List<ModelAnnotation> annotations;

	private List<ModelParameter> parameters;

	private List<String> initialSource;

	private List<String> completionSource;

	private List<String> exceptions;
	
	private String resultType;

	private String resultName;

	private List<String> javadoc;

	public Object mutex = new Object();

	
	public ModelOperation() {
		setModifiers(Modifier.PUBLIC);
		importedClasses = new LinkedHashMap<String, String>();
		annotations = new ArrayList<ModelAnnotation>();
		parameters = new ArrayList<ModelParameter>();
		initialSource = new ArrayList<String>();
		completionSource = new ArrayList<String>();
		exceptions = new ArrayList<String>();
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isSynchronous() {
		return isSynchronous;
	}

	public void setSynchronous(boolean isSynchronous) {
		this.isSynchronous = isSynchronous;
	}

	public Set<String> getImportedClasses() {
		Set<String> classes = importedClasses.keySet();
		Set<String> sortedClasses = new TreeSet<String>(classes);
		return sortedClasses;
	}
	
	public void addImportedClass(String type) {
		if (type != null) {
			if (TypeUtil.isImportedClassRequired(type)) {
				this.importedClasses.put(type, type);
			}
		}
	}
	
	public void addImportedClass(Type type) {
		if (type != null) {
			String className = TypeUtil.getClassName(type.getType());
			String packageName = TypeUtil.getPackageName(type.getType());
			String qualifiedName = packageName + "." + className;
			addImportedClass(qualifiedName);
		}
	}
	
	public void addImportedClass(ModelField modelField) {
		String type = modelField.getPackageName()+"."+modelField.getClassName();
		if (TypeUtil.isImportedClassRequired(type))
			addImportedClass(type);
	}

	public void addImportedClass(ModelParameter modelParameter) {
		String type = modelParameter.getPackageName()+"."+modelParameter.getClassName();
		if (TypeUtil.isImportedClassRequired(type))
			addImportedClass(type);
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
	
	public boolean hasParameter(String packageName, String className, String name) {
		return getParameter(packageName, className, name) != null;
	}
	
	public ModelParameter getParameter(String packageName, String className, String name) {
		synchronized (mutex) {
			Iterator<ModelParameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				ModelParameter modelParameter = iterator.next();
				if (!modelParameter.getPackageName().equals(packageName))
					continue;
				if (!modelParameter.getPackageName().equals(packageName))
					continue;
				if (!modelParameter.getPackageName().equals(packageName))
					continue;
				return modelParameter;
			}
			return null;
		}
	}
	
	public List<ModelParameter> getParameters() {
		synchronized (mutex) {
			return parameters;
		}
	}

	public void setParameters(List<ModelParameter> parameters) {
		synchronized (mutex) {
			this.parameters = parameters;
		}
	}
	
	public void addParameter(ModelParameter parameter) {
		synchronized (mutex) {
			parameters.add(parameter);
		}
	}
	
	public void addParameters(List<ModelParameter> parameters) {
		synchronized (mutex) {
			this.parameters.addAll(parameters);
		}
	}
	
	public List<String> getInitialSource() {
		return initialSource;
	}

	public void setInitialSource(List<String> source) {
		this.initialSource = source;
	}

	public void addInitialSource(String source) {
		if (source.length() > 0)
			this.initialSource.add(source);
	}

	public List<String> getCompletionSource() {
		return completionSource;
	}

	public void setCompletionSource(List<String> source) {
		this.completionSource = source;
	}

	public void addCompletionSource(String source) {
		if (source.length() > 0)
			this.completionSource.add(source);
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	public void addException(String exception) {
		this.exceptions.add(exception);
	}
	
	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
	
	public List<String> getJavadoc() {
		return javadoc;
	}

	public void setJavadoc(List<String> javadoc) {
		this.javadoc = javadoc;
	}

	public void addJavadoc(String text) {
		if (javadoc == null)
			javadoc = new ArrayList<String>();
		javadoc.add(text);
	}

	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	protected <T extends Comparable<T>> int compare(Collection<T> collecton1, Collection<T> collecton2) {
		if (collecton1 == null && collecton2 == null) return 0;
		if (collecton1 != null && collecton2 == null) return 1;
		if (collecton1 == null && collecton2 != null) return -1;
		int status = compare(collecton1.size(), collecton2.size());
		if (status != 0)
			return status;
		Iterator<T> iterator1 = collecton1.iterator();
		Iterator<T> iterator2 = collecton2.iterator();
		while (iterator2.hasNext() && iterator2.hasNext()) {
			T value1 = iterator1.next();
			T value2 = iterator2.next();
			status = value1.compareTo(value2);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	@Override
	public int compareTo(ModelOperation other) {
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(parameters, other.parameters);
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
		ModelOperation other = (ModelOperation) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (parameters != null)
			hashCode += parameters.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return getName()+"("+CodeUtil.getArgumentString(this)+")";
	}

}
