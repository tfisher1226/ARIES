package nam.data.src.main.java;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class MapperBeanProvider extends AbstractBeanProvider {

	protected Namespace namespace;
	
	protected Element mainElement;

	protected Element mapperElement;

	protected Reference reference;

	public ModelClass modelClass;
	
	
	public MapperBeanProvider(GenerationContext context) {
		super(context);
	}
	
	protected boolean isExtended() {
		return true;
	}

	public String getJavadoc(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		
		Buf buf = new Buf();
		buf.putLine2("/**");
		buf.putLine2(" * Provides mapping between {@link "+elementClassName+"} to {@link "+entityClassName+"}.");
		buf.putLine2(" *"); 
		buf.putLine2(" * @author "+context.getAuthor());
		buf.putLine2(" */");
		return buf.get();
	}

	

	protected String getParentClassName() {
		String className = DataLayerHelper.getMapperParentClassName(mapperElement);
		return className;
	}
	
	protected String getMapperPackageName() {
		//String packageName = DataLayerHelper.getMapperPackageName(namespace);
		if (reference != null && reference.getContained())
			return DataLayerHelper.getContainedMapperPackageName(namespace);
		return DataLayerHelper.getInferredMapperPackageName(namespace, mapperElement);
	}

	protected String getMapperClassName() {
		//String interfaceName = DataLayerHelper.getMapperInterfaceName(element);
		//String className = DataLayerHelper.getMapperClassName(element);
		if (reference != null && reference.getContained())
			return DataLayerHelper.getContainedMapperClassName(mainElement, reference);
		return DataLayerHelper.getInferredMapperClassName(namespace, mapperElement);
	}
	
	protected String getMapperTypeName() {
		if (reference != null && reference.getContained())
			return DataLayerHelper.getContainedMapperTypeName(namespace, mainElement, reference);
		return DataLayerHelper.getInferredMapperTypeName(namespace, mapperElement);
	}
	
	protected String getMapperBeanName() {
		String beanName = NameUtil.uncapName(getMapperClassName());
		return beanName;
	}
	
	protected String getEntityPackageName() {
		if (reference != null && reference.getContained())
			return DataLayerHelper.getContainedEntityPackageName(namespace);
		return DataLayerHelper.getInferredEntityPackageName(namespace, mapperElement);
	}
	
	protected String getEntityClassName() {
		if (reference != null && reference.getContained())
			return DataLayerHelper.getContainedEntityClassName(mainElement, reference);
		return DataLayerHelper.getInferredEntityClassName(namespace, mapperElement);
		//String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
	}
	
	protected String getEntityBeanName() {
		String beanName = NameUtil.uncapName(getEntityClassName());
		return beanName;
	}
	
	protected String getElementPackageName() {
		String packageName = ModelLayerHelper.getElementPackageName(mapperElement);
		return packageName;
	}
	
	protected String getElementClassName() {
		String className = ModelLayerHelper.getElementClassName(mapperElement);
		return className;
	}
	
	protected String getElementBeanName() {
		//ModelLayerHelper.getElementTypeLocalPartUncapped(mapperElement);
		String beanName = NameUtil.uncapName(getElementClassName());
		return beanName;
	}

	protected String getElementNameCapped() {
		String beanName = NameUtil.capName(getElementBeanName());
		return beanName;
	}

	protected String getElementLocalPartCapped() {
		String typeName = ModelLayerHelper.getElementTypeLocalPartCapped(mapperElement);
		return typeName;
	}

	
	/**
	 * Initialize operation
	 * ------------------------
	 * @param namespace 
	 */
	public String getSourceCode_Initialize(Namespace namespace, Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		//String entityClassName = DataLayerHelper.getConcreteEntityClassName(element);
		String entityClassName = getEntityClassName();
		String mapperBeanName = getMapperBeanName();

		Buf buf = new Buf();
		////buf.putLine2("setModelClass("+elementType+".class);");
		////buf.putLine2("setEntityClass("+entityClassName+".class);");
		buf.putLine2("setModelClass("+elementType+".class);");
		buf.putLine2("setEntityClass("+entityClassName+".class);");

		Set<String> alreadyProcessed = new HashSet<String>();
		List<Reference> fields = ElementUtil.getReferences(element);
		Iterator<Reference> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespace = TypeUtil.getNamespace(referenceType);
			
			if (!isImportRequiredForField(element, reference))
				continue;
//			if (field instanceof Reference && !FieldUtil.isCascadeDelete((Reference) field))
//				continue;
			if (element.getType().equals(referenceType))
				continue;
//			if (map.containsKey(referenceType))
//				continue;
			
			//String className = getMapperClassName();
			Element referenceElement = context.getElementByType(referenceType);
			String className = DataLayerHelper.getInferredMapperClassName(namespace, referenceElement);
			String beanName = NameUtil.uncapName(className);

			if (reference.getContained()) {
				className = DataLayerHelper.getContainedMapperClassName(element, reference);
				beanName = NameUtil.uncapName(className);
			}
			
			if (!alreadyProcessed.contains(beanName))
				alreadyProcessed.add(beanName);
			else continue;

			buf.putLine2("if ("+beanName+" == null)");
			if (reference.getContained() || referenceNamespace.equals(namespace.getUri())) {
				buf.putLine2("	"+beanName+" = new "+className+"();");
				
			} else {
				String mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(referenceNamespace);
				buf.putLine2("	"+beanName+" = "+mapperFixtureClassName+".get"+className+"();");
				//buf.putLine2("	"+beanName+" = new "+className+"();");
			}
		}
		
		return buf.get();
	}
	
	/*
	 * toEntity() methods
	 */

	public String getSourceCode_ToModel(Namespace namespace, Element element, Type owner) {
		return getSourceCode_ToModel(namespace, element, owner, false);
	}
	
	public String getSourceCode_ToModel(Namespace namespace, Element element, Type owner, boolean selfReferencing) {
		String entityClassName = getEntityClassName();
		String entityBeanName = getEntityBeanName();
		String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		String elementType = element.getType(); 
		//if (isExtended())
		//	elementClassName = "M";

//		if (elementName.equals("emailBox"))
//			System.out.println();

		Buf buf = new Buf();
		buf.putLine2("if ("+entityBeanName+" == null)");
		buf.putLine2("	return null;");
		if (isExtended())
			buf.putLine2(elementClassName+" "+elementBeanName+"Model = createModel();");
		else buf.putLine2(elementClassName+" "+elementBeanName+"Model = new "+elementClassName+"();");
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isTransient(field))
				continue;
			
			String structure = field.getStructure();
			String fieldType = field.getType();
			String fieldName = ModelLayerHelper.getFieldNameCapped(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldClassNameUncapped = ModelLayerHelper.getFieldClassNameUncapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);
			Element fieldElement = context.getElementByType(fieldType);
			
			/*
			//TODO decide when to use this below:
			if (ElementUtil.isSelfReferencing(element, field)) {
				if (structure.equals("list")) {
					buf.putLine2("@SuppressWarnings(\"unchecked\") List<E> "+fieldNameUncapped+"EntityList = (List<E>) "+fieldClassNameUncapped+"Entity.get"+fieldName+"();");
				} else if (structure.equals("set")) {
					buf.putLine2("@SuppressWarnings(\"unchecked\") Set<E> "+fieldNameUncapped+"EntitySet = (Set<E>) "+fieldClassNameUncapped+"Entity.get"+fieldName+"();");
				} else if (structure.equals("map")) {
					//TODO do this when we have a working example
					buf.putLine2("@SuppressWarnings(\"unchecked\") Map<E> "+fieldNameUncapped+"EntityMap = (Map<E>) "+fieldClassNameUncapped+"Entity.get"+fieldName+"();");
				} else {
					buf.putLine2("@SuppressWarnings(\"unchecked\") E "+fieldNameUncapped+"Entity = (E) "+fieldClassNameUncapped+"Entity.get"+fieldName+"();");
				}
			}
			*/
			
