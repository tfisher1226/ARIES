package nam.data;

import nam.ProjectLevelHelper;
import nam.client.src.main.java.CacheUnitManagerMBeanBuilder;
import nam.client.src.main.java.CacheUnitMonitorBuilder;
import nam.client.src.main.java.DataUnitManagerMBeanBuilder;
import nam.client.src.main.java.DataUnitMonitorBuilder;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Reference;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.PersistenceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.service.src.main.java.CacheUnitBuilder;
import nam.service.src.main.java.CacheUnitManagerBuilder;
import nam.service.src.main.java.CacheUnitStateBuilder;
import nam.service.src.main.java.CacheUnitStateProcessorBuilder;
import nam.service.src.main.java.DataUnitBuilder;
import nam.service.src.main.java.DataUnitManagerBuilder;
import nam.service.src.main.java.DataUnitStateBuilder;
import nam.service.src.main.java.DataUnitStateProcessorBuilder;
import nam.service.src.test.java.CacheUnitManagerCITBuilder;
import nam.service.src.test.java.DataUnitManagerCITBuilder;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class DataLayerHelper {
	
	public static GenerationContext context;
	
	public static boolean isCacheUnitRelated(Class<?> classObject) {
		return classObject.equals(CacheUnitBuilder.class) ||
			classObject.equals(CacheUnitMonitorBuilder.class) ||
			classObject.equals(CacheUnitManagerBuilder.class) || 
			classObject.equals(CacheUnitManagerCITBuilder.class) || 
			classObject.equals(CacheUnitManagerMBeanBuilder.class) ||
			classObject.equals(CacheUnitStateBuilder.class) || 
			classObject.equals(CacheUnitStateProcessorBuilder.class); 
	}
	
	public static boolean isDataUnitRelated(Class<?> classObject) {
		return classObject.equals(DataUnitBuilder.class) ||
			classObject.equals(DataUnitMonitorBuilder.class) ||
			classObject.equals(DataUnitManagerBuilder.class) || 
			classObject.equals(DataUnitManagerCITBuilder.class) || 
			classObject.equals(DataUnitManagerMBeanBuilder.class) ||
			classObject.equals(DataUnitStateBuilder.class) || 
			classObject.equals(DataUnitStateProcessorBuilder.class); 
	}

	public static boolean isStateRelated(Class<?> classObject) {
		return classObject.equals(DataUnitStateBuilder.class) ||
			classObject.equals(DataUnitStateProcessorBuilder.class) ||
			classObject.equals(CacheUnitStateBuilder.class) ||
			classObject.equals(CacheUnitStateProcessorBuilder.class);
	}


	public static String getDatabaseName(Persistence persistenceBlock) {
		return PersistenceUtil.getDatabaseName(persistenceBlock);
	}

	public static String getDataSourceName(Persistence persistenceBlock) {
		return PersistenceUtil.getDataSourceName(persistenceBlock);
	}


	/*
	 * Entity properties
	 * -----------------
	 */
	
	public static String getEntityName(String elementName) {
		return elementName + "Entity";
	}
	
	public static String getEntityName(Type element) {
		return getEntityName(ModelLayerHelper.getElementName(element));
	}
	
	public static String getEntityNameCapped(Type element) {
		return ModelLayerHelper.getElementNameCapped(element) + "Entity";
	}
	
	public static String getEntityNameUncapped(Type element) {
		return ModelLayerHelper.getElementNameUncapped(element) + "Entity";
	}
	
	public static String getEntityPackageName(Type element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return ProjectLevelHelper.getPackageName(namespace) + ".entity";
	}

	public static String getEntityPackageName(String namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".entity";
	}

	public static String getEntityPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".entity";
	}
	
	public static String getEntityClassName(Type element) {
		return ModelLayerHelper.getElementClassName(element) + "Entity";
	}

	public static String getConcreteEntityClassName(Type element) {
		return ModelLayerHelper.getElementNameCapped(element) + "Entity";
	}

	public static String getEntityQualifiedName(Namespace namespace, Type element) {
		return getEntityPackageName(namespace) + "." + getEntityClassName(element);
	}

	
	/*
	 * Inferred Entity properties
	 * (These are created as part of "cache" and "persist" structures in ARIEL)
	 * ------------------------------------------------------------------------
	 */

	public static String getInferredEntityPackageName(Namespace namespace, Element element) {
		return getInferredEntityPackageName(namespace.getUri(), element);
	}
	
	public static String getInferredEntityPackageName(String namespace, Element element) {
		String elementType = ModelLayerHelper.getInferredElementTypeName(namespace, element);
		String packageName = TypeUtil.getPackageName(elementType) + ".entity";
		return packageName;
	}

	public static String getInferredEntityClassName(Namespace namespace, Element element) {
		return getInferredEntityClassName(namespace.getUri(), element);
	}
	
	public static String getInferredEntityClassName(String namespace, Element element) {
		String elementType = ModelLayerHelper.getInferredElementTypeName(namespace, element);
		String className = TypeUtil.getClassName(elementType) + "Entity";
		return className;
	}

	public static String getInferredEntityQualifiedName(Namespace namespace, Element element) {
		String packageName = getInferredEntityPackageName(namespace, element);
		String className = getInferredEntityClassName(namespace, element);
		return packageName + "." + className;
	}
	
	public static String getInferredEntityTypeName(Namespace namespace, Element element) {
		return getInferredEntityTypeName(namespace.getUri(), element);
	}
	
	public static String getInferredEntityTypeName(String namespace, Element element) {
		String localPart = NameUtil.uncapName(element.getName()) + "Entity";
		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace + "/entity", localPart);
		return entityType;
	}

