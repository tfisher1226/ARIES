package nam.service.src.test.java;

import nam.model.ModelLayerHelper;
import nam.model.Type;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.TestType;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelUnit;


public abstract class AbstractDataItemSourceGenerator {

	protected GenerationContext context;
	
	protected ModelUnit modelUnit;

	protected MethodType methodType;

	protected TestType testType;
	
	protected Type dataItem;

	protected String type;

	protected String keyType;
	
	protected String paramName;
	
	protected String keyParamName;
	
	protected String keyTypeName;

	protected String typeName;
	
	protected String fieldName;
	
	protected String instanceName;
	
	protected String structure;

	protected String structuredName;
	
	protected String structuredType;
	
	protected String structuredParam;
	
	protected String fixtureName;

	protected String helperName;

	//String contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
	//String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";
	//String cacheName = modelUnit.getName();
	//String cacheName = getBeanName();

	
	public AbstractDataItemSourceGenerator(GenerationContext context) {
		this.context = context;
	}

	public void initialize(ModelUnit modelUnit, Type dataItem, MethodType methodType, TestType testType) {
		this.modelUnit = modelUnit;
		this.dataItem = dataItem;
		this.methodType = methodType;
		this.testType = testType;
		
		type = dataItem.getType();
		keyType = dataItem.getKey();
		paramName = TypeUtil.getLocalPart(type);
		
		keyParamName = null;
		keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}

		typeName = NameUtil.capName(paramName);
		fieldName = NameUtil.capName(dataItem.getName());
		instanceName = NameUtil.uncapName(fieldName);
		
		structure = dataItem.getStructure();
		structuredName = TypeUtil.getStructuredName(dataItem, true);
		structuredType = TypeUtil.getStructuredType(dataItem, true);
		structuredParam = TypeUtil.getStructuredParam(dataItem, true);
		
		fixtureName = ModelLayerHelper.getFixtureClassName(type);
		helperName = ModelLayerHelper.getModelHelperBeanName(type);
	}
	
}
