package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Process;
import nam.model.Unit;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.service.src.main.java.AbstractDataUnitManagerMBeanBuilder;
import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Builds an MBean interface for the DataUnit Manager {@link modelInterface} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitManagerMBeanBuilder extends AbstractDataUnitManagerMBeanBuilder {

	public DataUnitManagerMBeanBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelInterface> buildInterfaces(Process process) throws Exception {
		return buildInterfaces(process.getNamespace(), process.getDataUnits());
	}
	
	public List<ModelInterface> buildInterfaces(String namespace, List<Unit> units) throws Exception {
		List<ModelInterface> modelInterfacees = new ArrayList<ModelInterface>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			context.setUnit(unit);
			modelInterfacees.add(buildInterface(namespace, unit));
		}
		return modelInterfacees;
	}
	
	public ModelInterface buildInterface(String namespace, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String managerClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "ManagerMBean";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "ManagerMBean";
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
		initializeInterface(modelInterface, unit);
		return modelInterface; 
	}

	public void initializeInterface(ModelInterface modelInterface, Unit unit) throws Exception {
		this.modelUnit = modelInterface;
		initializeImportedClasses(modelInterface);
		initializeInterfaceAnnotations(modelInterface);
		initializeInstanceFields(modelInterface, "data");
		initializeInstanceMethods(modelInterface, unit);
	}
	
	protected void initializeInstanceMethods(ModelInterface modelInterface, Unit unit) throws Exception {
		createMethod_ClearContext(modelInterface, unit);
		//createMethod_CreateState(modelInterface, unit);
		//createMethod_ResetState(modelInterface, unit);
		createMethod_UpdateState(modelInterface, unit);
		//createMethod_SaveState(modelInterface, unit);
		createMethod_CommitState(modelInterface, unit);
		createMethods_DataAccess(modelInterface, unit, SourceType.SharedCache);
		//createMethods_DataAccess(modelInterface, unit, SourceType.CurrentState);
		//createMethods_DataAccess(modelInterface, unit, SourceType.PendingState);
		//createMethods_DataAccess(modelInterface, unit, SourceType.PreparedState);
	}

	protected void createMethod_ClearContext(ModelInterface modelInterface, Unit unit) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clearContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelInterface.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_UpdateState(ModelInterface modelInterface, Unit unit) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelInterface.addInstanceOperation(modelOperation);
	}

	protected void createMethod_SaveState(ModelInterface modelInterface, Unit unit) throws Exception {
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		String beanName = "state";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("saveState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		modelOperation.addParameter(CodeUtil.createParameter(className, beanName));
		modelInterface.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CommitState(ModelInterface modelInterface, Unit unit) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("commitState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelInterface.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Unit unit, SourceType sourceType) throws Exception {
		if (unit != null) {
			List<Element> elements = UnitUtil.getElements(unit);
			if (elements == null || elements.size() == 0) {
				log.warn("No items found in unit: "+unit.getName());
			} else {
				createMethods_DataAccess(modelUnit, elements, sourceType);
			}
		}
	}
	
	protected boolean isResultRequired(MethodType methodType) {
		if (methodType == MethodType.RemoveAll ||
			methodType == MethodType.RemoveAsItem ||
			methodType == MethodType.RemoveAsItemByKey ||
			methodType == MethodType.RemoveAsListByKeys ||
			methodType == MethodType.RemoveAsList ||
			methodType == MethodType.RemoveMatchingAsList)
				return false;
		return super.isResultRequired(methodType);
	}

}
