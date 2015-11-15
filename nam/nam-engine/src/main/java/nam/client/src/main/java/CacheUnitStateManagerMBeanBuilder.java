package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Process;
import nam.model.util.CacheUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelInterface;


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
public class CacheUnitStateManagerMBeanBuilder extends AbstractBeanBuilder {

	public CacheUnitStateManagerMBeanBuilder(GenerationContext context) {
		super(context);
	}
	
	public List<ModelInterface> buildInterfaces(Process process) throws Exception {
		return buildInterfaces(process.getCacheUnits());
	}

	public List<ModelInterface> buildInterfaces(List<Cache> cacheUnits) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			modelInterfaces.add(buildInterface(cacheUnit));
		}
		return modelInterfaces;
	}
	
	public ModelInterface buildInterface(Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "ManagerMBean";
		String rootName = CacheUtil.getRootName(cache);
		String beanName = CacheUtil.getCacheName(cache);
		String typeName = CacheUtil.getType(cache);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(typeName);
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(className);
		modelInterface.setName(beanName);
		initializeInterface(modelInterface, cache);
		return modelInterface; 
	}

	public void initializeInterface(ModelInterface modelInterface, Cache cache) throws Exception {
		initializeInstanceFields(modelInterface, cache);
		initializeInstanceMethods(modelInterface, cache);
	}

	protected void initializeInstanceFields(ModelInterface modelInterface, Cache cache) throws Exception {
		createMBeanNameConstant(modelInterface, cache);
	}

	protected void createMBeanNameConstant(ModelInterface modelInterface, Cache cache) {
		String className = CacheUtil.getClassName(cache) + "Manager";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		attribute.setClassName(String.class.getName());
		String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
		attribute.setName(mbeanName.toUpperCase());
		attribute.setDefault("\""+packageName+":name="+className+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelInterface.addInstanceAttribute(attribute);
	}
	
	protected void initializeInstanceMethods(ModelInterface modelInterface, Cache cache) throws Exception {
		createMethods_DataAccess(modelInterface, cache, SourceType.JMXInvocation);
	}
	
	
}