//	public static String getInferredEntityTypeName(Namespace namespace, String localPart) {
//		String entityType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri() + "/entity", localPart);
//		return entityType;
//	}

	public static String getInferredEntityBeanName(Namespace namespace, Element element) {
		String entityName = getInferredEntityBeanName(namespace.getUri(), element);
		return entityName;
	}
	
	public static String getInferredEntityBeanName(String namespace, Element element) {
		String className = getInferredEntityClassName(namespace, element);
		String entityName = NameUtil.uncapName(className);
		return entityName;
	}
	
	
	/*
	 * Inferred Mapper properties
	 * (These are created as result of "cache" and "persist" structures in ARIEL)
	 * --------------------------------------------------------------------------
	 */
	
	public static String getInferredMapperPackageName(Namespace namespace, Element element) {
		return getInferredMapperPackageName(namespace.getUri(), element);
	}
	
	public static String getInferredMapperPackageName(String namespace, Element element) {
		String elementType = ModelLayerHelper.getInferredElementTypeName(namespace, element);
		String packageName = TypeUtil.getPackageName(elementType) + ".map";
		return packageName;
	}
	
	public static String getInferredMapperClassName(Namespace namespace, Element element) {
		String className = getInferredMapperClassName(namespace.getUri(), element);
		return className;
	}
	
	public static String getInferredMapperClassName(String namespace, Element element) {
		String elementType = ModelLayerHelper.getInferredElementTypeName(namespace, element);
		String className = TypeUtil.getClassName(elementType) + "Mapper";
		return className;
	}
	
	public static String getInferredMapperBeanName(Namespace namespace, Element element) {
		String className = getInferredMapperClassName(namespace.getUri(), element);
		String beanName = NameUtil.uncapName(className);
		return beanName;
	}
	
	public static String getInferredMapperTypeName(Namespace namespace, Element element) {
		String typeName = getInferredMapperTypeName(namespace.getUri(), element);
		return typeName;
	}
	
	public static String getInferredMapperTypeName(String namespace, Element element) {
		String localPart = NameUtil.uncapName(element.getName()) + "Mapper";
		String mapperType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace + "/map", localPart);
		return mapperType;
	}

	public static String getInferredMapperTypeName(Namespace namespace, String localPart) {
		String mapperType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri() + "/map", localPart);
		return mapperType;
	}
	
	
	/*
	 * Contained Entity properties
	 * ---------------------------
	 */

	public static String getContainedEntityPackageName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getContainedEntityPackageName(namespace);
	}

	public static String getContainedEntityPackageName(Namespace namespace) {
		return getContainedEntityPackageName(namespace.getUri());
	}
	
	public static String getContainedEntityPackageName(String namespace) {
		String packageName = ProjectLevelHelper.getPackageName(namespace) + ".entity";
		return packageName;
	}

	public static String getContainedEntityClassName(Element element, Reference reference) {
		String elementName = NameUtil.capName(element.getName());
		String referenceType = reference.getType();
		String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
		String containedReferenceName = NameUtil.capName(referenceLocalPart);
		String className = elementName + containedReferenceName + "Entity"; 
		return className;
	}

	public static String getContainedEntityQualifiedName(Namespace namespace, Element element, Reference reference) {
		return getContainedEntityQualifiedName(namespace.getUri(), element, reference);
	}
	
	public static String getContainedEntityQualifiedName(String namespace, Element element, Reference reference) {
		String packageName = getContainedEntityPackageName(namespace);
		String className = getContainedEntityClassName(element, reference);
		String qualifiedName = packageName + "." + className;
		return qualifiedName;
	}

	public static String getContainedEntityTypeName(Namespace namespace, Element element, Reference reference) {
		String packageName = getContainedEntityPackageName(namespace);
		String className = getContainedEntityClassName(element, reference);
		String typeName = TypeUtil.getTypeFromPackageAndClass(packageName, className);
		return typeName;
	}
	
	
	/*
	 * Contained Mapper properties
	 * ---------------------------
	 */

	public static String getContainedMapperPackageName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getContainedMapperPackageName(namespace);
	}

	public static String getContainedMapperPackageName(Namespace namespace) {
		return getContainedMapperPackageName(namespace.getUri());
	}
	
	public static String getContainedMapperPackageName(String namespace) {
		String packageName = ProjectLevelHelper.getPackageName(namespace) + ".map";
		return packageName;
	}

	public static String getContainedMapperClassName(Element element, Reference reference) {
		String elementName = NameUtil.capName(element.getName());
		String referenceType = reference.getType();
		String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
		String containedReferenceName = NameUtil.capName(referenceLocalPart);
		String className = elementName + containedReferenceName + "Mapper"; 
		return className;
	}

	public static String getContainedMapperQualifiedName(Namespace namespace, Element element, Reference reference) {
		return getContainedMapperQualifiedName(namespace, element, reference);
	}
	
	public static String getContainedMapperQualifiedName(String namespace, Element element, Reference reference) {
		String packageName = getContainedMapperPackageName(namespace);
		String className = getContainedMapperClassName(element, reference);
		String qualifiedName = packageName + "." + className;
		return qualifiedName;
	}

	public static String getContainedMapperTypeName(Namespace namespace, Element element, Reference reference) {
		String packageName = getContainedMapperPackageName(namespace);
		String className = getContainedMapperClassName(element, reference);
		String typeName = TypeUtil.getTypeFromPackageAndClass(packageName, className);
		return typeName;
	}
	

	
	/*
	 * Field properties
	 * ----------------
	 */

	public static String getFieldEntityName(Field field) {
		return field.getName() + "Entity";
	}

	public static String getFieldEntityNameCapped(Type field) {
		return NameUtil.capName(field.getName()) + "Entity";
	}

	public static String getFieldEntityNameUncapped(Field field) {
		return NameUtil.uncapName(field.getName()) + "Entity";
	}

	public static String getFieldEntityPackageName(Field field) {
		return TypeUtil.getPackageName(field.getType()) + ".entity";
	}

	public static String getFieldEntityClassName(Field field) {
		return TypeUtil.getClassName(field.getType()) + "Entity";
	}

	public static String getFieldEntityQualifiedName(Field field) {
		return getFieldEntityPackageName(field) + "." + getFieldEntityClassName(field);
	}


	/*
	 * Manager bean properties
	 * -----------------------
	 */
	
	public static String getManagerNameUncapped(Unit unit) {
		return NameUtil.uncapName(getManagerInterfaceName(unit));
	}

	public static String getManagerNameUncapped(Element element) {
		return NameUtil.uncapName(getManagerInterfaceName(element));
	}

	public static String getManagerNameCapped(Element element) {
		return NameUtil.capName(getManagerInterfaceName(element));
	}

	public static String getManagerPackageName(Unit unit) {
		return ProjectLevelHelper.getPackageName(unit.getNamespace());
	}
	
	public static String getManagerPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".bean";
	}
	
	public static String getManagerInterfaceName(Unit unit) {
		return NameUtil.capName(unit.getName()) + "Manager";
	}

	public static String getManagerInterfaceName(Element element) {
		return NameUtil.capName(element.getName()) + "Manager";
	}

	public static String getManagerClassName(Unit unit) {
		return NameUtil.capName(unit.getName()) + "ManagerImpl";
	}

	public static String getManagerClassName(Element element) {
		return NameUtil.capName(element.getName()) + "ManagerImpl";
	}

	public static String getManagerQualifiedName(Unit unit) {
		return getManagerPackageName(unit) + "." + getManagerClassName(unit);
	}

	public static String getManagerQualifiedName(Namespace namespace, Element element) {
		return getManagerPackageName(namespace) + "." + getManagerClassName(element);
	}

	public static String getManagerParentInterfaceName(Unit unit) {
		return null;
	}

	public static String getManagerParentInterfaceName(Element element) {
		return null;
	}

	public static String getManagerParentClassName(Unit unit) {
		return null;
	}

	public static String getManagerParentClassName(Element element) {
		return null;
	}

	public static String getManagerContextName(Unit unit) {
		String qualifiedName = getManagerQualifiedName(unit);
		String simpleName = getManagerNameUncapped(unit);
		return getContextName(qualifiedName, simpleName);
	}

	public static String getManagerContextName(Namespace namespace, Element element) {
		String qualifiedName = getManagerQualifiedName(namespace, element);
		String simpleName = getManagerNameUncapped(element);
		return getContextName(qualifiedName, simpleName);
	}

	
	/*
	 * Repository bean properties
	 * --------------------------
	 */
	
	public static String getRepositoryNameUncapped(Unit unit) {
		return NameUtil.uncapName(getRepositoryInterfaceName(unit));
	}

	public static String getRepositoryNameUncapped(Element element) {
		return NameUtil.uncapName(getRepositoryInterfaceName(element));
	}

	public static String getRepositoryNameCapped(Element element) {
		return NameUtil.capName(getRepositoryInterfaceName(element));
	}

	public static String getRepositoryPackageName(Unit unit) {
		return ProjectLevelHelper.getPackageName(unit.getNamespace());
	}
	
	public static String getRepositoryPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace);
	}
	
	public static String getRepositoryInterfaceName(Unit unit) {
		return NameUtil.capName(unit.getName()) + "Repository";
	}

	public static String getRepositoryInterfaceName(Element element) {
		return NameUtil.capName(element.getName()) + "Repository";
	}

	public static String getRepositoryClassName(Unit unit) {
		return NameUtil.capName(unit.getName()) + "RepositoryImpl";
	}

	public static String getRepositoryClassName(Element element) {
		return NameUtil.capName(element.getName()) + "RepositoryImpl";
	}

	public static String getRepositoryQualifiedName(Unit unit) {
		return getRepositoryPackageName(unit) + "." + getRepositoryClassName(unit);
	}

	public static String getRepositoryQualifiedName(Namespace namespace, Element element) {
		return getRepositoryPackageName(namespace) + "." + getRepositoryClassName(element);
	}

	public static String getRepositoryParentInterfaceName(Unit unit) {
		return null;
	}

	public static String getRepositoryParentInterfaceName(Element element) {
		return null;
	}

	public static String getRepositoryParentClassName(Unit unit) {
		return "org.aries.data.AbstractRepository";
	}

	public static String getRepositoryParentClassName(Element element) {
		return null;
	}

	public static String getRepositoryContextName(Unit unit) {
		String qualifiedName = getRepositoryQualifiedName(unit);
		String simpleName = getRepositoryNameUncapped(unit);
		return getContextName(qualifiedName, simpleName);
	}
	
	public static String getRepositoryContextName(Namespace namespace, Element element) {
		String qualifiedName = getRepositoryQualifiedName(namespace, element);
		String simpleName = getRepositoryNameUncapped(element);
		return getContextName(qualifiedName, simpleName);
	}

	
	
	/*
	 * DAO bean properties
	 * -------------------
	 */
	
	public static String getDAOName(Element element) {
		return element.getName() + "Dao";
	}

	public static String getDAONameCapped(Element element) {
		return NameUtil.capName(element.getName()) + "Dao";
	}

	public static String getDAONameUncapped(Element element) {
		return NameUtil.uncapName(element.getName()) + "Dao";
	}
	
	public static String getDAOPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".dao";
	}
	
