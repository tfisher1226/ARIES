package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Process;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;

import aries.bpel.ActivityGenerator;
import aries.bpel.BPELUtil;
import aries.bpel.FlowGenerator;
import aries.bpel.SequenceGenerator;
import aries.codegen.AbstractBeanProvider;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class ProcessClassProvider extends AbstractBeanProvider {
	
	protected ModelClass currentClass;


	public ProcessClassProvider(GenerationContext context) {
		super(context);
	}

	public void setCurrentClass(ModelClass modelClass) {
		this.currentClass = modelClass;
	}

	public void generate(Process process, Sequence sequence) throws Exception {
		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		SequenceGenerator generator = new SequenceGenerator(context, bpelProcess);
		execute(generator, process, sequence);
	}

	public void generate(Process process, Flow flow) throws Exception {
		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		FlowGenerator generator = new FlowGenerator(context, bpelProcess);
		execute(generator, process, flow);
	}

	protected <A extends Activity> void execute(ActivityGenerator<A> generator, Process process, A activity) throws Exception {
		//set inputs
		generator.setCurrentClass(currentClass);
		//generator.setServiceState(context.getService().getState());
		generator.setGlobalVariables(getVariables(process));
		generator.clearState();

		//process generation
		generator.generate(activity);
		
		//get outputs
		//addStatementSources(generator.getStatementSources());
		//addImportedClasses(generator.getImportedClasses());
		//addInstanceOperations(generator.getInstanceOperations());
		currentClass.addSupportingClasses(generator.getSupportingClasses());
	}

	protected void addSupportingClasses(List<ModelClass> supportingClasses) {
		Iterator<ModelClass> iterator = supportingClasses.iterator();
		while (iterator.hasNext()) {
			ModelClass modelClass = iterator.next();
			//TODO
		}
	}

	public Map<String, Variable> getVariables(Process process) {
		org.eclipse.bpel.model.Process bpelProcess = BPELUtil.getBPELProcess(process);
		Map<String, Variable> variables = BPELUtil.createVariablesMap(bpelProcess.getVariables());
		return variables;
	}

//	protected void addStatementSources(Buf statementSources) {
//		currentOperation.addInitialSource(statementSources.get());
//	}
//
//	protected void addInstanceOperations(List<ModelOperation> instanceOperations) {
//		Iterator<ModelOperation> iterator = instanceOperations.iterator();
//		while (iterator.hasNext()) {
//			ModelOperation instanceOperation = iterator.next();
//			currentClass.addInstanceOperation(instanceOperation);
//		}
//	}
//
//	protected void addImportedClasses(List<String> importedClasses) {
//		Iterator<String> iterator = importedClasses.iterator();
//		while (iterator.hasNext()) {
//			String importedClass = iterator.next();
//			if (TypeUtil.isImportedClassRequired(importedClass))
//				currentClass.addImportedClass(importedClass);
//		}
//	}

	
//	protected void generateXX(If activity) throws Exception {
//		IfGenerator generator = new IfGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		ModelOperation modelOperation = generator.generate(activity);
//		modelClass.addInstanceOperation(modelOperation);
//
//		Buf buf = new Buf();
//		buf.putLine2("if ("+modelOperation.getName()+"()) {");
//		buf.putLine2("}");
//		currentOperation.addSourceCode(buf.get());
//	}
	
//	protected void generate(Invoke activity) throws Exception {
//		InvokeGenerator generator = new InvokeGenerator(context);
//		execute(generator, activity);

		//Process process = context.getBpelResource().getProcess();
		//generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
		//ModelOperation modelOperation = generator.generate(activity);
		//modelClass.addInstanceOperation(modelOperation);

		//add imported class for the invocation proxy
		//String typeName = TypeUtil.getTypeFromQName(activity.getPortType().getQName());
		//modelClass.addImportedClass(TypeUtil.getJavaName(typeName)+"Proxy");
		//addActivityOperationToProcess(modelOperation);
		

//		Buf buf = new Buf();
//		String inputType = TypeUtil.getTypeFromVariable(inputVariable);
//		String outputType = TypeUtil.getTypeFromVariable(outputVariable);
//		String inputClass = TypeUtil.getClassName(inputType);
//		String outputClass = TypeUtil.getClassName(outputType);
//		String inputVariableName = inputVariable.getName();
//		String outputVariableName = outputVariable.getName();
//		buf.put2(outputVariableName+" = "+modelOperation.getName()+"(");
		
//		List<ModelParameter> parameters = modelOperation.getParameters();
//		Iterator<ModelParameter> iterator = parameters.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			ModelParameter modelParameter = iterator.next();
//			if (i > 0)
//				buf.put(", ");
//			buf.put(inputVariableName);
//		}
//		buf.putLine(");");
//		mainOperation.addSourceCode(buf.get());
		
//		String name = activity.getName();
//		ToParts toParts = activity.getToParts();
//		FromParts fromParts = activity.getFromParts();
//		Variable inputVariable = activity.getInputVariable();
//		Variable outputVariable = activity.getOutputVariable();
//		Operation operation = activity.getOperation();
//		Sources sources = activity.getSources();
//		Targets targets = activity.getTargets();
//		PortType portType = activity.getPortType();
//		PartnerLink partnerLink = activity.getPartnerLink();
//		Definition enclosingDefinition = activity.getEnclosingDefinition();
//		FaultHandler faultHandler = activity.getFaultHandler();
//		CompensationHandler compensationHandler = activity.getCompensationHandler();
//	}
	
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
//		currentOperation = new ModelOperation();
//		currentOperation.setName(receiveActivity.getName());
//		currentOperation.setModifiers(Modifier.PUBLIC);
//		List<ModelParameter> parameters = currentOperation.getParameters();
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
//		currentOperation.addSourceCode(buf.get());		
//		
//		if (operation.getStyle() == OperationType.REQUEST_RESPONSE) {
//			Message outputMessage = operation.getOutput().getMessage();
//			Assert.notNull(outputMessage, "OutputMessage must exist");
//			@SuppressWarnings("unchecked") Collection<Part> outputMessageParts = outputMessage.getParts().values();
//			Assert.notNull(outputMessageParts, "OutputMessage must exist");
//			Assert.isTrue(outputMessageParts.size() == 1, "OutputMessage must have only one part");
//			String returnType = TypeUtil.getTypeFromMessagePart(outputMessageParts.iterator().next());
//			String returnClass = TypeUtil.getClassName(returnType);
//			currentOperation.setReturnType(returnClass);
//		}
//		currentClass.addInstanceOperation(currentOperation);
//	}
	

//	protected void generate(Reply activity) throws Exception {
//		ReplyGenerator generator = new ReplyGenerator(context);
//		Process process = context.getBpelResource().getProcess();
//		generator.setVariables(BPELUtil.createVariablesMap(process.getVariables()));
//		generator.generate(activity);
//		
//		String sourceCode = generator.getStatementSources().get();
//		currentOperation.addSourceCode(sourceCode+"\n");
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
//		currentOperation.addSourceCode(buf.get());
//	}

	
	


}
