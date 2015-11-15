package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Entity;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Result;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds a Process Defaults {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessDefaultsBuilder extends AbstractBeanBuilder {

	public ProcessDefaultsBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass buildClass(Process process) throws Exception {
		//String packageName = context.getModule().getGroupId();
		String namespace = process.getNamespace();
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process)  + "Defaults";
		String processName = ServiceLayerHelper.getProcessName(process);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(processName);
		initializeClass(modelClass, process);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Process process) throws Exception {
		initializeInterfaceAnnotations(modelClass, process);
		initializeImportedClasses(modelClass, process);
		initializeInstanceOperations(modelClass, process);
	}

	protected void initializeInterfaceAnnotations(ModelClass modelClass, Process process) throws Exception {
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Process process) throws Exception {
		initializeImportedClasses(modelClass, process.getOperations());
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
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		if (result != null) {
			Element element = context.findElement(result);
			if (element != null)
				addImportedClass(modelClass, element.getType());
			else addImportedClass(modelClass, result.getType());
		}
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Process process) throws Exception {
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			//TODO get this hack out of here:
			if (operation.getName().endsWith("StateManager"))
				continue;
			
			ModelOperation modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
			modelOperation.setName(NameUtil.uncapName(operation.getName())+"Enabled");
			modelOperation.setResultType("boolean");

			//List<Parameter> parameters = operation.getParameters();
			//Assert.isTrue(parameters.size() == 1, "One parameter supported for now");
			//Parameter parameter = parameters.get(0);

			//ModelParameter modelParameter = new ModelParameter();
			//modelParameter.setPackageName(TypeUtil.getPackageName(parameter.getType()));
			//modelParameter.setClassName(TypeUtil.getClassName(parameter.getType()));
			//modelParameter.setName(parameter.getName());
			//modelOperation.addParameter(modelParameter);

			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setClassName("int");
			modelParameter.setName("option");
			modelOperation.addParameter(modelParameter);
			
			//modelOperation.addAnnotation(AnnotationUtil.createWebMethodAnnotation());
			//modelClass.addImportedClass("javax.jws.WebMethod");
			
			List<Result> results = operation.getResults();
			Result result = null;
			if (!results.isEmpty())
				result = results.get(0);
			if (result != null) {
				String name = result.getName();
				String localPart = TypeUtil.getLocalPart(result.getType());
				if (name.toLowerCase().equals(localPart.toLowerCase()))
					name = "response";
				//modelOperation.addAnnotation(AnnotationUtil.createWebResultAnnotation(name));
				//modelInterface.addImportedClass("javax.jws.WebResult");
			}
			modelOperation.addInitialSource(generateSource_OptionStatusCheck());
			modelClass.addInstanceOperation(modelOperation);
			addImportedClasses(modelClass, operation);
		}
	}

	private String generateSource_OptionStatusCheck() {
		Buf buf = new Buf();
		buf.putLine2("return true;");
		return buf.get();
	}

	protected void initializeClassMethods(ModelClass modelClass, List<Entity> entities) throws Exception {
		Iterator<Entity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			initializeClassMethods(modelClass, entity);
		}
	}

	protected void initializeClassMethods(ModelClass modelClass, Entity entity) throws Exception {
		//modelInterface.addInterfaceOperation(createGetEntityOperation(entity));
		//modelInterface.addInterfaceOperation(createGetEntityListOperation(entity));
		//modelInterface.addInterfaceOperation(createSaveEntityOperation(entity));
		//String packageName = context.getModule().getGroupId()+".model";
		//modelInterface.addImportedClass(packageName+"."+entity.getType());
	}
	
}

//public List<Execution> buildExecutions(ModelService modelService) {
//List<Execution> executions = new ArrayList<Execution>();
//return executions;
//}
//
//public Execution buildExecution(ModelExecution modelExecution) {
//Execution execution = new Execution();
//return execution;
//}
//
//public List<Execution> buildExecutions(ModelService modelService) {
//List<Execution> executions = new ArrayList<Execution>();
//return executions;
//}
//
//public Execution buildExecution(ModelExecution modelExecution) {
//Execution execution = new Execution();
//return execution;
//}

