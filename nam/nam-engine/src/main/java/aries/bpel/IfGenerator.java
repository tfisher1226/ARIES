package aries.bpel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Else;
import org.eclipse.bpel.model.ElseIf;
import org.eclipse.bpel.model.If;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.util.EList;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class IfGenerator extends AbstractActivityGenerator<If> {

	//private static Map<String, String> activityCache = new HashMap<String, String>();

	private static Set<String> operationCache = new HashSet<String>();

	
	public IfGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}

	public void generate(If activity) throws Exception {
		//String operationName = CodeUtil.getOperationName(activity);

		//ExpressionGenerator expressionGenerator1 = new ExpressionGenerator(variables);
		//String conditionBody = expressionGenerator1.generate(activity.getCondition().getBody());
		
		//String cachedCondition = activityCache.get(activity.getName());
		//if (cachedCondition == null || !cachedCondition.equals(conditionBody)) {

		//if (!operationCache.contains(operationName)) {
			//operationCache.add(operationName);

			//ModelOperation modelOperation = new ModelOperation();
			//modelOperation.setModifiers(Modifier.PROTECTED);
			//modelOperation.setName(operationName);
			//modelOperation.setReturnType("boolean");
	
			//process the condition
			//Buf sourceCode = new Buf();
			Condition condition = activity.getCondition();

			ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
			String output = expressionGenerator.generate(condition.getBody());
			//activityCache.put(activity.getName(), output);
			//sourceCode.putLine("boolean value = "+output+";");
			//sourceCode.putLine("return value;");
			
			//addStatementsToOperation(modelOperation, sourceCode);
			//addInstanceOperation(modelOperation);		
		//}

		//if (isInsideForEach())
		//	statementSources.setIndent(1);
		statementSources.putLine2("if ("+output+") {");

		//add invocation source code to current operation
		//statementSources.putLine2("if ("+operationName+"()) {");
		//currentOperation.addInitialSource(statementSources.get());
		addStatementSources(statementSources);

		statementSources = new Buf();
		//if (isInsideForEach())
		//	statementSources.setIndent(1);
		
		//process the affirmative branch
		Activity mainActivity = activity.getActivity();
		if (mainActivity instanceof Sequence) {
			SequenceGenerator sequenceGenerator = createSequenceGenerator();
			sequenceGenerator.generate((Sequence) mainActivity);
		}

		//process the negative condition branches
		EList<ElseIf> elseIfActivityList = activity.getElseIf();
		if (elseIfActivityList != null && elseIfActivityList.size() > 0) {
			Iterator<ElseIf> iterator = elseIfActivityList.iterator();
			while (iterator.hasNext()) {
				ElseIf elseIfActivity = iterator.next();
				condition = elseIfActivity.getCondition();

				//get references to the variables in scope
				Map<String, Variable> variables = new HashMap<String, Variable>();
				variables.putAll(globalVariables);
				variables.putAll(localVariables);

				expressionGenerator = new ExpressionGenerator(variables);
				output = expressionGenerator.generate(condition.getBody());
				
				statementSources.putLine2("} else if ("+output+") {");
				//currentOperation.addInitialSource(statementSources.get());
				addStatementSources(statementSources);
				statementSources = new Buf();

				if (elseIfActivity instanceof Sequence) {
					SequenceGenerator sequenceGenerator = createSequenceGenerator();
					sequenceGenerator.generate((Sequence) elseIfActivity);
				}
			}
		}

		//process the negative branch
		Else elseActivity = activity.getElse();
		if (elseActivity != null && elseActivity.getActivity() instanceof Sequence) {
			
			statementSources.putLine2("} else {");
			addStatementSources(statementSources);
			//currentOperation.addInitialSource(statementSources.get());
			
			statementSources = new Buf();
			//if (isInsideForEach())
			//	statementSources.setIndent(1);

			SequenceGenerator sequenceGenerator = createSequenceGenerator();
			sequenceGenerator.generate((Sequence) elseActivity.getActivity());
			addStatementSources(sequenceGenerator.getStatementSources());
		}

		statementSources.putLine2("}");
		addStatementSources(statementSources);
		//currentOperation.addInitialSource(statementSources.get());
		statementSources = new Buf();
	}

	protected SequenceGenerator createSequenceGenerator() {
		SequenceGenerator sequenceGenerator = new SequenceGenerator(context, process);
		sequenceGenerator.setGlobalVariables(getVariablesFromProcess(process));
		sequenceGenerator.setLocalVariables(localVariables);
		sequenceGenerator.setCurrentClass(currentClass);
		sequenceGenerator.setCurrentOperation(currentOperation);
		sequenceGenerator.setInsideCodeBlock(true);
		sequenceGenerator.setInsideFlow(isInsideFlow());
		sequenceGenerator.setIndentationLevel(1);
		return sequenceGenerator;
	}
	
}
