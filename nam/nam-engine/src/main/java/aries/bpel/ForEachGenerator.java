package aries.bpel;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import nam.model.util.TypeUtil;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class ForEachGenerator extends AbstractBlockGenerator<ForEach> {

	public ForEachGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}
	
	public ModelOperation getCurrentOperation() {
		return currentOperation;
	}

	@Override
	public void generate(ForEach forEachActivity) throws Exception {
		String operationName = CodeUtil.getOperationName(forEachActivity);
		currentOperation = new ModelOperation();
		currentOperation.setModifiers(Modifier.PROTECTED);
		currentOperation.setName(operationName);
		currentOperation.setResultType("void");
		getCurrentClass().addInstanceOperation(currentOperation);

		//this will hold source code
		Buf initialSource = new Buf();
		initialSource.put(generateForLoopOpenStatement(forEachActivity));
		initialSource.put(generateForLoopLocalVariables(forEachActivity));
		currentOperation.addInitialSource(initialSource.get());

		Activity activity = forEachActivity.getActivity();
		setInsideCodeBlock(true);
		generateActivity(activity);
		
		Buf completionSource = new Buf();
		completionSource.putLine2("}");
		currentOperation.addCompletionSource(completionSource.get());
		//addStatementsToOperation(currentOperation, initialSource);
		addInvocationSource(currentOperation);
	}

	protected String generateForLoopOpenStatement(ForEach forEachActivity) throws Exception {
		Expression finalCounterValue = forEachActivity.getFinalCounterValue();
		ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
		String output = expressionGenerator.generate(finalCounterValue);
		Variable counter = forEachActivity.getCounterName();
		String counterName = counter.getName();
		Buf buf = new Buf();
		buf.putLine2("for (int "+counterName+" = 0; "+counterName+" < this."+output+"; "+counterName+"++) {");
		return buf.get();
	}
	
	protected String generateForLoopLocalVariables(ForEach forEachActivity) {
		Buf buf = new Buf();
		Activity activity = forEachActivity.getActivity();
		if (activity instanceof Scope) {
			Scope scopActivity = (Scope) activity;
			Variables variables = scopActivity.getVariables();
			
			Map<String, Variable> variablesMap = BPELUtil.createVariablesMap(variables);
			addLocalVariables(variablesMap);
			
			Iterator<Variable> iterator = variables.getChildren().iterator();
			while (iterator.hasNext()) {
				Variable variable = iterator.next();

				Message message = variable.getMessageType();
				if (message != null) {
					@SuppressWarnings("unchecked") Collection<Part> parts = message.getParts().values();
					Iterator<Part> partIterator = parts.iterator();
					while (partIterator.hasNext()) {
						Part part = (Part) partIterator.next();
						String type = TypeUtil.getTypeFromMessagePart(part);
						String packageName = TypeUtil.getPackageName(type);
						String className = TypeUtil.getClassName(type);
						String defaultValue = CodeUtil.getDefaultValueForType(type);
						getCurrentClass().addImportedClass(packageName+"."+className);
						//buf.putLine3(className+" "+variable.getName()+"_"+part.getName()+" = "+defaultValue+";");
					}
				} else {
					String type = TypeUtil.getTypeFromVariable(variable);
					String packageName = TypeUtil.getPackageName(type);
					String className = TypeUtil.getClassName(type);
					String defaultValue = CodeUtil.getDefaultValueForType(type);
					getCurrentClass().addImportedClass(packageName+"."+className);
					//buf.putLine3(className+" "+variable.getName()+" = "+defaultValue+";");
				}
			}
		}
		return buf.get();
	}

}
