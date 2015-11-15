package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Field;
import nam.model.Process;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import nam.service.src.main.java.AbstractDataUnitManagerMBeanBuilder;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Builds an MBean interface for the Cache State Manager {@link modelInterface} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitManagerMBeanBuilder extends AbstractDataUnitManagerMBeanBuilder {

	public CacheUnitManagerMBeanBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelInterface> buildInterfaces(Process process) throws Exception {
		return buildInterfaces(process.getNamespace(), process.getCacheUnits());
	}
	
	public List<ModelInterface> buildInterfaces(String namespace, List<Cache> cacheUnits) throws Exception {
		List<ModelInterface> modelInterfacees = new ArrayList<ModelInterface>();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			context.setCache(cacheUnit);
			modelInterfacees.add(buildInterface(namespace, cacheUnit));
		}
		return modelInterfacees;
	}
	
	public ModelInterface buildInterface(String namespace, Cache cache) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String managerClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "ManagerMBean";
		String beanName = DataLayerHelper.getCacheUnitNameUncapped(cache) + "ManagerMBean";
		String beanType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(managerClassName);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(beanType);
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(managerClassName);
		modelInterface.setName(beanName);
		modelInterface.addImportedClass(packageName+"."+managerClassName);
		initializeInterface(modelInterface, cache);
		return modelInterface; 
	}

	public void initializeInterface(ModelInterface modelInterface, Cache cache) throws Exception {
		this.modelUnit = modelInterface;
		initializeImportedClasses(modelInterface);
		initializeInterfaceAnnotations(modelInterface);
		initializeInstanceFields(modelInterface, "cache");
		initializeInstanceMethods(modelInterface, cache);
	}

	protected void initializeInstanceMethods(ModelInterface modelInterface, Cache cache) throws Exception {
		//createMethod_CreateState(modelInterface, cache);
		//createMethod_ResetState(modelInterface, cache);
		//createMethod_UpdateState(modelInterface, cache);
		//createMethod_SaveState(modelInterface, cache);
		//createMethod_CommitState(modelInterface, cache);
		createMethods_DataAccess(modelInterface, cache, SourceType.SharedCache);
		//createMethods_DataAccess(modelInterface, cache, SourceType.CurrentState);
		//createMethods_DataAccess(modelInterface, cache, SourceType.PendingState);
		//createMethods_DataAccess(modelInterface, cache, SourceType.PreparedState);
	}

	protected void createMethod_UpdateState(ModelInterface modelInterface, Cache cache) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelInterface.addInstanceOperation(modelOperation);
	}

	protected void createMethod_SaveState(ModelInterface modelInterface, Cache cache) throws Exception {
		String className = CacheUtil.getClassName(cache) + "State";
		String beanName = "state";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("saveState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(CodeUtil.createParameter(className, beanName));
		modelInterface.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CommitState(ModelInterface modelInterface, Cache cache) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("commitState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelInterface.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Cache cache, SourceType sourceType) throws Exception {
		if (cache != null) {
			List<Field> fields = ElementUtil.getFields(cache);
			if (fields == null || fields.size() == 0) {
				log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, fields, sourceType);
			}
		}
	}

}
