package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;

import nam.client.ClientLayerHelper;
import nam.model.Cache;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelInterface;


/**
 * Builds an MBean interface for the Service Manager {@link modelInterface} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceMBeanBuilder extends AbstractBeanBuilder {

	public ServiceMBeanBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelInterface build(Service service) throws Exception {
		String namespace = ClientLayerHelper.getNamespace(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		//String interfaceName = ServiceUtil.getInterfaceName(service);
		String className = ClientLayerHelper.getClientInterfaceName(service) + "ManagerMBean";
		String rootName = ServiceUtil.getRootName(service);
		String beanName = NameUtil.capName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(className);
		modelInterface.setName(beanName);
		modelInterface.setNamespace(namespace);
		initializeInterface(modelInterface, service);
		return modelInterface; 
	}

	public void initializeInterface(ModelInterface modelInterface, Service service) throws Exception {
		initializeInstanceFields(modelInterface, service);
		initializeInstanceMethods(modelInterface, service);
	}

	protected void initializeInstanceFields(ModelInterface modelInterface, Service service) throws Exception {
		createMBeanNameConstant(modelInterface, service);
	}

	protected void createMBeanNameConstant(ModelInterface modelInterface, Service service) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		attribute.setClassName(String.class.getName());
		String mbeanName = NameUtil.splitStringUsingUnderscores(beanName) + "_MBEAN_NAME";
		attribute.setName(mbeanName.toUpperCase());
		attribute.setDefault("\""+packageName+":name="+rootName+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelInterface.addInstanceAttribute(attribute);
	}
	
	protected void initializeInstanceMethods(ModelInterface modelInterface, Service service) throws Exception {
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			createMethods_DataAccess(modelInterface, cacheUnit, SourceType.CurrentState);
			//createMethods_DataAccess(modelInterface, cacheUnit, false, true);
		}
	}
	
	
}