//			if (fieldName.equalsIgnoreCase("ParentBox"))
//				System.out.println();
			
			if (fieldType.equals(elementType) && selfReferencing) {
				if (FieldUtil.isInverse(field)) {
					buf.putLine2(elementBeanName+"Model.set"+fieldName+"(toModelFlat("+entityBeanName+".get"+fieldName+"()));");
					continue;
				}
			}
			
			String fieldMapperClassName = null;
			String fieldMapperBeanName = null;
			if (field instanceof Reference) {
				Reference reference = (Reference) field;
				if (reference.getContained()) {
					fieldClassName = ModelLayerHelper.getFieldClassName(reference);
					fieldMapperClassName = DataLayerHelper.getContainedMapperClassName(element, reference);
				} else fieldMapperClassName = DataLayerHelper.getInferredMapperClassName(namespace, fieldElement);
				fieldMapperBeanName = NameUtil.uncapName(fieldMapperClassName);
				//mapperBeanName = fieldClassNameUncapped + "Mapper.";
				if (element.getType().equals(field.getType()))
					fieldMapperBeanName = "this";
				fieldMapperBeanName += ".";
			}
			
			String ownerName = null;
			if (owner != null) {
				ownerName = ModelLayerHelper.getElementNameCapped(owner);
				if (owner.getType().equals(field.getType())) {
					buf.putLine2(elementBeanName+"Model.set"+fieldName+"(parent"+ownerName+");");
					//buf.putLine2("model.set"+fieldName+"("+mapperBeanName+"toModel("+ownerName+"Entity));");
					continue;
				}
			}
			
