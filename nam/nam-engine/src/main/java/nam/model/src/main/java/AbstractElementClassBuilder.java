package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Application;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Reference;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.FieldUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.ClassUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public abstract class AbstractElementClassBuilder extends AbstractBeanBuilder {

	private Map<String, Namespace> namespaceMap;
	
	
	public AbstractElementClassBuilder(GenerationContext context) {
		super(context);
	}

	protected String getNamespacePackageName() throws Exception {
		String namespace = context.getModule().getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		return packageName;
	}

	protected void initializeImportedClasses(ModelClass modelClass) {
		modelClass.addImportedClass("java.io.Serializable");
		//modelClass.addImportedClass("org.apache.commons.logging.Log");
		//modelClass.addImportedClass("org.apache.commons.logging.LogFactory");
		//modelClass.addImportedClass("org.aries.Assert");
		//modelClass.addImportedClass("org.aries.RuntimeContext");
		//modelClass.addImportedClass("org.aries.jaxb.JAXBSessionCache");
		//modelClass.addImportedClass("org.aries.launcher.Bootstrap");
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		//modelClass.addImportedClass("org.aries.util.FileUtil");
	}

	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		modelClass.addStaticAttribute(createStaticLoggerAttribute(modelClass.getClassName()));
		modelClass.addStaticAttribute(createStaticPropertyPrefixLoggerAttribute());
	}
	
	public static ModelAttribute createStaticLoggerAttribute(String className) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		attribute.setPackageName("org.apache.commons.logging");
		attribute.setClassName("Log");
		attribute.setName("log");
		attribute.setDefault("LogFactory.getLog("+className+".class)");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected ModelAttribute createStaticPropertyPrefixLoggerAttribute() {
		Application application = context.getApplication();
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.FINAL + Modifier.STATIC);
		attribute.setPackageName("java.lang");
		attribute.setClassName("String");
		attribute.setName("PROPERTY_PREFIX");
		attribute.setDefault("\""+application.getArtifactId()+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
	}

	protected List<Namespace> getNamespaces(Information information) {
		List<Namespace> namespaces = information.getNamespaces();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			checkAddNamespacesToMap(namespace);
		}
		
		List<Namespace> values = new ArrayList<Namespace>();
		values.addAll(namespaceMap.values());
		return values;
	}

	protected void checkAddNamespacesToMap(Namespace namespace) {
		if (!namespaceMap.containsKey(namespace.getUri()))
			namespaceMap.put(namespace.getUri(), namespace);
		List<Namespace> imports = namespace.getImports();
		Iterator<Namespace> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Namespace importedNamespace = iterator.next();
			checkAddNamespacesToMap(importedNamespace);
		}
	}
	
	
	protected <F extends Field, T extends Type> Map<F, T> createFieldTypeMap(List<F> fields) {
		Map<F, T> map = new HashMap<F, T>();
		
		Iterator<F> iterator = fields.iterator();
		while (iterator.hasNext()) {
			F field = iterator.next();
			String fieldType = field.getType();

			if (FieldUtil.isTransient(field))
				continue;
			
			//TODO handle special case for PersonName
			if (field.getName().equals("firstName")) {
				@SuppressWarnings("unchecked") T fieldElement = (T) context.getElementByName("personName");
				map.put(field, fieldElement);
			}
			
			if (field instanceof Attribute) {
				String className = TypeUtil.getClassName(fieldType);
				if (ClassUtil.isJavaDefaultType(className)) {
					map.put(field, null);
					
				} else {
					@SuppressWarnings("unchecked") T fieldEnumeration = (T) context.getEnumerationByType(fieldType);
					map.put(field, fieldEnumeration);
				}
			}

			if (field instanceof Reference) {
				@SuppressWarnings("unchecked") T fieldElement = (T) context.getElementByType(fieldType);
				map.put(field, fieldElement);
			}
		}
		return map;
	}
	

