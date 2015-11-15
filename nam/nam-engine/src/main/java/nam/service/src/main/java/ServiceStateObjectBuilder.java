package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Cache;
import nam.model.Field;
import nam.model.Item;
import nam.model.ListItem;
import nam.model.MapItem;
import nam.model.Service;
import nam.model.SetItem;
import nam.model.util.CacheUtil;
import nam.model.util.ServiceUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Service State module {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceStateObjectBuilder extends AbstractBeanBuilder {

	public ServiceStateObjectBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = service.getNamespace();
		packageName = ProjectLevelHelper.getPackageName(namespace);
		className = NameUtil.capName(service.getName())+"State";
//		if (!StringUtils.isEmpty(service.getClassName()))
//			className = service.getClassName();
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setParentClassName("ServiceState");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, service);
		initializeClassConstructors(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("common.tx.state.ServiceState");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		String className = NameUtil.capName(service.getName()) + "State";
		CodeUtil.addSerialVersionUIDField(modelClass);
		CodeUtil.addStaticLoggerField(modelClass, className);
		createCurrentStateFilename(modelClass, service);
		createShadowStateFilename(modelClass, service);
		createAttributesForDataItems(modelClass, service);
	}

	protected void createCurrentStateFilename(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("LATEST_STATE_FILENAME");
		attribute.setDefault("\""+service.getName()+"_CurrentState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createShadowStateFilename(ModelClass modelClass, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("SHADOW_STATE_FILENAME");
		attribute.setDefault("\""+service.getName()+"_ShadowState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createAttributesForDataItems(ModelClass modelClass, Service service) {
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			if (cacheUnit != null) {
				List<Item> items = CacheUtil.getItems(cacheUnit);
				if (items == null || items.size() == 0) {
					//TODO sort these into alphabetical order
					createAttributes(modelClass, items);
				}
			}
		}
	}
	
	public <T extends Field> void createAttributes(ModelClass modelClass, List<T> items) {
		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			String name = item.getName();
			String type = item.getType();
			String packageName = util.getPackageNameFromXSDType(type);
			String className = util.getClassNameFromXSDType(type);

			ModelAttribute instanceAttribute = new ModelAttribute();
			instanceAttribute.setModifiers(Modifier.PRIVATE);
			instanceAttribute.setName(name);
			instanceAttribute.setPackageName(packageName);
			
			if (item instanceof MapItem) {
				MapItem mapItem = (MapItem) item;
				String key = mapItem.getKey();
				String keyPackageName = util.getPackageNameFromXSDType(key);
				String keyClassName = util.getClassNameFromXSDType(key);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass(packageName+"."+className);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
				instanceAttribute.setClassName("Map<"+keyClassName+", "+className+">");
				
			} else if (item instanceof ListItem) {
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass(packageName+"."+className);
				instanceAttribute.setClassName("List<"+className+">");

			} else if (item instanceof SetItem) {
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass(packageName+"."+className);
				instanceAttribute.setClassName("Set<"+className+">");
				
			} else if (item instanceof Item) {
				modelClass.addImportedClass(packageName+"."+className);
				instanceAttribute.setClassName(className);
			}
			
			modelClass.addInstanceAttribute(instanceAttribute);
			modelClass.addImportedClass(packageName+"."+className);
		}
	}

	protected void initializeClassConstructors(ModelClass modelClass, Service service) throws Exception {
		createConstructor_Default(modelClass, service);
		createConstructor_ParentState(modelClass, service);
	}
	
	protected void createConstructor_Default(ModelClass modelClass, Service service) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		List<String> sourceLines = new ArrayList<String>();
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			List<Item> items = CacheUtil.getItems(cacheUnit);
			if (items == null || items.size() == 0) {
				sourceLines.addAll(createSourceForFactoryMethod(modelClass, items));
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void createConstructor_ParentState(ModelClass modelClass, Service service) {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String className = NameUtil.capName(service.getName()) + "State";

		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("parent");
		modelParameter.setPackageName(packageName);
		modelParameter.setClassName(className);
		modelConstructor.addParameter(modelParameter);
		
		List<String> sourceLines = new ArrayList<String>();
		sourceLines.add("super(parent);");
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			List<Item> items = CacheUtil.getItems(cacheUnit);
			if (items == null || items.size() == 0) {
				sourceLines.addAll(createSourceForFactoryMethod(modelClass, items, true));
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		createMethod_CreateState(modelClass, service);
		createMethod_ResetState(modelClass, service);
		createMethod_GetDerivedState(modelClass, service);
	}
	
	protected void createMethod_CreateState(ModelClass modelClass, Service service) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String className = NameUtil.capName(service.getName())+"State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+className+"();"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ResetState(ModelClass modelClass, Service service) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String className = NameUtil.capName(service.getName())+"State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				className+" state = createState();",
				"return state;"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetDerivedState(ModelClass modelClass, Service service) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String className = NameUtil.capName(service.getName())+"State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createSuppressWarningsAnnotation("unchecked"));
		modelOperation.setName("getDerivedState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+className+"(this);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
}
