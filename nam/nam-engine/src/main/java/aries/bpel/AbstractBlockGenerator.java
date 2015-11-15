package aries.bpel;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.If;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;

import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public abstract class AbstractBlockGenerator<T extends Activity> extends AbstractActivityGenerator<T> {

	public AbstractBlockGenerator() {
		this.verbose = true;
	}

	public AbstractBlockGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}

	public void generateActivity(Activity activity) throws Exception {
		if (activity instanceof Assign) {
			generateActivity((Assign) activity);
		} else if (activity instanceof Flow) {
			generateActivity((Flow) activity);
		} else if (activity instanceof ForEach) {
			generateActivity((ForEach) activity);
		} else if (activity instanceof If) {
			generateActivity((If) activity);
		} else if (activity instanceof Invoke) {
			generateActivity((Invoke) activity);
		} else if (activity instanceof Receive) {
			generateActivity((Receive) activity);
		} else if (activity instanceof Reply) {
			generateActivity((Reply) activity);
		} else if (activity instanceof Scope) {
			generateActivity((Scope) activity);
		} else if (activity instanceof Sequence) {
			generateActivity((Sequence) activity);
		}
	}
	
	protected void generateActivity(Assign activity) throws Exception {
		AssignGenerator generator = new AssignGenerator(context, process);
		generator.setIndentationLevel(getIndentationLevel());
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generateActivity(generator, activity);
	}

	protected void generateActivity(Flow activity) throws Exception {
		FlowGenerator generator = new FlowGenerator(context, process);
		generator.setIndentationLevel(getIndentationLevel());
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generateActivity(generator, activity);
	}

	protected void generateActivity(ForEach activity) throws Exception {
		ForEachGenerator generator = new ForEachGenerator(context, process);

		//set inputs
		generator.setCurrentClass(currentClass);
		//generator.setServiceState(serviceState);
		generator.setGlobalVariables(getVariablesFromProcess(process));
		generator.setLocalVariables(localVariables);
		generator.setInsideCodeBlock(true);
		generator.setInsideFlow(isInsideFlow());
		generator.setIndentationLevel(getIndentationLevel());

		//process generation
		generator.generate(activity);

		//get outputs
		ModelOperation modelOperation = generator.getCurrentOperation();
		currentClass.addInstanceOperation(modelOperation);
		addStatementSources(generator.getStatementSources());
		addImportedClasses(generator.getImportedClasses());
		addInstanceOperations(generator.getInstanceOperations());
		addSupportingClasses(generator.getSupportingClasses());
	}

	protected void generateActivity(If activity) throws Exception {
		IfGenerator generator = new IfGenerator(context, process);
		generator.setIndentationLevel(getIndentationLevel());
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generateActivity(generator, activity);
	}

	protected void generateActivity(Invoke activity) throws Exception {
		InvokeGenerator generator = new InvokeGenerator(context, process);
		generator.setIndentationLevel(getIndentationLevel());
		generateActivity(generator, activity);
	}

	protected void generateActivity(Receive activity) throws Exception {
		ReceiveGenerator generator = new ReceiveGenerator(context, process);

		//set inputs
		generator.setCurrentClass(currentClass);
		//generator.setServiceState(serviceState);
		generator.setGlobalVariables(getVariablesFromProcess(process));
		generator.setLocalVariables(localVariables);
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generator.setIndentationLevel(getIndentationLevel());

		//process generation
		generator.generate(activity);

		//get outputs
		currentOperation = generator.getCurrentOperation();
		currentClass.addInstanceOperation(currentOperation);
		addImportedClasses(generator.getImportedClasses());
		addInstanceOperations(generator.getInstanceOperations());
		addSupportingClasses(generator.getSupportingClasses());
	}
	
	protected void generateActivity(Reply activity) throws Exception {
		ReplyGenerator generator = new ReplyGenerator(context, process);
		
		//set inputs
		generator.setCurrentOperation(currentOperation);
		generator.setCurrentClass(currentClass);
		//generator.setServiceState(serviceState);
		generator.setGlobalVariables(getVariablesFromProcess(process));
		generator.setLocalVariables(localVariables);
		generator.setInsideCodeBlock(true);
		generator.setInsideFlow(isInsideFlow());
		generator.setIndentationLevel(getIndentationLevel());

		//process generation
		generator.generate(activity);

		//get outputs
		ModelOperation modelOperation = generator.getCurrentOperation();
		if (modelOperation != null)
			currentClass.addInstanceOperation(modelOperation);
		addStatementSources(generator.getStatementSources());
		addImportedClasses(generator.getImportedClasses());
		addInstanceOperations(generator.getInstanceOperations());
		addSupportingClasses(generator.getSupportingClasses());
	}

	protected void generateActivity(Scope activity) throws Exception {
		ScopeGenerator generator = new ScopeGenerator(context, process);
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generateActivity(generator, activity);
	}
	
	protected void generateActivity(Sequence activity) throws Exception {
		SequenceGenerator generator = new SequenceGenerator(context, process);
		generator.setInsideCodeBlock(isInsideCodeBlock());
		generator.setInsideFlow(isInsideFlow());
		generateActivity(generator, activity);
	}

	protected <A extends Activity> void generateActivity(ActivityGenerator<A> generator, A activity) throws Exception {
		//set inputs
		generator.setCurrentClass(currentClass);
		generator.setCurrentOperation(currentOperation);
		//generator.setServiceState(serviceState);
		generator.setGlobalVariables(getVariablesFromProcess(process));
		generator.setLocalVariables(localVariables);
		generator.setIndentationLevel(getIndentationLevel());

		//process generation
		generator.generate(activity);
		
		//get outputs
		addStatementSources(generator.getStatementSources());
		addImportedClasses(generator.getImportedClasses());
		addInstanceOperations(generator.getInstanceOperations());
		addSupportingClasses(generator.getSupportingClasses());
	}

	
	
	
