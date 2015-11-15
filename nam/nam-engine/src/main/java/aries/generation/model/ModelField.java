package aries.generation.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;


public class ModelField {

	private int modifiers;

	private String name;

	private String type;

	private String packageName;

	private String className;

	private Object value;

	private boolean unique;

	private boolean required;

	private boolean nillable;

	private boolean fullyQualified;
	
	private boolean generateGetter;

	private boolean generateSetter;

	private boolean generateUnsetMethod;

	private boolean generateAddMethod;

	private boolean generateRemoveMethod;

	private boolean generateClearMethod;

	private boolean synchronizationEnabled;
	
	private boolean keyEnabled;

	private int multiplicity;

	private String structure;

	private String keyType;

	private String keyPackageName;

	private String keyClassName;

	private List<String> javadoc;

	private List<ModelAnnotation> annotations;
	
	private Map<String, String> importedClasses;
	

	public ModelField() {
		generateGetter = true;
		generateSetter = true;
		annotations = new ArrayList<ModelAnnotation>();
		importedClasses = new LinkedHashMap<String, String>();
		structure = "item";
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
//		if (name.equals("ref"))
//			System.out.println();
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type.equals("Shipment"))
			System.out.println();
		this.type = type;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getQualifiedName() {
		return packageName + "." + className;
	}

	public Object getDefault() {
		return value;
	}

	public void setDefault(Object value) {
		this.value = value;
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
	
	public List<ModelAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<ModelAnnotation> annotations) {
		this.annotations = annotations;
	}

	public void addAnnotation(ModelAnnotation annotation) {
		annotations.add(annotation);;
	}

	public void addAnnotations(List<ModelAnnotation> annotations) {
		this.annotations.addAll(annotations);
	}

	public Set<String> getImportedClasses() {
		return importedClasses.keySet();
	}
	
	public void addImportedClass(String type) {
		if (type != null) {
			if (TypeUtil.isImportedClassRequired(type)) {
				importedClasses.put(type, type);
			}
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

	
	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isNullable() {
		return nillable;
	}

	public void setNullable(boolean nillable) {
		this.nillable = nillable;
	}

	public boolean isFullyQualified() {
		return fullyQualified;
	}

	public void setFullyQualified(boolean fullyQualified) {
		this.fullyQualified = fullyQualified;
	}

	public boolean getGenerateGetter() {
		return generateGetter;
	}

	public void setGenerateGetter(boolean generateGetter) {
		this.generateGetter = generateGetter;
	}

	public boolean getGenerateSetter() {
		return generateSetter;
	}

	public void setGenerateSetter(boolean generateSetter) {
		this.generateSetter = generateSetter;
	}

	public boolean getGenerateUnsetMethod() {
		return generateUnsetMethod;
	}

	public void setGenerateUnsetMethod(boolean generateUnsetMethod) {
		this.generateUnsetMethod = generateUnsetMethod;
	}

	public boolean getGenerateAddMethod() {
		return generateAddMethod;
	}

	public void setGenerateAddMethod(boolean generateAddMethod) {
		this.generateAddMethod = generateAddMethod;
	}

	public boolean getGenerateRemoveMethod() {
		return generateRemoveMethod;
	}

	public void setGenerateRemoveMethod(boolean generateRemoveMethod) {
		this.generateRemoveMethod = generateRemoveMethod;
	}

	public boolean getGenerateClearMethod() {
		return generateClearMethod;
	}

	public void setGenerateClearMethod(boolean generateClearMethod) {
		this.generateClearMethod = generateClearMethod;
	}

	public boolean isSynchronizationEnabled() {
		return synchronizationEnabled;
	}

	public void setSynchronizationEnabled(boolean synchronizationEnabled) {
		this.synchronizationEnabled = synchronizationEnabled;
	}

	/**
	 * Returns whether this attribute is one of the attributes that uniquely
	 * identify an instance.
	 * 
	 * @return whether this attribute is one of the attributes that uniquely
	 *         identify an instance
	 */
	public boolean isKeyEnabled() {
		return keyEnabled;
	}

	/**
	 * Sets whether this attribute is one of the attributes that uniquely identify
	 * an instance.
	 * 
	 * @param keyEnabled Whether this attribute is one of the attributes that uniquely
	 *          identify an instance.
	 */
	public void setKeyEnabled(boolean keyEnabled) {
		this.keyEnabled = keyEnabled;
	}
	
	public int getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(int multiplicity) {
		this.multiplicity = multiplicity;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKeyPackageName() {
		return keyPackageName;
	}

	public void setKeyPackageName(String keyPackageName) {
		this.keyPackageName = keyPackageName;
	}

	public String getKeyClassName() {
		return keyClassName;
	}

	public void setKeyClassName(String keyClassName) {
		this.keyClassName = keyClassName;
	}
	
	//this is to be overridden by subtypes
	public String getRepresentation() {
		return "item";
	}
	
	public String getCappedName() {
		return NameUtil.capName(getName());
	}

	public String getUncappedName() {
		return NameUtil.uncapName(getName());
	}

	public boolean isBoolean() {
		return "boolean".equals(getClassName());
	}

	public boolean isInt() {
		return "int".equals(getClassName());
	}

	public boolean isChar() {
		return "char".equals(getClassName());
	}

	public boolean isByte() {
		return "byte".equals(getClassName());
	}

	public boolean isShort() {
		return "short".equals(getClassName());
	}

	public boolean isLong() {
		return "long".equals(getClassName());
	}

	public boolean isDouble() {
		return "double".equals(getClassName());
	}

	public boolean isFloat() {
		return "float".equals(getClassName());
	}

	
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		ModelField other = (ModelField) object;
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