package nam.client.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds a simple, standalone Java application to launch a client request 
 * via the Service Proxy (i.e. client-side) Implementation {@link ModelClass} 
 * object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientProxyMainBuilder extends AbstractBeanBuilder {

	private ServiceProxyMainProvider serviceProxyMainProvider;
	
	
	public ClientProxyMainBuilder(GenerationContext context) {
		serviceProxyMainProvider = new ServiceProxyMainProvider(context);
		this.context = context;
	}

	public ModelClass build(Service service) throws Exception {
		String namespace = service.getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		String serviceName = NameUtil.capName(service.getName());
		String className = serviceName+"ProxyMain";
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(serviceName);
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
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.findElement(parameter);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, parameter.getType());
		}
		if (!operation.getResults().isEmpty()) {
			Result result = operation.getResults().get(0);
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Service service) throws Exception {
		ModelOperation mainOperation = createMainOperation();
		modelClass.addStaticOperation(mainOperation);
		Buf mainMethodSource = new Buf();
		mainMethodSource.putLine2("NodeManager nodeManager = new NodeManager();");
		mainMethodSource.putLine2("nodeManager.initialize();");
		modelClass.addImportedClass("org.aries.node.NodeManager");
		
		//modelClass.addInstanceOperation(createActionOperation(service));
		//modelClass.addInstanceOperation(createValidateOperation());
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PUBLIC+Modifier.STATIC);
			String operationName = "invoke"+NameUtil.capName(operation.getName());
			modelOperation.setName(operationName);
			modelOperation.addInitialSource(serviceProxyMainProvider.generate(modelClass, modelOperation, operation));
			modelClass.addInstanceOperation(modelOperation);
			mainMethodSource.putLine2(operationName+"();");
		}

		mainOperation.addInitialSource(mainMethodSource.get());
	}

}