//	protected void generate(Assign activity) throws Exception {
//		AssignGenerator generator = new AssignGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		ModelOperation modelOperation = generator.generate(activity);
//		modelClass.addInstanceOperation(modelOperation);
//		addActivityOperationToProcess(modelOperation);
//	}
//
//	protected void generate(If activity) throws Exception {
//		IfGenerator generator = new IfGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		ModelOperation modelOperation = generator.generate(activity);
//		modelClass.addInstanceOperation(modelOperation);
//
//		Buf buf = new Buf();
//		buf.putLine2("if ("+modelOperation.getName()+"()) {");
//		buf.putLine2("}");
//		mainOperation.addSourceCode(buf.get());
//
//	}
//
//	protected void generate(Invoke activity) throws Exception {
//		InvokeGenerator generator = new InvokeGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		ModelOperation modelOperation = generator.generate(activity);
//		modelClass.addInstanceOperation(modelOperation);
//
//		//add imported class for the invocation proxy
//		String typeName = TypeUtil.getTypeFromQName(activity.getPortType().getQName());
//		modelClass.addImportedClass(TypeUtil.getJavaName(typeName)+"Proxy");
//		
//		Variable inputVariable = activity.getInputVariable();
//		Variable outputVariable = activity.getOutputVariable();
//
////		Buf buf = new Buf();
////		String inputType = TypeUtil.getTypeFromVariable(inputVariable);
////		String outputType = TypeUtil.getTypeFromVariable(outputVariable);
////		String inputClass = TypeUtil.getClassName(inputType);
////		String outputClass = TypeUtil.getClassName(outputType);
////		String inputVariableName = inputVariable.getName();
////		String outputVariableName = outputVariable.getName();
////		buf.put2(outputVariableName+" = "+modelOperation.getName()+"(");
//		
////		List<ModelParameter> parameters = modelOperation.getParameters();
////		Iterator<ModelParameter> iterator = parameters.iterator();
////		for (int i=0; iterator.hasNext(); i++) {
////			ModelParameter modelParameter = iterator.next();
////			if (i > 0)
////				buf.put(", ");
////			buf.put(inputVariableName);
////		}
////		buf.putLine(");");
////		mainOperation.addSourceCode(buf.get());
//
//		addActivityOperationToProcess(modelOperation);
//
//		
////		String name = activity.getName();
////		ToParts toParts = activity.getToParts();
////		FromParts fromParts = activity.getFromParts();
////		Variable inputVariable = activity.getInputVariable();
////		Variable outputVariable = activity.getOutputVariable();
////		Operation operation = activity.getOperation();
////		Sources sources = activity.getSources();
////		Targets targets = activity.getTargets();
////		PortType portType = activity.getPortType();
////		PartnerLink partnerLink = activity.getPartnerLink();
////		Definition enclosingDefinition = activity.getEnclosingDefinition();
////		FaultHandler faultHandler = activity.getFaultHandler();
////		CompensationHandler compensationHandler = activity.getCompensationHandler();
//	}
//
//	
//	protected void generate(Receive receiveActivity) {
//		String name = receiveActivity.getName();
//		FromParts fromParts = receiveActivity.getFromParts();
//		//Variable variable = receiveActivity.getVariable();
//		Operation operation = receiveActivity.getOperation();
//		Sources sources = receiveActivity.getSources();
//		Targets targets = receiveActivity.getTargets();
//		PortType portType = receiveActivity.getPortType();
//		PartnerLink partnerLink = receiveActivity.getPartnerLink();
//		MessageExchange messageExchange = receiveActivity.getMessageExchange();
//		Definition enclosingDefinition = receiveActivity.getEnclosingDefinition();
//		Boolean suppressJoinFailure = receiveActivity.getSuppressJoinFailure();
//
//		mainOperation = new ModelOperation();
//		mainOperation.setName(receiveActivity.getName());
//		mainOperation.setModifiers(Modifier.PUBLIC);
//		List<ModelParameter> parameters = mainOperation.getParameters();
//
//		Message inputMessage = operation.getInput().getMessage();
//		@SuppressWarnings("unchecked") Collection<Part> inputMessageParts = inputMessage.getParts().values();
//		//Assert.isTrue(parts.size() == 1, "Incoming message must have only one part");
//		Iterator<Part> iterator = inputMessageParts.iterator();
//		while (iterator.hasNext()) {
//			Part part = iterator.next();
//			ModelParameter modelParameter = new ModelParameter();
//			String parameterType = TypeUtil.getTypeFromMessagePart(part);
//			String parameterPackage = TypeUtil.getPackageName(parameterType);
//			String parameterClass = TypeUtil.getClassName(parameterType);
//			//String parameterName = TypeUtil.getLocalPart(parameterType);
//			modelParameter.setPackageName(parameterPackage);
//			modelParameter.setClassName(parameterClass);
//			//modelParameter.setName(parameterName);
//			modelParameter.setName(part.getName());
//			parameters.add(modelParameter);
//		}
//		
//		Buf buf = new Buf();
//		Variable variable = receiveActivity.getVariable();
//		Part part = inputMessageParts.iterator().next();
//		buf.putLine2(variable.getName()+" = "+part.getName()+";");
//		mainOperation.addSourceCode(buf.get());		
//		
//		if (operation.getStyle() == OperationType.REQUEST_RESPONSE) {
//			Message outputMessage = operation.getOutput().getMessage();
//			Assert.notNull(outputMessage, "OutputMessage must exist");
//			@SuppressWarnings("unchecked") Collection<Part> outputMessageParts = outputMessage.getParts().values();
//			Assert.notNull(outputMessageParts, "OutputMessage must exist");
//			Assert.isTrue(outputMessageParts.size() == 1, "OutputMessage must have only one part");
//			String returnType = TypeUtil.getTypeFromMessagePart(outputMessageParts.iterator().next());
//			String returnClass = TypeUtil.getClassName(returnType);
//			mainOperation.setReturnType(returnClass);
//		}
//		modelClass.addInstanceOperation(mainOperation);
//	}
//	
//
//	protected void generate(Reply activity) throws Exception {
//		ReplyGenerator generator = new ReplyGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		
//		Buf buf = new Buf();
//		buf.putLine2(generator.generate(activity));
//		mainOperation.addSourceCode(buf.get());
//		
//		String name = activity.getName();
//		ToParts toParts = activity.getToParts();
//		Variable variable = activity.getVariable();
//		Operation operation = activity.getOperation();
//		Sources sources = activity.getSources();
//		Targets targets = activity.getTargets();
//		PortType portType = activity.getPortType();
//		PartnerLink partnerLink = activity.getPartnerLink();
//		MessageExchange messageExchange = activity.getMessageExchange();
//		Definition enclosingDefinition = activity.getEnclosingDefinition();
//		Boolean suppressJoinFailure = activity.getSuppressJoinFailure();
//		QName faultName = activity.getFaultName();
//	}
//
//	protected void addActivityOperationToProcess(ModelOperation modelOperation) {
//		Buf buf = new Buf();
//		String returnType = modelOperation.getReturnType();
//		
//		if (returnType != null && !returnType.equals("void")) {
//			buf.put2(returnType+" "+modelOperation.getName()+"(");
//		} else {
//			buf.put2(modelOperation.getName()+"(");
//		}
//		List<ModelParameter> parameters = modelOperation.getParameters();
//		Iterator<ModelParameter> iterator = parameters.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			ModelParameter modelParameter = iterator.next();
//			if (i > 0)
//				buf.put(", ");
//			buf.put(modelParameter.getName());
//		}
//		buf.putLine(");");
//		mainOperation.addSourceCode(buf.get());
//	}

	
}
