package aries.bpel;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.model.Service;
import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variable;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class InvokeGenerator extends AbstractActivityGenerator<Invoke> {

	private static Set<String> operationCache = new HashSet<String>();
	
	
	public InvokeGenerator(GenerationContext context, Process process) { 
		super(context, process);
		this.verbose = true;
	}
	
	public void generate(Invoke invokeActivity) throws Exception {
		String operationName = CodeUtil.getOperationName(invokeActivity);
		Variable inputVariable = invokeActivity.getInputVariable();
		Variable outputVariable = invokeActivity.getOutputVariable();

		ModelOperation modelOperation = null;
		Operation operation = invokeActivity.getOperation();
		PartnerLink partnerLink = invokeActivity.getPartnerLink();
		PortType portType = invokeActivity.getPortType();
		String linkName = partnerLink.getName();
		Buf sourceCode = new Buf();
		boolean selfIsTarget = false;

		//using direct method call?
		if (linkName.equals("direct")) {
			Assert.notNull(portType, "PortType must be specified for \"direct\" invocations");
			selfIsTarget = portType.getQName().getLocalPart().equals("self");
		}

		if (!selfIsTarget) {
			modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PROTECTED);
			modelOperation.setName(operationName);
		}
		
//		if (inputVariable != null && inputVariable.getName().contains("bookPurchaseRequest")) {
//			System.out.println();
//		}
		
		if (inputVariable != null && !isGlobalVariable(inputVariable)) {
			//TODO put this getType specific code into subroutine
			String typeName = TypeUtil.getTypeFromVariable(inputVariable);
			if (typeName == null) {
				String name = operation.getEInput().getName();
				org.eclipse.wst.wsdl.Message eMessage = operation.getEInput().getEMessage();
				Part ePart = (Part) eMessage.getEParts().get(0);
				typeName = TypeUtil.getTypeFromMessagePart(ePart);
			}
			String packageName = TypeUtil.getPackageName(typeName);
			String className = TypeUtil.getClassName(typeName);
			
			if (!selfIsTarget) {
				ModelParameter modelParameter = new ModelParameter();
				modelParameter.setPackageName(packageName);
				modelParameter.setClassName(className);
				getCurrentClass().addImportedClass(packageName+"."+className);
				modelParameter.setName(CodeUtil.getVariableName(inputVariable, false));
				modelOperation.addParameter(modelParameter);
			}
		}
		
		String resultName = null;
		if (outputVariable != null && isLocalVariable(outputVariable)) {
			String returnType = TypeUtil.getTypeFromVariable(outputVariable);
			String returnClass = TypeUtil.getClassName(returnType);
			resultName = getVariableName(outputVariable);
			modelOperation.setResultName(resultName);
			modelOperation.setResultType(returnClass);
		}
		
		//using direct method call?
		if (linkName.equals("direct")) {
			String methodName = operation.getName();
				
			if (!selfIsTarget) {
				String namespace = portType.getQName().getNamespaceURI();
				String packageName = ProjectLevelHelper.getPackageName(namespace);
				String className = portType.getQName().getLocalPart();
				String beanName = packageName + "." + NameUtil.uncapName(className);
				addImportedClass(packageName + "." + className);
				sourceCode.putLine2(className+" bean = BeanContext.get(\""+beanName+"\");");
			}
				
			if (outputVariable != null) {
				String returnType = TypeUtil.getTypeFromVariable(outputVariable);
				String returnTypePackageName = TypeUtil.getPackageName(returnType);
				String returnTypeClassName = TypeUtil.getClassName(returnType);
				addImportedClass(returnTypePackageName+"."+returnTypeClassName);

				String inputVariableName = getVariableName(inputVariable);
				String outputVariableName = getVariableName(outputVariable);
				String inputVariableType = TypeUtil.getTypeFromVariable(inputVariable);
				String outputVariableType = TypeUtil.getTypeFromVariable(outputVariable);

				if (inputVariableName.equals(outputVariableName) && inputVariableType.equals(outputVariableType))
					outputVariableName = "output" + NameUtil.capName(outputVariableName);
				sourceCode.put2(returnTypeClassName+" "+outputVariableName +" = ");
			}

			if (!selfIsTarget)
				sourceCode.put2("bean.");
			sourceCode.putLine2(methodName+"(");	

			if (inputVariable == null) {
				sourceCode.putLine(");");
			} else {
				String parameterType = TypeUtil.getTypeFromVariable(outputVariable);
				String parameterPackageName = TypeUtil.getPackageName(parameterType);
				String parameterClassName = TypeUtil.getClassName(parameterType);
				addImportedClass(parameterPackageName+"."+parameterClassName);
				sourceCode.putLine(inputVariable.getName()+");");
			}
			
		} else {
			Service service = context.getImportedServiceByPortType(portType.getQName());
			Assert.notNull(service, "Service not found: "+portType.getQName());
			String inputVariableName = getVariableName(inputVariable);
			String outputVariableName = getVariableName(outputVariable);
			sourceCode.put(BPELGenerationUtil.generateCallToRemoteOperation(this, service, operation, inputVariableName, outputVariableName));
			sourceCode.put(generateSourceConditionStateAssignments(invokeActivity));
		}
		
		if (resultName != null) {
			sourceCode.put("return "+resultName+";");
		}
			
		//Process process = context.getProcess();
		//context.addProcess(portType.getQName().toString(), process);

		//add parameter to operation
		//String parameterType = TypeUtil.getTypeFromVariable(inputVariable);
		//String parameterPackage = TypeUtil.getPackageName(parameterType);
		//String parameterClass = TypeUtil.getClassName(parameterType);
		//String parameterName = TypeUtil.getLocalPart(parameterType);
		//ModelParameter modelParameter = new ModelParameter();
		//modelParameter.setPackageName(parameterPackage);
		//modelParameter.setClassName(parameterClass);
		//modelParameter.setName(parameterName);
		//modelOperation.getParameters().add(modelParameter);
		
		//add return type to operation
		//String returnType = TypeUtil.getTypeFromVariable(outputVariable);
		//String returnClass = TypeUtil.getClassName(returnType);
		//modelOperation.setReturnType(returnClass);
		
		//sourceCode.put2(outputVariableName+" = proxy."+modelOperation.getName()+"(");
		//List<ModelParameter> parameters = modelOperation.getParameters();
		//Iterator<ModelParameter> iterator = parameters.iterator();
		//for (int i=0; iterator.hasNext(); i++) {
		//	ModelParameter modelParameter = iterator.next();
		//	if (i > 0)
		//		sourceCode.put(", ");
		//	sourceCode.put(inputVariableName);
		//}
		//sourceCode.putLine(");");

		//get source code for method body
		//String typeName = TypeUtil.getTypeFromQName(portType.getQName());
		//String className = TypeUtil.getClassName(typeName);
		//sourceCode.putLine(className+"Proxy proxy = new "+className+"Proxy();");
		//String variableName = NameUtil.uncapName(returnClass);
		//sourceCode.putLine(returnClass+" "+variableName+" = proxy."+remoteOperation.getName()+"("+parameterName+");");
		//sourceCode.putLine("return "+variableName+";");

		if (selfIsTarget) {
			addStatementSources(sourceCode);			
			
		} else {
			if (!operationCache.contains(operationName)) {
				operationCache.add(operationName);
				//add source code to local operation
				addStatementsToOperation(modelOperation, sourceCode);
				addInstanceOperation(modelOperation);
			}
			
			if (!isInsideFlow()) {
				//add invocation source code to current operation
				addInvocationSource(modelOperation);
			}
		}
	}

}
