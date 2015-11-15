package aries.generation.model;

import java.util.Collection;
import java.util.List;

import nam.model.Type;


public interface ModelUnit {

	public String getQualifiedName();

	public String getName();

	public void setName(String name);
	
	public String getType();

	public void setType(String type);
	
	public String getClassName();

	public void setClassName(String className);

	public String getPackageName();

	public void setPackageName(String packageName);

	public String getNamespace();

	public void setNamespace(String namespace);

	public List<String> getJavadoc();

	public void setJavadoc(List<String> javadoc);
	
	public Collection<String> getImportedClasses();
	
	public void addImportedClass(Type type);

	public void addImportedClass(String type);

	public Collection<ModelOperation> getInstanceOperations();

	public void addInstanceOperation(ModelOperation operation);

	public void addInstanceOperations(Collection<ModelOperation> operations);

	//public boolean removeInstanceOperation(ModelOperation operation);

	public Collection<ModelAttribute> getInstanceAttributes();

	public void addInstanceAttribute(ModelAttribute instanceAttribute);

	//public boolean removeInstanceAttribute(ModelAttribute instanceAttribute);

	public Collection<ModelReference> getInstanceReferences();

	public void addInstanceReference(ModelReference instanceReference);

	//public boolean removeInstanceReference(ModelField instanceReference);

}