//	protected List<Field> createSortedFieldList(Map<Field, Element> map) {
//		Set<Field> keySet = map.keySet();
//		List<Field> keyList = new ArrayList<Field>(keySet);
//		TypeUtil.sortByClassName(keyList);
//		return keyList;
//	}

	protected List<Field> createSortedFieldListByName(Collection<Field> unsortedFields) {
		List<Field> fieldList = new ArrayList<Field>(unsortedFields);
		TypeUtil.sortByInstanceName(fieldList);
		return fieldList;
	}
	
	protected List<Field> createSortedFieldListByClassName(Collection<Field> unsortedFields) {
		List<Field> fieldList = new ArrayList<Field>(unsortedFields);
		TypeUtil.sortByClassName(fieldList);
		return fieldList;
	}

	protected List<Field> createSortedFieldListByInstanceId(Collection<Field> unsortedFields) {
		List<Field> fieldList = new ArrayList<Field>(unsortedFields);
		sortTypesByInstanceId(fieldList);
		return fieldList;
	}
	
	public String getInstanceId(Type type) {
		Element element = context.getElementByType(type.getType());
		if (element != null)
			return getInstanceId(element, type);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameCapped(type);
		String instanceId = fieldNameUncapped;
		return instanceId;
	}
	
	protected String getInstanceId(Element element, Type type) {
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(type);
		String instanceId = elementNameUncapped + fieldNameCapped;
		return instanceId;
	}

	public <T extends Type> List<T> sortTypesByInstanceName(Collection<T> types) {
		List<T> typeList = new ArrayList<T>(types);
		TypeUtil.sortByInstanceName(typeList);
		return typeList;
	}

	public <T extends Type> List<T> sortTypesByInstanceId(Collection<T> types) {
		List<T> typeList = new ArrayList<T>(types);
		Collections.sort(typeList, new Comparator<T>() {
			public int compare(T type1, T type2) {
				String instanceId1 = getInstanceId(type1);
				String instanceId2 = getInstanceId(type2);
				return instanceId1.compareTo(instanceId2);
			}
		});
		return typeList;
	}

	protected void sortModelOperationsByName(List<ModelOperation> modelOperations) {
		Collections.sort(modelOperations, new Comparator<ModelOperation>() {
			public int compare(ModelOperation modelOperation1, ModelOperation modelOperation2) {
				return modelOperation1.getName().compareTo(modelOperation2.getName());
			}
		});
	}

	protected void sortModelReferencesByName(List<ModelReference> modelReferences) {
		Collections.sort(modelReferences, new Comparator<ModelReference>() {
			public int compare(ModelReference modelReference1, ModelReference modelReference2) {
				return modelReference1.getName().compareTo(modelReference2.getName());
			}
		});
	}

	protected void sortModelReferencesByClassName(List<ModelReference> modelReferences) {
		Collections.sort(modelReferences, new Comparator<ModelReference>() {
			public int compare(ModelReference modelReference1, ModelReference modelReference2) {
				String type1 = modelReference1.getType();
				String type2 = modelReference2.getType();
				String className1 = TypeUtil.getClassName(type1);
				String className2 = TypeUtil.getClassName(type2);
				return className1.compareTo(className2);
			}
		});
	}
	
	protected void sortModelReferencesByQualifiedName(List<ModelReference> modelReferences) {
		Collections.sort(modelReferences, new Comparator<ModelReference>() {
			public int compare(ModelReference modelReference1, ModelReference modelReference2) {
				String type1 = modelReference1.getType();
				String type2 = modelReference2.getType();
				String qualifiedName1 = TypeUtil.getPackageName(type1)+"."+TypeUtil.getClassName(type1);
				String qualifiedName2 = TypeUtil.getPackageName(type2)+"."+TypeUtil.getClassName(type2);
				return qualifiedName1.compareTo(qualifiedName2);
			}
		});
	}

	protected void sortModelReferencesByLocalPart(List<ModelReference> modelReferences) {
		Collections.sort(modelReferences, new Comparator<ModelReference>() {
			public int compare(ModelReference modelReference1, ModelReference modelReference2) {
				String type1 = modelReference1.getType();
				String type2 = modelReference2.getType();
				String localPart1 = TypeUtil.getLocalPart(type1);
				String localPart2 = TypeUtil.getLocalPart(type2);
				return localPart1.compareTo(localPart2);
				
			}
		});
	}

	protected Collection<Type> getUniqueElementsFromParameter(Service service) {
		Set<Type> parameterTypes = new HashSet<Type>();
		
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> iterator2 = parameters.iterator();
			while (iterator2.hasNext()) {
				Parameter parameter = iterator2.next();
				String parameterType = parameter.getType();
				if (TypeUtil.isDefaultType(parameterType))
					continue;
				
				Element parameterElement = context.getElementByType(parameterType);
				if (parameterElement != null) {
					parameterTypes.add(parameterElement);
					continue;
				}
				Enumeration parameterEnumeration = context.getEnumerationByType(parameterType);
				if (parameterEnumeration != null) {
					parameterTypes.add(parameterEnumeration);
					continue;
				}
			}
		}
		return parameterTypes;
	}

}