//	public static String getDAOPackageName(Element element) {
//		return getDAOPackageName(element.getType());
//	}

	public static String getDAOPackageName(Field field) {
		return getDAOPackageName(field.getType());
	}

	public static String getDAOPackageName(String type) {
		return TypeUtil.getPackageName(type) + ".dao";
	}

	public static String getDAOInterfaceName(String name) {
		return NameUtil.capName(name) + "Dao";
	}

	public static String getDAOInterfaceName(Element element) {
		return getDAOInterfaceNameFromType(element.getType());
	}

//	public static String getDAOInterfaceName(Field field) {
//		return getDAOInterfaceName(field.getType());
//	}

	public static String getDAOInterfaceNameFromType(String type) {
		return getDAOInterfaceName(TypeUtil.getLocalPart(type));
	}

	public static String getDAOClassName(String name) {
		return NameUtil.capName(name) + "DaoImpl";
	}

	public static String getDAOClassName(Element element) {
		return getDAOClassNameFromType(element.getType());
	}

//	public static String getDAOClassName(Field field) {
//		return getDAOClassName(field.getType());
//	}

	public static String getDAOClassNameFromType(String type) {
		return getDAOClassName(TypeUtil.getLocalPart(type));
	}

	public static String getDAOQualifiedInterfaceName(Namespace namespace, Element element) {
		return getDAOPackageName(namespace) + "." + getDAOInterfaceName(element);
	}

	public static String getDAOQualifiedClassName(Namespace namespace, Element element) {
		return getDAOPackageName(namespace) + "." + getDAOClassName(element);
	}

	public static String getDAOParentInterfaceName(Element element) {
		return "org.aries.common.dao.Dao";
	}

	public static String getDAOParentClassName(Element element) {
		return "org.aries.common.dao.DaoImpl";
	}


	/*
	 * Query bean properties
	 * ----------------------
	 */
	
	public static String getQueryName(Element element) {
		return element.getName() + "Query";
	}

	public static String getQueryNameCapped(Element element) {
		return NameUtil.capName(element.getName()) + "Query";
	}

	public static String getQueryNameUncapped(Element element) {
		return getQueryNameUncapped(element.getName());
	}

	public static String getQueryNameUncapped(Field field) {
		return getQueryNameUncapped(field.getName());
	}

	public static String getQueryNameUncapped(String name) {
		return NameUtil.uncapName(name) + "Query";
	}

	public static String getQueryPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".query";
	}
	
