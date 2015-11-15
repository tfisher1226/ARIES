package aries.bpel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Variable;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.TokenUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public abstract class AbstractActivityGenerator<T extends Activity> implements ActivityGenerator<T> {

	protected static Set<String> operationCache = new HashSet<String>();
	

	/*
	 * State
	 */

	
	/*
	 * Inputs
	 */

	protected Process process;
	
	protected GenerationContext context;

	protected ModelClass currentClass;

	protected ModelOperation currentOperation;

//	protected ServiceState serviceState;

	protected Map<String, Variable> localVariables;

	protected Map<String, Variable> globalVariables;
	
	protected boolean insideCodeBlock;

	protected boolean insideFlow;

//	protected boolean hasCatchBlock;
	
	protected int indentLevel;
	
	protected boolean verbose;

	/*
	 * outputs
	 */

	protected Buf statementSources = new Buf();

//	protected List<String> sourceStatements = new ArrayList<String>();

	protected List<String> importedClasses = new ArrayList<String>();

	protected List<ModelOperation> instanceOperations = new ArrayList<ModelOperation>();

	protected List<ModelClass> supportingClasses = new ArrayList<ModelClass>();


	public AbstractActivityGenerator() {
		this.verbose = false;
	}

	public AbstractActivityGenerator(GenerationContext context, Process process) {
		this.context = context;
		this.process = process;
		this.verbose = false;
		//operationCache.clear();
	}

	public void clearState() {
		operationCache.clear();
	}
	
	
	/*
	 * inputs
	 */

	public ModelClass getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(ModelClass modelClass) {
		this.currentClass = modelClass;
	}

	public ModelOperation getCurrentOperation() {
		return currentOperation;
	}

	public void setCurrentOperation(ModelOperation modelOperation) {
		this.currentOperation = modelOperation;
	}

//	public void setServiceState(ServiceState serviceState) {
//		this.serviceState = serviceState;
//	}

	//get reference to a variable in scope
	public Variable getVariable(String name) {
		Variable variable = null;
		if (localVariables != null)
			variable = getLocalVariable(name);
		if (variable == null)
			variable = getGlobalVariable(name);
		return variable;
	}

	//TODO is it ok to do this extra name check? - maybe not because there may an incorrect value in here... due to there being another variable of that name.
	public Variable getGlobalVariable(String name) {
		Variable variable = getGlobalVariables().get(name);
		if (variable == null && name.contains("_")) {
			String subName = name.substring(0, name.indexOf("_")-1);
			variable = getGlobalVariables().get(subName);
		}
		return variable;
	}
	
	public Variable getLocalVariable(String name) {
		return getLocalVariables().get(name);
	}

	public boolean isGlobalVariable(String name) {
		return getGlobalVariables().containsKey(name);
	}
	
	public boolean isLocalVariable(String name) {
		return getLocalVariables().containsKey(name);
	}

	public boolean isGlobalVariable(Variable variable) {
		return getGlobalVariables().containsValue(variable);
	}
	
	public boolean isLocalVariable(Variable variable) {
		return getLocalVariables().containsValue(variable);
	}

	public Map<String, Variable> getGlobalVariables() {
		if (globalVariables == null)
			globalVariables = new HashMap<String, Variable>();
		return globalVariables;
	}

	public Map<String, Variable> getLocalVariables() {
		if (localVariables == null)
			localVariables = new HashMap<String, Variable>();
		return this.localVariables;
	}

	//get references to the variables in scope
	public Map<String, Variable> getVariables() {
		Map<String, Variable> variables = new HashMap<String, Variable>();
		variables.putAll(getGlobalVariables());
		variables.putAll(getLocalVariables());
		return variables;
	}
	
	public void addGlobalVariables(Map<String, Variable> globalVariables) {
		getGlobalVariables().putAll(globalVariables);
	}

	public void addLocalVariables(Map<String, Variable> localVariables) {
		getLocalVariables().putAll(localVariables);
	}

	public void setGlobalVariables(Map<String, Variable> globalVariables) {
		this.globalVariables = globalVariables;
	}

	public void setLocalVariables(Map<String, Variable> localVariables) {
		this.localVariables = localVariables;
	}

	public String getVariableName(Variable variable) {
		if (variable == null)
			return null;
		String name = CodeUtil.getVariableName(variable, isGlobalVariable(variable));
		return name;
	}
	
	public boolean isInsideCodeBlock() {
		return insideCodeBlock;
	}

	public void setInsideCodeBlock(boolean insideCodeBlock) {
		this.insideCodeBlock = insideCodeBlock;
	}

	public boolean isInsideFlow() {
		return insideFlow;
	}

	public void setInsideFlow(boolean insideFlow) {
		this.insideFlow = insideFlow;
	}

	public int getIndentationLevel() {
		return indentLevel;
	}

	public void setIndentationLevel(int indentationLevel) {
		this.indentLevel = indentationLevel;
	}
	
	public void increaseIndentationLevel(int indentationLevel) {
		this.indentLevel += indentationLevel;
	}

	
	/*
	 * outputs
	 */

	public Buf getStatementSources() {
		return statementSources;
	}
	
//	public List<String> getSourceStatement() {
//		return sourceStatements;
//	}

	public List<String> getImportedClasses() {
		return importedClasses;
	}

	public void addImportedClass(String importedClass) {
		if (TypeUtil.isImportedClassRequired(importedClass))
			this.importedClasses.add(importedClass);
	}

	public List<ModelOperation> getInstanceOperations() {
		return instanceOperations;
	}

	public void addInstanceOperation(ModelOperation instanceOperation) {
		this.instanceOperations.add(instanceOperation);
	}

	public List<ModelClass> getSupportingClasses() {
		return supportingClasses;
	}

	public void addSupportingClass(ModelClass supportingClass) {
		this.supportingClasses.add(supportingClass);
	}

	public void addSupportingClasses(List<ModelClass> supportingClasses) {
		this.supportingClasses.addAll(supportingClasses);
	}


	
	/*
	 * common operations
	 */

	protected Map<String, Variable> getVariablesFromProcess(Process process) {
		Map<String, Variable> variables = BPELUtil.createVariablesMap(process.getVariables());
		return variables;
	}

	protected Variable getVariableForFaultType(String faultTypeName) {
		Collection<Variable> values = localVariables.values();
		Iterator<Variable> iterator = values.iterator();
		while (iterator.hasNext()) {
			Variable variable = (Variable) iterator.next();
			String typeName = TypeUtil.getTypeFromVariable(variable);
			if (typeName.equals(faultTypeName))
				return variable;
		}
		return null;
	}
	
	
	public void addStatementSources(Buf statementSources) {
		if (currentOperation != null) {
			//currentOperation.addInitialSource(statementSources.get());
			addStatementsToOperation(currentOperation, statementSources);
		}
	}
	
	public void addInstanceOperations(List<ModelOperation> instanceOperations) {
		Iterator<ModelOperation> iterator = instanceOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation instanceOperation = iterator.next();
			currentClass.addInstanceOperation(instanceOperation);
		}
	}

	public void addImportedClasses(List<String> importedClasses) {
		Iterator<String> iterator = importedClasses.iterator();
		while (iterator.hasNext()) {
			String importedClass = iterator.next();
			if (TypeUtil.isImportedClassRequired(importedClass))
				currentClass.addImportedClass(importedClass);
		}
	}

	protected void addStatementsToOperation(ModelOperation modelOperation, Buf sourceCode) {
		Buf buf = new Buf();
		String code = sourceCode.get();
		String NL = System.getProperty("line.separator");
		String[] lines = TokenUtil.tokenize(code, NL);
		int indentLevel = getIndentationLevel();
		List<String> exceptions = modelOperation.getExceptions();
		boolean hasCatchBlock = exceptions != null && exceptions.size() > 0;
		if (hasCatchBlock)
			indentLevel++;
		if (isInsideCodeBlock())
			indentLevel++;
		for (String line : lines) {
			for (int i=0; i < indentLevel; i++)
				buf.put("\t");
			buf.putLine(line);
		}
		modelOperation.addInitialSource(buf.get());
	}
	
	protected void addInvocationSource(ModelOperation modelOperation) {
		String resultType = modelOperation.getResultType();
		if (resultType != null && !resultType.equals("void")) {
			String resultName = modelOperation.getResultName();
			statementSources.put(resultType+" "+resultName+" = "+modelOperation.getName()+"(");
		} else {
			statementSources.put(modelOperation.getName()+"(");
		}
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter modelParameter = iterator.next();
			if (i > 0)
				statementSources.put(", ");
			statementSources.put(modelParameter.getName());
		}
		statementSources.putLine(");");
	}
	
	
	protected String generateSourceConditionStateAssignments(Activity activity) throws Exception {
		Buf buf = new Buf();
		Sources sources = activity.getSources();
		if (sources != null) {
			Iterator<Source> sourceIterator = sources.getChildren().iterator();
			while (sourceIterator.hasNext()) {
				Source source = sourceIterator.next();
				Link link = source.getLink();
				String linkName = NameUtil.getFilteredName(link.getName());
				Condition condition = source.getTransitionCondition();
				if (condition != null) {
					ExpressionGenerator expressionGenerator = new ExpressionGenerator(localVariables);
					String conditionText = expressionGenerator.generate(condition.getBody());
					buf.putLine2(linkName+" = "+conditionText+";");
				} else {
					buf.putLine2(linkName+" = true;");
				}
			}
		}
		return buf.get();
	}
	
}