//			if (fieldName.equalsIgnoreCase("books"))
//				System.out.println();
			
			if (isImportRequiredForField(element, field)) {
				Reference reference = (Reference) field;

//				if (reference.getMappedBy() != null)
//					System.out.println();
				
				if (structure.equals("list")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new ArrayList<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+fieldNameUncapped+"EntityList)));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new ArrayList<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+elementBeanName+"Model, "+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new ArrayList<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					}
					
				} else if (structure.equals("set")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashSet<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+fieldNameUncapped+"EntitySet)));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashSet<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+elementBeanName+"Model, "+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashSet<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					}
					
				} else if (structure.equals("map")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						//TODO do this when we have a working example
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashMap<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+fieldNameUncapped+"EntityMap)));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashMap<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+elementBeanName+"Model, "+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					
					} else {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"(new HashMap<"+fieldClassName+">("+fieldMapperBeanName+"toModelList("+entityBeanName+"."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldQualifiedName);
					}
					
				} else {
					if (ElementUtil.isSelfReferencing(element, field) && ownerName == null) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+fieldMapperBeanName+"toModel("+fieldNameUncapped+"Entity));");
					
					} else if (ElementUtil.isSelfReferencing(element, field) && ownerName != null) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+fieldMapperBeanName+"toModel("+ownerName+", "+fieldNameUncapped+"Entity));");
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+fieldMapperBeanName+"toModel("+elementBeanName+"Model, "+entityBeanName+"."+toGetMethod(field)+"()));");
					
					} else {
						buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+fieldMapperBeanName+"toModel("+entityBeanName+"."+toGetMethod(field)+"()));");
					}
				}
				
			} else {
				if (ElementUtil.isSelfReferencing(element, field) && ownerName == null)
					buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+fieldNameUncapped+"Entity);");
				buf.putLine2(elementBeanName+"Model.set"+fieldName+"("+entityBeanName+"."+toGetMethod(field)+"());");
			}
		}
		
		buf.putLine2("return "+elementBeanName+"Model;");
		return buf.get();
	}
	
	public String getSourceCode_ToModelList(Namespace namespace, Element element, Field owner) {
		String entityClassName = getEntityClassName();
		String entityBeanName = getEntityBeanName();
		String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
//		String collectionInterface = "List";
//		String collectionImplementation = "ArrayList";
//		String structure = element.getStructure();
//		if (structure.equals("list")) {
//			collectionInterface = "List";
//			collectionImplementation = "ArrayList";
//		} else if (structure.equals("set")) {
//			collectionInterface = "List";
//			collectionImplementation = "ArrayList";
//		} else if (structure.equals("map")) {
//			collectionInterface = "List";
//			collectionImplementation = "ArrayList";
//		}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+entityBeanName+"List == null)");
		buf.putLine2("	return null;");
		buf.putLine2("List<"+elementClassName+"> "+elementBeanName+"ModelList = new ArrayList<"+elementClassName+">();");
		buf.putLine2("for ("+entityClassName+" "+entityBeanName+" : "+entityBeanName+"List) {");
		if (owner != null) {
			String ownerName = ModelLayerHelper.getFieldNameUncapped(owner);
			buf.putLine2("	"+elementClassName+" "+elementBeanName+"Model = toModel("+ownerName+"Model, "+entityBeanName+");");
		} else buf.putLine2("	"+elementClassName+" "+elementBeanName+"Model = toModel("+entityBeanName+");");
		buf.putLine2("	"+elementBeanName+"ModelList.add("+elementBeanName+"Model);");
		buf.putLine2("}");
		buf.putLine2("return "+elementBeanName+"ModelList;");
		return buf.get();
	}


	/*
	 * toEntity() methods
	 */

	public String getSourceCode_ToEntity(Namespace namespace, Element element, Field owner) {
		return getSourceCode_ToEntity(namespace, element, owner, false);
	}
	
	public String getSourceCode_ToEntity(Namespace namespace, Element element, Field owner, boolean selfReferencing) {
		String entityClassName = getEntityClassName();
		String entityBeanName = getEntityBeanName();
		//String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//if (isExtended()) {
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"Model == null)");
		buf.putLine2("	return null;");	
		if (isExtended())
			buf.putLine2(entityClassName+" "+entityBeanName+" = createEntity();");
		else buf.putLine2(entityClassName+" "+entityBeanName+" = new "+entityClassName+"();");
		if (owner != null) {
			String ownerName = ModelLayerHelper.getFieldNameUncapped(owner);
			buf.putLine2("toEntity("+ownerName+"Entity, "+entityBeanName+", "+elementBeanName+"Model);");
		} else buf.putLine2("toEntity("+entityBeanName+", "+elementBeanName+"Model);");
		buf.putLine2("return "+entityBeanName+";");
		return buf.get();
	}
	
	public String getSourceCode_ToEntity2(Namespace namespace, Element element, Field owner) {
		String entityBeanName = getEntityBeanName();
		String elementBeanName = getElementBeanName();

		Buf buf = new Buf();
		buf.putLine2("if ("+entityBeanName+" != null && "+elementBeanName+"Model != null) {");
		buf.putLines(1, getSourceCode_ToEntity2(namespace, element, owner, false));
		buf.putLine2("}");
		return buf.get();
	}
		
	public String getSourceCode_ToEntity2(Namespace namespace, Element element, Field owner, boolean selfReferencing) {
		//String entityClassName = getEntityClassName();
		String entityBeanName = getEntityBeanName();
		//String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		//String elementName = getElementTypeLocalPartUncapped();
		String elementType = element.getType(); 
		
//		if (elementName.equals("emailBox"))
//			System.out.println();
		
		Buf buf = new Buf();
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isTransient(field))
				continue;
			
			String structure = field.getStructure();
			String fieldType = field.getType();
			String fieldName = ModelLayerHelper.getFieldNameCapped(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldClassNameUncapped = ModelLayerHelper.getFieldClassNameUncapped(field);
			String fieldEntityClassName = DataLayerHelper.getFieldEntityClassName(field);
			String fieldEntityQualifiedName = DataLayerHelper.getFieldEntityQualifiedName(field);
			Element fieldElement = context.getElementByType(fieldType);

			if (ElementUtil.isSelfReferencing(element, field)) {
				if (structure.equals("list")) {
					buf.putLine2("@SuppressWarnings(\"unchecked\") List<"+fieldClassName+"> "+fieldNameUncapped+"ModelList = (List<"+fieldClassName+">) "+fieldClassNameUncapped+"Model.get"+fieldName+"();");
				} else if (structure.equals("set")) {
					buf.putLine2("@SuppressWarnings(\"unchecked\") Set<"+fieldClassName+"> "+fieldNameUncapped+"ModelSet = (Set<"+fieldClassName+">) "+fieldClassNameUncapped+"Model.get"+fieldName+"();");
				} else if (structure.equals("map")) {
					//TODO do this when we have a working example
					buf.putLine2("@SuppressWarnings(\"unchecked\") Map<String, "+fieldClassName+"> "+fieldNameUncapped+"ModelMap = (Map<String, "+fieldClassName+">) "+fieldClassNameUncapped+"Model.get"+fieldName+"();");
				} else {
					buf.putLine2("@SuppressWarnings(\"unchecked\") "+fieldClassName+" "+fieldNameUncapped+"Model = ("+fieldClassName+") "+fieldClassNameUncapped+"Model.get"+fieldName+"();");
				}
			}
			
			if (fieldType.equals(elementType) && selfReferencing) {
				if (FieldUtil.isInverse(field))
					buf.putLine2(entityBeanName+".set"+fieldName+"(toEntityFlat("+elementBeanName+"Model.get"+fieldName+"()));");
				continue;
			}

			if (fieldType.equals(elementType) && selfReferencing)
				continue;

			String fieldMapperClassName = null;
			String fieldMapperBeanName = null;
			if (field instanceof Reference) {
				Reference reference = (Reference) field;
				if (reference.getContained()) {
					fieldMapperClassName = DataLayerHelper.getContainedMapperClassName(element, reference);
					fieldEntityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
					fieldEntityQualifiedName = DataLayerHelper.getContainedEntityQualifiedName(namespace, element, reference);
				} else fieldMapperClassName = DataLayerHelper.getInferredMapperClassName(namespace, fieldElement);
				fieldMapperBeanName = NameUtil.uncapName(fieldMapperClassName);
				//mapperBeanName = fieldClassNameUncapped + "Mapper.";
				if (element.getType().equals(field.getType()))
					fieldMapperBeanName = "this";
				fieldMapperBeanName += ".";
			}
			
//			if (fieldName.toLowerCase().equals("parentbox"))
//				System.out.println();
			
			String ownerName = null;
			if (owner != null) {
				ownerName = ModelLayerHelper.getFieldNameUncapped(owner);
				if (ownerName.equals(fieldNameUncapped)) {
					buf.putLine2(elementBeanName+".set"+fieldName+"("+ownerName+"Entity);");
					//buf.putLine2("entity.set"+fieldName+"("+mapperBeanName+"toEntity("+ownerName+"));");
					continue;
				}
			}
			
			if (isImportRequiredForField(element, field)) {
				Reference reference = (Reference) field;
				
//				if (reference.getMappedBy() != null)
//					System.out.println();
				
				if (structure.equals("list")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"(new ArrayList<"+fieldEntityClassName+">("+fieldMapperBeanName+"toEntityList("+fieldNameUncapped+"ModelList)));");
						modelClass.addImportedClass(fieldEntityQualifiedName);

					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"(new ArrayList<"+fieldEntityClassName+">("+fieldMapperBeanName+"toEntityList("+entityBeanName+", "+elementBeanName+"Model."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldEntityQualifiedName);
					
					} else {
						buf.putLine2(entityBeanName+".set"+fieldName+"(new ArrayList<"+fieldEntityClassName+">("+fieldMapperBeanName+"toEntityList("+elementBeanName+"Model."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldEntityQualifiedName);
					}
					
				} else if (structure.equals("set")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"(new HashSet<"+fieldEntityClassName+">("+fieldMapperBeanName+"toEntityList("+fieldNameUncapped+"ModelSet)));");
						modelClass.addImportedClass(fieldEntityQualifiedName);

					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"(new HashSet<"+fieldEntityClassName+">("+fieldMapperBeanName+"toEntityList("+entityBeanName+", "+elementBeanName+"Model."+toGetMethod(field)+"())));");
						modelClass.addImportedClass(fieldEntityQualifiedName);

					} else {
						buf.putLine2("Collection<"+fieldEntityClassName+"> "+entityBeanName+"List = "+fieldMapperBeanName+"toEntityList("+elementBeanName+"Model."+toGetMethod(field)+"());");
						buf.putLine2(entityBeanName+".set"+fieldName+"(new ArrayList<"+fieldEntityClassName+">("+entityBeanName+"List));");
						modelClass.addImportedClass(fieldEntityQualifiedName);
					}
					
				} else if (structure.equals("map")) {
					if (ElementUtil.isSelfReferencing(element, field)) {
						//TODO do this when we have a working example
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntityMap("+fieldNameUncapped+"ModelMap)));");
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntityMap("+entityBeanName+", "+elementBeanName+"Model."+toGetMethod(field)+"())));");
					
					} else {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntityMap("+elementBeanName+"Model."+toGetMethod(field)+"())));");
						//buf.putLine2(elementName+"Entity.set"+fieldName+"(new HashMap<"+fieldEntityClassName+">("+mapperBeanName+"toEntityList("+elementName+"Entity, "+elementName+"Model."+toGetMethod(field)+"())));");
						//buf.putLine2(elementName+"Entity.set"+fieldName+"(new HashMap<"+fieldEntityClassName+">("+mapperBeanName+"toEntityList("+elementName+"Model."+toGetMethod(field)+"())));");
					}
					
				} else {
					if (ElementUtil.isSelfReferencing(element, field) && ownerName == null) {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldNameUncapped+"Model);");
					
					} else if (ElementUtil.isSelfReferencing(element, field) && ownerName != null) {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntity("+ownerName+"Entity, "+fieldNameUncapped+"Model));");
					
					} else if (reference.getMappedBy() != null || FieldUtil.hasInverse(element, field, fieldElement) || ElementUtil.isSelfReferencing(element, field)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntity("+entityBeanName+", "+elementBeanName+"Model."+toGetMethod(field)+"()));");
					
					} else if (FieldUtil.isTwoWay(field)) {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldNameUncapped+"Model);");
						
					} else {
						buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldMapperBeanName+"toEntity("+elementBeanName+"Model."+toGetMethod(field)+"()));");
					}
				}
				
			} else {
				if (ElementUtil.isSelfReferencing(element, field))
					buf.putLine2(entityBeanName+".set"+fieldName+"("+fieldNameUncapped+"Model);");
				buf.putLine2(entityBeanName+".set"+fieldName+"("+elementBeanName+"Model."+toGetMethod(field)+"());");
			}
		}
		
		return buf.get();
	}
	
	public String getSourceCode_ToEntityList(Namespace namespace, Element element, Field owner) {
		String entityClassName = getEntityClassName();
		String entityBeanName = getEntityBeanName();
		String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"ModelList == null)");
		buf.putLine2("	return null;");
		buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = new ArrayList<"+entityClassName+">();");
		buf.putLine2("for ("+elementClassName+" "+elementBeanName+"Model : "+elementBeanName+"ModelList) {");
		if (owner != null) {
			String ownerName = ModelLayerHelper.getFieldNameUncapped(owner);
			buf.putLine2("	"+entityClassName+" "+entityBeanName+" = toEntity("+ownerName+"Entity, "+elementBeanName+"Model);");
		} else buf.putLine2("	"+entityClassName+" "+entityBeanName+" = toEntity("+elementBeanName+"Model);");
		buf.putLine2("	"+entityBeanName+"List.add("+entityBeanName+");");
		buf.putLine2("}");
		buf.putLine2("return "+entityBeanName+"List;");
		return buf.get();
	}
	
	public String getSourceCode_ToEntitySet(Namespace namespace, Element element, Field owner) {
		String entityClassName = getEntityClassName();
		//String entityBeanName = getEntityBeanName();
		String elementClassName = getElementClassName();
		String elementBeanName = getElementBeanName();
		
		//String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//String elementClassName = ModelLayerHelper.getElementClassName(element);
		//String entityClassName = DataLayerHelper.getEntityClassName(element);
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"ModelSet == null)");
		buf.putLine2("	return null;");
		buf.putLine2("Set<"+entityClassName+"> "+elementBeanName+"EntitySet = new HashSet<"+entityClassName+">();");
		buf.putLine2("for ("+elementClassName+" "+elementBeanName+"Model : "+elementBeanName+"ModelSet) {");
		if (owner != null) {
			String ownerName = ModelLayerHelper.getFieldNameUncapped(owner);
			buf.putLine2("	"+entityClassName+" "+elementBeanName+"Entity = toEntity("+ownerName+"Entity, "+elementBeanName+"Model);");
		} else buf.putLine2("	"+entityClassName+" "+elementBeanName+"Entity = toEntity("+elementBeanName+"Model);");
		buf.putLine2("	"+elementBeanName+"EntitySet.add("+elementBeanName+"Entity);");
		buf.putLine2("}");
		buf.putLine2("return "+elementBeanName+"EntitySet;");
		return buf.get();
	}
	
	/*
	 * SelfReferencing methods
	 */

	public String getSourceCode_ToModel_SelfReferencing(Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine2("return toModel(null, "+elementName+"Entity);");
		return buf.get();
	}
	
	public String getSourceCode_ToModel_SelfReferencing2(Element element) {
		String elementBeanName = getElementBeanName();
		String elementClassName = getElementClassName();
		String entityClassName = getEntityClassName();
		String elementNameCapped = getElementLocalPartCapped();
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		List<Field> selfReferencingFields = ElementUtil.getSelfReferencingFields(element);
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"Entity == null)");
		buf.putLine2("	return null;");
		
		Iterator<Field> iterator = selfReferencingFields.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field field = iterator.next();
			String fieldName = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			
			if (i > 0)
				buf.putLine2("");
			buf.putLine2(elementClassName+" "+elementBeanName+"Model = toModelFlat(parent"+elementNameCapped+", "+elementBeanName+"Entity);");
			buf.putLine2("Set<"+entityClassName+"> "+fieldName+"EntityList = (Set<"+entityClassName+">) "+elementBeanName+"Entity.get"+fieldNameCapped+"();");
			buf.putLine2("if ("+fieldName+"EntityList != null) {");
			buf.putLine2("	List<"+elementClassName+"> "+fieldName+"ModelList = toModelList("+elementBeanName+"Model, "+fieldName+"EntityList);");
			buf.putLine2("	ListUtil.replaceElements((Set<"+elementClassName+">) "+elementBeanName+"Model.get"+fieldNameCapped+"(), "+fieldName+"ModelList);");
		}
			
		buf.putLine2("}");
		buf.putLine2("return "+elementBeanName+"Model;");
		return buf.get();
	}

	public String getSourceCode_ToModel_SelfReferencing_Flat(Namespace namespace, Element element) {
		return getSourceCode_ToModel(namespace, element, element, true);
	}
	
	public String getSourceCode_ToModelList_SelfReferencing(Namespace namespace, Element element) {
		String elementName = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("return toModelList(null, "+elementName+"EntityList);");
		return buf.get();
	}
	
	public String getSourceCode_ToModelList_SelfReferencing2(Namespace namespace, Element element) {
		Reference owner = ElementUtil.getInverseReference(element);
		return getSourceCode_ToModelList(namespace, element, owner);
	}
	
	
	/*
	 * 2WaySelfReferencing methods
	 */

	public String getSourceCode_ToModel_2WaySelfReferencing(Element element) {
		String elementBeanName = getElementBeanName();
		String elementClassName = getElementClassName();
		String entityClassName = getEntityClassName();
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"Entity == null)");
		buf.putLine2("	return null;");
		buf.putLine2(elementClassName+" "+elementBeanName+"Model = toModelFlat("+elementBeanName+"Entity);");
		buf.putLine2("List<"+entityClassName+"> childEntityList = "+elementBeanName+"Entity.getChildren();");
		buf.putLine2("if (childEntityList != null) {");
		buf.putLine2("	List<"+elementClassName+"> childModelList = toModelList(childEntityList);");
		buf.putLine2("	ListUtil.replaceElements("+elementBeanName+"Model.getChildren(), childModelList);");
		buf.putLine2("}");
		buf.putLine2("return "+elementBeanName+"Model;");
		return buf.get();
	}
	
	public String getSourceCode_ToModel_2WaySelfReferencing_Flat(Namespace namespace, Element element) {
		return getSourceCode_ToModel(namespace, element, null, true);
	}

	public String getSourceCode_ToModelList_2WaySelfReferencing(Namespace namespace, Element element) {
		return getSourceCode_ToModelList(namespace, element, null);
	}

	public String getSourceCode_ToEntity_2WaySelfReferencing(Namespace namespace, Element element) {
		String elementBeanName = getElementBeanName();
		String elementClassName = getElementClassName();
		String entityClassName = getEntityClassName();
		//if (isExtended()) {
		//	elementClassName = "M";
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"Model == null)");
		buf.putLine2("	return null;");
		buf.putLine2(entityClassName+" "+elementBeanName+"Entity = toEntityFlat("+elementBeanName+"Model);");
		buf.putLine2("List<"+elementClassName+"> childModelList = "+elementBeanName+"Model.getChildren();");
		buf.putLine2("if (childModelList != null) {");
		buf.putLine2("	List<"+entityClassName+"> childEntityList = toEntityList(childModelList);");
		buf.putLine2("	ListUtil.replaceElements("+elementBeanName+"Entity.getChildren(), childEntityList);");
		buf.putLine2("}");
		buf.putLine2("return "+elementBeanName+"Entity;");
		return buf.get();
	}

	public String getSourceCode_ToEntity_2WaySelfReferencing_Flat(Namespace namespace, Element element) {
		String elementBeanName = getElementBeanName();
		String entityClassName = getEntityClassName();
		//if (isExtended()) {
		//	entityClassName = "E";
		//}
		
		Buf buf = new Buf();
		buf.putLine2("if ("+elementBeanName+"Model == null)");
		buf.putLine2("	return null;");
		if (isExtended())
			buf.putLine2(entityClassName+" "+elementBeanName+"Entity = createEntity();");
		else buf.putLine2(entityClassName+" "+elementBeanName+"Entity = new "+entityClassName+"();");
		buf.put(getSourceCode_ToEntity2(namespace, element, null, true));
		buf.putLine2("return "+elementBeanName+"Entity;");
		return buf.get();
	}

	public String getSourceCode_ToEntityList_2WaySelfReferencing(Namespace namespace, Element element) {
		return getSourceCode_ToEntityList(namespace, element, null);
	}


	public String toGetMethod(Field field) {
		String fieldName = ModelLayerHelper.getFieldNameCapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		if (!field.getStructure().equals("map") && fieldClassName.toLowerCase().equals("boolean"))
			return "is" + fieldName;
		return "get" + fieldName;
	}

}
