package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Operation;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds a simple, standalone Java application to launch a server-side 
 * service implementation object {@link ModelClass} given a {@link Service} 
 * Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceClassMainBuilder extends AbstractBeanBuilder {

	private ServiceClassMainProvider serviceClassMainProvider;
	
	
	public ServiceClassMainBuilder(GenerationContext context) {
		serviceClassMainProvider = new ServiceClassMainProvider(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = ServiceUtil.getNamespace(service);
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String className = ServiceLayerHelper.getServiceInterfaceName(service) + "Main";
		String rootName = ServiceUtil.getRootName(service);
		String serviceName = NameUtil.capName(interfaceName);
		String beanName = NameUtil.capName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setNamespace(namespace);
		
		ModelAttribute fileNameAttribute = new ModelAttribute();
		fileNameAttribute.setModifiers(Modifier.PUBLIC+Modifier.STATIC+Modifier.FINAL);
		fileNameAttribute.setName("FILE_NAME");
		fileNameAttribute.setClassName("String");
		fileNameAttribute.setDefault("\""+NameUtil.uncapName(serviceName)+".aries\"");
		fileNameAttribute.setGenerateGetter(false);
		fileNameAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(fileNameAttribute);

		ModelAttribute runtimeHomeAttribute = new ModelAttribute();
		runtimeHomeAttribute.setModifiers(Modifier.PUBLIC+Modifier.STATIC+Modifier.FINAL);
		runtimeHomeAttribute.setName("RUNTIME_HOME");
		runtimeHomeAttribute.setClassName("String");
		runtimeHomeAttribute.setDefault("\"../"+context.getProjectName()+"\"");
		runtimeHomeAttribute.setGenerateGetter(false);
		runtimeHomeAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(runtimeHomeAttribute);

		ModelAttribute hostNameAttribute = new ModelAttribute();
		hostNameAttribute.setModifiers(Modifier.PUBLIC+Modifier.STATIC+Modifier.FINAL);
		hostNameAttribute.setName("HOST");
		hostNameAttribute.setClassName("String");
		hostNameAttribute.setDefault("\"localhost\"");
		hostNameAttribute.setGenerateGetter(false);
		hostNameAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(hostNameAttribute);

		ModelAttribute portNumberAttribute = new ModelAttribute();
		portNumberAttribute.setModifiers(Modifier.PUBLIC+Modifier.STATIC+Modifier.FINAL);
		portNumberAttribute.setName("PORT");
		portNumberAttribute.setClassName("int");
		portNumberAttribute.setDefault("9082");
		portNumberAttribute.setGenerateGetter(false);
		portNumberAttribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(portNumberAttribute);

		initializeClass(modelClass, service);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Service service) throws Exception {
		//modelClass.setParentClassName("org.aries.action.AbstractAction");
		initializeImportedClasses(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceOperations(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Service service) throws Exception {
		initializeImportedClasses(modelClass, ServiceUtil.getOperations(service));
	}

	protected void initializeImportedClasses(ModelClass modelClass, List<Operation> operations) throws Exception {
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			initializeImportedClasses(modelClass, operation);
		}
	}

	protected void initializeImportedClasses(ModelClass modelClass, Operation operation) throws Exception {
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		ModelOperation modelOperation = createMainOperation();
		modelOperation.addInitialSource(serviceClassMainProvider.generate(modelClass));
		modelClass.addStaticOperation(modelOperation);
	}

}
