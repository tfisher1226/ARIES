package aries.generation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ModelInterface extends AbstractGeneratedContainer implements ModelUnit, Comparable<ModelInterface> {

	private String name;

	private String type;

	private String author;

	private String version;

	private List<String> javadoc;
	
	private String className;

	private String packageName;

	private String namespace;

	//private Map<String, String> extendedInterfaces;

	private List<String> extendedInterfaces;

	private List<ModelAnnotation> interfaceAnnotations;

	private Set<ModelField> interfaceFields;

	private Set<ModelAttribute> interfaceAttributes;

	private Set<ModelReference> interfaceReferences;

	private Set<ModelOperation> interfaceOperations;


	/**
	 * Constructs an uninitialized <code>ModelInterface</code>.
	 */
	public ModelInterface() {
		//extendedInterfaces = new LinkedHashMap<String, String>();
		extendedInterfaces = new ArrayList<String>();
		interfaceAnnotations = new ArrayList<ModelAnnotation>();
		interfaceFields = new LinkedHashSet<ModelField>();
		interfaceAttributes = new LinkedHashSet<ModelAttribute>();
		interfaceReferences = new LinkedHashSet<ModelReference>();
		interfaceOperations = new LinkedHashSet<ModelOperation>();
	}

	public String getName() {
		return name;
	}

	public void setName(String elementName) {
		this.name = elementName;
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
	

//	public void addImportedClass(String type) {
//		if (type != null) {
//			int indexOf = type.indexOf("<");
//			if (indexOf != -1)
//				type = type.substring(0, indexOf);
//			if (TypeUtil.isImportedClassRequired(type))
//				importedClasses.put(type, type);
//		}
//	}

//	public void addImportedClasses(Collection<String> importedClasses) {
//		Iterator<String> iterator = importedClasses.iterator();
//		while (iterator.hasNext()) {
//			String importedClass = iterator.next();
//			addImportedClass(importedClass);
//		}
//	}
	
	public List<String> getExtendedInterfaces() {
		return extendedInterfaces;
	}

	public void addExtendedInterface(String extendedInterface) {
		extendedInterfaces.add(extendedInterface);
	}

	public void setExtendedInterfaces(List<String> extendedInterfaces) {
		this.extendedInterfaces = extendedInterfaces;
	}

//	public Map<String, String> getExtendedInterfaces() {
//		return extendedInterfaces;
//	}
//
//	public void addExtendedInterface(String name, String extendedInterface) {
//		extendedInterfaces.put(name, extendedInterface);
//	}
//
//	public void setExtendedInterfaces(Map<String, String> extendedInterfaces) {
//		this.extendedInterfaces = extendedInterfaces;
//	}

	public List<ModelAnnotation> getInterfaceAnnotations() {
		return interfaceAnnotations;
	}

	public void addInterfaceAnnotation(ModelAnnotation interfaceAnnotation) {
		interfaceAnnotations.add(interfaceAnnotation);
	}

	public void setInterfaceAnnotations(List<ModelAnnotation> interfaceAnnotations) {
		this.interfaceAnnotations = interfaceAnnotations;
	}

	public Set<ModelAttribute> getInstanceAttributes() {
		return interfaceAttributes;
	}

	public void addInstanceAttribute(ModelAttribute instanceAttribute) {
		interfaceAttributes.add(instanceAttribute);
		interfaceFields.add(instanceAttribute);
	}

	public boolean removeInstanceAttribute(ModelAttribute instanceAttribute) {
		interfaceFields.add(instanceAttribute);
		return interfaceAttributes.remove(instanceAttribute);
	}

	public Set<ModelReference> getInstanceReferences() {
		return interfaceReferences;
	}

	public void addInstanceReference(ModelReference instanceReference) {
		interfaceFields.add(instanceReference);
		interfaceReferences.add(instanceReference);
	}

	public boolean removeInstanceReference(ModelField instanceReference) {
		interfaceFields.remove(instanceReference);
		return interfaceReferences.remove(instanceReference);
	}
	
	public void setInstanceAttributes(Set<ModelAttribute> interfaceAttributes) {
		this.interfaceAttributes = interfaceAttributes;
	}

	public Set<ModelOperation> getInstanceOperations() {
		return interfaceOperations;
	}

	public void addInstanceOperation(ModelOperation modelOperation) {
		addImportedClasses(modelOperation.getImportedClasses());
		interfaceOperations.add(modelOperation);
	}

	public void addInstanceOperations(Collection<ModelOperation> modelOperations) {
		Iterator<ModelOperation> iterator = modelOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation modelOperation = iterator.next();
			addInstanceOperation(modelOperation);
		}
	}

//	public boolean removeInstanceOperation(ModelOperation operation) {
//		return instanceOperations.remove(operation);
//	}
	
	@Override
	public int compareTo(ModelInterface modelInterface) {
		ModelInterface modelInterface1 = (ModelInterface) this;
		ModelInterface modelInterface2 = (ModelInterface) modelInterface;
		String qualifiedName1 = modelInterface1.getPackageName()+"."+modelInterface1.getClassName();
		String qualifiedName2 = modelInterface2.getPackageName()+"."+modelInterface2.getClassName();
		return qualifiedName1.compareTo(qualifiedName2);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelInterface other = (ModelInterface) object;
		return this.getType().equals(other.getType());
	}
	
	@Override
	public String toString() {
		return packageName + "." + className;
	}
	
}
