package nam.client.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Cache;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
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
public class ServiceStateMonitorBuilder extends AbstractBeanBuilder {

	public ServiceStateMonitorBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ClientLayerHelper.getClientPackageName(service);
		String className = ClientLayerHelper.getClientClassName(service) + "Monitor";
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setParentClassName("AbstractMonitor");
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
		modelClass.addImportedClass("common.tx.management.AbstractMonitor");
	}

	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		//CodeUtil.addStaticLoggerField(modelClass, className);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Service service) throws Exception {
		createConstructor(modelClass, service);
	}
	
	protected void createConstructor(ModelClass modelClass, Service service) {
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

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			createMethods_DataAccess(modelClass, cacheUnit, SourceType.CurrentState);
		}
	}
	
}
