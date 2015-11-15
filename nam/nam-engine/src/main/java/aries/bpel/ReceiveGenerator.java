package aries.bpel;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.OperationType;

import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.MessageExchange;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Target;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.w3c.dom.Element;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ReceiveGenerator extends AbstractActivityGenerator<Receive> {

	public ReceiveGenerator(GenerationContext context, Process process) { 
		super(context, process);
		this.verbose = true;
	}

	public ModelOperation getCurrentOperation() {
		return currentOperation;
	}
	
	@Override
	public void generate(Receive receiveActivity) throws Exception {
		String operationName = CodeUtil.getOperationName(receiveActivity);

		//FromParts fromParts = receiveActivity.getFromParts();
		//Variable variable = receiveActivity.getVariable();
		Operation operation = receiveActivity.getOperation();
		//Sources sources = activity.getSources();
		//Targets targets = activity.getTargets();
		PortType portType = receiveActivity.getPortType();
		PartnerLink partnerLink = receiveActivity.getPartnerLink();
		MessageExchange messageExchange = receiveActivity.getMessageExchange();
		Definition enclosingDefinition = receiveActivity.getEnclosingDefinition();
		Boolean suppressJoinFailure = receiveActivity.getSuppressJoinFailure();

		currentOperation = new ModelOperation();
		currentOperation.setName(operationName);
		currentOperation.setModifiers(Modifier.PUBLIC);
		List<ModelParameter> parameters = currentOperation.getParameters();
		
//		ModelParameter modelParameter = new ModelParameter();
//		Message inputMessage = (Message) operation.getInput().getMessage();
//		String parameterType = TypeUtil.getTypeFromMessage(inputMessage);
//		String parameterPackage = TypeUtil.getPackageName(parameterType);
//		String parameterClass = TypeUtil.getClassName(parameterType);
//		String parameterName = TypeUtil.getLocalPart(parameterType);
//		modelParameter.setPackageName(parameterPackage);
//		modelParameter.setClassName(parameterClass);
//		modelParameter.setName(parameterName);
//		parameters.add(modelParameter);

		/*
		 * NOTE: Input will be null here if the declaration 
		 * of the Receive-command variable is not found.
		 */
		Message inputMessage = (Message) operation.getInput().getMessage();
		@SuppressWarnings("unchecked") Collection<Part> inputMessageParts = inputMessage.getParts().values();
		Iterator<Part> partIterator = inputMessageParts.iterator();
		for (int i=0; partIterator.hasNext(); i++) {
			Part part = partIterator.next();
			ModelParameter modelParameter = new ModelParameter();
			String parameterType = TypeUtil.getTypeFromMessagePart(part);
			String parameterPackage = TypeUtil.getPackageName(parameterType);
			String parameterClass = TypeUtil.getClassName(parameterType);
			//String parameterName = TypeUtil.getLocalPart(parameterType);
			String referenceName = inputMessage.getQName().getLocalPart()+"_"+part.getName();
			addImportedClass(parameterPackage+"."+parameterClass);
			modelParameter.setPackageName(parameterPackage);
			modelParameter.setClassName(parameterClass);
			modelParameter.setRefName(referenceName);
			modelParameter.setName(part.getName());
			parameters.add(modelParameter);
		}
		
		@SuppressWarnings("unchecked") Collection<Fault> faults = operation.getFaults().values();
		Iterator<Fault> faultIterator = faults.iterator();
		while (faultIterator.hasNext()) {
			Fault fault = faultIterator.next();
			String faultTypeName = TypeUtil.getTypeFromMessage((Message) fault.getMessage());
			String faultJavaName = TypeUtil.getJavaName(faultTypeName);
			String faultClassName = TypeUtil.getClassName(faultTypeName);
			currentOperation.addException(faultClassName);
			addImportedClass(faultJavaName);
			//hasCatchBlock = true;
		}

		Buf initialSource = new Buf();
		Buf completionSource = new Buf();

		Iterator<ModelParameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			ModelParameter modelParameter = parameterIterator.next();
			initialSource.putLine2("this."+modelParameter.getRefName()+" = "+modelParameter.getName()+";");

			//Variable variable = getVariable(modelParameter.getRefName());
			//org.aries.nam.model.old.Process namProcess = context.getProcessByName(process.getName());
			//ProcessVariable processVariable = ProcessStateUtil.getVariableByName(namProcess.getState(), modelParameter.getRefName());
			//Variable variable = variable.get(modelParameter.getName());
			//if (processVariable != null)
			//	initialSource.putLine2("this."+processVariable.getName()+" = "+modelParameter.getName()+";");
			//if (variable != null)
			//	initialSource.putLine2("this."+variable.getName()+" = "+modelParameter.getName()+";");
		}

		initialSource.put(generateSourceConditionStateAssignments(receiveActivity));
		//initialSource.putLine2("stateManager.getPendingBookOrdersPlaced().addAll(bookOrderRequest.getBookOrders().getElements());");

//		Sources sources = receiveActivity.getSources();
//		Iterator<Source> sourceIterator = sources.getChildren().iterator();
//		while (sourceIterator.hasNext()) {
//			Source source = sourceIterator.next();
//			Link link = source.getLink();
//			String linkName = NameUtil.getFilteredName(link.getName());
//			Condition condition = source.getTransitionCondition();
//			ExpressionGenerator expressionGenerator = new ExpressionGenerator(variables);
//			String conditionText = expressionGenerator.generate(condition.getBody());
//			initialSource.putLine2(linkName+" = "+conditionText+";");
//		}

		
		if (isInsideFlow()) {
			String returnType = "String";
			String exceptionType = "ErrorMessage";
			String exceptionVariable = "error";
			
			initialSource.putLine2("try {");
			initialSource.putLine3("Executor<"+returnType+", "+exceptionType+"> executor = createExecutor();");
			initialSource.putLine3("executor.execute();");
			initialSource.putLine3("return executor.getReturnValue();");
			initialSource.putLine2("} catch ("+exceptionType+" "+exceptionVariable+") {");
			initialSource.putLine3("throw "+exceptionVariable+";");
			initialSource.putLine2("}");
			addImportedClass("org.aries.process.Executor");

			List<ModelClass> flowActionClasses = new ArrayList<ModelClass>();
			
			EObject containingActivity = receiveActivity.eContainer();
			if (containingActivity instanceof Flow) {
				Flow flowActivity = (Flow) containingActivity;
				Iterator<Activity> iterator = flowActivity.getActivities().iterator();
				while (iterator.hasNext()) {
					Activity activity = (Activity) iterator.next();
					if (activity == receiveActivity)
						continue;
					ModelClass flowActionClass = buildFlowActionClass(activity);
					flowActionClasses.add(flowActionClass);
					addSupportingClass(flowActionClass);
				}
				
			} else if (containingActivity instanceof Sequence) {
				
			} else {
				throw new RuntimeException("Unexpected containing activity: "+containingActivity.getClass().getSimpleName());
			}
			
			ModelOperation createExecuterOperation = buildCreateExecuterOperation(returnType, exceptionType, flowActionClasses);
			addInstanceOperation(createExecuterOperation);
		}
		
//		if (hasCatchBlock && false) {
//			initialSource.putLine2("try {");
//			faultIterator = faults.iterator();
//			while (faultIterator.hasNext()) {
//				Fault fault = faultIterator.next();
//				Process process = context.getBpelResource().getProcess();
//				FaultHandler faultHandlers = process.getFaultHandlers();
//				EList<Catch> catchList = faultHandlers.getCatch();
//				Catch catchObject = catchList.get(0);
//				QName faultName = catchObject.getFaultName();
//				Variable faultVariable2 = catchObject.getFaultVariable();
//				
//				String faultTypeName = TypeUtil.getTypeFromMessage((Message) fault.getMessage());
//				String faultClassName = TypeUtil.getClassName(faultTypeName);
//				completionSource.putLine2("} catch ("+faultClassName+" e) {");
//				Variable faultVariable = getVariableForFaultType(faultTypeName);
//				if (faultVariable != null) {
//					String faultVariableName = faultVariable.getName();
//					completionSource.putLine3(faultVariableName +" = new "+faultClassName+"();");
//					completionSource.putLine3("throw "+faultVariableName+";");
//				} else {
//					completionSource.putLine3(faultClassName+" fault = new "+faultClassName+"();");
//					completionSource.putLine3("throw fault;");
//				}
//			}
//			completionSource.putLine2("}");
//		}
		
		if (operation.getStyle() == OperationType.REQUEST_RESPONSE) {
			Message outputMessage = (Message) operation.getOutput().getMessage();
			Assert.notNull(outputMessage, "OutputMessage must exist");
			@SuppressWarnings("unchecked") Collection<Part> outputMessageParts = outputMessage.getParts().values();
			Assert.notNull(outputMessageParts, "OutputMessage must exist");
			Assert.isTrue(outputMessageParts.size() == 1, "OutputMessage must have only one part");
			Part part = outputMessageParts.iterator().next();
			String returnType = TypeUtil.getTypeFromMessagePart(part);
			String returnClass = TypeUtil.getClassName(returnType);
			currentOperation.setResultType(returnClass);

			String referenceName = outputMessage.getQName().getLocalPart()+"_"+part.getName();
			completionSource.putLine2("try {");
			completionSource.putLine2("	this."+referenceName+" = futureResult.get();");
			completionSource.putLine2("	return this."+referenceName+";");
			completionSource.putLine2("} catch (Exception e) {");
			completionSource.putLine2("	throw new RuntimeException(e);");
			completionSource.putLine2("}");
			
		} else {
			//String resultClassName = getResultClassName(sequence);
			//completionSource.putLine2("futureResult.set("+firstParameterName+");");
		}

		//TODO do not put this next line into every receive op
		//completionSource.putLine2("stateManager.updateState();");
		currentOperation.addInitialSource(initialSource.get());
		currentOperation.addCompletionSource(completionSource.get());
	}

	
	protected ModelOperation buildCreateExecuterOperation(String returnType, String exceptionType, List<ModelClass> flowActionClasses) {
		addImportedClass("org.aries.process.Executor");
		addImportedClass("org.aries.process.ConcurrentExecutor");
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createExecutor");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("org.aries.process.Executor<String, ErrorMessage>");
		Buf buf = new Buf();
		buf.putLine2("Executor<"+returnType+", "+exceptionType+"> executor = new ConcurrentExecutor<"+returnType+", "+exceptionType+">();");
		Iterator<ModelClass> iterator = flowActionClasses.iterator();
		while (iterator.hasNext()) {
			ModelClass flowActionClass = iterator.next();
			buf.putLine2("executor.register(new "+flowActionClass.getClassName()+"(this));");
		}
		buf.putLine2("return executor;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	
//	protected ModelClass buildFlowActionClass(Activity activity) {
//		if (activity instanceof Assign) {
//			return buildFlowActionClass((Assign) activity);
//		} else if (activity instanceof Flow) {
//			return buildFlowActionClass((Flow) activity);
//		} else if (activity instanceof If) {
//			return buildFlowActionClass((If) activity);
//		} else if (activity instanceof Invoke) {
//			return buildFlowActionClass((Invoke) activity);
//		} else if (activity instanceof Receive) {
//			return buildFlowActionClass((Receive) activity);
//		} else if (activity instanceof Reply) {
//			return buildFlowActionClass((Reply) activity);
//		} else if (activity instanceof Sequence) {
//			return buildFlowActionClass((Sequence) activity);
//		}
//		return null;
//	}

	
	private ModelClass currentFlowActionClass;

	protected ModelClass buildFlowActionClass(Activity activity) throws Exception {
		ModelClass modelClass = new ModelClass();
		currentFlowActionClass = modelClass;
		modelClass.setType(TypeUtil.getTypeFromElement(activity.getElement()));
		modelClass.setPackageName(currentClass.getPackageName());
		String operationName = CodeUtil.getOperationName(activity);
		modelClass.setClassName(NameUtil.capName(operationName)+"Action");
		modelClass.setParentClassName("org.aries.process.ConcurrentAction");
		modelClass.addImportedClass("org.aries.process.ConcurrentAction");
		modelClass.addInstanceAttribute(buildFlowActionClassProcessAttribute());
		modelClass.addInstanceConstructor(buildFlowActionClassConstructor(activity));
		modelClass.addInstanceOperation(buildFlowActionClassGuardOperation(activity));
		modelClass.addInstanceOperation(buildFlowActionClassExecuteOperation(activity));
		return modelClass;
	}

//	protected ModelClass buildFlowActionClass(Invoke invokeActivity) {
//		ModelClass modelClass = new ModelClass();
//		modelClass.setPackageName(currentClass.getPackageName());
//		String operationName = CodeUtil.getOperationName(invokeActivity);
//		modelClass.setClassName(NameUtil.capName(operationName)+"Action");
//		modelClass.addInstanceAttribute(buildFlowActionClassProcessAttribute());
//		modelClass.addConstructor(buildFlowActionClassConstructor(invokeActivity));
//		modelClass.addInstanceOperation(buildFlowActionClassGuardOperation(invokeActivity));
//		modelClass.addInstanceOperation(buildFlowActionClassExecuteOperation(invokeActivity));
//		return modelClass;
//	}
//
//	protected ModelClass buildFlowActionClass(Reply replyActivity) {
//		ModelClass modelClass = new ModelClass();
//		modelClass.setPackageName(currentClass.getPackageName());
//		String operationName = CodeUtil.getOperationName(replyActivity);
//		modelClass.setClassName(NameUtil.capName(operationName)+"Action");
//		modelClass.addInstanceAttribute(buildFlowActionClassProcessAttribute());
//		modelClass.addConstructor(buildFlowActionClassConstructor(replyActivity));
//		modelClass.addInstanceOperation(buildFlowActionClassGuardOperation(replyActivity));
//		modelClass.addInstanceOperation(buildFlowActionClassExecuteOperation(replyActivity));
//		return modelClass;
//	}

//	protected ModelClass buildFlowActionClass(Sequence sequenceActivity) throws Exception {
//		String operationName = CodeUtil.getOperationName(sequenceActivity);
//		String className = NameUtil.capName(operationName)+"Action";
//		ModelClass modelClass = new ModelClass();
//		modelClass.setPackageName(currentClass.getPackageName());
//		modelClass.setClassName(className);
//		//modelClass.setName(className);
//		return modelClass;
//	}

	protected ModelAttribute buildFlowActionClassProcessAttribute() throws Exception {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName(currentClass.getPackageName());
		modelAttribute.setClassName(currentClass.getClassName());
		modelAttribute.setName("process");
		return modelAttribute;
	}

	protected ModelConstructor buildFlowActionClassConstructor(Activity activity) throws Exception {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setPackageName(currentClass.getPackageName());
		modelParameter.setClassName(currentClass.getClassName());
		modelParameter.setName("process");
		modelConstructor.addParameter(modelParameter);
		Buf buf = new Buf();
		buf.putLine2("this.process = process;");
		modelConstructor.addInitialSource(buf.get());
		return modelConstructor;
	}
	
	protected ModelOperation buildFlowActionClassGuardOperation(Activity activity) throws Exception {
		EList<Target> targets = activity.getTargets().getChildren();
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		modelOperation.setName("isRunnable");
		Buf buf = new Buf();
		if (targets.size() == 0) {
			buf.put2("return true;");
		} else {
			buf.put2("return ");
			Iterator<Target> iterator = targets.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Target target = iterator.next();
				Link link = target.getLink();
				String operationName = CodeUtil.getOperationName(link);
				if (i > 0)
					buf.put(" || ");
				buf.put("process."+operationName+"()");
			}
			buf.putLine(";");
		}
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation buildFlowActionClassExecuteOperation(Activity activity) throws Exception {
		if (activity instanceof Reply)
			return buildFlowActionClassExecuteOperationForReply((Reply) activity);
		if (activity instanceof Sequence)
			return buildFlowActionClassExecuteOperationForSequence((Sequence) activity);
		return buildFlowActionClassExecuteOperationForActivity(activity);
	}
	
	protected ModelOperation buildFlowActionClassExecuteOperationForReply(Reply replyActivity) throws Exception {
		ModelOperation modelOperation = buildFlowActionClassExecuteOperation(replyActivity, getSourceForActivity(replyActivity));
		return modelOperation;
	}
	
	protected ModelOperation buildFlowActionClassExecuteOperationForSequence(Sequence sequenceActivity) throws Exception {
		Buf buf = new Buf();
		List<Activity> activities = sequenceActivity.getActivities();
		Iterator<Activity> iterator = activities.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity instanceof Assign) {
				buf.put(getSourceForActivity((Assign) activity));
			} else if (activity instanceof Reply) {
				buf.put(getSourceForActivity((Reply) activity));
			} else {
				buf.put(getSourceForActivity(activity));
			}
		}
		ModelOperation modelOperation = buildFlowActionClassExecuteOperation(sequenceActivity, buf.get());
		return modelOperation;
	}
	
	protected ModelOperation buildFlowActionClassExecuteOperationForActivity(Activity activity) throws Exception {
		ModelOperation modelOperation = buildFlowActionClassExecuteOperation(activity, getSourceForActivity(activity));
		return modelOperation;
	}
	
	protected ModelOperation buildFlowActionClassExecuteOperation(Activity activity, String source) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		modelOperation.setName("execute");
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(source);
		return modelOperation;
	}

	protected String getSourceForActivity(Activity activity) throws Exception {
		Buf buf = new Buf();
		String operationName = CodeUtil.getOperationName(activity);
		buf.putLine2("process."+operationName+"();");
		return buf.get();
	}

	protected String getSourceForActivity(Assign assignActivity) throws Exception {
		AssignGenerator generator = new AssignGenerator(context, process);
		generator.setCurrentClass(currentFlowActionClass);
		//generator.setServiceState(serviceState);
		generator.setGlobalVariables(getVariablesFromProcess(process));
		generator.setLocalVariables(localVariables);
		generator.setInsideFlow(isInsideFlow());
		//process generation
		generator.generate(assignActivity);
		addImportedClasses(generator.getImportedClasses());
		addInstanceOperations(generator.getInstanceOperations());
		//get invocation source
		Buf buf = new Buf();
		buf.put(getSourceForActivity((Activity) assignActivity));
		buf.putLine2("terminateNormally = true;");
		return buf.get();
	}

	protected String getSourceForActivity(Reply replyActivity) throws Exception {
		Buf buf = new Buf();
		String name = CodeUtil.getVariableName(replyActivity.getVariable());
		buf.putLine2("returnValue = process.get"+NameUtil.capName(name)+"();");
		buf.putLine2("terminateNormally = true;");
		return buf.get();
	}

}
