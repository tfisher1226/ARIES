package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelInterface;


/**
 * Builds an MBean interface for the Process State Manager {@link modelInterface} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessStateManagerMBeanBuilder extends AbstractBeanBuilder {

	public ProcessStateManagerMBeanBuilder(GenerationContext context) {
		super(context);
	}

	public ModelInterface build(Process process) throws Exception {
		String namespace = ProcessUtil.getNamespace(process);
		String processName = ServiceLayerHelper.getProcessName(process);
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process) + "StateManagerMBean";
		String rootName = ServiceLayerHelper.getProcessRootName(process);
		String beanName = NameUtil.uncapName(className);

		setRootName(rootName);
		setPackageName(packageName);
		setClassName(className);
		setBeanName(beanName);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(className);
		modelInterface.setName(beanName);
		initializeClass(modelInterface, process);
		return modelInterface; 
	}

	public void initializeClass(ModelInterface modelInterface, Process process) throws Exception {
		initializeInstanceFields(modelInterface, process);
		initializeInstanceMethods(modelInterface, process);
	}

	protected void initializeInstanceFields(ModelInterface modelInterface, Process process) throws Exception {
		createMBeanNameConstant(modelInterface, process);
	}

	protected void createMBeanNameConstant(ModelInterface modelInterface, Process process) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		attribute.setClassName(String.class.getName());
		String mbeanName = NameUtil.splitStringUsingUnderscores(rootName+"StateManager") + "_MBEAN_NAME";
		attribute.setName(mbeanName.toUpperCase());
		attribute.setDefault("\""+packageName+":name="+rootName+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelInterface.addInstanceAttribute(attribute);
	}
	
	protected void initializeInstanceMethods(ModelInterface modelInterface, Process process) throws Exception {
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			createMethods_DataAccess(modelInterface, cacheUnit, SourceType.CurrentState);
			//createMethods_DataAccess(modelInterface, process.getCache(), SourceType.JMXInvocation);
		}
	}
	
	
}