//	public static String getQueryPackageName(Element element) {
//		return getQueryPackageName(element.getType());
//	}

	public static String getQueryPackageName(Field field) {
		return getQueryPackageName(field.getType());
	}

	public static String getQueryPackageName(String type) {
		return TypeUtil.getPackageName(type) + ".query";
	}

	public static String getQueryInterfaceName(Element element) {
		return getQueryInterfaceName(element.getType());
	}

	public static String getQueryInterfaceName(Field field) {
		return getQueryInterfaceName(field.getType());
	}

	public static String getQueryInterfaceName(String type) {
		return getQueryClassName(type);
	}

	public static String getQueryClassName(Element element) {
		return getQueryClassName(element.getType());
	}

	public static String getQueryClassName(Field field) {
		return getQueryClassName(field.getType());
	}

	public static String getQueryClassName(String type) {
		return TypeUtil.getClassName(type) + "Query";
	}

	public static String getQueryQualifiedName(Namespace namespace, Element element) {
		return getQueryPackageName(namespace) + "." + getQueryClassName(element);
	}
	
	public static String getQueryParentInterfaceName(Element element) {
		return null;
	}

	public static String getQueryParentClassName(Element element) {
		String entityClassName = "T"; //DataLayerHelper.getEntityClassName(element);
		return "org.aries.common.query.AbstractQuery<"+entityClassName+">";
	}
	
	
	/*
	 * Mapper bean properties
	 * ----------------------
	 */
	
	public static String getMapperName(Element element) {
		return element.getName() + "Mapper";
	}

	public static String getMapperNameCapped(Element element) {
		return NameUtil.capName(element.getName()) + "Mapper";
	}

	public static String getMapperNameUncapped(Element element) {
		return getMapperNameUncapped(element.getName());
	}

	public static String getMapperNameUncapped(Field field) {
		return getMapperNameUncapped(field.getName());
	}

	public static String getMapperNameUncapped(String name) {
		return NameUtil.uncapName(name) + "Mapper";
	}

	public static String getMapperPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".map";
	}

	public static String getMapperPackageName(Field field) {
		return getMapperPackageName(field.getType());
	}

	public static String getMapperPackageName(String type) {
		return TypeUtil.getPackageName(type) + ".map";
	}

	public static String getMapperInterfaceName(Element element) {
		return getMapperInterfaceName(element.getType());
	}

	public static String getMapperInterfaceName(Field field) {
		return getMapperInterfaceName(field.getType());
	}

	public static String getMapperInterfaceName(String type) {
		return getMapperClassName(type);
	}

	public static String getMapperClassName(Element element) {
		return getMapperClassName(element.getType());
	}

	public static String getMapperClassName(Field field) {
		return getMapperClassName(field.getType());
	}

	public static String getMapperClassName(String type) {
		return TypeUtil.getClassName(type) + "Mapper";
	}

	public static String getMapperQualifiedName(String namespace, Element element) {
		return ProjectLevelHelper.getPackageName(namespace) + ".map." + getMapperClassName(element);
	}
	
	public static String getMapperQualifiedName(Namespace namespace, Element element) {
		return getMapperQualifiedName(namespace.getUri(), element);
	}
	
	public static String getMapperParentInterfaceName(Element element) {
		return null;
	}

	public static String getMapperParentClassName(Element element) {
		return "org.aries.data.map.AbstractMapper";
	}
	

	/*
	 * Importer bean properties
	 * ------------------------
	 */
	
	public static String getImporterName(Element element) {
		return element.getName() + "Importer";
	}

	public static String getImporterNameCapped(Element element) {
		return NameUtil.capName(element.getName()) + "Importer";
	}

	public static String getImporterNameUncapped(Element element) {
		//String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		//return getImporterNameUncapped(elementType);
		return getImporterNameUncapped(element.getName());
	}

	public static String getImporterNameUncapped(Field field) {
		return getImporterNameUncapped(field.getName());
	}

	public static String getImporterNameUncapped(String name) {
		return NameUtil.uncapName(name) + "Importer";
	}

	public static String getImporterPackageName(Namespace namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".process";
	}
	
