package aries.bpel;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Service;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Variable;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ReplyGenerator extends AbstractActivityGenerator<Reply> {

	private ModelOperation replyOperation;
	
	
	public ReplyGenerator(GenerationContext context, Process process) { 
		super(context, process);
		this.verbose = true;
	}

	public ModelOperation getCurrentOperation() {
		return replyOperation;
	}

	@Override
	public void generate(Reply replyActivity) throws Exception {
//		if (!isInsideFlow()) {
//			String variableName = CodeUtil.getVariableName(replyActivity.getVariable());
//			statementSources.put2("return "+variableName+";");
//			return;
//		}
		
		String operationName = CodeUtil.getOperationName(replyActivity);
		//FromParts fromParts = replyActivity.getFromParts();
		Operation operation = replyActivity.getOperation();
		Variable variable = replyActivity.getVariable();
		//Sources sources = replyActivity.getSources();
		//Targets targets = replyActivity.getTargets();
		PortType portType = replyActivity.getPortType();
		Assert.notNull(portType, "PortType must exist");
		Service service = context.getImportedServiceByPortType(portType.getQName());
		Assert.notNull(service, "Service must exist");
		//PartnerLink partnerLink = replyActivity.getPartnerLink();
		//MessageExchange messageExchange = replyActivity.getMessageExchange();
		//Definition enclosingDefinition = replyActivity.getEnclosingDefinition();
		//Boolean suppressJoinFailure = replyActivity.getSuppressJoinFailure();

//		if (operationName.equals("replyBookPaymentAccepted"))
//			System.out.println();
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		List<ModelParameter> parameters = modelOperation.getParameters();

//		if (!isInsideForEach())
//			currentOperation = replyOperation;
		
//		if (operation.getInput() == null)
//			System.out.println();
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
			modelOperation.addException(faultClassName);
			addImportedClass(faultJavaName);
			//hasCatchBlock = true;
		}

		Buf initialSource = new Buf();
		Buf completionSource = new Buf();

		Iterator<ModelParameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			ModelParameter modelParameter = parameterIterator.next();
			//ServiceVariable serviceVariable = ServiceStateUtil.getVariableByName(serviceState, modelParameter.getRefName());
			//Variable variable = variable.get(modelParameter.getName());
			//if (serviceVariable != null) {
			if (variable != null) {
				boolean isGlobal = isGlobalVariable(variable);
				if (isGlobal) {
					String variableName = CodeUtil.getVariableName(variable, isGlobal);
					initialSource.putLine2(variableName+" = "+modelParameter.getName()+";");
				}
			}
		}

		String variableName = CodeUtil.getVariableName(variable, isGlobalVariable(variable));
		initialSource.put(BPELGenerationUtil.generateCallToRemoteOperation(this, service, operation, variableName, null));
		//initialSource.put(generateSourceConditionStateAssignments(replyActivity));

		modelOperation.addInitialSource(initialSource.get());
		modelOperation.addCompletionSource(completionSource.get());

		if (isInsideCodeBlock()) {
			addInvocationSource(modelOperation);
		}

		if (!operationCache.contains(operationName)) {
			operationCache.add(operationName);
			replyOperation = modelOperation;
		}

	}
	
}
