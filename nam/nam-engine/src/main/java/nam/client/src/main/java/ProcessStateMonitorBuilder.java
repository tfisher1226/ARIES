package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Process State module {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessStateMonitorBuilder extends AbstractBeanBuilder {

	public ProcessStateMonitorBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Process process) throws Exception {
		String namespace = ProcessUtil.getNamespace(process);
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process) + "StateMonitor";
		String rootName = ServiceLayerHelper.getProcessRootName(process);
		String beanName = NameUtil.uncapName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("AbstractMonitor");
		initializeClass(modelClass, process);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Process process) throws Exception {
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, process);
		initializeClassConstructors(modelClass, process);
		initializeInstanceMethods(modelClass, process);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("common.tx.management.AbstractMonitor");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Process process) throws Exception {
		//CodeUtil.addStaticLoggerField(modelClass, className);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Process process) throws Exception {
		createConstructor(modelClass, process);
	}
	
	protected void createConstructor(ModelClass modelClass, Process process) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter hostNameParameter = CodeUtil.createParameter_HostName();
		ModelParameter portNumberParameter = CodeUtil.createParameter_PortNumber();
		modelConstructor.addParameter(hostNameParameter);
		modelConstructor.addParameter(portNumberParameter);
		List<String> sourceLines = new ArrayList<String>();
		sourceLines.add("setHost(host);");
		sourceLines.add("setPort(port);");
		sourceLines.add("getURL();");
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Process process) throws Exception {
		createMethod_GetMBeanName(modelClass, process);
		createMethod_GetMBeanClass(modelClass, process);
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			createMethods_DataAccess(modelClass, cacheUnit, SourceType.JMXInvocation);
		}
	}
	
	protected void createMethod_GetMBeanName(ModelClass modelClass, Process process) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		String baseName = rootName + "StateManager";
		String mbeanName = NameUtil.splitStringUsingUnderscores(baseName) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource("return "+baseName+"MBean."+mbeanName.toUpperCase()+";");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetMBeanClass(ModelClass modelClass, Process process) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("getMBeanClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Class<?>");
		String mbeanClass = rootName + "StateManagerMBean";
		String sourceCode = CodeUtil.createMethodSource("return "+mbeanClass+".class;");
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
}