//	public static String getImporterPackageName(Element element) {
//		return getImporterPackageName(element.getType());
//	}

//	public static String getImporterPackageName(Field field) {
//		return getImporterPackageName(field.getType());
//	}

	public static String getImporterPackageName(String type) {
		return TypeUtil.getPackageName(type) + ".process";
	}
	
	public static String getImporterInterfaceName(Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		return getImporterInterfaceName(elementType);
		//return getImporterInterfaceName(element.getName());
	}

//	public static String getImporterInterfaceName(Field field) {
//		return getImporterInterfaceName(field.getType());
//	}

	public static String getImporterInterfaceName(String name) {
		return NameUtil.capName(name) + "Importer";
	}

	public static String getImporterClassName(Element element) {
		String elementType = ModelLayerHelper.getElementTypeLocalPartUncapped(element);
		return getImporterClassName(elementType);
		//return getImporterClassName(element.getName());
	}

//	public static String getImporterClassName(Field field) {
//		return getImporterClassName(field.getType());
//	}

	public static String getImporterClassName(String name) {
		return NameUtil.capName(name) + "Importer";
	}

	public static String getImporterParentInterfaceName(Element element) {
		return null;
	}

	public static String getImporterParentClassName(Element element) {
		return null;
	}

	public static String getMapperFixturePackageName(String namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".util";
	}

	public static String getMapperFixtureClassName(Namespace namespace) {
		return getMapperFixtureClassName(namespace.getUri());
	}
	
	public static String getMapperFixtureClassName(String namespaceUri) {
		String packageName = ProjectLevelHelper.getPackageName(namespaceUri);
		String baseName = NameUtil.getLastSegmentFromPackageName(packageName);
		baseName = NameUtil.toCamelCase(baseName);
		baseName = NameUtil.capName(baseName);
		String className = baseName + "MapperFixture";
		return className;
	}

	public static String getMapperFixtureQualifiedName(Namespace namespace) {
		return getMapperFixtureQualifiedName(namespace.getUri());
	}

	public static String getMapperFixtureQualifiedName(String namespaceUri) {
		return getMapperFixturePackageName(namespaceUri) + "." + getMapperFixtureClassName(namespaceUri);
	}

	
	
	/*
	 * CacheUnit bean properties
	 * -------------------------
	 */
	
	public static String getCacheUnitName(Cache cacheUnit) {
		return cacheUnit.getName();
	}

	public static String getCacheUnitNameCapped(Cache cacheUnit) {
		return NameUtil.capName(getCacheUnitName(cacheUnit));
	}

	public static String getCacheUnitNameUncapped(Cache cacheUnit) {
		return NameUtil.uncapName(getCacheUnitName(cacheUnit));
	}

	public static String getCacheUnitPackageName(String namespace, Cache cacheUnit) {
		return ProjectLevelHelper.getPackageName(namespace) + ".data." + getCacheUnitNameUncapped(cacheUnit);
	}

	public static String getCacheUnitInterfaceName(Cache cacheUnit) {
		return getCacheUnitInterfaceName(cacheUnit.getType());
	}

	public static String getCacheUnitInterfaceName(String type) {
		return TypeUtil.getClassName(type);
	}

	public static String getCacheUnitClassName(Cache cacheUnit) {
		return getCacheUnitClassName(cacheUnit.getType());
	}

	public static String getCacheUnitClassName(String type) {
		return TypeUtil.getClassName(type) + "Impl";
	}

	public static String getCacheUnitQualifiedName(Namespace namespace, Cache cacheUnit) {
		return getCacheUnitQualifiedName(namespace.getUri(), cacheUnit);
	}

	public static String getCacheUnitQualifiedName(String namespace, Cache cacheUnit) {
		return getCacheUnitPackageName(namespace, cacheUnit) + "." + getCacheUnitInterfaceName(cacheUnit);
	}
	
	public static String getCacheUnitTypeName(String namespace, Cache cacheUnit) {
		String packageName = getCacheUnitPackageName(namespace, cacheUnit);
		String interfaceName = getCacheUnitInterfaceName(cacheUnit);
		String typeName = TypeUtil.getTypeFromPackageAndClass(packageName, interfaceName);
		return typeName;
	}


	/*
	 * PersistenceUnit bean properties
	 * -------------------------------
	 */
	
	public static String getPersistenceUnitName(Unit unit) {
		return UnitUtil.getUnitName(unit);
	}

	public static String getPersistenceUnitNameCapped(Unit unit) {
		return NameUtil.capName(getPersistenceUnitName(unit));
	}

	public static String getPersistenceUnitNameUncapped(Unit unit) {
		return NameUtil.uncapName(getPersistenceUnitName(unit));
	}

	public static String getPersistenceUnitType(Unit unit) {
		return TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), getPersistenceUnitNameUncapped(unit));
	}
	
	public static String getPersistenceUnitPackageName(String namespace, Unit unit) {
		return ProjectLevelHelper.getPackageName(namespace) + ".data." + getPersistenceUnitNameUncapped(unit);
	}

	public static String getPersistenceUnitInterfaceName(Unit unit) {
		return getPersistenceUnitInterfaceName(unit.getName());
	}

	public static String getPersistenceUnitInterfaceName(String name) {
		return NameUtil.capName(name);
	}

	public static String getPersistenceUnitClassName(Unit unit) {
		return getPersistenceUnitClassName(unit.getName());
	}

	public static String getPersistenceUnitClassName(String name) {
		return NameUtil.capName(name);
	}

	public static String getPersistenceUnitQualifiedName(Namespace namespace, Unit unit) {
		return getPersistenceUnitQualifiedName(namespace.getUri(), unit);
	}

	public static String getPersistenceUnitQualifiedName(String namespace, Unit unit) {
		return getPersistenceUnitPackageName(namespace, unit) + "." + getPersistenceUnitClassName(unit);
	}

	public static String getPersistenceUnitTypeName(Namespace namespace, Unit unit) {
		return getPersistenceUnitTypeName(namespace.getUri(), unit);
	}
	
	public static String getPersistenceUnitTypeName(String namespace, Unit unit) {
		String packageName = getPersistenceUnitPackageName(namespace, unit);
		String interfaceName = getPersistenceUnitInterfaceName(unit);
		String typeName = TypeUtil.getTypeFromPackageAndClass(packageName, interfaceName);
		return typeName;
	}
	
	
	/*
	 * EntityFixture methods
	 * ---------------------
	 */

	public static String getEntityFixturePackageName(Namespace namespace) {
		return getEntityFixturePackageName(namespace.getUri());
	}
	
	public static String getEntityFixturePackageName(String namespace) {
		return ProjectLevelHelper.getPackageName(namespace) + ".util";
	}

	public static String getEntityFixtureClassName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getEntityFixtureClassName(namespace);
	}
	
	public static String getEntityFixtureClassName(Namespace namespace) {
		return getEntityFixtureClassName(namespace.getUri());
	}
	
	public static String getEntityFixtureClassName(String namespace) {
		//String packageName = ProjectLevelHelper.getPackageName(namespace);
		//String fixtureName = FixtureUtil.getFixtureNameFromPackageName(packageName);
		String lastSegment = NameUtil.getLastSegment(namespace, "/");
		lastSegment = NameUtil.toCamelCase(lastSegment);
		String className = NameUtil.capName(lastSegment) + "EntityFixture";
		//String className = NameUtil.capName(fixtureName);
		return className;
	}

	public static String getEntityFixtureQualifiedName(Element element) {
		String namespace = TypeUtil.getNamespace(element.getType());
		return getEntityFixtureQualifiedName(namespace);
	}
	
	public static String getEntityFixtureQualifiedName(Namespace namespace) {
		return getEntityFixtureQualifiedName(namespace.getUri());
	}

	public static String getEntityFixtureQualifiedName(String namespace) {
		return getEntityFixturePackageName(namespace) + "." + getEntityFixtureClassName(namespace); 
	}
	
	
	/*
	 * Helper bean properties
	 * ----------------------
	 */
	
	public static String getHelperNameUncapped(Unit unit) {
		return NameUtil.uncapName(getHelperClassName(unit));
	}

	public static String getHelperNameCapped(Unit unit) {
		return NameUtil.uncapName(getHelperClassName(unit));
	}

	public static String getHelperPackageName(Unit unit) {
		String packageFromNamespace = ProjectLevelHelper.getPackageName(unit.getNamespace());
		return packageFromNamespace + ".data" + "." + getPersistenceUnitNameUncapped(unit);
	}
	
	public static String getHelperClassName(Unit unit) {
		return NameUtil.capName(unit.getName()) + "Helper";
	}
	
	public static String getHelperQualifiedName(Unit unit) {
		return getHelperPackageName(unit) + "." + getHelperClassName(unit);
	}
	
	public static String getHelperNameUncapped(Cache cache) {
		return NameUtil.uncapName(getHelperClassName(cache));
	}

	public static String getHelperNameCapped(Cache cache) {
		return NameUtil.uncapName(getHelperClassName(cache));
	}

	public static String getHelperPackageName(Cache cache) {
		String packageFromNamespace = ProjectLevelHelper.getPackageName(cache.getNamespace());
		return packageFromNamespace + ".cache" + "." + getCacheUnitNameUncapped(cache);
	}
	
	public static String getHelperClassName(Cache cache) {
		return NameUtil.capName(cache.getName()) + "Helper";
	}
	
	public static String getHelperQualifiedName(Cache cache) {
		return getHelperPackageName(cache) + "." + getHelperClassName(cache);
	}
	
	
	
	public static String getContextName(String qualifiedName, String simpleName) {
		if (context.isEnabled("useQualifiedContextNames")) {
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-1);
			String contextName = contextPrefix + ".data." + simpleName;
			return contextName; 
		}
		return simpleName;
	}

}
