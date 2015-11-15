package aries.generation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class modeling a Java Bean.
 * 
 */
public class ModelClass extends AbstractGeneratedContainer implements ModelUnit, Comparable<ModelClass> {
	
	private boolean isAbstract;
	
	private String name;

	private String type;

	private String className;

	private String packageName;

	private String parentClassName;

	private String namespace;

	private String author;

	private String version;

	private List<String> javadoc;

	private Map<String, String> implementedInterfaces;
	
	private List<ModelAnnotation> classAnnotations;

	private Set<ModelConstructor> constructors;

	private Set<ModelAttribute> staticAttributes;

	private Set<ModelReference> staticReferences;

	private Set<ModelOperation> staticOperations;

	private Set<ModelField> instanceFields;

	private Set<ModelAttribute> instanceAttributes;

	private Set<ModelReference> instanceReferences;

	private Set<ModelOperation> instanceOperations;

	private Map<String, ModelVariable> localVariables;
	
	private List<ModelClass> supportingClasses;

	
	/**
	 * Constructs an uninitialized @{link ModelClass}.
	 */
	public ModelClass() {
		implementedInterfaces = new LinkedHashMap<String, String>();
		classAnnotations = new ArrayList<ModelAnnotation>();
		staticAttributes = new LinkedHashSet<ModelAttribute>();
		staticReferences = new LinkedHashSet<ModelReference>();
		staticOperations = new LinkedHashSet<ModelOperation>();
		constructors = new LinkedHashSet<ModelConstructor>();
		instanceFields = new LinkedHashSet<ModelField>();
		instanceAttributes = new LinkedHashSet<ModelAttribute>();
		instanceReferences = new LinkedHashSet<ModelReference>();
		instanceOperations = new LinkedHashSet<ModelOperation>();
		//instanceOperations = new LinkedHashMap<String, ModelOperation>();
		localVariables = new LinkedHashMap<String, ModelVariable>();
		supportingClasses = new ArrayList<ModelClass>();
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getQualifiedName() {
		return packageName + "." + className;
	}
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public String getParentClassName() {
		return parentClassName;
	}

	public void setParentClassName(String parentClassName) {
		this.parentClassName = parentClassName;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
	

	public Set<String> getImplementedInterfaces() {
		Set<String> classes = implementedInterfaces.keySet();
		//Set<String> sortedClasses = new TreeSet<String>(classes);
		return classes;
	}
	
	public void addImplementedInterface(String type) {
		implementedInterfaces.put(type, type);
	}
	
	public List<ModelAnnotation> getClassAnnotations() {
		return classAnnotations;
	}

	public void setClassAnnotations(List<ModelAnnotation> classAnnotations) {
		this.classAnnotations = classAnnotations;
	}

	public void addClassAnnotation(ModelAnnotation classAnnotation) {
		classAnnotations.add(classAnnotation);
	}

	public void removeClassAnnotation(ModelAnnotation classAnnotation) {
		classAnnotations.remove(classAnnotation);
	}
	
	
	/**
	 * Convenience method that returns a comma-separated list of constructor 
	 * parameter types and names. 
	 * 
	 * @return a comma-separated list of attributes, formatted like
	 *   <code>parameter1-type parameter1-name, parameter2-type parameter2-name (, ...)</code>
	 */
	public String constructorParameterDescription() {
		return parameterDescription(getInstanceFields().iterator());
	}

	/**
	 * Convenience method that returns a comma-separated list of the key attribute
	 * types and names. The result of this method can be used as the parameter
	 * list for the lookup method.
	 * 
	 * @return a comma-separated list of key attributes, formatted like
	 *   <code>key-parameter1-type key-parameter1-name, key-parameter2-type key-parameter2-name (, ...)</code>
	 */
	public String keyParameterDescription() {
		return parameterDescription(getInstanceFields().iterator());
	}

	private String parameterDescription(Iterator attributes) {
		StringBuffer result = new StringBuffer();

		for (Iterator i = attributes; i.hasNext();) {
			ModelAttribute parameter = (ModelAttribute) i.next();
			result.append(parameter.getClassName()).append(' ');
			result.append(parameter.getUncappedName());

			if (i.hasNext()) {
				result.append(", ");
			}
		}
		return result.toString();
	}

	
	/*
	 * Static-Attributes
	 */
	
	public Set<ModelField> getStaticFields() {
		Set<ModelField> set = new LinkedHashSet<ModelField>();
		set.addAll(staticAttributes);
		return set;
	}
	
	public Set<ModelAttribute> getStaticAttributes() {
		return staticAttributes;
	}

	public Set<ModelReference> getStaticReferences() {
		return staticReferences;
	}

	public void addStaticAttribute(ModelAttribute staticAttribute) {
		addImportedClasses(staticAttribute.getImportedClasses());
		staticAttributes.add(staticAttribute);
	}

	public void addStaticReference(ModelReference staticReference) {
		addImportedClasses(staticReference.getImportedClasses());
		staticReferences.add(staticReference);
	}

	public boolean removeStaticAttribute(ModelAttribute staticAttribute) {
		return staticAttributes.remove(staticAttribute);
	}
	
	public boolean removeStaticReference(ModelReference staticReference) {
		return staticReferences.remove(staticReference);
	}
	

	/*
	 * Static-Operations
	 */

	public int getStaticOperationCount() {
		return staticOperations.size();
	}

	public Set<ModelOperation> getStaticOperations() {
		return staticOperations;
	}

	public void addStaticOperation(ModelOperation operation) {
		addImportedClasses(operation.getImportedClasses());
		staticOperations.add(operation);
	}

	public boolean removeStaticOperation(ModelOperation operation) {
		return staticOperations.remove(operation);
	}
	
	
	/*
	 * Instance-Fields
	 */
	
	public Set<ModelField> getInstanceFields() {
		return instanceFields;
	}
	
	public Set<ModelField> getInstanceFields2() {
		Set<ModelField> set = new LinkedHashSet<ModelField>();
		set.addAll(instanceAttributes);
		set.addAll(instanceReferences);
		return set;
	}

	public Set<ModelAttribute> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void addInstanceAttribute(ModelAttribute instanceAttribute) {
		if (instanceAttribute == null)
			return;
		addImportedClasses(instanceAttribute.getImportedClasses());
		instanceAttributes.add(instanceAttribute);
		instanceFields.add(instanceAttribute);
	}
	
	public boolean removeInstanceAttribute(ModelAttribute instanceAttribute) {
		if (instanceAttribute == null)
			return false;
		instanceFields.add(instanceAttribute);
		return instanceAttributes.remove(instanceAttribute);
	}

	public Set<ModelReference> getInstanceReferences() {
		return instanceReferences;
	}

	public void addInstanceReferences(Collection<ModelReference> instanceReferences) {
		Iterator<ModelReference> iterator = instanceReferences.iterator();
		while (iterator.hasNext()) {
			ModelReference instanceReference = iterator.next();
			addInstanceReference(instanceReference);
		}
	}
	
	public void addInstanceReference(ModelReference instanceReference) {
		addImportedClasses(instanceReference.getImportedClasses());
		instanceFields.add(instanceReference);
		instanceReferences.add(instanceReference);
	}

	public boolean removeInstanceReference(ModelField instanceReference) {
		instanceFields.remove(instanceReference);
		return instanceReferences.remove(instanceReference);
	}

	
	/*
	 * Constructors
	 */
	
	public Set<ModelConstructor> getInstanceConstructors() {
		return constructors;
	}
	
	public void addInstanceConstructor(ModelConstructor constructor) {
		if (constructor != null)
			constructors.add(constructor);
	}

	public void removeInstanceConstructor(ModelConstructor constructor) {
		constructors.remove(constructor);
	}
	
	
	/*
	 * Instance-Operations
	 */

	public Collection<ModelOperation> getInstanceOperations() {
		return instanceOperations;
	}

	public void addInstanceOperation(ModelOperation modelOperation) {
		if (modelOperation != null) {
			addImportedClasses(modelOperation.getImportedClasses());
			instanceOperations.add(modelOperation);
		}
	}
	
	public void addInstanceOperations(Collection<ModelOperation> modelOperations) {
		Iterator<ModelOperation> iterator = modelOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation modelOperation = iterator.next();
			//if (modelOperation == null)
			//	System.out.println();
			addInstanceOperation(modelOperation);
		}
	}
	
//	public boolean removeInstanceOperation(ModelOperation operation) {
//		return instanceOperations.remove(operation);
//	}

	
	/*
	 * Local variables
	 */
	
	public void clearLocalVariables() {
		localVariables.clear();
	}

	public Map<String, ModelVariable> getLocalVariables() {
		return localVariables;
	}

	public ModelVariable getLocalVariable(String name) {
		return localVariables.get(name);
	}

	public void putLocalVariable(String name, ModelVariable modelVariable) {
		this.localVariables.put(name, modelVariable);
	}

	public void putAllLocalVariables(Map<String, ModelVariable> modelVariables) {
		this.localVariables.putAll(modelVariables);
	}

	
	/*
	 * Supporting Classes
	 */
	
	public List<ModelClass> getSupportingClasses() {
		return supportingClasses;
	}

	public void addSupportingClass(ModelClass supportingClass) {
		this.supportingClasses.add(supportingClass);
	}

	public void addSupportingClasses(List<ModelClass> supportingClasses) {
		this.supportingClasses.addAll(supportingClasses);
	}

	@Override
	public int compareTo(ModelClass modelClass) {
		ModelClass modelClass1 = (ModelClass) this;
		ModelClass modelClass2 = (ModelClass) modelClass;
		String qualifiedName1 = modelClass1.getPackageName()+"."+modelClass1.getClassName();
		String qualifiedName2 = modelClass2.getPackageName()+"."+modelClass2.getClassName();
		return qualifiedName1.compareTo(qualifiedName2);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelClass other = (ModelClass) object;
		return this.getType().equals(other.getType());
	}
	
	@Override
	public String toString() {
		return packageName + "." + className;
	}

}