package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Field;
import nam.model.Item;
import nam.model.ListItem;
import nam.model.MapItem;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Service;
import nam.model.SetItem;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Service State Processor {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceStateProcessorBuilder extends AbstractBeanBuilder {

	public ServiceStateProcessorBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = service.getNamespace();
		setPackageName(context.getPackageName(service));
		setRootName(NameUtil.capName(service.getName()));
		setBeanName(service.getName());
		setClassName(rootName+"StateProcessor");
//		if (!StringUtils.isEmpty(service.getClassName()))
//			setClassName(service.getClassName());
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.addImplementedInterface("ServiceStateProcessor<"+rootName+"State>");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeImportedClasses(modelClass);
		initializeClassConstructors(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("common.tx.state.ServiceStateProcessor");
		modelClass.addImportedClass("org.aries.registry.ProcessLocator");
		modelClass.addImportedClass("org.aries.runtime.BeanContext");
	}

	protected void initializeClassConstructors(ModelClass modelClass, Service service) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"// nothing for now"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			initializeInstanceFields(modelClass, service, cache);
		}
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service, Cache cache) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		List<Field> itemsFromCache = getItemsFromCache(cache);
		Iterator<Field> iterator = itemsFromCache.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field item = iterator.next();
			String type = item.getType();
			String name = NameUtil.capName(item.getName());
			String packageName = util.getPackageNameFromXSDType(type);
			String className = util.getClassNameFromXSDType(type);
			modelClass.addImportedClass(packageName+"."+className);
			ModelAttribute modelAttribute = new ModelAttribute();
			modelClass.addInstanceAttribute(modelAttribute);
			modelAttribute.setModifiers(Modifier.PRIVATE);

			if (item instanceof Item) {
				modelAttribute.setClassName(className);
				modelAttribute.setName("pending"+name);

			} else if (item instanceof ListItem) {
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");
				modelAttribute.setName("pending"+name);
				modelAttribute.setClassName("List<"+className+">");
				modelAttribute.setDefault("new ArrayList<"+className+">()");
			
			} else if (item instanceof SetItem) {
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");
				modelClass.addImportedClass(packageName+"."+className);
				modelAttribute.setName("pending"+name);
				modelAttribute.setClassName("Set<"+className+">");
				modelAttribute.setDefault("new HashSet<"+className+">()");

			} else if (item instanceof MapItem) {
				MapItem mapItem = (MapItem) item;
				String key = mapItem.getKey();
				String keyPackageName = util.getPackageNameFromXSDType(key);
				String keyClassName = util.getClassNameFromXSDType(key);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.HashMap");
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
				modelAttribute.setName("pending"+name);
				modelAttribute.setClassName("Map<"+keyClassName+", "+className+">");
				modelAttribute.setDefault("new HashMap<"+keyClassName+", "+className+">()");
			}
		}
		
	}

	protected ModelAttribute createAttribute_SerialVersionUID() {
		ModelAttribute staticAttribute = new ModelAttribute();
		staticAttribute.setModifiers(Modifier.PUBLIC+Modifier.FINAL+Modifier.STATIC);
		staticAttribute.setClassName("long");
		staticAttribute.setName("serialVersionUID");
		staticAttribute.setDefault(1L);
		return staticAttribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		createMethod_ResetState(modelClass, service);
		createMethod_ValidateState(modelClass, service);
		createMethod_UpdateState(modelClass, service);
		createMethod_ProcessRequest(modelClass, service);
	}
	
	protected void createMethod_ResetState(ModelClass modelClass, Service service) throws Exception {
		String serviceName = NameUtil.capName(service.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(serviceName+"State", "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			List<Field> itemsFromCache = getItemsFromCache(cache);
			Iterator<Field> iterator2 = itemsFromCache.iterator();
			while (iterator2.hasNext()) {
				Field item = iterator2.next();
				String name = NameUtil.capName(item.getName());
				if (item instanceof Item) {
					sourceLines.add("state.set"+name+"(null);");
				} else {
					sourceLines.add("state.get"+name+"().clear();");
				}
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ValidateState(ModelClass modelClass, Service service) throws Exception {
		String serviceName = NameUtil.capName(service.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("validateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(boolean.class.getName());
		ModelParameter modelParameter = CodeUtil.createParameter(serviceName+"State", "state");
		modelOperation.addParameter(modelParameter);
		
		List<String> sourceLines = new ArrayList<String>();
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			List<Field> itemsFromCache = getItemsFromCache(cache);
			if (itemsFromCache.size() == 0) {
				sourceLines.add("return true;");
			} else {
				Buf buf = new Buf();
				buf.put("return ");
				Iterator<Field> iterator2 = itemsFromCache.iterator();
				for (int i=0; iterator2.hasNext(); i++) {
					Field item = iterator2.next();
					String name = NameUtil.capName(item.getName());
					if (i > 0)
						buf.put(" && ");
					buf.put("pending"+name+" != null");
				}
				buf.put(";");
				sourceLines.add(buf.get());
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_UpdateState(ModelClass modelClass, Service service) throws Exception {
		String serviceName = NameUtil.capName(service.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(serviceName+"State", "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			List<Field> itemsFromCache = getItemsFromCache(cache);
			Iterator<Field> iterator2 = itemsFromCache.iterator();
			while (iterator2.hasNext()) {
				Field item = iterator2.next();
				//String itemType = item.getType();
				//String itemName = item.getName();
				//String className = NameUtil.getLocalNameFromXSDType(itemType);
				String itemNameCapped = NameUtil.capName(item.getName());
				String pendingItemName = "pending"+itemNameCapped;
				if (item instanceof Item) {
					sourceLines.add("state.set"+itemNameCapped+"(null);");
				} else if (item instanceof ListItem) {
					sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
				} else if (item instanceof SetItem) {
					sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
				} else if (item instanceof MapItem) {
					//sourceLines.add("state.get"+name+"().clear();");
					//sourceLines.add("List<"+className+"> "+itemName+" = "+pendingItemName+".getElements();");
					sourceLines.add("state.get"+itemNameCapped+"().putAll("+pendingItemName+");");
				}
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ProcessRequest(ModelClass modelClass, Service service) throws Exception {
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String packageName = context.getPackageName(service);
		String serviceName = NameUtil.capName(service.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("processRequest");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.setResultType(packageName+"."+serviceName+"State");
		modelOperation.setResultType("void");

		//TODO Assuming only one service operation exists for now
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());
		Operation operation = ServiceUtil.getOperations(service).get(0);
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			modelOperation.addParameter(CodeUtil.createParameter(parameters, parameter));
			modelClass.addImportedClass(TypeUtil.getJavaName(parameter.getType()));
		}
		
		List<String> sourceLines = new ArrayList<String>();
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator2 = cacheUnits.iterator();
		while (iterator2.hasNext()) {
			Cache cacheUnit = iterator2.next();
			Process process = service.getProcess();
			if (process == null || cacheUnit == null) {
				throw new RuntimeException("CODE PROBLEM");
				
			} else {
				String processName = process.getName();
				String cacheName = cacheUnit.getName();
				//sourceLines.add("this.pendingBookOrders = request.getBookOrders();");
				//sourceLines.add("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
				//sourceLines.add(processName+" process = processLocator.lookup("+processName+".class, correlationId);");
				//sourceLines.add("process.receive"+serviceName+"(pendingBookOrders);");
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

}
