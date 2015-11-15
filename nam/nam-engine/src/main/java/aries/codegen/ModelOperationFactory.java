package aries.codegen;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.ProjectLevelHelper;
import nam.client.ClientLayerHelper;
import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Cache;
import nam.model.Callback;
import nam.model.Command;
import nam.model.CommandName;
import nam.model.Definition;
import nam.model.Element;
import nam.model.Fault;
import nam.model.Field;
import nam.model.Group;
import nam.model.Interactor;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Project;
import nam.model.Reference;
import nam.model.Result;
import nam.model.Service;
import nam.model.Type;
import nam.model.Unit;
import nam.model.statement.AbstractBlock;
import nam.model.statement.DefinitionCommand;
import nam.model.statement.DoneCommand;
import nam.model.statement.ExceptionBlock;
import nam.model.statement.ExpressionStatement;
import nam.model.statement.IfStatement;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ListenCommand;
import nam.model.statement.PostCommand;
import nam.model.statement.ReplyCommand;
import nam.model.statement.ResponseBlock;
import nam.model.statement.SendCommand;
import nam.model.statement.TimeoutBlock;
import nam.model.util.ApplicationUtil;
import nam.model.util.CommandUtil;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ParameterUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.ExpressionUtil;
import aries.codegen.util.ModelFieldUtil;
import aries.generation.AriesModelUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelReference;
import aries.generation.model.ModelVariable;



public class ModelOperationFactory {

	private GenerationContext context;

	private ModelClass modelClass;

	private String messageType;
	
	private Element messageElement;

	protected Set<ModelReference> referenceStack;

	protected Map<String, Type> availableVariablesInScope;
	
	protected Map<String, Type> definedLocalVariables;
	
	protected Map<String, Type> definedVariablesInScope;
	
	protected Set<String> requiredLocalVariables;
	
	protected Set<String> requiredVariablesInScope;
	
	protected Set<ModelOperation> localOperations;

	//protected Map<String, ModelOperation> localOperationStack;

	//protected Map<String, ModelOperation> callbackOperationStack;

	private Set<ModelOperation> messageCreationOperations;
	
	private Set<ModelOperation> loggedStateCancelOperations;

	private Set<ModelOperation> loggedStateUndoOperations;

	private Set<ModelOperation> logStateOperations;
	
	private Set<String> keyWordsInScope;
	
	private AriesModelUtil ariesModelUtil;

	private int optionCount;


	public ModelOperationFactory(GenerationContext context) {
		ariesModelUtil = new AriesModelUtil(context);
		messageCreationOperations = new LinkedHashSet<ModelOperation>();
		loggedStateCancelOperations = new LinkedHashSet<ModelOperation>();
		loggedStateUndoOperations = new HashSet<ModelOperation>();
		logStateOperations = new LinkedHashSet<ModelOperation>();
		keyWordsInScope = new HashSet<String>();
		this.context = context;
	}
	
	public void reset(ModelClass modelClass) {
		this.modelClass = modelClass;
		resetServiceState();
		resetCallbackState();
		referenceStack = new LinkedHashSet<ModelReference>();
	}
	
	public void resetServiceState() {
		availableVariablesInScope = new HashMap<String, Type>();
		definedLocalVariables = new HashMap<String, Type>();
		definedVariablesInScope = new HashMap<String, Type>();
		requiredLocalVariables = new LinkedHashSet<String>();
		requiredVariablesInScope = new LinkedHashSet<String>();
		localOperations = new LinkedHashSet<ModelOperation>();
		//localOperationStack = new HashMap<String, ModelOperation>();
	}
	
	public void resetCallbackState() {
		definedLocalVariables = new HashMap<String, Type>();
		definedVariablesInScope = new HashMap<String, Type>();
		requiredLocalVariables = new LinkedHashSet<String>();
		requiredVariablesInScope = new LinkedHashSet<String>();
		//callbackOperationStack = new HashMap<String, ModelOperation>();
	}

	public Collection<ModelOperation> getMessageCreationOperations() {
		return CodeUtil.sortOperations(messageCreationOperations);
	}

	public Collection<ModelOperation> getLoggedStateCancelOperations() {
		return CodeUtil.sortOperations(loggedStateCancelOperations);
	}

	public Collection<ModelOperation> getLoggedStateUndoOperations() {
		return CodeUtil.sortOperations(loggedStateUndoOperations);
	}

	public Collection<ModelOperation> getLogStateOperations() {
		return CodeUtil.sortOperations(logStateOperations);
	}

//	public Map<String, ModelOperation> getLocalOperationStack() {
//		return localOperationStack;
//}

	public void addMessageCreationOperations(ModelOperation modelOperation) {
		messageCreationOperations.add(modelOperation);
	}

	public void addLoggedStateCancelOperation(ModelOperation modelOperation) {
		loggedStateCancelOperations.add(modelOperation);
	}

	public void addLoggedStateUndoOperation(ModelOperation modelOperation) {
		loggedStateUndoOperations.add(modelOperation);
	}

	public void addLogStateOperation(ModelOperation modelOperation) {
		logStateOperations.add(modelOperation);
	}

	public void addLogStateOperations(Collection<ModelOperation> modelOperations) {
		logStateOperations.addAll(modelOperations);
	}

	public Set<ModelReference> getReferenceStack() {
		return referenceStack;
	}


	/*
	 * AvailableVariablesInScope
	 */
	
	public Map<String, Type> getAvailableVariablesInScope() {
		return availableVariablesInScope;
	}

	public Type getAvailableVariableInScope(String name) {
		return availableVariablesInScope.get(name);
	}

	public boolean isAvailableVariableInScope(String name) {
		return availableVariablesInScope.containsKey(name);
	}
	
	public void addAvailableVariableInScope(String name, Type type) {
		availableVariablesInScope.put(name, type);
	}

	/*
	 * DefinedLocalVariables
	 */
	
	public Map<String, Type> getDefinedLocalVariables() {
		return definedLocalVariables;
	}

	public Type getDefinedLocalVariable(String name) {
		return definedLocalVariables.get(name);
	}

	public boolean isDefinedLocalVariable(String name) {
		return definedLocalVariables.containsKey(name);
	}

	public void addDefinedLocalVariable(String name, Type type) {
		definedLocalVariables.put(name, type);
	}

	public void addDefinedLocalVariables(Command command) {
		addDefinedLocalVariables(command.getParameters());
	}
	
	public void addDefinedLocalVariables(List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			Element element = context.getElementByType(parameter.getType());
			addDefinedLocalVariable(name, element);
		}
	}

	//TODO do not remove variables that existed previously before this command was added
	public void removeDefinedLocalVariables(Command command) {
		removeDefinedLocalVariables(command.getParameters());
	}

	public void removeDefinedLocalVariables(List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			removeDefinedLocalVariables(name);
		}
	}

	public void removeDefinedLocalVariables(String name) {
		definedLocalVariables.remove(name);
	}

	/*
	 * DefinedVariablesInScope
	 */
	
	public Map<String, Type> getDefinedVariablesInScope() {
		return definedVariablesInScope;
	}

	public Type getDefinedVariableInScope(String name) {
		return definedVariablesInScope.get(name);
	}

	public boolean isDefinedVariableInScope(String name) {
		return definedVariablesInScope.containsKey(name);
	}

	public void addDefinedVariableInScope(String name, Type type) {
		definedVariablesInScope.put(name, type);
	}

	public void addDefinedVariablesInScope(Command command) {
		addDefinedVariablesInScope(command.getParameters());
	}
	
	public void addDefinedVariablesInScope(List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			Element element = context.getElementByType(parameter.getType());
			addDefinedVariableInScope(name, element);
		}
	}
	

	/*
	 * RequiredLocalVariables
	 */
	
	public Set<String> getRequiredLocalVariables() {
		return requiredLocalVariables;
	}

	public boolean isRequiredLocalVariable(String name) {
		return requiredLocalVariables.contains(name);
	}
	
	public void addRequiredLocalVariable(String name) {
		if (!definedLocalVariables.containsKey(name))
			requiredLocalVariables.add(name);
	}
	
	public void addRequiredLocalVariables(Command command) {
		addRequiredLocalVariables(command.getParameters());
	}
	
	public void addRequiredLocalVariables(List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			addRequiredLocalVariable(name);
		}
	}

	public void addRequiredLocalVariables(Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String name = field.getName();
			if (name.equals("id"))
				continue;
			addRequiredLocalVariable(name);
		}
	}
	
	/*
	 * RequiredVariablesInScope
	 */
	
	public Set<String> getRequiredVariablesInScope() {
		return requiredVariablesInScope;
	}

	public boolean isRequiredVariableInScope(String name) {
		return requiredVariablesInScope.contains(name);
	}
	
	public void addRequiredVariableInScope(String name) {
		if (!definedVariablesInScope.containsKey(name))
			requiredVariablesInScope.add(name);
	}
	
	public void addRequiredVariablesInScope(Command command) {
		addRequiredVariablesInScope(command.getParameters());
	}
	
	//TODO be careful about what gets added here - also skip over certain keywords
	public void addRequiredVariablesInScope(List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			if (name.endsWith("Message"))
				continue;
			//TODO skip keywords
			if (name.equals("reason") || name.equals("cause"))
				continue;
			addRequiredVariableInScope(name);
		}
	}

	public void addRequiredVariablesInScope(Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String name = field.getName();
			if (name.equals("id"))
				continue;
			addRequiredVariableInScope(name);
		}
	}
	
	public void clearRequiredVariablesInScope() {
		requiredVariablesInScope.clear();
	}
	
	
//	public Map<String, ModelOperation> getCallbackOperationStack() {
//		return callbackOperationStack;
//	}

	
	/*
	 * Attribute helper methods
	 * ------------------------
	 */

	public List<ModelReference> getPendingReferencesFromStack() {
		return getPendingReferencesFromStack(referenceStack);
	}
	
	public List<ModelReference> getPendingReferencesFromStack(Set<ModelReference> references) {
		List<ModelReference> list = new ArrayList<ModelReference>();
		Iterator<ModelReference> iterator = references.iterator();
		while (iterator.hasNext()) {
			ModelReference reference = iterator.next();
			list.add(reference);
		}
		references.clear();
		return list;
	}


	/*
	 * Operation helper methods
	 * ------------------------
	 */

	public Set<ModelOperation> getPendingLocalOperationsFromStack() {
		//return getPendingOperationsFromStack(localOperationStack);
		return localOperations;
	}
	
//	public List<ModelOperation> getPendingCallbackOperationsFromStack() {
//		return getPendingOperationsFromStack(callbackOperationStack);
//	}
	
//	public Set<ModelOperation> getPendingOperationsFromStack(Map<String, ModelOperation> operationStack) {
//		Set<ModelOperation> set = new LinkedHashSet<ModelOperation>();
//		List<String> keys = new ArrayList<String>(operationStack.keySet());
//		Collections.sort(keys);
//		
//		Iterator<String> iterator = keys.iterator();
//		while (iterator.hasNext()) {
//			String key = iterator.next();
//			ModelOperation modelOperation = operationStack.get(key);
//			set.add(modelOperation);
//		}
//		//operationStack.clear();
//		//Collections.sort(list);
//		return set;
//	}

	public void pushLocalOperationCache(ModelOperation modelOperation) {
		if (!localOperations.contains(modelOperation)) {
			localOperations.add(modelOperation);
			//System.out.println(">>>"+modelOperation);
		} //else System.out.println(">>>"+modelOperation);
	}

	public void pushLocalOperationCache(List<ModelOperation> modelOperations) {
		Iterator<ModelOperation> iterator = modelOperations.iterator();
		while (iterator.hasNext()) {
			ModelOperation modelOperation = iterator.next();
			pushLocalOperationCache(modelOperation);
		}
	}

//	public void pushLocalOperationCache(String operationKey, ModelOperation modelOperation) {
//		String operationId = getOperationId(modelOperation);
//		if (!createdOperationSet.contains(operationId)) {
//			createdOperationSet.add(operationId);
//			localOperationStack.put(operationKey, modelOperation);
//		}
//	}

	public String getOperationId(ModelOperation modelOperation) {
		String argumentString = CodeUtil.getArgumentString(modelOperation);
		String operationId = modelOperation.getName() + "(" +argumentString+ ")";
		return operationId;
	}

	//resolving data linkage between operation's parameters and command's arguments
	public String generateSourceForCommandInput(Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = command.getParameters().iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			Type element = context.findTypeByName(argument);
			if (!argument.contains(".") && !isArgumentIncludedInParameters(argument, parameters) && element != null) {
			//if (!argument.contains(".") && !isArgumentIncludedInParameters(argument, parameters)) {
				String argumentUncapped = NameUtil.uncapName(argument);
				String argumentCapped = NameUtil.capName(argument);
				buf.putLine(argumentCapped+" "+argumentUncapped+" = new "+argumentCapped+"();");
			}
		}
		return buf.get();
	}

	public boolean isArgumentIncludedInParameters(String argument, List<Parameter> parameters) {
		Iterator<Parameter> iterator = parameters.iterator(); 
		while (iterator.hasNext()) {
			Parameter parameter = (Parameter) iterator.next();
			if (parameter.getName().equalsIgnoreCase(argument))
				return true;
		}
		return false;
	}


	public String generateSourceForVariables() throws Exception {
		return generateSourceForVariables(null);
	}
	
	public String generateSourceForVariables(List<String> arguments) throws Exception {
		Buf buf = new Buf();
		Process process = context.getProcess();
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(process);

		Iterator<String> iterator = requiredVariablesInScope.iterator();
		while (iterator.hasNext()) {
			String variableName = iterator.next();
			String fieldName = NameUtil.capName(variableName);
			String beanName = NameUtil.uncapName(variableName);
			
			if (arguments != null && arguments.contains(variableName))
				continue;
			
			boolean availableVariableInScope = isAvailableVariableInScope(beanName);
			boolean definedVariableInScope = isDefinedVariableInScope(beanName);
			boolean definedLocalVariable = isDefinedLocalVariable(beanName);
//			if (definedLocalVariable)
//				continue;
			
//			if (beanName.equalsIgnoreCase("order"))
//				System.out.println();
			
			boolean isLocal = false;
			Type variableType = availableVariablesInScope.get(variableName);
			if (variableType == null) {
				Element messageElement = context.getMessageElement();
				Field field = ElementUtil.getField(messageElement, variableName);
				if (field != null) {
					variableType = field;
					//isLocal = true;
				}
			}

			if (variableType == null) {
				Service service = context.getService();
				Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
				Operation processOperation = ProcessUtil.getOperation(process, serviceOperation.getName());
				String messageType = ServiceLayerHelper.getMessageType(processOperation);
				Element messageElement = context.getElementByType(messageType);
				Field field = ElementUtil.getField(messageElement, variableName);
				if (field != null) {
					variableType = field;
					//isLocal = true;
				}
			}
			
//			if (variableName.equals("address"))
//				System.out.println();
			
			if (variableType != null) {
				String typeName = variableType.getType();
				if (!typeName.startsWith("{")) {
					Element element = context.getElementByName(typeName);
					modelClass.addImportedClass(element);
				} else modelClass.addImportedClass(variableType);
			}
			
			if (!isLocal && variableType != null)
				buf.putLine2(printType(variableType)+" "+variableName+" = "+processContextName+".get"+fieldName+"();");
			else buf.putLine2(variableName+" = "+processContextName+".get"+fieldName+"();");
			
			if (variableType != null)
				addDefinedLocalVariable(variableName, variableType);
		}
		
		return buf.get();
	}
	

	public String generateSourceForCommands(Operation operation, List<Command> commands) throws Exception {
		Buf buf = new Buf();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			
			//TODO initializeCommandArgumentsWithOperationParameters(operation, command);
			//buf.put(generateSourceForCommandInput(operation, command));
			
			String sourceCode = generateSourceForCommand(operation, command);
			buf.put(sourceCode);
			
			if (command instanceof ReplyCommand) {
				//ReplyCommand replyCommand = (ReplyCommand) command;
				//String callbackInterface = operationBaseNameCapped;
				//fireEventSourceBuf.putLine2("fire"+callbackInterface+"Done();");
				//serviceInterface = NameUtil.capName(replyCommand.getServiceName());
			}
		}
		return buf.get();
	}

	public String generateSourceForDoneCommand(Operation operation, List<Command> commands) throws Exception {
		Buf buf = new Buf();
		int size = commands.size();
		if (size > 0) {
			Command lastCommand = commands.get(size-1);
			if (lastCommand instanceof DoneCommand) {
				DoneCommand doneCommand = (DoneCommand) lastCommand;
				String sourceCode = generateSourceForDoneCommand(2, operation, doneCommand);
				buf.put(sourceCode);
			}
		}
		return buf.get();
	}
	
	public String generateSourceForCommand(Operation operation, Command command) throws Exception {
		return generateSourceForCommand(2, operation, command);
	}
	
	public String generateSourceForCommand(int indent, Operation operation, Command command) throws Exception {
		//System.out.println(">>>"+command.getText());
		//if (command.getText() == null)
		//	System.out.println();
		//if (command.getText() != null && command.getText().equalsIgnoreCase("OrderBooks"))
		//	System.out.println();
		
		CommandName name = command.getName();
		switch (name) {
		case EXPRESSION: return generateSourceForExpressionCommand(indent, operation, command);
		//case DONE: return generateSourceForDoneCommand(indent, operation, (DoneCommand) command);
		case CALL: return generateSourceForCallCommand(indent, operation, command);
		case INVOKE: return generateSourceForInvokeCommand(indent, operation, (InvokeCommand) command);
		case EXECUTE: return generateSourceForExecuteCommand(indent, operation, command);
		case LISTEN: return generateSourceForListenCommand(indent, operation, (ListenCommand) command);
		case NEW: return generateSourceForNewCommand(indent, operation, command);
		case OPTION: return generateSourceForOptionCommand(indent, operation, command);
		case POST: return generateSourceForPostCommand(indent, operation, (PostCommand) command);
		case REPLY: return generateSourceForReplyCommand(indent, operation, (ReplyCommand) command);
		case SEND: return generateSourceForSendCommand(indent, operation, (SendCommand) command);
		case THROW: return generateSourceForThrowCommand(indent, operation, command);
		default: 
			return "";
		}
	}

	public String generateSourceForExpressionCommand(int indent, Operation operation, Command command) throws Exception {
		//ariesModelUtil.printCommandSource(command);
		Buf buf = new Buf();
		String type = command.getType();
		performTokenSubstitution(command);
		String source = ariesModelUtil.getCommandSource(command);
		if (source != null) {
			if (type.equals("if"))
				return printIfCommand(indent, operation, (IfStatement) command);
			if (type.equals("DEFINITION"))
				return printDefinitionCommand(indent, (DefinitionCommand) command);
			if (type.equals("expression"))
				return printExpressionStatement(indent, (ExpressionStatement) command);
			if (!source.endsWith(";"))
				source += ";";
			//System.out.println(">>> "+source);
			buf.put(indent, source);
		}
		buf.putLine("");
		return buf.get();
	}

	public void performTokenSubstitution(Command command) throws Exception {
		//System.out.println(">>> "+printStatementCommand(0, command));
		List<String> tokens = new ArrayList<String>(command.getTokens());
		Iterator<String> iterator = tokens.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String token = iterator.next();
			String processedToken = performTokenSubstitution(token);
			if (processedToken != null) {
				command.getTokens().set(i, processedToken);
			}
		}
	}

	public List<String> performTokenSubstitution(List<String> list) {
		List<String> processedParameters = new ArrayList<String>();
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String parameter = iterator.next();
			String processedParameter = performTokenSubstitution(parameter);
			processedParameters.add(processedParameter);
		}
		return processedParameters;
	}
	
	public String performTokenSubstitution(String token) {
		Process process = context.getProcess();
		if (token.equals("context"))
			return ServiceLayerHelper.getProcessContextInstanceName(process);
 					
		List<Cache> caches = process.getCacheUnits();
		Iterator<Cache> cacheIterator = caches.iterator();
		while (cacheIterator.hasNext()) {
			Cache cache = cacheIterator.next();
			if (cache.getName().equalsIgnoreCase(token)) {
				String newToken = NameUtil.uncapName(token) + "Manager";
				return newToken;
			}
		}
		Persistence persistence = context.getModule().getPersistence();
		if (persistence != null) {
			List<Unit> units = PersistenceUtil.getUnits(persistence);
			Iterator<Unit> iterator = units.iterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				if (unit.getName().equalsIgnoreCase(token)) {
					String newToken = NameUtil.uncapName(token) + "Manager";
					return newToken;
				}
			}
		}

		Element elementByName = context.getElementByName(token + "Message");
		if (elementByName != null)
			return token + "Message";
		return token;
	}

	public String printIfCommand(int indent, Operation operation, IfStatement ifNode) throws Exception {
		return printIfCommand(indent, operation, ifNode, false);
	}

	public String printElseIfCommand(int indent, Operation operation, IfStatement ifNode) throws Exception {
		return printIfCommand(indent, operation, ifNode, true);
	}

	public String printIfCommand(int indent, Operation operation, IfStatement ifStatement, boolean isElse) throws Exception {
		String conditionText = ifStatement.getConditionText();
		//indent += ifStatement.getIndent();
		
		Buf buf = new Buf();
		if (!isElse)
			buf.putLine(indent, "if ("+conditionText+") {");
		else buf.putLine(indent, "} else if ("+conditionText+") {");
		
		//print main body of statements here
		buf.put(printStatementCommands(indent+1, operation, ifStatement.getCommands()));
		
		//print elseif bodies of statements here
		List<Command> elseIfCommands = ifStatement.getElseIfCommands();
		Iterator<Command> iterator = elseIfCommands.iterator();
		while (iterator.hasNext()) {
			Command elseIfCommand = iterator.next();
			buf.put(printElseIfCommand(indent, operation, (IfStatement) elseIfCommand));
		}
		
		//print else body of statements here
		List<Command> elseCommands = ifStatement.getElseCommands();
		if (elseCommands != null && elseCommands.size() > 0) {
			buf.putLine(indent, "} else {");
			buf.put(printStatementCommands(indent+1, operation, elseCommands));
			buf.putLine(indent, "}");
		} else
			buf.putLine(indent, "}");
		return buf.get();
	}

	public String printDefinitionCommand(int indent, DefinitionCommand definitionCommand) throws Exception {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());

		Definition definition = definitionCommand.getDefinition();
		String name = performTokenSubstitution(definition.getName());
		Type type = definition.getType();
		//if (definitionCommand.getIndent() != null)
		//	indent += definitionCommand.getIndent();
		boolean isDefined = isDefinedVariableInScope(name);
		addDefinedVariableInScope(name, type);
		addAvailableVariableInScope(name, type);
		
		Buf buf = new Buf();
		//String typeName = performTokenSubstitution(type.getName());
		List<String> parameters = performTokenSubstitution(definition.getParameters());
		String argumentString = NameUtil.toCommaSeparatedString(parameters);
		String target = performTokenSubstitution(definition.getTarget());
		
		//TODO special case here - need handle it better
		Operation operation = context.getOperation();
		List<Parameter> operationParameters = operation.getParameters();
		if (target.endsWith("Message") && operationParameters.size() == 1 && operationParameters.get(0).getName().equals(target))
			target = "message";

//		Iterator<Parameter> iterator = operationParameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			Element element = context.getElementByType(parameter.getType());
//			Assert.notNull(element, "Element not found for parameter: "+parameter.getName());
//			Field field = ElementUtil.getField(element, target);
//			if (field != null) {
//				String beanName = field.getName();
//				String fieldName = NameUtil.capName(beanName);
//				//addDefinedVariableInScope(fieldName, field);
//				boolean availableVariableInScope = isAvailableVariableInScope(beanName);
//				boolean definedVariableInScope = isDefinedVariableInScope(beanName);
//				boolean definedLocalVariable = isDefinedLocalVariable(beanName);
////				if (!definedVariableInScope && !definedLocalVariable) {
////					buf.putLine(indent, fieldName+" "+beanName+" = "+processContextName+".get"+fieldName+"();");
////					addDefinedLocalVariable(beanName, field);
////				}
//				break;
//			}
//		}

		//TODO include checks for these when we cannot find the referenced variable
		boolean availableVariableInScope = isAvailableVariableInScope(target);
		boolean definedVariableInScope = isDefinedVariableInScope(target);
		boolean definedLocalVariable = isDefinedLocalVariable(target);
		//TODO end

		Application application = context.getApplication();
		String applicationName = NameUtil.uncapName(application.getName());
		if (!target.equals(applicationName+"Context") && !target.equals("message")) {
			Element element = context.getElementByName(target);
			//TODO exclude if a global variable
			if (element != null)
				//addRequiredVariableInScope(target);
				modelClass.addImportedClass(element);
		}
		
		//Handle string literals
		
//		if (name.equals("dateTime"))
//			System.out.println();
		
		if (target.equals("factory")) {
			Element element = context.getElementByName(type.getName());
			Assert.notNull(element, "Element not found for: "+type.getName());
			String packageName = ModelLayerHelper.getElementPackageName(element);
			String factoryPrefix = NameUtil.getSimpleName(packageName);
			target = factoryPrefix + "ObjectFactory";
			referenceStack.add(createReference_ObjectFactory(packageName));
		}
		
		Boolean isNew = definition.getIsNew();
		List<String> selectors = definition.getSelectors();
		String typeBlock = isDefined ? "" : printType(type)+" ";
		
		if (selectors.size() == 1 && selectors.get(0).startsWith("\"") && selectors.get(0).endsWith("\"")) {
			//this is special case for a text literal as an assigned value
			buf.putLine(indent, typeBlock+name+" = "+selectors.get(0)+";");
		
		} else if (isNew != null && isNew) {
			//this is special case for "new" definitions
			buf.putLine(indent, typeBlock+name+" = new "+target+printSelectors(selectors)+"("+argumentString+");");
			//addImportedClass(target);
			
		} else {
			buf.putLine(indent, typeBlock+name+" = "+target+"."+printSelectors(selectors)+"("+argumentString+");");
		}
		
		Element elementForType = context.getElementByName(type.getName());
		//Assert.notNull(elementForType, "Element not found for type: "+type.getName());
		if (elementForType != null)
			modelClass.addImportedClass(elementForType);
		return buf.get();
	}

	public void addImportedClass(String target) {
		String qualifiedName = ClassUtil.convertTypeToJavaClass(target);
		if (qualifiedName != null) {
			modelClass.addImportedClass(qualifiedName);
			return;
		}
		Element element = context.getElementByName(target);
		if (element != null) {
			modelClass.addImportedClass(element);
			return;
		}
		//TODO handle this case somehow...
		//TODO add class imports to ARIEL?
	}

	public String printExpressionStatement(int indent, ExpressionStatement expression) throws Exception {
		//if (expression.getMethodName().equals("addToBookOrders"))
			//argumentString = "shipment.getOrder().getBooks()";
			//System.out.println();

		Buf buf = new Buf();
		String targetName = performTokenSubstitution(expression.getTargetName());
		List<Parameter> parameters = expression.getParameters();

		String subExpressionText = "";
		ExpressionStatement subExpression = expression.getSubExpression();
		if (subExpression != null) {
			String subExpressionTarget = subExpression.getTargetName();
			if (!subExpressionTarget.equals("this"))
				subExpressionText = subExpressionTarget;
			else subExpressionText = subExpression.getTargetName();
			List<String> selectorChain = subExpression.getSelectorChain();
			Iterator<String> iterator = selectorChain.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String selector = iterator.next();
				subExpressionText += ".";
				subExpressionText += selector;
			}
			addRequiredVariableInScope(subExpressionTarget);
		}
		
		if (subExpressionText.isEmpty()) {
			subExpressionText = CodeUtil.getArgumentString(parameters);
			addRequiredVariablesInScope(parameters);
		}
		
		buf.putLine(indent, targetName+"."+expression.getSelector()+"("+subExpressionText+");");
		return buf.get();
	}

	public String printType(Type type) throws Exception {
		return printType(type.getStructure(), type.getType(), type.getKey());
	}
	
	public String printType(String structure, String type, String key) throws Exception {
		if (type.startsWith("{"))
			type = TypeUtil.getClassName(type);
		String className = performTokenSubstitution(type);
		Buf buf = new Buf();
		if (structure == null)
			structure = "item";
		if (structure.equals("item")) {
			buf.put(className);
		} else if (structure.equals("list")) {
			buf.put("List<"+className+">");
			modelClass.addImportedClass("java.util.List");
		} else if (structure.equals("set")) {
			buf.put("Set<"+className+">");
			modelClass.addImportedClass("java.util.Set");
		} else if (structure.equals("map")) {
			String keyClassName = performTokenSubstitution(key);
			buf.put("Map<"+keyClassName+", "+className+">");
			modelClass.addImportedClass("java.util.Map");
		}
		return buf.get();
	}
	
	public String printSelectors(List<String> selectors) throws Exception {
		Buf buf = new Buf();
		int size = selectors.size();
		Iterator<String> iterator = selectors.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String selector = iterator.next();
			if (i+1 == size) {
				if (selector.startsWith("create")) {
					String token = selector.substring(6);
					selector = "create" + performTokenSubstitution(token);
				} else if (selector.startsWith("remove")) {
					//do nothing
				} else if (!selector.startsWith("get")) {
					selector = "get" + NameUtil.capName(selector);
				}
			}
			buf.put(selector);
		}
		return buf.get();
	}

//	public String printReplyCommand(int indent, ReplyCommand replyCommand) throws Exception {
//		Buf buf = new Buf();
//		replyCommand.getParameters();
//		String argumentString = CodeUtil.getArgumentString(replyCommand);
//		String operationName = "reply_" + replyCommand.getMessageName() + "";
//		
////		if (operationName.equals("replyShipmentScheduled"))
////			System.out.println();
//		
//		buf.putLine(indent, operationName+"("+argumentString+");");
//		//List<Parameter> arguments = replyCommand.getParameters();
//		String processScope = replyCommand.getProcessScope();
//		if (processScope.equals("receive") || processScope.equals("message"))
//			pushLocalOperationCache(createReplyOperation_CustomArgs(2, replyCommand));
//		if (processScope.equals("exception"))
//			pushLocalOperationCache(createReplyOperation_ErrorArgs(2, replyCommand));
//		if (processScope.equals("timeout"))
//			pushLocalOperationCache(createReplyOperation_TimeoutArgs(2, replyCommand));
//		pushLocalOperationCache(createReplyOperation_MessageArgs(2, replyCommand));
//		return buf.get();
//	}
	
	public String printSendCommand(int indent, Command sendCommand) throws Exception {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String serviceName = getExtendedDomainServiceName(interactor);
		
		Buf buf = new Buf();
		sendCommand.getParameters();
		String operationName = "send_"+NameUtil.capName(serviceName) + "_request";
		List<String> arguments = performTokenSubstitution(CodeUtil.getArgumentList(sendCommand));
		String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, operationName+"("+argumentString+");");
//		if (!createdOperationSet.contains(operationName)) {
//			pushLocalOperationCache(createSendOperation(2, (Command) sendCommand));
//			pushLocalOperationCache(createSendOperation2(2, (Command) sendCommand));
//			pushLocalOperationCache(createSendOperation3(2, (Command) sendCommand));
//			createdOperationSet.add(operationName);
//		}
		return buf.get();
	}
	

//	public String printUnnamedCommand(int indent, BlockStatement statementCommand) {
//		return printStatementCommands(indent, statementCommand.getCommands());
//	}
	
	public String printStatementCommands(int indent, Operation operation, List<Command> commands) throws Exception {
		Buf buf = new Buf();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			String text = printStatementCommand(indent, operation, command);
			buf.put(text);
		}
		return buf.get();
	}
	
	public String printStatementCommand(int indent, Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		String type = command.getType();
		//String val = statementNode.getValue();
		//System.out.println(">>>"+val);
		if (type.equals("if")) {
			buf.put(printIfCommand(indent, operation, (IfStatement) command));
//		} else if (type.equals("unnamed")) {
//			buf.put(printStatementCommand(indent, (Command) statementCommand));
//		} else if (text.equals("while")) {
//			buf.put(printWhileNode(indent, (WhileNode) statementCommand));
//		} else if (text.equals("for")) {
//			buf.put(printForNode(indent, (ForNode) statementCommand));
//		} else if (text.equals("do")) {
//			buf.put(printDoNode(indent, (DoNode) statementCommand));
//		} else if (text.equals("continue")) {
//			buf.put(printContinueNode(indent, (ContinueNode) statementCommand));
//		} else if (text.equals("break")) {
//			buf.put(printBreakNode(indent, (BreakNode) statementCommand));
//		} else if (text.equals("throw")) {
//			buf.put(printThrowNode(indent, (ThrowNode) statementCommand));
//		} else if (text.equals("return")) {
//			buf.put(printReturnNode(indent, (ReturnNode) statementCommand));
			
		} else if (type.equals("invoke")) {
			buf.put(generateSourceForInvokeCommand(indent, operation, (InvokeCommand) command));
		} else if (type.equals("send")) {
			buf.put(generateSourceForSendCommand(indent, operation, (SendCommand) command));
		} else if (type.equals("reply")) {
			buf.put(generateSourceForReplyCommand(indent, operation, (ReplyCommand) command));
		} else if (type.equals("done")) {
			buf.put(generateSourceForDoneCommand(indent, operation, (DoneCommand) command));
		} else if (type.equals("EXPR")) {
			buf.put(generateSourceForExpressionCommand(indent, operation, command));
		} else if (type.equals("expression")) {
			buf.put(generateSourceForExpressionCommand(indent, operation, command));
		} else if (type.equals("DEFINITION")) {
			buf.put(generateSourceForExpressionCommand(indent, operation, command));
		} else if (type.equals("unnamed")) {
			buf.put(printStatementCommands(indent, operation, command.getCommands()));
			
		} else {
			String source = ariesModelUtil.getCommandSource(command);
			//System.out.println(">>>"+source);
			if (source == null)
				throw new RuntimeException("CODE PROBLEM");
			if (!source.endsWith(";"))
				source += ";";
			buf.putLine(indent, source);
			
		}
		return buf.get();
	}
	
	
	public String generateSourceForNewCommand(int indent, Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		buf.put(indent, "");
		List<Parameter> arguments = command.getParameters();
		Iterator<Parameter> iterator = arguments.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			if (i > 0 && !argument.equals("(") && !argument.equals(")") && !argument.equals(";"))
				buf.put(" ");
			buf.put(argument);
		}
		buf.putLine("");
		return buf.get();
	}

	public String generateSourceForCallCommand(int indent, Operation operation, Command command) throws Exception {
		String commandText = NameUtil.uncapName(command.getText());
		Interactor actor = (Interactor) command.getActor();
		//String target = actor.getTarget();
		//String role = actor.getRole();
		//if (target.equals("buyer"))
		//	System.out.println();
		
		//TODO check access here based on role/channel/role
		
		//initializeCommandArgumentsWithOperationParameters(operation, command);
		
		List<Parameter> parameters = operation.getParameters();
		List<Parameter> arguments = command.getParameters();
		Iterator<Parameter> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			Parameter parameter1 = iterator.next();
			String argument = parameter1.getName();
			argument = ExpressionUtil.extractFieldFromExpression(argument);
			
			//ExpressionGenerator expressionGenerator = new ExpressionGenerator();
			//String queryValue = expressionGenerator.generate(argument);
			
			Iterator<Parameter> parameterIterator = parameters.iterator();
			while (parameterIterator.hasNext()) {
				Parameter parameter = parameterIterator.next();
				Element element = context.getElementByType(parameter.getType());
				if (element != null) {
					String elementName = NameUtil.uncapName(element.getName());
					if (argument.equalsIgnoreCase(elementName)) {
						ModelVariable modelVariable = new ModelVariable();
						modelVariable.setName(elementName);
						modelVariable.setType(element.getType());
						modelVariable.setOwner(parameter.getName());
						String structure = element.getStructure();
						if (structure == null)
							structure = "item";
						modelVariable.setConstruct(structure);
						modelClass.putLocalVariable(elementName, modelVariable);
						break;
					}

					Field field = ElementUtil.getField(element, argument);
					//if (field == null)
					//	System.out.println();
					if (field != null) {
						ModelVariable modelVariable = new ModelVariable();
						modelVariable.setName(field.getName());
						modelVariable.setType(field.getType());
						modelVariable.setOwner(parameter.getName());
						String structure = field.getStructure();
						if (structure == null)
							structure = "item";
						modelVariable.setConstruct(structure);
						modelClass.putLocalVariable(field.getName(), modelVariable);
						break;
					}
				}
			}
		}
		
		String elementName = null;
		String methodName = null;
		String className = null;
		String invocationString = null;
		if (commandText.contains(".")) {
			int indexOf = commandText.indexOf(".");
			elementName = commandText.substring(0, indexOf);
			//Element element = context.getElementByName(elementName);
			//Assert.notNull(element, "Element not found: "+elementName);
			elementName = elementName + "Manager";
			className = NameUtil.capName(elementName);
			methodName = commandText.substring(indexOf+1);
			invocationString = elementName + "." + methodName;
			
		} else {
			methodName = "process" + NameUtil.capName(commandText);
			invocationString = methodName;
		}
		
		String argumentString = CodeUtil.getArgumentString(command);
		List<Result> results = command.getResults();

		Buf buf = new Buf();
		Result result = null;
		if (results.size() > 0)
			result = results.get(0);
		if (result != null) {
			String packageName = null;
			String resultType = result.getType();
			String typeName = getTypeFromXSDType(result.getType());
			Element element = context.getElementByType(typeName);
			if (element != null) {
				String elementType = element.getType();
				packageName = TypeUtil.getPackageName(elementType);
				className = TypeUtil.getClassName(elementType);
			} else {
				int indexOfColon = resultType.indexOf(":");
				String localPart = result.getType().substring(indexOfColon+1);
				element = context.getElementByName(localPart);
				String elementType = element.getType();
				packageName = TypeUtil.getPackageName(elementType);
				className = TypeUtil.getClassName(elementType);
			}

			modelClass.addImportedClass(packageName+"."+className);

			//TODO we may not ever neeed this - result should be assumed to already exist in the code
			ModelVariable currentLocalVariable = modelClass.getLocalVariable(result.getName());
			if (currentLocalVariable == null && false) {
				ModelVariable modelVariable = new ModelVariable();
				modelVariable.setName(result.getName());
				modelVariable.setType(getTypeFromXSDType(result.getType()));
				modelVariable.setOwner(getFieldOwnerFromParameters(parameters, result.getName()));
				modelVariable.setConstruct(result.getConstruct());
				modelClass.putLocalVariable(result.getName(), modelVariable);
			}
			
			String construct = result.getConstruct();
			if (construct != null) {
				if (construct.equalsIgnoreCase("list")) {
					className = "List<"+className+">";
					modelClass.addImportedClass("java.util.List");
					
				} else if (construct.equalsIgnoreCase("set")) {
					className = "Set<"+className+">";
					modelClass.addImportedClass("java.util.Set");
					
				} else if (construct.equalsIgnoreCase("map")) {
					int indexOfColon = result.getKey().indexOf(":");
					String resultKey = result.getKey().substring(indexOfColon+1);
					className = "Map<"+resultKey+", "+className+">";
					modelClass.addImportedClass("java.util.Map");
				}
			}
			
			if (currentLocalVariable != null)
				buf.putLine(indent, result.getName()+" = "+invocationString+"("+argumentString+");");
			else buf.putLine(indent, className+" "+result.getName()+" = "+commandText+"("+argumentString+");");
		} else {
			buf.putLine(indent, invocationString+"("+argumentString+");");
		}

		if (elementName != null) {
			Operation referencedOperation = createReferencedOperation(methodName, arguments, command.getResults());
			context.addReferencedMethod(elementName, referencedOperation);
		}

		if (!invocationString.contains(".")) {
			if (arguments.size() > 0) {
				ModelOperation calledOperation = createCalledOperation(command, arguments, 0);
				pushLocalOperationCache(calledOperation);
			}
			//operationStack.push(createCalledOperation(command, 0));
		}
		
		return buf.get();
	}

	public String generateSourceForDoneCommand(int indent, Operation operation, DoneCommand doneCommand) throws Exception {
		return generateSourceForDoneCommand(indent, operation, doneCommand, false);
	}
	
	public String generateSourceForDoneCommand(int indent, Operation operation, DoneCommand doneCommand, boolean insideRequestHandler) throws Exception {
		Buf buf = new Buf();
//		if (insideRequestHandler) {
//			String serviceId = NameUtil.capName(operation.getName());
//			buf.putLine(indent, "fire_"+serviceId+"_request_handled();");
//		}
		Application application = context.getApplication();
		List<String> serviceIds = doneCommand.getServiceIds();
		Iterator<String> iterator = serviceIds.iterator();
		while (iterator.hasNext()) {
			String serviceId = iterator.next();
			Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceId);
			Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceId);
			serviceId += (incomingCallback == null && outgoingCallback == null) ? "_request" : "_response";
			serviceId = NameUtil.capName(serviceId);
			if (insideRequestHandler && incomingCallback == null && incomingCallback == null)
				buf.putLine(indent, "fire_"+serviceId+"_handled();");
			buf.putLine(indent, "handle_"+serviceId+"_done();");
		}
		if (serviceIds.size() == 0) {
			String serviceId = NameUtil.capName(operation.getName());
			buf.putLine(indent, "handle_"+serviceId+"_request_done();");
		}
		//if (serviceIds.contains("purchaseBooks"))
		//	System.out.println();
		if (doneCommand.isNeedsReturn())
			buf.putLine(indent, "return;");
		return buf.get();
	}
	
	public String generateSourceForInvokeCommand(int indent, Operation operation, InvokeCommand invokeCommand) throws Exception {
		Interactor interactor = (Interactor) invokeCommand.getActor();
		//invoker.setRole("");
		//invoker.setService("");
		//invoker.setChannel(null);
		//invoker.setLink(null);
		//invoker.setTransacted(true);
		//invoker.setTimeout(10L);
		//invoker.setTimeToLive(1000L);

		//TODO check access here based on role/channel/role
		String serviceInterface = NameUtil.capName(interactor.getService());
		String serviceName = getExtendedDomainServiceName(interactor);
		
//		if (serviceName.contains("shipBooks"))
//			System.out.println();

		//List<Project> projects = context.getProjectList();
		//Service remoteService = ProjectUtil.getServiceByName(projects, serviceName);
		//String clientPackageName = ClientLayerHelper.getClientPackageName(remoteService);
		//String parameterString = CodeUtil.getArgumentString(invokeCommand);
		//String packageName = getPackageNameFromTargetName(invoker.getTarget());
		//modelClass.addImportedClass(clientPackageName+"."+serviceNameCapped);
		//modelClass.addImportedClasses(CodeUtil.getImportedClasses(command));
		String operationName = "send_"+serviceName+"_request";
		//addRequiredVariablesInScope(sendCommand);
		addDefinedLocalVariables(invokeCommand);
		
//		definedLocalVariables.size();
//		definedVariablesInScope.size();
//		requiredLocalVariables.size();
//		requiredVariablesInScope.size();
//		availableVariablesInScope.size();
		
		String parameterString = "";
		List<Parameter> parameters = invokeCommand.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			argument = NameUtil.uncapName(argument);
			addRequiredVariableInScope(argument);
			//if (!argument.endsWith("Message"))
			//	argument += "Message";
			if (i > 0)
				parameterString += ", ";
			parameterString += argument;
		}
		
		Buf buf = new Buf();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			String messageType = getMessageTypeForService(serviceInterface);
			Element messageElement = context.getElementByName(messageType);
			
			addRequiredLocalVariables(messageElement);
			buf.putLine(indent, "//TODO set timer for timeout");
			buf.putLine(indent, operationName + "("+parameterString+");");
			
			boolean hasParameters = !invokeCommand.getParameters().isEmpty();
			if (hasParameters)
				pushLocalOperationCache(createSendOperation(2, invokeCommand));
			pushLocalOperationCache(createSendOperation2(2, invokeCommand));
			pushLocalOperationCache(createSendOperation_cancel(2, invokeCommand));
			pushLocalOperationCache(createSendOperation_undo(2, invokeCommand));
			pushLocalOperationCache(createSendOperation4(2, invokeCommand));
			pushLocalOperationCache(createSendOperation5(2, invokeCommand));
			pushLocalOperationCache(createSendTimeoutOperation(2, invokeCommand));
			//pushLocalOperationCache(createOperations_HandleRequest_exception(2, operation, invokeCommand));
			//pushLocalOperationCache(createOperations_HandleRequest_timeout(2, operation, invokeCommand));
			addRequiredVariablesInScope(invokeCommand);
			break;
			
		case POJO:	
			buf.putLine(indent, "ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
			buf.putLine(indent, serviceInterface+"Client client = proxyLocator.get(\""+serviceInterface+"\");");
			buf.putLine(indent, "client.setCorrelationId(getCorrelationId());");
			buf.putLine(indent, "client."+serviceName+"("+parameterString+");");
			break;
		}

		return buf.get();
	}

	public ModelOperation createSendOperation(int indent, Command command) {
		Interactor interactor = (Interactor) command.getActor();
		String remoteServiceName = interactor.getService();
		String serviceName = getExtendedDomainServiceName(interactor);
		
		String operationName = "send_"+serviceName+"_request";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		
		List<Parameter> parameters = command.getParameters();
		List<String> arguments = new ArrayList<String>();
		
		Buf buf = new Buf();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String name = parameter.getName();
			String type = parameter.getType();

			Element elementByType = context.getElementByType(type);
			if (elementByType != null && name.endsWith("Message")) {
				String sourceCode = getSourceAndArgumentsFromMessageElement(arguments, elementByType);
				buf.put(sourceCode);

			} else {
				String sourceCode = getSourceForParameter(parameter);
				buf.put(sourceCode);
				arguments.add(name);
			}
		}

		String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, operationName+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}


	private String getSourceForParameter(Parameter parameter) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());

		String name = parameter.getName();
		String type = parameter.getType();
		String keyType = parameter.getKey();
		String construct = parameter.getConstruct();
		
		String beanName = name;
		String fieldName = NameUtil.capName(name);
		String typeName = TypeUtil.getClassName(type);
		
		boolean availableVariableInScope = isAvailableVariableInScope(typeName);
		boolean definedVariableInScope = isDefinedVariableInScope(typeName);
		boolean definedLocalVariable = isDefinedLocalVariable(typeName);
		
//		if (beanName.equalsIgnoreCase("order"))
//			System.out.println();
		
		Buf buf = new Buf();
		if (construct.equals("item")) {
			buf.putLine2(typeName+" "+beanName+" = "+processContextName+".get"+fieldName+"();");
			
		} else if (construct.equals("list")) {
			modelClass.addImportedClass("java.util.List");
			buf.putLine2("List<"+typeName+"> "+beanName+" = "+processContextName+".get"+fieldName+"();");
			
		} else if (construct.equals("set")) {
			modelClass.addImportedClass("java.util.Set");
			buf.putLine2("Set<"+typeName+"> "+beanName+" = "+processContextName+".get"+fieldName+"();");
			
		} else if (construct.equals("map")) {
			modelClass.addImportedClass("java.util.Map");
			Assert.notNull(keyType, "Key must exist: "+name);
			String keyName = TypeUtil.getClassName(keyType);
			buf.putLine2("Map<"+keyName+", "+typeName+"> "+beanName+" = "+processContextName+".get"+fieldName+"();");
		}
		
		return buf.get();
	}
	
	public ModelOperation createSendOperation2(int indent, SendCommand sendCommand) {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String remoteApplicationName = interactor.getName();
		String remoteServiceName = interactor.getService();
		String serviceName = getExtendedDomainServiceName(interactor);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("send_"+serviceName+"_request");
		modelOperation.setResultType("void");

//		if (serviceName.equalsIgnoreCase("shipBooks"))
//			System.out.println();
		
		List<Parameter> parameters = sendCommand.getParameters();
		//List<String> arguments = new ArrayList<String>();

		Buf buf = new Buf();
		boolean messageCreated = false;
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String elementName = parameter.getName();
			String elementType = parameter.getType();
			
			//Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);

			Element parameterElement = context.getElementByType(elementType);
			if (parameterElement != null && parameterElement .getName().endsWith("Message")) {
				String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, remoteServiceName, parameterElement, parameters);
				buf.put(sourceForRemoteServiceCall);
				//arguments.add(elementName);
				messageCreated = true;

			} else {
				String fieldName = elementName;
				//arguments.add(fieldName);
				ModelParameter modelParameter = CodeUtil.createParameter(parameter);
				modelOperation.addParameter(modelParameter);
			}
		}

		if (!messageCreated) {
			Service service = context.getServiceByName(remoteServiceName);
			Operation operation = ServiceUtil.getOperation(service, remoteServiceName);
			Assert.isTrue(operation.getParameters().size() == 1, "Assuming one parameter only for now");
			Element messageElement = context.getElementByType(operation.getParameters().get(0).getType());
			String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, remoteServiceName, messageElement, parameters);
			buf.put(sourceForRemoteServiceCall);
		}
		
		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, "send_"+serviceName+"_request(message);");
		//buf.putLine(indent, "sendRequest"+serviceNameCapped+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	public ModelOperation createSendOperation4(int indent, SendCommand sendCommand) {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String remoteApplicationName = NameUtil.capName(interactor.getName());
		String remoteServiceName = NameUtil.capName(interactor.getService());
		String remoteDomainServiceName = getExtendedDomainServiceName(interactor);

		List<Project> projects = context.getProjectList();
		Application remoteApplication = ProjectUtil.getApplicationByName(projects, remoteApplicationName);
		//Service remoteService = findServiceFromGroupOrApplication(remoteApplicationName, remoteServiceName);
		//Assert.notNull(remoteService, "Remote service not found: "+remoteApplicationName+"["+serviceName+"]");
		//modelClass.addImportedClass(remoteService.getPackageName()+"."+remoteService.getInterfaceName());
		remoteApplicationName = remoteApplication.getName(); 

		//String remoteServiceNameCapped = NameUtil.capName(remoteServiceName);
		Service remoteService = context.getServiceByName(remoteApplicationName, remoteServiceName);

		//String serviceName = invoker.getService();
		//String serviceInterface = NameUtil.capName(serviceName);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("send_"+remoteDomainServiceName+"_request");
		modelOperation.setResultType("void");

		//List<Parameter> parameters = sendCommand.getParameters();
		String messageName = sendCommand.getMessageName();
		if (messageName == null) {
			Operation operation = ServiceUtil.getOperation(remoteService, remoteServiceName);
			messageName = operation.getParameters().get(0).getName();
		}
		
		String messageBeanName = "message"; 
		//String messageBeanName = NameUtil.uncapName(messageName); 
		String messageClassName = NameUtil.capName(messageName);
		Element messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messagePackageName = TypeUtil.getPackageName(messageElement.getType());
		List<String> arguments = new ArrayList<String>();
		arguments.add("message");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(messageBeanName);
		modelParameter.setClassName(messageClassName);
		modelParameter.setPackageName(messagePackageName);
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(messagePackageName+"."+messageClassName);
		
//		Iterator<Parameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			String elementName = parameter.getName();
//			String elementType = parameter.getType();
//			String elementTypeLocalPart = TypeUtil.getLocalPart(elementType);
//			
//			String parameterName = elementName;
//			if (parameters.size() == 1 && elementType.endsWith("Message"))
//				parameterName = "message";
//			
//			Element elementByType = context.getElementByType(elementType);
//			if (elementByType != null) {
//				String className = TypeUtil.getClassName(elementType);
//				String packageName = TypeUtil.getPackageName(elementType);
//
//				ModelParameter modelParameter = new ModelParameter();
//				modelParameter.setName(parameterName);
//				modelParameter.setClassName(className);
//				modelParameter.setPackageName(packageName);
//				modelOperation.addParameter(modelParameter);
//				arguments.add(parameterName);
//	
//			} else if (CodeGenUtil.isJavaDefaultType(elementTypeLocalPart)) {
//				String className = TypeUtil.getClassName(elementType);
//				String packageName = TypeUtil.getPackageName(elementType);
//
//				ModelParameter modelParameter = new ModelParameter();
//				modelParameter.setName(elementName);
//				modelParameter.setClassName(className);
//				modelParameter.setPackageName(packageName);
//				modelOperation.addParameter(modelParameter);
//				//arguments.add(elementName);
//
//			} else {
//				System.out.println();
//			}
//		}

		List<Fault> faults = ServiceUtil.getFaults(remoteService);
		boolean hasApplicationFault = faults.size() > 0;
		
		Buf buf = new Buf();
		buf.putLine(indent, "Runnable timeoutHandler = create_"+remoteDomainServiceName+"_request_timeoutHandler();");
		//buf.putLine(indent, "TimeoutHandler timeoutHandler = startTimeoutHandler(create_"+serviceInterface+"_request_timeoutHandler(), "+serviceName+"Timeout);");
		buf.putLine(indent, "try {");
		
		indent++;
		buf.putLine(indent, "send_"+remoteDomainServiceName+"_request(message, timeoutHandler);");
			
		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		//buf.putLine(indent, serviceInterface+" client = getClient("+serviceInterface+".ID);");
		//buf.putLine(indent, "client."+serviceName+"("+argumentString+");");
		//buf.putLine(indent, "timeoutHandler.reset();");
		//buf.putLine(indent, "logState_"+serviceInterface+"(message);");
		//buf.putLine(indent, "fire_"+serviceInterface+"_request_sent();");
		
		indent--;
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) { 
			Fault fault = iterator.next();
			String exceptionPackageName = TypeUtil.getPackageName(fault.getType());
			String exceptionClassName = TypeUtil.getClassName(fault.getType());
			buf.putLine(indent, "} catch ("+exceptionClassName+" e) {");
			buf.putLine(indent, "	handle_"+remoteDomainServiceName+"_request_exception(e);");
			//buf.putLine(indent, "	fire_"+serviceInterface+"_outgoing_request_aborted(e);");
			modelClass.addImportedClass(exceptionPackageName+"."+exceptionClassName);
		}
		buf.putLine(indent, "} catch (Throwable e) {");
		//buf.putLine(indent, "	timeoutHandler.reset();");
		buf.putLine(indent, "	e = ExceptionUtil.getRootCause(e);");
		buf.putLine(indent, "	handle_"+remoteDomainServiceName+"_request_exception(e);");
		//buf.putLine(indent, "	fire_"+serviceInterface+"_outgoing_request_aborted(e);");
		buf.putLine(indent, "}");
		
		//Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);
		//Service remoteService = ProjectUtil.getServiceByName(context.getProjectList(), serviceName);
		String clientPackageName = ClientLayerHelper.getClientPackageName(remoteService);
		modelClass.addImportedClass(clientPackageName+"."+remoteService.getInterfaceName());
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createSendOperation5(int indent, SendCommand sendCommand) {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String remoteServiceName = NameUtil.capName(interactor.getService());
		String remoteServiceNameUncapped = NameUtil.uncapName(interactor.getService());
		String remoteDomainServiceName = getExtendedDomainServiceName(interactor);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("send_"+remoteDomainServiceName+"_request");
		modelOperation.setResultType("void");
		
		String remoteApplicationName = interactor.getName();
		Service remoteService = findServiceFromGroupOrApplication(remoteApplicationName, remoteServiceNameUncapped);
		Assert.notNull(remoteService, "Remote service not found: "+remoteApplicationName+"["+remoteServiceNameUncapped+"]");
		
		List<Fault> faults = ServiceUtil.getFaults(remoteService);
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) {
			Fault fault = iterator.next();
			String faultClassName = TypeUtil.getClassName(fault.getType());
			modelOperation.addException(faultClassName);
		}

		//List<Parameter> parameters = sendCommand.getParameters();
		String messageName = sendCommand.getMessageName();
		if (messageName == null) {
			Service service = context.getServiceByName(remoteServiceNameUncapped);
			Operation operation = ServiceUtil.getOperation(service, remoteServiceNameUncapped);
			messageName = operation.getParameters().get(0).getName();
		}
		
		String messageBeanName = "message"; 
		//String messageBeanName = NameUtil.uncapName(messageName); 
		String messageClassName = NameUtil.capName(messageName);
		Element messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messagePackageName = TypeUtil.getPackageName(messageElement.getType());
		List<String> arguments = new ArrayList<String>();
		arguments.add("message");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(messageBeanName);
		modelParameter.setClassName(messageClassName);
		modelParameter.setPackageName(messagePackageName);
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(messagePackageName+"."+messageClassName);

		modelParameter = new ModelParameter();
		modelParameter.setName("timeoutHandler");
		modelParameter.setClassName("Runnable");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);

		Buf buf = new Buf();
		buf.putLine(indent, "TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, "+remoteServiceNameUncapped+"Timeout);");
		//buf.putLine(indent, "TimeoutHandler timeoutHandler = startTimeoutHandler(create_"+serviceInterface+"_request_timeoutHandler(), "+serviceName+"Timeout);");
		buf.putLine(indent, "try {");
		
		indent++;
		String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, remoteServiceName+" client = getClient("+remoteServiceName+".ID);");
		buf.putLine(indent, "client."+remoteServiceNameUncapped+"("+argumentString+");");
		buf.putLine(indent, "logState_"+remoteDomainServiceName+"(message);");
		buf.putLine(indent, "fire_"+remoteServiceName+"_request_sent();");
		
		indent--;
		buf.putLine(indent, "} finally {");
		buf.putLine(indent, "	timeoutHelper.reset();");
		buf.putLine(indent, "}");

		modelOperation.addInitialSource(buf.get());
		
		//Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);
		//Service remoteService = ProjectUtil.getServiceByName(context.getProjectList(), serviceName);
		String clientPackageName = ClientLayerHelper.getClientPackageName(remoteService);
		modelClass.addImportedClass(clientPackageName+"."+remoteService.getInterfaceName());
		return modelOperation;
	}

	public ModelOperation createSendTimeoutOperation(int indent, SendCommand sendCommand) {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String remoteApplicationName = interactor.getName();
		String remoteServiceName = interactor.getService();
		String remoteServiceNameCapped = NameUtil.capName(remoteServiceName);
		String serviceName = getExtendedDomainServiceName(interactor);
		
		List<Project> projects = context.getProjectList();
		Application remoteApplication = ProjectUtil.getApplicationByName(projects, remoteApplicationName);
		remoteApplicationName = remoteApplication.getName(); 

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+serviceName+"_request_timeoutHandler");
		modelOperation.setResultType("Runnable");
		
		Buf buf = new Buf();
		buf.putLine(indent, "return new Runnable() {");
		buf.putLine(indent, "	public void run() {");
		buf.putLine(indent, "		"+remoteServiceName+"Timeout = DEFAULT_TIMEOUT;");
		buf.putLine(indent, "		String reason = \""+remoteServiceNameCapped+" timed-out\";");
		buf.putLine(indent, "		handle_"+serviceName+"_request_timeout(reason);");
		buf.putLine(indent, "	}");
		buf.putLine(indent, "};");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public ModelOperation createSendOperation_cancel(int indent, SendCommand sendCommand) {
		return createSendOperation3(indent, sendCommand, "cancel");
	}
	
	public ModelOperation createSendOperation_undo(int indent, SendCommand sendCommand) {
		return createSendOperation3(indent, sendCommand, "undo");
	}
	
	public ModelOperation createSendOperation3(int indent, SendCommand sendCommand, String operationType) {
		Interactor interactor = (Interactor) sendCommand.getActor();
		String remoteApplicationName = interactor.getName();
		String remoteServiceName = interactor.getService();
		String serviceName = getExtendedDomainServiceName(interactor);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("send_"+serviceName+"_request_"+operationType);
		modelOperation.setResultType("void");

//		if (serviceName.equalsIgnoreCase("shipBooks"))
//			System.out.println();
		
		List<Parameter> parameters = sendCommand.getParameters();
		//List<String> arguments = new ArrayList<String>();
		
		Buf buf = new Buf();
//		if (operationType.equals("undo")) {
//			Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);
//			Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
//			String messageType = ServiceLayerHelper.getMessageType(serviceOperation);
//			String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
//			//String messageBeanName = ServiceLayerHelper.getMessageBeanName(serviceOperation);
//			Element messageElement = context.getElementByType(messageType);
//			
//			List<Reference> references = ElementUtil.getReferences(messageElement);
//			Iterator<Reference> iterator = references.iterator();
//			for (int i=0; iterator.hasNext(); i++) {
//				Reference reference = (Reference) iterator.next();
//				String structuredType = TypeUtil.getStructuredType(reference);
//				String structuredName = TypeUtil.getStructuredName(reference);
//			}
//		}
		
		boolean messageCreated = false;
//		Iterator<Parameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			String elementName = parameter.getName();
//			String elementType = parameter.getType();
//			
//			//Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);
//
//			Element parameterElement = context.getElementByType(elementType);
//			if (parameterElement != null && parameterElement .getName().endsWith("Message")) {
//				String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, serviceName, parameterElement, null);
//				buf.put(sourceForRemoteServiceCall);
//				//arguments.add(elementName);
//				messageCreated = true;
//
//			} else {
//				String fieldName = elementName;
//				//arguments.add(fieldName);
//				ModelParameter modelParameter = CodeUtil.createParameter(parameter);
//				modelOperation.addParameter(modelParameter);
//			}
//		}

		Service service = context.getServiceByName(remoteServiceName);
		Operation operation = ServiceUtil.getOperation(service, remoteServiceName);
		Assert.isTrue(operation.getParameters().size() == 1, "Assuming one parameter only for now");
		Element messageElement = context.getElementByType(operation.getParameters().get(0).getType());
		
		Service remoteService = context.getServiceByName(remoteApplicationName, remoteServiceName);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(remoteService);
		String messageType = ServiceLayerHelper.getMessageType(serviceOperation);
		String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
		//String messageBeanName = ServiceLayerHelper.getMessageBeanName(serviceOperation);
		String messageBeanName = "message";
		
		buf.putLine(indent, messageClassName+" "+messageBeanName+" = create"+messageClassName+"();");
		
		if (operationType.equals("undo")) {
			List<Reference> references = ElementUtil.getReferences(messageElement);
			Iterator<Reference> iterator2 = references.iterator();
			for (int i=0; iterator2.hasNext(); i++) {
				Reference reference = iterator2.next();
				ModelParameter modelParameter = createParameter(reference.getType(), reference.getName(), reference.getStructure());
				modelOperation.addParameter(modelParameter);
				modelClass.addImportedClass(modelParameter);
			}

			Iterator<Reference> iterator = references.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Reference reference = (Reference) iterator.next();
				String fieldName = reference.getName();
				String fieldNameCapped = NameUtil.capName(fieldName);
				//String structuredType = TypeUtil.getStructuredType(reference);
				//String structuredName = TypeUtil.getStructuredName(reference);
				buf.putLine2(messageBeanName+".set"+fieldNameCapped+"("+fieldName+");");
			}
		}
		
		//String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, serviceName, messageElement, parameters);
		//buf.put(sourceForRemoteServiceCall);
		
		if (operationType.equals("cancel"))
			buf.putLine2("message.setCancelRequest(true);");
		if (operationType.equals("undo"))
			buf.putLine2("message.setUndoRequest(true);");

		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, "send_"+serviceName+"_request(message);");
		//buf.putLine(indent, "sendRequest"+serviceNameCapped+"("+argumentString+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
//	public ModelOperation createSendOperation_cancel(int indent, SendCommand sendCommand) {
//		Interactor invoker = (Interactor) sendCommand.getActor();
//		String remoteApplicationName = invoker.getName();
//		
//		String serviceName = invoker.getService();
//		String serviceNameCapped = NameUtil.capName(serviceName);
//		
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setModifiers(Modifier.PROTECTED);
//		modelOperation.setName("send_"+serviceNameCapped+"_request_cancel");
//		modelOperation.setResultType("void");
//
////		if (serviceName.equalsIgnoreCase("shipBooks"))
////			System.out.println();
//		
//		List<Parameter> parameters = sendCommand.getParameters();
//		//List<String> arguments = new ArrayList<String>();
//
//		Buf buf = new Buf();
//		boolean messageCreated = false;
//		Iterator<Parameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			String elementName = parameter.getName();
//			String elementType = parameter.getType();
//			
//			//Service remoteService = context.getServiceByName(remoteApplicationName, serviceName);
//
//			Element parameterElement = context.getElementByType(elementType);
//			if (parameterElement != null && parameterElement .getName().endsWith("Message")) {
//				String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, serviceName, parameterElement);
//				buf.put(sourceForRemoteServiceCall);
//				//arguments.add(elementName);
//				messageCreated = true;
//
//			} else {
//				String fieldName = elementName;
//				//arguments.add(fieldName);
//				ModelParameter modelParameter = CodeUtil.createParameter(parameter);
//				modelOperation.addParameter(modelParameter);
//			}
//		}
//
////		if (!messageCreated) {
////			Service service = context.getServiceByName(serviceName);
////			Operation operation = ServiceUtil.getOperation(service, serviceName);
////			Assert.isTrue(operation.getParameters().size() == 1, "Assuming one parameter only for now");
////			Element messageElement = context.getElementByType(operation.getParameters().get(0).getType());
////			String sourceForRemoteServiceCall = getSourceForRemoteServiceCall(indent, remoteApplicationName, serviceName, messageElement);
////			buf.put(sourceForRemoteServiceCall);
////		}
//		
//		buf.putLine("message.setCancelRequest(true);");
//		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
//		buf.putLine(indent, "send_"+serviceNameCapped+"_request(message);");
//		//buf.putLine(indent, "sendRequest"+serviceNameCapped+"("+argumentString+");");
//		modelOperation.addInitialSource(buf.get());
//		return modelOperation;
//	}

	public ModelOperation createOperation_ServiceHandler(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(service.getProcess());
		Application application = context.getApplication();
		clearRequiredVariablesInScope();

		String operationName = operation.getName();
		String operationRole = operation.getRole();
		String serviceId = operation.getName();
		String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseNameCapped = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseNameCapped;
		else operationName = NameUtil.uncapName(operation.getName());

		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		boolean isResponse = incomingCallback != null || outgoingCallback != null;
		operationName += !isResponse ? "_request" : "_response";
		if (incomingCallback != null || outgoingCallback != null)
			resetCallbackState();

		if (service != null && service instanceof Callback == false) {
			resetServiceState();
		}
		
		//System.out.println(">>>>"+operation.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = createParameter(parameter);
			if (parameters.size() == 1)
				modelParameter.setName("message");
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
			Element parameterElement = context.getElementByType(parameter.getType());
			addAvailableVariableInScope(parameter.getName(), parameterElement);
		}
		
//		if (modelClass.getClassName().equals("BuyerProcess"))
//			System.out.println();
//		if (operation.getName().equals("shipBooks"))
//			System.out.println();
//		if (operation.getName().equals("orderAccepted"))
//			System.out.println();

		Buf initializationSourceBuf = new Buf();
		Buf localVariablesSourceBuf = new Buf();
		Buf operationBodySourceBuf = new Buf();
		
		if (parameters.size() > 1) {
			String argumentString = ParameterUtil.getArgumentString(operation);
			initializationSourceBuf.putLine2(processContextName+".initializeContext("+argumentString+");");
		} else initializationSourceBuf.putLine2(processContextName+".initializeContext(message);"); 
		//initializationSourceBuf.putLine2("initializeContext(\""+operationBaseNameCapped+"\", "+argumentString+");");

		/*
		iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();

			Element parameterElement = context.getElementByType(parameterType);
			if (parameterElement != null && parameterName.endsWith("Message")) {
				List<Field> fieldNames = ElementUtil.getApplicationDefinedFields(parameterElement);
				Iterator<Field> fieldIterator = fieldNames.iterator();
				while (fieldIterator.hasNext()) {
					Field field = fieldIterator.next();
					String beanName = field.getName();
					String fieldType = field.getType();
					String fieldName = NameUtil.capName(beanName);
					String typeName = TypeUtil.getClassName(fieldType);
					String packageName = TypeUtil.getPackageName(fieldType);
					String typeWithStructure = getClassNameWithStructure(field);
					initializationSourceBuf.putLine2(typeWithStructure+" "+beanName+" = "+parameterName+".get"+fieldName+"();");
					modelClass.addImportedClass(packageName+"."+typeName);
				}
			}
		}
		*/
		
		iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterType = parameter.getType();

			Element elementByType = context.getElementByType(parameterType);
			if (elementByType != null && parameterName.endsWith("Message")) {
				List<String> fieldNames = ElementUtil.getApplicationDefinedFieldNames(elementByType);
				Iterator<String> fieldIterator = fieldNames.iterator();
				while (fieldIterator.hasNext()) {
					String beanName = fieldIterator.next();
					String fieldName = NameUtil.capName(beanName);
					//String typeName = TypeUtil.getClassName(parameterType);
					//initializationSourceBuf.putLine2(processContextName+".set"+fieldName+"("+beanName+");");
					//initializationSourceBuf.putLine2(processContextName+".set"+fieldName+"("+beanName+");");
				}
				
			} else if (elementByType != null && parameterName.endsWith("Event")) {

			} else {
				String fieldName = NameUtil.capName(parameterName);
				//String typeName = TypeUtil.getClassName(parameterType);
				initializationSourceBuf.putLine2(processContextName+".set"+fieldName+"("+parameterName+");");
			}
		}
		
		if (parameters.size() == 1) {
			messageType = parameters.get(0).getType();
			messageElement = context.getElementByType(messageType);
			context.setMessageType(messageType);
			context.setMessagElement(messageElement);
		}
		
		//Set<String> requiredVariablesInScope = getRequiredVariablesInScope();
		
		List<Command> commands = operation.getCommands();
		operationBodySourceBuf.put(generateSourceForCommands(operation, commands));
		List<String> arguments = CodeUtil.getArgumentList(modelOperation);
		localVariablesSourceBuf.put(generateSourceForVariables(arguments));
		
		//add local variables
		Map<String, ModelVariable> localVariables = modelClass.getLocalVariables();
		Iterator<ModelVariable> variableIterator = localVariables.values().iterator();
		while (variableIterator.hasNext()) {
			ModelVariable modelVariable = variableIterator.next();
			String construct = modelVariable.getConstruct();
			if (construct.equals("list")) {
				String fieldName = modelVariable.getName();
				String fieldNameCapped = NameUtil.capName(fieldName);
				String packageName = TypeUtil.getPackageName(modelVariable.getType());
				String className = TypeUtil.getClassName(modelVariable.getType());
				localVariablesSourceBuf.putLine2("List<"+className+"> "+fieldName+" = "+modelVariable.getOwner()+".get"+fieldNameCapped+"();");
				modelClass.addImportedClass(packageName+"."+className);
				modelClass.addImportedClass("java.util.List");
			}
		}
		
		//buf.putLine(2, "updateState();");
		//String serviceInterfaceName = operationBaseNameCapped;
		//String processContextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
		//buf2.putLine2("fire"+serviceInterfaceName+"Complete("+processContextInstanceName+");");
		
//		//add event source
//		if (serviceInterface != null)
//			fireEventSourceBuf.putLine2("fire"+serviceInterface+"Done();");
		
		if (isResponse)
			operationBodySourceBuf.putLine2("handle_"+serviceInterface+"_response_done();");
		//else operationBodySourceBuf.putLine2("fire_"+serviceInterface+"_request_handled();");
		//operationBodySourceBuf.put(generateSourceForDoneCommand(operation, commands));
			
		int size = commands.size();
		if (size > 0) {
			Command lastCommand = commands.get(size-1);
			if (lastCommand instanceof DoneCommand) {
				DoneCommand doneCommand = (DoneCommand) lastCommand;
				String sourceCode = generateSourceForDoneCommand(2, operation, doneCommand, !isResponse);
				operationBodySourceBuf.put(sourceCode);
			} else {
				if (!isResponse)
					operationBodySourceBuf.putLine2("fire_"+serviceInterface+"_request_handled();");
			}
		}
		
		//assemble the source
		Buf buf = new Buf();
		buf.put(initializationSourceBuf.get());
		buf.put(localVariablesSourceBuf.get());
		buf.put(operationBodySourceBuf.get());
		//buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createOperation_ServiceHandler_exception(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Application application = context.getApplication();

		String operationName = operation.getName();
		String operationRole = operation.getRole();
		//String serviceId = operation.getName();
		//String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseNameCapped = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseNameCapped;
		else operationName = NameUtil.uncapName(operation.getName());

		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterfaceName);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterfaceName);
		operationName += (incomingCallback == null && outgoingCallback == null) ? "_request" : "_response";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName+"_exception");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		ModelParameter modelParameter = createParameter("Throwable", "cause");
		modelOperation.addParameter(modelParameter);

		Buf buf = new Buf();
		buf.putLine2("//TODO");
		buf.putLine2("fire_"+serviceInterfaceName+"_error_sent();");
		buf.putLine2("fire_"+serviceInterfaceName+"_incoming_request_aborted(cause);");
		//buf.putLine2("handle_"+serviceInterfaceName+"_request_done();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createOperation_ServiceHandler_timeout(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Application application = context.getApplication();

		String operationName = operation.getName();
		String operationRole = operation.getRole();
		//String serviceId = operation.getName();
		//String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseNameCapped = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseNameCapped;
		else operationName = NameUtil.uncapName(operation.getName());

		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterfaceName);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterfaceName);
		operationName += (incomingCallback == null && outgoingCallback == null) ? "_request" : "_response";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(operationName+"_timeout");
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		ModelParameter modelParameter = createParameter("String", "reason");
		modelOperation.addParameter(modelParameter);

		Buf buf = new Buf();
		buf.putLine2("//TODO");
		buf.putLine2("fire_"+serviceInterfaceName+"_error_sent();");
		buf.putLine2("fire_"+serviceInterfaceName+"_incoming_request_aborted(reason);");
		//buf.putLine2("handle_"+serviceInterfaceName+"_request_done();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createOperation_ServiceHandler_cancel(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(service.getProcess());
		Application application = context.getApplication();

		String operationName = operation.getName();
		String operationRole = operation.getRole();
		String serviceId = operation.getName();
		String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseName = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseName;
		else operationName = NameUtil.uncapName(operation.getName());
		
		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		//Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		boolean isCallback = incomingCallback != null || outgoingCallback != null;
		operationName += isCallback ? "_response_cancel" : "_request_cancel";
		//operationName += callback == null ? "Request_undo" : "Response_undo";
		operationName += "";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		modelOperation.addJavadoc("stop anything currently running from making further progress");
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		//System.out.println(">>>>"+operation.getName());
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = createParameter(parameter);
			if (parameters.size() == 1)
				modelParameter.setName("message");
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
		}

		Buf buf = new Buf();
		buf.putLine2("String correlationId = message.getCorrelationId();");
		
		if (parameters.size() > 1) {
			String argumentString = ParameterUtil.getArgumentString(operation);
			buf.putLine2(processContextName+".initializeContext("+argumentString+");");
		} else buf.putLine2(processContextName+".initializeContext(message);"); 
		//initializationSourceBuf.putLine2("initializeContext(\""+operationBaseNameCapped+"\", "+argumentString+");");

		Set<String> events = OperationUtil.getIncomingEvents(operation);
		Iterator<String> iterator2 = events.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			eventName = NameUtil.uncapName(eventName);
			buf.putLine2(eventName+"Executor.cancel();");
		}
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			String remoteServiceInterfaceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
			buf.putLine2("cancel_"+remoteServiceInterfaceName+"(correlationId);");
		}

		if (isCallback)
			buf.putLine2("handle_"+serviceInterface+"_response_undo(message);");
		else buf.putLine2("handle_"+serviceInterface+"_request_undo(message);");
		buf.putLine2("fire_"+serviceInterface+"_request_cancel_done();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createOperation_ServiceHandler_undo(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(service.getProcess());
		Application application = context.getApplication();

		String operationName = operation.getName();
		String operationRole = operation.getRole();
		String serviceId = operation.getName();
		String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseName = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseName;
		else operationName = NameUtil.uncapName(operation.getName());
		
		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		//Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		operationName += (incomingCallback == null && outgoingCallback == null) ? "_request_undo" : "_response_undo";
		//operationName += callback == null ? "Request_undo" : "Response_undo";
		operationName += "";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		modelOperation.addJavadoc("unwind any existing actions associated with this correlationId");

		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		//System.out.println(">>>>"+operation.getName());
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			ModelParameter modelParameter = createParameter(parameter);
			if (parameters.size() == 1)
				modelParameter.setName("message");
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
		}

		Buf buf = new Buf();
		buf.putLine2("String correlationId = message.getCorrelationId();");
		
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator3 = remoteServices.iterator();
		while (iterator3.hasNext()) {
			Service remoteService = iterator3.next();
			String serviceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
			buf.putLine2("undo_"+serviceName+"(correlationId);");
		}
		
		buf.put(createSource_UndoCommands(modelClass, service, operation));
		
		Set<ModelOperation> undoOperations = createOperation_UndoCommands(modelClass, service, operation);
		loggedStateUndoOperations.addAll(undoOperations);
		
		if (incomingCallback == null && outgoingCallback == null)
			buf.putLine2("fire_"+operationBaseName+"_request_undo_done();");
		else buf.putLine2("fire_"+operationBaseName+"_response_undo_done();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	public ModelOperation createOperation_ServiceHandler_Done(ModelClass modelClass, Operation operation) throws Exception {
		Application application = context.getApplication();
		
		String operationName = operation.getName();
		String operationRole = operation.getRole();
		String serviceId = operation.getName();
		String serviceInterface = NameUtil.capName(serviceId);
		String operationBaseName = NameUtil.capName(operationName);
		if (operationRole != null)
			operationName = operationRole + "_" + operationBaseName;
		else operationName = NameUtil.uncapName(operation.getName());
		operationName += "";
		
		Callback incomingCallback = ApplicationUtil.getIncomingCallbackByName(application, serviceInterface);
		Callback outgoingCallback = ApplicationUtil.getOutgoingCallbackByName(application, serviceInterface);
		operationName += (incomingCallback == null && outgoingCallback == null) ? "_request" : "_response";
		operationName += "_done";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		Set<String> events = OperationUtil.getIncomingEvents(operation);
		Iterator<String> iterator2 = events.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			eventName = NameUtil.uncapName(eventName);
			buf.putLine2(eventName+"Executor.close();");
		}
		
		if (incomingCallback == null && outgoingCallback == null)
			buf.putLine2("fire_"+operationBaseName+"_request_done();");
		else buf.putLine2("fire_"+operationBaseName+"_response_done();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}


	
	
	
	
	//this is for the response handling blocks for an outgoing request
	public List<ModelOperation> createOperations_HandleRequest_responses(int indent, Operation operation, InvokeCommand invokeCommand) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<ResponseBlock> responseBlocks = invokeCommand.getResponseBlocks();
		Iterator<ResponseBlock> iterator = responseBlocks.iterator();
		//this.scope = "exception";
		while (iterator.hasNext()) {
			ResponseBlock responseBlock = iterator.next();
			ModelOperation modelOperation = createOperation_HandleRequest_response(indent, operation, invokeCommand, responseBlock);
			modelOperations.add(modelOperation);
		}
		//scope = "default";
		return modelOperations;
	}
	
	//this is for one response handling block of an outgoing request
	public ModelOperation createOperation_HandleRequest_response(int indent, Operation operation, Command commmand, ResponseBlock responseBlock) throws Exception {
		Interactor invoker = (Interactor) commmand.getActor();
		//String remoteApplicationName = invoker.getName();

		String serviceName = invoker.getService();
		String serviceNameCapped = NameUtil.capName(serviceName);

		String callbackName = responseBlock.getName();
		String callbackNameCapped = NameUtil.capName(callbackName);

		Service localService = context.getService();
		String localServiceName = ServiceLayerHelper.getServiceInterfaceName(localService);

		Service service = ProjectUtil.getServiceByName(context.getProjectList(), serviceNameCapped);
		Callback callback = ServiceUtil.getOutgoingCallbackByName(service, callbackNameCapped);
		
		Operation callbackOperation = ServiceUtil.getDefaultOperation(callback);
		ModelOperation modelOperation = createOperation_ServiceHandler(modelClass, callback, callbackOperation);
		return modelOperation;
	}
	
	//this is for the exception handling blocks for an outgoing request
	public List<ModelOperation> createOperations_HandleRequest_exceptions(int indent, Operation operation, InvokeCommand invokeCommand) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<ExceptionBlock> exceptionBlocks = invokeCommand.getExceptionBlocks();
		Iterator<ExceptionBlock> iterator = exceptionBlocks.iterator();
		//this.scope = "exception";
		while (iterator.hasNext()) {
			ExceptionBlock exceptionBlock = iterator.next();
			List<Command> commands = exceptionBlock.getCommands();
			Interactor interactor = (Interactor) invokeCommand.getActor();
			ModelOperation modelOperation = createOperation_HandleRequest_exception(indent, operation, interactor, commands);
			modelOperations.add(modelOperation);
		}
		//scope = "default";
		return modelOperations;
	}
	
	//this is for one exception handling block of an outgoing request
	public ModelOperation createOperation_HandleRequest_exception(int indent, Operation operation, Interactor interactor, List<Command> commands) throws Exception {
		//String remoteApplicationName = interactor.getName();		
		//String remoteServiceName = interactor.getService();

		String serviceName = NameUtil.capName(interactor.getService());
		String domainServiceName = getExtendedDomainServiceName(interactor);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("handle_"+domainServiceName+"_request_exception");
		modelOperation.setResultType("void");
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);

//		if (serviceName.equals("Shipper_ShipBooks"))
//			System.out.println();
		
		Buf buf = new Buf();
		String sourceForCommands = generateSourceForCommands(operation, commands);
		List<String> arguments = CodeUtil.getArgumentList(modelOperation);
		
		requiredVariablesInScope.clear();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			addRequiredVariablesInScope(command);
		}
		
		String sourceForVariables = generateSourceForVariables(arguments);
		buf.put(sourceForVariables);
		buf.put(sourceForCommands);
		
		buf.putLine2("fire_"+serviceName+"_outgoing_request_aborted(cause);");
		//buf.putLine2("fire_"+serviceName+"_error_sent();");
		//buf.putLine2("handle_"+localServiceName+"_request_done();");
		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	//this is for the timeout handling blocks for an outgoing request
	public List<ModelOperation> createOperations_HandleRequest_timeouts(int indent, Operation operation, InvokeCommand invokeCommand) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<TimeoutBlock> timeoutBlocks = invokeCommand.getTimeoutBlocks();
		Iterator<TimeoutBlock> iterator = timeoutBlocks.iterator();
		//this.scope = "timeout";
		while (iterator.hasNext()) {
			TimeoutBlock timeoutBlock = iterator.next();
			List<Command> commands = timeoutBlock.getCommands();
			Interactor interactor = (Interactor) invokeCommand.getActor();
			ModelOperation modelOperation = createOperation_HandleRequest_timeout(indent, operation, interactor, commands);
			modelOperations.add(modelOperation);
		}
		//scope = "default";
		return modelOperations;
	}
	
	//this is for one timeout handling block of an outgoing request
	public ModelOperation createOperation_HandleRequest_timeout(int indent, Operation operation, Interactor interactor, List<Command> commands) throws Exception {
		
		String serviceName = NameUtil.capName(interactor.getService());
		String domainServiceName = getExtendedDomainServiceName(interactor);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("handle_"+domainServiceName+"_request_timeout");
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("reason");
		modelParameter.setClassName("String");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		requiredVariablesInScope.clear();
		String sourceForCommands = generateSourceForCommands(operation, commands);
		List<String> arguments = CodeUtil.getArgumentList(modelOperation);
		String sourceForVariables = generateSourceForVariables(arguments);
		buf.put(sourceForVariables);
		buf.put(sourceForCommands);
		
		buf.putLine2("fire_"+serviceName+"_outgoing_request_aborted(reason);");
		//buf.putLine2("fire_"+serviceNameCapped+"_error_sent();");
		//buf.putLine2("handle_"+localServiceName+"_request_done();");
		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public String generateSourceForExecuteCommand(int indent, Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		return buf.get();
	}

	public String generateSourceForListenCommand(int indent, Operation operation, ListenCommand listenCommand) throws Exception {
		Interactor actor = (Interactor) listenCommand.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String eventName = listenCommand.getMessageName();
		String eventNameCapped = NameUtil.capName(eventName);
		String eventNameUncapped = NameUtil.uncapName(eventName);
		String responseType = eventNameCapped;
		if (!responseType.endsWith("Message"))
			responseType += "Message";
		String responseName = NameUtil.uncapName(responseType);
		//String operationName = "waitFor" + commandTextCapped;
		//String argumentString = CodeUtil.getArgumentString(listenCommand);
		String eventNameBase = NameUtil.uncapName(eventName);
		if (eventNameBase.endsWith("Event"))
			eventNameBase = eventName.substring(eventName.length() - 5);
		addDefinedLocalVariables(listenCommand);
		
		Buf buf = new Buf();
		//buf.putLine(indent, operationName+"("+argumentString+");");
		buf.putLine(indent, eventNameBase+"Executor.register();");

		//listenCommandCount++;
		Element responseElement = context.getElementByName(responseType);
		modelClass.addImportedClass(responseElement);
		pushLocalOperationCache(createOperation_EventHandler(2, operation, listenCommand));
		pushLocalOperationCache(createOperation_EventHandler_Cancel(2, operation, listenCommand));
		addOperations_EventHandler_Timeout(2, operation, listenCommand);
		addOperations_EventHandler_Exception(2, operation, listenCommand);

//		if (operationName.equals("replyBooksAvailable"))
//			System.out.println();
		
		return buf.get();
	}

	public ModelOperation createOperation_EventHandler(int indent, Operation operation, ListenCommand listenCommand) throws Exception {
		Interactor interactor = (Interactor) listenCommand.getActor();
		String serviceName = interactor.getService();
		String eventName = listenCommand.getMessageName();
		String eventNameBase = NameUtil.capName(eventName);
		if (eventNameBase.endsWith("Event"))
			eventNameBase = eventNameBase.substring(eventName.length() - 5);
		String operationName = "handle_" + eventNameBase + "_event";
		if (!eventName.endsWith("Event"))
			eventName += "Event";

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.setIndex(listenCommandCount);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		
		String eventBeanName = NameUtil.uncapName(eventName); 
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);
		String eventPackageName = TypeUtil.getPackageName(eventElement.getType());
		String eventClassName = TypeUtil.getClassName(eventElement.getType());
		modelClass.addImportedClass(eventElement);
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("event");
		modelParameter.setClassName(eventClassName);
		modelParameter.setPackageName(eventPackageName);
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		List<Command> commands = listenCommand.getCommands();
		String sourceCode = generateSourceForCommands(operation, commands);
		buf.put(sourceCode);

		buf.putLine(indent, "fire_"+eventNameBase+"_process_complete();");
		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createOperation_EventHandler_Cancel(int indent, Operation operation, ListenCommand listenCommand) throws Exception {
		Interactor interactor = (Interactor) listenCommand.getActor();
		String serviceName = interactor.getService();
		String eventName = listenCommand.getMessageName();
		String eventNameBase = NameUtil.capName(eventName);
		if (eventNameBase.endsWith("Event"))
			eventNameBase = eventNameBase.substring(eventName.length() - 5);
		String operationName = "handle_" + eventNameBase + "_event_cancel";
		if (!eventName.endsWith("Event"))
			eventName += "Event";

		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.setIndex(listenCommandCount);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("reason");
		modelParameter.setClassName("String");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);
		
		String eventBeanName = NameUtil.uncapName(eventName); 
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);
		List<Command> commands = listenCommand.getCommands();

		Buf buf = new Buf();
		//String sourceCode = generateSourceForCommands(operation, commands);
		//buf.put(sourceCode);

		buf.putLine(indent, "fire_"+eventNameBase+"_process_aborted(reason);");
		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public void addOperations_EventHandler_Timeout(int indent, Operation operation, ListenCommand listenCommand) throws Exception {
		List<TimeoutBlock> timeoutBlocks = listenCommand.getTimeoutBlocks();
		Iterator<TimeoutBlock> iterator = timeoutBlocks.iterator();
		for (int index=0; iterator.hasNext(); index++) {
			TimeoutBlock timeoutBlock = iterator.next();
			ModelOperation modelOperation = createOperation_EventHandler_Timeout(indent, index, operation, listenCommand, timeoutBlock);
			pushLocalOperationCache(modelOperation);
		}
	}

	public ModelOperation createOperation_EventHandler_Timeout(int indent, int index, Operation operation, ListenCommand listenCommand, TimeoutBlock timeoutBlock) throws Exception {
		Interactor interactor = (Interactor) listenCommand.getActor();
		String serviceName = interactor.getService();
		String eventName = listenCommand.getMessageName();
		String eventNameBase = NameUtil.capName(eventName);
		if (eventNameBase.endsWith("Event"))
			eventNameBase = eventNameBase.substring(eventName.length() - 5);
		String operationName = "handle_" + eventNameBase + "_event_timeout";
		if (listenCommand.getTimeoutBlocks().size() > 1)
			operationName += index;
		if (!eventName.endsWith("Event"))
			eventName += "Event";
		//scope = "timeout";
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.setIndex(listenCommandCount);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("reason");
		modelParameter.setClassName("String");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);

		String eventBeanName = NameUtil.uncapName(eventName); 
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);

		Buf buf = new Buf();
		buf.putLine(indent, "fire_"+eventNameBase+"_process_aborted(reason);");

		List<Command> commands = timeoutBlock.getCommands();
		String sourceCode = generateSourceForCommands(operation, commands);
		buf.put(sourceCode);

		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		//scope = "default";
		return modelOperation;
	}
	
	public void addOperations_EventHandler_Exception(int indent, Operation operation, ListenCommand listenCommand) throws Exception {
		List<ExceptionBlock> exceptionBlocks = listenCommand.getExceptionBlocks();
		Iterator<ExceptionBlock> iterator = exceptionBlocks.iterator();
		for (int index=0; iterator.hasNext(); index++) {
			ExceptionBlock exceptionBlock = iterator.next();
			ModelOperation modelOperation = createOperation_EventHandler_Exception(indent, index, operation, listenCommand, exceptionBlock);
			pushLocalOperationCache(modelOperation);
		}
	}
	
	public ModelOperation createOperation_EventHandler_Exception(int indent, int index, Operation operation, ListenCommand listenCommand, ExceptionBlock exceptionBlock) throws Exception {
		Interactor interactor = (Interactor) listenCommand.getActor();
		String serviceName = interactor.getService();
		String eventName = listenCommand.getMessageName();
		String eventNameBase = NameUtil.capName(eventName);
		if (eventNameBase.endsWith("Event"))
			eventNameBase = eventNameBase.substring(eventName.length() - 5);
		String operationName = "handle_" + eventNameBase + "_event_exception";
		if (listenCommand.getExceptionBlocks().size() > 1)
			operationName += index;
		if (!eventName.endsWith("Event"))
			eventName += "Event";
		//scope = "exception";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		//modelOperation.setIndex(listenCommandCount);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("cause");
		modelParameter.setClassName("Throwable");
		modelParameter.setPackageName("java.lang");
		modelOperation.addParameter(modelParameter);
		
		String eventBeanName = NameUtil.uncapName(eventName); 
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);

		Buf buf = new Buf();
		buf.putLine(indent, "fire_"+eventNameBase+"_process_aborted(cause);");

		List<Command> commands = exceptionBlock.getCommands();
		String sourceCode = generateSourceForCommands(operation, commands);
		buf.put(sourceCode);

		buf.put(generateSourceForDoneCommand(operation, commands));
		modelOperation.addInitialSource(buf.get());
		//scope = "default";
		return modelOperation;
	}
	
	public String generateSourceForOptionCommand(int indent, Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		String parameterName = operation.getParameters().get(0).getName();
		if (parameterName.endsWith("Message"))
			parameterName = parameterName.substring(0, parameterName.length()-7);
		String operationName = NameUtil.uncapName(parameterName)+"Enabled";
		String newMethodName = NameUtil.capName(context.getProcess().getName())+"Defaults."+operationName;
		buf.putLine(indent, "if ("+newMethodName+"("+optionCount+")) {");
		List<Command> commands = command.getCommands();
		Iterator<Command> iterator = commands.iterator();
		while (iterator.hasNext()) {
			Command command2 = iterator.next();
			buf.put(indent, generateSourceForCommand(indent+1, operation, command2));
			if (command2.getName() == CommandName.REPLY) {
				String typeName = command2.getText();
				if (!typeName.endsWith("Message"))
					typeName += "Message";
				
				Element element = context.getElementByName(typeName);
				//String className = TypeUtil.getClassName(parameterElement.getType());
				//String packageName = TypeUtil.getPackageName(parameterElement.getType());
				modelClass.addImportedClass(element);
			}
		}
		buf.putLine(indent+1, "updateState();");
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String generateSourceForPostCommand(int indent, Operation operation, PostCommand postCommand) throws Exception {
		Interactor actor = (Interactor) postCommand.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(postCommand.getEventName());
		String commandTextUncapped = NameUtil.uncapName(postCommand.getEventName());
		String responseType = commandTextCapped;
		if (!responseType.endsWith("Event"))
			responseType += "Event";
		String responseName = NameUtil.uncapName(responseType);
		String operationName = "post_" + commandTextCapped;
		
		Buf buf = new Buf();
		String argumentString = CodeUtil.getArgumentString(postCommand);
		buf.putLine(indent, operationName+"("+argumentString+");");
		
//		if (operationName.equals("replyBooksAvailable"))
//			System.out.println();
		
		Element responseElement = context.getElementByName(responseType);
		modelClass.addImportedClass(responseElement);
		pushLocalOperationCache(createPostOperation2(2, postCommand));
		pushLocalOperationCache(createPostOperation3(2, postCommand));
		return buf.get();
		
	}
	
	public ModelOperation createPostOperation2(int indent, PostCommand command) throws Exception {
		Interactor interactor = (Interactor) command.getActor();
		String serviceName = interactor.getService();
		String eventName = command.getEventName();
		String operationName = "post_" + eventName;
		eventName += "Event";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		String eventBeanName = NameUtil.uncapName(eventName); 
//		String messageClassName = NameUtil.capName(messageName);
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);
//		String messagePackageName = TypeUtil.getPackageName(messageElement.getType());
		
//		ModelParameter modelParameter = new ModelParameter();
//		modelParameter.setName(messageBeanName);
//		modelParameter.setClassName(messageClassName);
//		modelParameter.setPackageName(messagePackageName);
//		modelOperation.addParameter(modelParameter);

		List<String> arguments = new ArrayList<String>();
		arguments.add(eventBeanName);

		Buf buf = new Buf();
		String sourceCode = getSourceForNewEventElement(indent, null, serviceName, eventElement);
		buf.put(sourceCode);

		List<Parameter> parameters = command.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String type = parameter.getType();
			String key = parameter.getKey();
			String name = TypeUtil.getLocalPart(type);
			String construct = parameter.getConstruct();
			
			String className = TypeUtil.getClassName(type);
			String packageName = TypeUtil.getPackageName(type);
			//String structuredType = printType(construct, type, key);
			
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(name);
			modelParameter.setClassName(className);
			modelParameter.setPackageName(packageName);
			modelParameter.setConstruct(parameter.getConstruct());
			modelOperation.addParameter(modelParameter);
			
			//TODO 
			//this will need to become "smart" 
			//in order to choose the "correct" field
			String fieldName = NameUtil.capName(name);
			buf.putLine2("event.set"+fieldName+"("+name+");");
		}

		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		//buf.putLine(indent, operationName+"("+argumentString+");");
		buf.putLine(indent, operationName+"(event);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public ModelOperation createPostOperation3(int indent, PostCommand command) {
		String processName = ServiceLayerHelper.getProcessInstanceName(context.getProcess());
		processName = processName.substring(0, processName.length() - 7);
		
		//Interactor interactor = (Interactor) command.getActor();
		String eventName = command.getEventName();
		//String serviceName = interactor.getService();
		//String serviceInterface = NameUtil.capName(serviceName);
		String eventInterface = NameUtil.capName(eventName);
		String eventOperation = NameUtil.uncapName(eventName);
		String operationName = "post_" + eventName;
		eventName += "Event";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		String eventBeanName = "event"; 
		//String messageBeanName = NameUtil.uncapName(messageName); 
		String eventClassName = NameUtil.capName(eventName);
		Element eventElement = context.getElementByName(eventName);
		Assert.notNull(eventElement, "Event element not found: "+eventName);
		String eventPackageName = TypeUtil.getPackageName(eventElement.getType());

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(eventBeanName);
		modelParameter.setClassName(eventClassName);
		modelParameter.setPackageName(eventPackageName);
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(eventPackageName+"."+eventClassName);
		
		//List<Parameter> parameters = command.getParameters();
		List<String> arguments = new ArrayList<String>();
		arguments.add(eventBeanName);
		
		Buf buf = new Buf();
		//buf.putLine(indent, "EventMulticaster eventMulticaster = new EventMulticaster();");
		buf.putLine(indent, processName+"EventMulticaster.fireEvent("+eventBeanName+");");
		//modelClass.addImportedClass("org.aries.event.EventMulticaster");
		buf.putLine2("fire_"+eventInterface+"_event_posted();");
		modelOperation.addInitialSource(buf.get());
		
		//Callback callback = context.getOutgoingCallbackByName(callbackInterface);
		//modelClass.addImportedClass(callback.getPackageName()+"."+callback.getInterfaceName());
		return modelOperation;
	}
	
	
	public String generateSourceForReplyCommand(int indent, Operation operation, ReplyCommand replyCommand) throws Exception {
		Interactor actor = (Interactor) replyCommand.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(replyCommand.getMessageName());
		//String commandTextUncapped = NameUtil.uncapName(replyCommand.getMessageName());
		String messageNameCapped = commandTextCapped;
		if (!messageNameCapped.endsWith("Message"))
			messageNameCapped += "Message";
		String messageBeanName = NameUtil.uncapName(messageNameCapped);
		String operationName = "reply_" + commandTextCapped + "";
		String argumentString = CodeUtil.getArgumentString(replyCommand);
		String processScope = replyCommand.getProcessScope();
		//addRequiredLocalVariables(replyCommand);
		//addDefinedVariablesInScope(replyCommand);
		addDefinedLocalVariables(replyCommand);

//		if (commandTextCapped.startsWith("ShipmentFailed"))
//			System.out.println();
		
		Element messageElement = context.getElementByName(messageNameCapped);
		boolean hasCauseField = ElementUtil.hasField(messageElement, "cause");
		boolean hasReasonField = ElementUtil.hasField(messageElement, "reason");
		
		Buf buf = new Buf();
		if (processScope.equals("receive") || processScope.equals("message")) {
			buf.putLine(indent, operationName+"("+argumentString+");");
			
		} else if (processScope.equals("timeout")) {
			if (commandTextCapped.startsWith("OrderRejected"))
				System.out.println();
			if (argumentString.isEmpty())
				buf.putLine(indent, operationName+"(reason);");
			else if (!argumentString.contains("reason"))
				buf.putLine(indent, operationName+"("+argumentString+", reason);");
			else buf.putLine(indent, operationName+"("+argumentString+");");
			
		} else if (processScope.equals("exception")) {
			if (commandTextCapped.startsWith("OrderRejected"))
				System.out.println();
			if (argumentString.isEmpty())
				buf.putLine(indent, operationName+"(cause);");
			else if (!argumentString.contains("cause"))
				buf.putLine(indent, operationName+"("+argumentString+", cause);");
			else buf.putLine(indent, operationName+"("+argumentString+");");
		}
		
//		if (responseType.startsWith("BooksAvailable"))
//			System.out.println();
		
		addRequiredLocalVariables(messageElement);
		modelClass.addImportedClass(messageElement);
		createReplyOperations(indent, operation, replyCommand);
		addRequiredVariablesInScope(replyCommand);
		return buf.get();
	}

	public Collection<ModelOperation> createReplyOperations(int indent, Operation operation, ReplyCommand replyCommand) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		String commandTextCapped = NameUtil.capName(replyCommand.getMessageName());
		//String commandTextUncapped = NameUtil.uncapName(replyCommand.getMessageName());
		String responseType = commandTextCapped;
		
		addDefinedLocalVariables(replyCommand);
		Element responseElement = context.getElementByName(responseType);
		addRequiredLocalVariables(responseElement);
		modelClass.addImportedClass(responseElement);
		String processScope = replyCommand.getProcessScope();
		if (processScope.equals("receive") || processScope.equals("message"))
			modelOperations.add(createReplyOperation_CustomArgs(2, replyCommand));
		if (processScope.equals("exception"))
			modelOperations.add(createReplyOperation_ErrorArgs(2, replyCommand));
		if (processScope.equals("timeout"))
			modelOperations.add(createReplyOperation_TimeoutArgs(2, replyCommand));
		modelOperations.add(createReplyOperation_MessageArgs(2, replyCommand));
		pushLocalOperationCache(modelOperations);
		removeDefinedLocalVariables(replyCommand);
		addRequiredVariablesInScope(replyCommand);
		return modelOperations;
	}
	
	public ModelOperation createReplyOperation_CustomArgs(int indent, ReplyCommand command) throws Exception {
		Interactor interactor = (Interactor) command.getActor();
		String serviceName = interactor.getService();
		String messageName = command.getMessageName();
		String operationName = "reply_" + messageName + "";

		Element messageElement = context.getElementByName(messageName + "Message");
		if (messageElement != null)
			messageName += "Message";
		if (messageElement == null)
			messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messageBeanName = NameUtil.uncapName(messageName); 

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

//		String messagePackageName = TypeUtil.getPackageName(messageElement.getType());
		
//		ModelParameter modelParameter = new ModelParameter();
//		modelParameter.setName(messageBeanName);
//		modelParameter.setClassName(messageClassName);
//		modelParameter.setPackageName(messagePackageName);
//		modelOperation.addParameter(modelParameter);

		List<String> arguments = new ArrayList<String>();
		arguments.add(messageBeanName);

//		if (messageName.startsWith("OrderRejected"))
//			System.out.println();
		
		definedLocalVariables.size();
		definedVariablesInScope.size();
		requiredLocalVariables.size();
		requiredVariablesInScope.size();
		availableVariablesInScope.size();
		
		List<Parameter> parameters = command.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String type = parameter.getType();
			String key = parameter.getKey();
			String name = parameter.getName();
			//String name = TypeUtil.getLocalPart(type);
			String structure = parameter.getConstruct();
			
//			if (name.equals("booksInStock"))
//				System.out.println();
			
			String className = TypeUtil.getClassName(type);
			String packageName = TypeUtil.getPackageName(type);
			//String structuredType = printType(construct, type, key);
			String structuredName = ModelFieldUtil.getStructuredName(className, structure);
			String parameterName = structuredName;
			
//			if (parameterName.equals("bookList"))
//				System.out.println();

			Field field = ElementUtil.getCorrespondingField(messageElement, parameter);
			if (field != null) {
				parameterName = ModelFieldUtil.getStructuredName(field.getName(), field.getStructure());
				parameterName = NameUtil.uncapName(field.getName());
				addDefinedLocalVariable(parameterName, field);
			} else {
				Element parameterElement = context.getElementByType(parameter.getType());
				addDefinedLocalVariable(parameterName, parameterElement);
			}
			
//			if (parameterName.equals("books"))
//				System.out.println();

			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(parameterName);
			modelParameter.setClassName(className);
			modelParameter.setPackageName(packageName);
			
			if (FieldUtil.isCollection(structure))
				modelParameter.setConstruct("collection");
			else modelParameter.setConstruct(structure);
			modelOperation.addParameter(modelParameter);
			
//			if (isDefinedLocalVariable(name)) {
//				addRequiredLocalVariable(name);
//				Field field = ElementUtil.getCorrespondingField(messageElement, parameter);
//				if (field != null) {
//					String fieldName = NameUtil.capName(field.getName());
//					buf.putLine2("message.set"+fieldName+"("+name+");");
//				}
//			}
		}

		Buf buf = new Buf();
		String applicationName = context.getApplication().getName();
		String sourceCode = getSourceForCallbackServiceCall(indent, applicationName, serviceName, messageElement, parameters);
		buf.put(sourceCode);

		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		//buf.putLine(indent, operationName+"("+argumentString+");");
		buf.putLine(indent, operationName+"(message);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public ModelOperation createReplyOperation_ErrorArgs(int indent, ReplyCommand replyCommand) throws Exception {
		Interactor interactor = (Interactor) replyCommand.getActor();
		String serviceName = interactor.getService();
		String messageName = replyCommand.getMessageName();
		String operationName = "reply_" + messageName + "";
		keyWordsInScope.add("cause");
		
		Element messageElement = context.getElementByName(messageName + "Message");
		if (messageElement != null)
			messageName += "Message";
		if (messageElement == null)
			messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messageBeanName = NameUtil.uncapName(messageName); 

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		
//		if (messageName.startsWith("OrderRejected"))
//			System.out.println();
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.addAll(replyCommand.getParameters());
		List<ModelParameter> modelParameters = CodeUtil.createParameters(parameters);
		modelOperation.addParameters(modelParameters);
		
		if (!modelOperation.hasParameter("java.lang", "Throwable", "cause")) {
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setPackageName("java.lang");
			modelParameter.setClassName("Throwable");
			modelParameter.setName("cause");
			modelParameter.setConstruct("item");
			modelOperation.addParameter(modelParameter);
			//this is necessarily created for the purpose of passing into source creation module below
			parameters.add(ParameterUtil.createParameter("java.lang", "Throwable", "cause", "item"));
		}
		
//		if (messageName.startsWith("ShipmentFailed"))
//			System.out.println();

		Buf buf = new Buf();
		//definedLocalVariables.clear();
		String applicationName = context.getApplication().getName();
		String sourceCodeServiceCall = getSourceForCallbackServiceCall(indent, applicationName, serviceName, messageElement, parameters);
		
		clearRequiredVariablesInScope();
		addRequiredVariablesInScope(replyCommand);
		List<String> arguments = CodeUtil.getArgumentList(modelOperation);
		String sourceForVariables = generateSourceForVariables(arguments);
		buf.put(sourceForVariables);
		buf.put(sourceCodeServiceCall);

		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		//buf.putLine(indent, operationName+"("+argumentString+");");
		buf.putLine(indent, operationName+"(message);");
		modelOperation.addInitialSource(buf.get());
		keyWordsInScope.clear();
		return modelOperation;
	}

	public ModelOperation createReplyOperation_TimeoutArgs(int indent, ReplyCommand command) throws Exception {
		Interactor interactor = (Interactor) command.getActor();
		String serviceName = interactor.getService();
		String messageName = command.getMessageName();
		String operationName = "reply_" + messageName + "";
		keyWordsInScope.add("reason");

		Element messageElement = context.getElementByName(messageName + "Message");
		if (messageElement != null)
			messageName += "Message";
		if (messageElement == null)
			messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messageBeanName = NameUtil.uncapName(messageName); 

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");
		
//		if (messageName.startsWith("ShipmentFailed"))
//			System.out.println();
		
		List<Parameter> parameters = command.getParameters();
		List<ModelParameter> modelParameters = CodeUtil.createParameters(parameters);
		modelOperation.addParameters(modelParameters);
		
		if (!modelOperation.hasParameter("java.lang", "String", "reason")) {
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName("reason");
			modelParameter.setClassName("String");
			modelParameter.setPackageName("java.lang");
			modelParameter.setConstruct("item");
			modelOperation.addParameter(modelParameter);
			//this is necessarily created for the purpose of passing into source creation module below
			parameters.add(ParameterUtil.createParameter("java.lang", "String", "reason", "item"));
		}

		Buf buf = new Buf();
		String applicationName = context.getApplication().getName();
		String sourceCodeServiceCall = getSourceForCallbackServiceCall(indent, applicationName, serviceName, messageElement, parameters);
		//String sourceForVariables = generateSourceForVariables();
		//buf.put(sourceForVariables);
		buf.put(sourceCodeServiceCall);
		
		Field field = ElementUtil.getField(messageElement, "reason");
		if (field != null) {
			String fieldName = NameUtil.capName(field.getName());
			buf.putLine2("message.set"+fieldName+"(reason);");
		}

		//String argumentString = NameUtil.toCommaSeparatedString(arguments);
		//buf.putLine(indent, operationName+"("+argumentString+");");
		buf.putLine(indent, operationName+"(message);");
		modelOperation.addInitialSource(buf.get());
		keyWordsInScope.clear();
		return modelOperation;
	}
	
	public ModelOperation createReplyOperation_MessageArgs(int indent, ReplyCommand command) throws Exception {
		//Interactor interactor = (Interactor) command.getActor();
		String messageName = command.getMessageName();
		//String serviceName = interactor.getService();
		//String serviceInterface = NameUtil.capName(serviceName);
		String callbackInterface = NameUtil.capName(messageName);
		String callbackOperation = NameUtil.uncapName(messageName);
		String operationName = "reply_" + messageName + "";
		
		Element messageElement = context.getElementByName(messageName + "Message");
		if (messageElement != null)
			messageName += "Message";
		if (messageElement == null)
			messageElement = context.getElementByName(messageName);
		Assert.notNull(messageElement, "Message element not found: "+messageName);
		String messagePackageName = TypeUtil.getPackageName(messageElement.getType());
		String messageClassName = NameUtil.capName(messageName);
		//String messageBeanName = NameUtil.uncapName(messageName); 
		String messageBeanName = "message"; 
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(messageBeanName);
		modelParameter.setClassName(messageClassName);
		modelParameter.setPackageName(messagePackageName);
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(messagePackageName+"."+messageClassName);
		
		//List<Parameter> parameters = command.getParameters();
		List<String> arguments = new ArrayList<String>();
		arguments.add(messageBeanName);
		
		Buf buf = new Buf();
		String argumentString = NameUtil.toCommaSeparatedString(arguments);
		buf.putLine(indent, callbackInterface+" client = getClient("+callbackInterface+".ID);");
		buf.putLine(indent, "client."+callbackOperation+"("+argumentString+");");
		buf.putLine2("fire_"+callbackInterface+"_response_sent();");
		modelOperation.addInitialSource(buf.get());
		
		Application application = context.getApplication();
		Callback callback = ApplicationUtil.getOutgoingCallbackByName(application, callbackInterface);
		//Callback callback = context.getOutgoingCallbackByName(callbackInterface);
		String clientPackageName = ClientLayerHelper.getClientPackageName(callback);
		String clientInterfaceName = ClientLayerHelper.getClientInterfaceName(callback);
		modelClass.addImportedClass(clientPackageName+"."+clientInterfaceName);
		return modelOperation;
	}
	
	
	
	public String getSourceAndArgumentsFromMessageElement(List<String> arguments, Element messageElement) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		Buf buf = new Buf();
		
		List<Field> fields = ElementUtil.getApplicationDefinedFields(messageElement);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String beanName = field.getName();
			String fieldName = NameUtil.capName(beanName);
			String typeName = TypeUtil.getClassName(field.getType());
			
			buf.putLine2(typeName+" "+beanName+" = "+processContextName+".get"+fieldName+"();");
			arguments.add(beanName);
		}
		
		return buf.get();
	}

	
	public String getSourceForCallbackServiceCall(int indent, String applicationName, String serviceName, Element messageElement, List<Parameter> parameters) {
		return getSourceForServiceCall(indent, applicationName, serviceName, messageElement, parameters, false);
	}

	public String getSourceForRemoteServiceCall(int indent, String applicationName, String serviceName, Element messageElement, List<Parameter> parameters) {
		return getSourceForServiceCall(indent, applicationName, serviceName, messageElement, parameters, true);
	}

	public String getSourceForServiceCall(int indent, String applicationName, String serviceName, Element messageElement, List<Parameter> parameters, boolean isRemote) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String serviceNameCapped = NameUtil.capName(serviceName);
		String elementName = messageElement.getName();
		String elementType = messageElement.getType();

		String messageBeanName = "message";
		//String messageBeanName = NameUtil.uncapName(elementName);
		String messageClassName = TypeUtil.getClassName(elementType);
		String messagePackageName = ModelLayerHelper.getElementPackageName(messageElement);
		String factoryPrefix = NameUtil.getSimpleName(messagePackageName);
		String messageBaseName = messageClassName.replace("Message", "");

		Buf buf = new Buf();
		if (isRemote)
			buf.putLine(indent, messageClassName+" "+messageBeanName+" = create"+messageClassName+"();");
		else buf.putLine(indent, messageClassName+" "+messageBeanName+" = "+processContextName+".create"+messageClassName+"();");
		//buf.putLine(indent, messageClassName+" "+messageBeanName+" = "+factoryPrefix+"ObjectFactory.create"+messageClassName+"();");

		//Service serviceForCaller = context.getService();
		Service serviceForMessage = findServiceFromGroupOrApplication(applicationName, serviceName);
		Service outgoingCallback = context.getOutgoingCallbackByName(messageBaseName);
		//Service serviceForCaller = ApplicationUtil.getIncomingCallbackByName(context.getApplication(), messageBaseName);
		//Service serviceForCaller = ApplicationUtil.getIncomingCallbackByName(context.getApplication(), messageBaseName);
		//Service serviceForMessage = ApplicationUtil.getIncomingCallbackByName(context.getApplication(), messageBaseName);
		//Service serviceForCaller = findServiceFromGroupOrApplication(applicationName, serviceName);
		//Assert.notNull(serviceForCaller, "Service for caller not found: "+serviceName);
		Assert.notNull(serviceForMessage, "Service for caller not found: "+serviceName);

//		if (outgoingCallback == null) {
//			//List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(serviceForCaller);
//			List<Callback> incomingCallbacks = ServiceUtil.getIncomingCallbacks(context.getService());
//			Iterator<Callback> callbackIterator = incomingCallbacks.iterator();
//			while (callbackIterator.hasNext()) {
//				Callback callback = callbackIterator.next();
//				String callbackName = callback.getName();
//				String callbackNameCapped = NameUtil.capName(callbackName);
//				buf.putLine(indent, processContextName+".add"+callbackNameCapped+"ReplyTo(message);");
//				//buf.putLine(indent, messageBeanName+".addReplyToDestinations(\""+callbackName+"\", "+processContextName+".get"+callbackNameCapped+"Destination());");
//			}
//		}
		
		//buf.putLine(indent, "initializeMessage(\""+serviceNameCapped+"\", "+messageBeanName+");");

//		if (parameters != null) {
//			Iterator<Parameter> iterator = iterator;
//			while (iterator.hasNext()) {
//				Parameter parameter = iterator.next();
//				String paramName = parameter.getName();
//				String fieldName = NameUtil.capName(paramName);
//				
//				boolean reasonSpecialCase = false;
//				if (paramName.equals("reason") && keyWordsInScope.contains("cause")) {
//					buf.putLine2("message.setReason(cause.getMessage());");
//					reasonSpecialCase = true;
//				}
//				
//				if (isDefinedLocalVariable(paramName) && !reasonSpecialCase) {
//					//if (isDefinedLocalVariable(beanName) && !beanName.equals("reason") && !beanName.equals("cause"))
//					//if (isDefinedLocalVariable(beanName) && causeField == null && reasonField == null && !beanName.equals("reason") && !beanName.equals("cause"))
//					//if (requiredLocalVariables.contains(beanName) || definedLocalVariables.containsKey(beanName))
//					buf.putLine2(messageBeanName+".set"+fieldName+"("+paramName+");");
//					addRequiredVariableInScope(paramName);
//				}
//			}
//		}
		
		List<Field> fields = ElementUtil.getApplicationDefinedFields(messageElement);
		Iterator<Field> fieldIterator = fields.iterator();
		while (fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			String beanName = field.getName();
			String fieldType = field.getType();
			String fieldName = NameUtil.capName(beanName);
			Element fieldByType = context.getElementByType(fieldType);
			String className = TypeUtil.getClassName(fieldType);
			String packageName = TypeUtil.getPackageName(fieldType);
			
//			Field causeField = ElementUtil.getField(messageElement, "cause");
//			if (causeField != null) {
//				String causeFieldName = NameUtil.capName(causeField.getName());
//				buf.putLine2("message.set"+causeFieldName+"(cause);");
//				reasonField = ElementUtil.getField(messageElement, "reason");
//				if (reasonField != null) {
//					buf.putLine2("message.setReason(cause.getMessage());");
//				}
//			}

//			//Field reasonField = null;
//			boolean reasonSpecialCase = false;
//			if (beanName.equals("reason") && keyWordsInScope.contains("cause")) {
//				buf.putLine2("message.setReason(cause.getMessage());");
//				reasonSpecialCase = true;
//			}
			
//			if (beanName.equals("books"))
//				System.out.println();
			
			//if (isDefinedLocalVariable(beanName)) {
			//if (isDefinedLocalVariable(beanName) && !reasonSpecialCase) {
			if (isDefinedLocalVariable(beanName) && !beanName.equals("reason") && !beanName.equals("cause")) {
			//if (isDefinedLocalVariable(beanName) && causeField == null && reasonField == null && !beanName.equals("reason") && !beanName.equals("cause"))
			//if (requiredLocalVariables.contains(beanName) || definedLocalVariables.containsKey(beanName))
				buf.putLine2(messageBeanName+".set"+fieldName+"("+beanName+");");
				addRequiredVariableInScope(beanName);
				
			} else if (parameters != null) {
				Set<Parameter> alreadyUsedParameters = new HashSet<Parameter>();
				Iterator<Parameter> iterator = parameters.iterator();
				while (iterator.hasNext()) {
					Parameter parameter = iterator.next();
					if (alreadyUsedParameters.contains(parameter))
						continue;
					
					//special case for error arg "cause" populating field "reason"
					if (parameter.getName().equals("cause") && beanName.equals("reason")) {
						buf.putLine2(messageBeanName+".set"+fieldName+"(cause.getMessage());");
						alreadyUsedParameters.add(parameter);
						continue;
					}
					
					String paramType = parameter.getType();
					if (!paramType.equals(fieldType))
						continue;
					
					if (!FieldUtil.isStructureCompatible(field, parameter) &&
						!FieldUtil.isStructuresCompatibleWithCollection(field, parameter))
						continue;

					String structure = parameter.getConstruct();
					String structuredName = ModelFieldUtil.getStructuredName(className, structure);
					if (isDefinedLocalVariable(structuredName)) {
						buf.putLine2(messageBeanName+".set"+fieldName+"("+structuredName+");");
						//addRequiredVariableInScope(structuredName);
						alreadyUsedParameters.add(parameter);
						continue;
					}

					String paramName = parameter.getName();
					if (isDefinedLocalVariable(paramName)) {
						buf.putLine2(messageBeanName+".set"+fieldName+"("+beanName+");");
						addRequiredVariableInScope(beanName);
						alreadyUsedParameters.add(parameter);
					}
				}
			}

			if (fieldByType != null) {
			} else if (CodeGenUtil.isJavaDefaultType(fieldType) || CodeGenUtil.isJavaLangType(fieldType) || CodeGenUtil.isJavaPrimitiveType(fieldType)) {
			}
		}
		
		return buf.get();
	}
	
	public Service findServiceFromGroupOrApplication(String applicationOrGroupName, String serviceName) {
		Application application = context.getApplicationByName(applicationOrGroupName);
		if (application != null) {
			Service service = ApplicationUtil.getServiceByName(application, serviceName);
			if (service != null)
				return service;
		}
		Group group = context.getGroupByName(applicationOrGroupName);
		List<Application> applications = group.getApplications();
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			application = iterator.next();
			Service service = ApplicationUtil.getServiceByName(application, serviceName);
			if (service != null)
				return service;
		}
		return null;
	}


	public String getSourceForNewEventElement(int indent, String serviceName, Element eventElement) {
		return getSourceForNewEventElement(indent, null, serviceName, eventElement);
	}
	
	public String getSourceForNewEventElement(int indent, ModelOperation modelOperation, String serviceName, Element eventElement) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String serviceNameCapped = NameUtil.capName(serviceName);
		String elementName = eventElement.getName();
		String elementType = eventElement.getType();

		String eventBeanName = "event";
		//String messageBeanName = NameUtil.uncapName(elementName);
		String eventClassName = TypeUtil.getClassName(elementType);
		String eventPackageName = ModelLayerHelper.getElementPackageName(eventElement);
		String factoryPrefix = NameUtil.getSimpleName(eventPackageName);
		String eventServiceName = eventClassName.replace("Event", "");

		Buf buf = new Buf();
		buf.putLine(indent, eventClassName+" "+eventBeanName+" = new "+eventClassName+"();");
		//buf.putLine(indent, eventClassName+" "+eventBeanName+" = "+processContextName+".create"+eventClassName+"();");
		//buf.putLine(indent, messageClassName+" "+messageBeanName+" = "+factoryPrefix+"ObjectFactory.create"+messageClassName+"();");
		return buf.get();
	}
	
	
	public Operation getOperation(Process process, String operationName, List<Parameter> arguments) {
		List<Operation> operations = process.getOperations();
		Iterator<Operation> iterator = operations.iterator();
		
		top:
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (!operation.getName().equalsIgnoreCase(operationName))
				continue;
			
			Iterator<Parameter> iterator2 = arguments.iterator();
			while (iterator2.hasNext()) {
				Parameter argument = iterator2.next();
				boolean argumentFound = false;

				List<Parameter> parameters = operation.getParameters();
				Iterator<Parameter> iterator3 = parameters.iterator();
				while (iterator3.hasNext()) {
					Parameter parameter = iterator3.next();
					if (parameter.getName().equalsIgnoreCase(argument.getName())) {
						argumentFound = true;
						break;
					}
				}
				
				if (!argumentFound)
					continue top;
			}
			
			return operation;
		}
		return null;
	}
	
	/**
	 * We expect an Operation analogous to this ModelOperation 
	 * already exists in the Process (i.e. as a result of being 
	 * created via a Reply command or a Send command).  So we 
	 * just look this up and get necessary details from it
	 * (parameter types).
	 */
	public ModelOperation createReplyOperationOLD(int indent, ReplyCommand command, List<Parameter> arguments) throws Exception {
		Interactor actor = (Interactor) command.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(command.getMessageName());
		String commandTextUncapped = NameUtil.uncapName(command.getMessageName());
		String replyOperationName = "reply" + commandTextCapped;
		String localOperationName = "reply" + commandTextCapped;
		String serviceName = commandTextCapped;
		String targetName = actor.getTarget();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);

		Map<String, ModelParameter> modelParameterMap = new HashMap<String, ModelParameter>();
		Map<String, Parameter> parameterMap = new HashMap<String, Parameter>();
		
		Operation operation = getOperation(context.getProcess(), replyOperationName, arguments);
		//Assert.notNull(operation, "Operation not found in process: "+replyOperationName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(replyOperationName);
		modelOperation.setResultType("void");
		
		if (operation == null) {
			List<ModelParameter> modelParameters = CodeUtil.createParameters(arguments);
			modelOperation.getParameters().addAll(modelParameters);
			Iterator<ModelParameter> iterator = modelParameters.iterator();
			while (iterator.hasNext()) {
				ModelParameter modelParameter = iterator.next();
				String parameterName = modelParameter.getName();
				modelParameterMap.put(parameterName, modelParameter);
				modelClass.addImportedClass(modelParameter);
			}
		}
		
		if (operation != null) {
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				String parameterName = parameter.getName();
				String parameterType = parameter.getType();
				String parameterKey = parameter.getKey();
				parameterMap.put(parameterName, parameter);
	
				ModelParameter modelParameter = new ModelParameter();
				modelParameter.setName(parameter.getName());
				modelParameter.setConstruct(parameter.getConstruct());
				modelParameter.setPackageName(TypeUtil.getPackageName(parameterType));
				modelParameter.setClassName(TypeUtil.getClassName(parameterType));
				if (parameterKey != null) {
					modelParameter.setKeyPackageName(TypeUtil.getPackageName(parameterKey));
					modelParameter.setKeyClassName(TypeUtil.getClassName(parameterKey));
				}
				modelOperation.getParameters().add(modelParameter);
				modelClass.addImportedClass(modelParameter);
			}
		}
		
//		Project project = context.getProject();
//		Application application = ProjectUtil.getApplicationByName(project, targetName);
		
//		Callback callback = ApplicationUtil.getCallbackByName(application, serviceName);
//		List<Operation> operations = ServiceUtil.getOperations(callback);
//		Assert.isTrue(operations.size() == 1, "Only one operation per callback");
//		List<Parameter> parameters = operations.get(0).getParameters();
//		
//		Iterator<String> iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			String argumentCapped = NameUtil.capName(argument);
//			String parameterType = null;
//			String parameterConstruct = null;
//
//			Element parameterElement = context.getElementByName(argument);
//			if (parameterElement == null) {
//				String messageName = getFieldOwnerFromParameters(parameters, argument);
//				parameterElement = context.getElementByName(messageName);
//				Field field = ElementUtil.getField(parameterElement, argument);
//				Assert.notNull(field, "Field not found: "+argument);
//				parameterType = field.getType();
//				parameterConstruct = field.getStructure();
//			} else {
//				parameterType = parameterElement.getType();
//				parameterConstruct = "item";
//			}
//
//			String className = TypeUtil.getClassName(parameterType);
//			String packageName = TypeUtil.getPackageName(parameterType);
//			ModelParameter modelParameter = new ModelParameter();
//			modelParameter.setName(argument);
//			modelParameter.setClassName(className);
//			modelParameter.setPackageName(packageName);
//			modelParameter.setConstruct(parameterConstruct);
//			modelOperation.addParameter(modelParameter);
//			modelClass.addImportedClass(modelParameter);
//			if (parameterConstruct.equals("list"))
//				modelClass.addImportedClass("java.util.List");
//			else if (parameterConstruct.equals("set"))
//				modelClass.addImportedClass("java.util.Set");
//			else if (parameterConstruct.equals("map"))
//				modelClass.addImportedClass("java.util.Map");
//		}
		
//		Buf buf = new Buf();
//		Iterator<String> iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			String messageName = getFieldOwnerFromParameters(parameters, argument);
//			String messageType = NameUtil.capName(messageName);
//			Element messageElement = context.getElementByName(messageName);
//			String packageName = TypeUtil.getPackageName(messageElement.getType());
//			String simpleName = NameUtil.getSimpleName(packageName);
//			String objectFactoryName = simpleName + "ObjectFactory";
//			buf.putLine(indent+2, messageType+" "+messageName+" = "+objectFactoryName+".create"+messageType+"();");
//			attributeStack.add(createAttribute_ObjectFactory(packageName));
//		}
		
		String messageClassName = commandTextCapped + "Message";
		String messageBeanName = commandTextUncapped + "Message";
		
		Element messageElement = context.getElementByName(messageClassName);
		Assert.notNull(messageElement, "Message element not found: "+messageClassName);
		String packageName = ModelLayerHelper.getElementPackageName(messageElement);
		String factoryPrefix = NameUtil.getSimpleName(packageName);
		
		Buf buf = new Buf();
		Buf argumentString = new Buf();
		buf.putLine(indent, messageClassName+" "+messageBeanName+" = "+factoryPrefix+"ObjectFactory.create"+messageClassName+"();");
		
		Iterator<Parameter> iterator2 = arguments.iterator();
		for (int i=0; iterator2.hasNext(); i++) {
			Parameter argument = iterator2.next();
			String argumentName = argument.getName();
			String argumentType = TypeUtil.getClassName(argument.getType());
			
			ModelParameter modelParameter = modelParameterMap.get(argumentName);
			if (modelParameter != null) {
				String type = modelParameter.getClassName();
				String construct = modelParameter.getConstruct();
				List<Field> fieldsByType = ElementUtil.getFieldsByClassName(messageElement, construct, type);
//				if (fieldsByType.size() == 0)
//					System.out.println();
				if (fieldsByType.size() == 0)
					//System.out.println();
					continue;
				//Assert.isTrue(fieldsByType.size() > 0, "Field not found: construct="+construct+", type="+type);
				/*
				 * FOR NOW: JUST TAKE THE FIRST ONE
				 * In future we can make this lookup more intelligent.
				 */
				Field field = fieldsByType.get(0);
				argumentType = ModelLayerHelper.getFieldNameCapped(field);
				
			} else {
				Parameter parameter = parameterMap.get(argumentName);
				if (parameter != null) {
					String type = parameter.getType();
					String construct = parameter.getConstruct();
					List<Field> fieldsByType = ElementUtil.getFieldsByType(messageElement, construct, type);
					Assert.isTrue(fieldsByType.size() > 0, "Field not found: construct="+construct+", type="+type);
					/*
					 * FOR NOW: JUST TAKE THE FIRST ONE
					 * In future we can make this lookup more intelligent.
					 */
					Field field = fieldsByType.get(0);
					argumentType = ModelLayerHelper.getFieldNameCapped(field);
				}
			}
			
//			List<ModelParameter> parameters = modelOperation.getParameters();
//			Iterator<ModelParameter> iterator = parameters.iterator();
//			while (iterator.hasNext()) {
//				ModelParameter modelParameter = iterator.next();
//				if (modelParameter.getName().equalsIgnoreCase(argument.getName())) {
//					argumentType = modelParameter.getClassName();
//					break;
//				}
//			}
			
			//String messageName = getFieldOwnerFromParameters(parameters, argument);
			buf.putLine(indent, messageBeanName+".set"+argumentType+"("+argument.getName()+");");
			if (i > 0)
				argumentString.put(", ");
			argumentString.put(messageBeanName);
		}
		
		buf.putLine(indent, localOperationName+"("+argumentString.get()+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public ModelOperation createReplyOperationOLD(int indent, ReplyCommand replyCommand) throws Exception {
		Interactor actor = (Interactor) replyCommand.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(replyCommand.getMessageName());
		String commandTextUncapped = NameUtil.uncapName(replyCommand.getMessageName());
		String replyOperationName = "reply" + commandTextCapped;
		String clientOperationName = commandTextUncapped;
		String serviceName = commandTextCapped;
		//String targetName = actor.getTarget();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);
		//List<String> arguments = command.getParameters();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(replyOperationName);
		modelOperation.setResultType("void");

		//TODO RESOLVE THIS OUT SOON
		//if (targetName != null && targetName.endsWith("Group"))
		//	targetName = targetName.replace("Group", "");
			
//		Project project = context.getProject();
//		Application application = ProjectUtil.getApplicationByName(project, targetName);
//		Callback callback = ApplicationUtil.getCallbackByName(application, serviceName);
//		List<Operation> operations = ServiceUtil.getOperations(callback);
//		Assert.isTrue(operations.size() == 1, "Only one operation per callback");
//		List<Parameter> parameters = operations.get(0).getParameters();
//
//		Iterator<Parameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			String messageType = parameter.getName();
//			String messageName = NameUtil.uncapName(messageType);
//			Element parameterElement = context.getElementByName(messageType);
//			String className = TypeUtil.getClassName(parameterElement.getType());
//			String packageName = TypeUtil.getPackageName(parameterElement.getType());
//			ModelParameter modelParameter = new ModelParameter();
//			modelParameter.setName(messageName);
//			modelParameter.setClassName(className);
//			modelParameter.setPackageName(packageName);
//			modelOperation.addParameter(modelParameter);
//			modelClass.addImportedClass(modelParameter);
//		}
//		
////		String serviceName = invoker.getService();
////		String serviceNameCapped = NameUtil.capName(invoker.getService());
////		String packageName = getPackageNameFromTargetName(invoker.getTarget());
////		String parameters = generateCallerSideNameList(command.getParameters());
////		packageName = getPackageNameFromTargetName(invoker.getTarget());
////		modelClass.addImportedClass(packageName+"."+serviceNameCapped+"Client");
//		
////		if (parameterType.endsWith("Message"))
////			parameterType = parameterType.substring(0, parameterType.length()-7);
//		
////		packageName = getPackageNameFromTargetName(targetName);
////		modelClass.addImportedClass(packageName+"."+parameterType+"Client");
//		
////		String parameters = generateCallerSideNameList(command.getParameters());
////		String parameters = "parameters";
//		
////		Buf buf = new Buf();
////		if (arguments.size() > 0) {
////			buf.putLine(indent+2, messageType+" "+messageName+" = new "+messageType+"();");
////			Iterator<String> iterator = arguments.iterator();
////			while (iterator.hasNext()) {
////				String argument = iterator.next();
////				String argumentCapped = NameUtil.capName(argument);
////				buf.putLine2(messageName+".set"+argumentCapped+"("+argument+");");
////			}
////		}
//		
//		//packageName = getPackageNameFromTargetName(actor.getTarget());
//		packageName = ServiceLayerHelper.getServicePackageName(callback);
//		modelClass.addImportedClass(packageName+"."+serviceName+"Client");
//		String parameterList = generateCallerSideParameterList2(modelOperation.getParameters());
//		String parameterList = "";

		String interfaceName = commandTextCapped;
		String instanceName = commandTextUncapped;
		String messageClassName = interfaceName + "Message";
		String messageBeanName = instanceName + "Message";

		Element messageElement = context.getElementByName(messageClassName);
		Assert.notNull(messageElement, "Message element not found: "+messageClassName);
		String messagePackageName = ModelLayerHelper.getElementPackageName(messageElement);

		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(messageBeanName);
		modelParameter.setClassName(messageClassName);
		modelParameter.setPackageName(messagePackageName);
		modelOperation.addParameter(modelParameter);
		modelClass.addImportedClass(modelParameter);
		
		List<Project> projects = context.getProjectList();
		String parameterList = generateCallerSideParameterList2(modelOperation.getParameters());
		Callback callback = ProjectUtil.getOutgoingCallbackByName(projects, interfaceName);
		Assert.notNull(callback, "Callback not found: "+interfaceName);
		
		Buf buf = new Buf();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			buf.putLine(indent, serviceName+" client = getClient("+serviceName+".ID);");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
			
		case POJO:	
			buf.putLine(indent, "ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
			buf.putLine(indent, serviceName+"Client client = proxyLocator.get(\""+serviceName+"\");");
			buf.putLine(indent, "client.setCorrelationId(getCorrelationId());");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
		}
		
		//buf.putLine(indent+2, interfaceName+" client = getClient(\""+callback.getDomain()+"."+interfaceName+"\");");
		//buf.putLine(indent+2, "client."+instanceName+"("+messageBeanName+");");
		
		//buf.putLine(indent+2, "ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
		//buf.putLine(indent+2, serviceName+"Client client = proxyLocator.get(\""+serviceName+"\");");
		//buf.putLine(indent+2, "client.setCorrelationId(getCorrelationId());");
		//buf.putLine(indent+2, "client."+clientOperationName+"("+parameterList+");");
		//String generatedSource = CodeUtil.createMethodSource(buf.get());
		
		//String remoteServiceName = replyCommand.getMessageName();
		//remoteServiceName = NameUtil.uncapName(remoteServiceName);
		//Service remoteService = ProjectUtil.getOutgoingCallbackByName(projects, remoteServiceName);
		
		String clientPackageName = ClientLayerHelper.getClientPackageName(callback);
		modelClass.addImportedClass(clientPackageName + "." + interfaceName);
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public String getMessageTypeForService(String serviceName) {
		Service service = context.getServiceByName(serviceName);
		if (service == null)
			service = context.getOutgoingCallbackByName(serviceName);
		Assert.notNull(service, "Service not found: "+serviceName);
		Operation operation = ServiceUtil.getOperation(service, serviceName);
		String parameterName = operation.getParameters().get(0).getName();
		String messageType = NameUtil.capName(parameterName);
		if (!messageType.endsWith("Message"))
			messageType += "Message";
		return messageType;
	}
	
	public String generateSourceForSendCommand(int indent, Operation operation, SendCommand sendCommand) throws Exception {
		Interactor interactor = (Interactor) sendCommand.getActor();
		//String remoteApplicationName = interactor.getName();
		String remoteServiceName = interactor.getService();
		
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String serviceInterface = getExtendedDomainServiceName(interactor);

		//need parameter types, not response type
		String messageType = getMessageTypeForService(remoteServiceName);
		//String responseName = NameUtil.uncapName(responseType);
		//buf.putLine(indent+2, responseType+" "+responseName+" = new "+responseType+"();");
		String operationName = "send_" + serviceInterface + "_request";
		//String argumentString = CodeUtil.getArgumentString(sendCommand);
		//addDefinedVariablesInScope(sendCommand);
		addDefinedLocalVariables(sendCommand);
		
//		List<String> arguments = command.getParameters();
//		Iterator<String> iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			String argumentCapped = NameUtil.capName(argument);
//			buf.putLine2(responseName+".set"+argumentCapped+"("+argument+");");
//		}

		//String replyOperationName = "reply"+commandTextCapped;
		List<Parameter> arguments = sendCommand.getParameters();
		Iterator<Parameter> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			String parameterType = NameUtil.capName(argument);
			if (!parameterType.endsWith("Message"))
				parameterType += "Message";
			//String parameterName = NameUtil.uncapName(parameterType);
			//buf.putLine(indent+2, messageType+" "+messageName+" = new "+messageType+"();");
			Element messageElement = context.getElementByName(parameterType);
			modelClass.addImportedClass(messageElement);
			
			ModelVariable modelVariable = new ModelVariable();
			modelVariable.setName(argument);
			modelVariable.setOwner(parameterType);
			//modelClass.addLocalVariable(modelVariable);
		}

		Buf buf = new Buf();
		buf.put(indent, operationName + "(");
		iterator = arguments.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			argument = NameUtil.uncapName(argument);
			//if (!argument.endsWith("Message"))
			//	argument += "Message";
			if (i > 0)
				buf.put(", ");
			buf.put(argument);
		}
		buf.putLine(");");
		//buf.putLine(indent+2, "updateState();");

		//String targetName = interactor.getTarget();
		Element messageElement = context.getElementByName(messageType);
		modelClass.addImportedClass(messageElement);
		
		addRequiredLocalVariables(messageElement);
		boolean hasParameters = !sendCommand.getParameters().isEmpty();
		
		if (hasParameters)
			pushLocalOperationCache(createSendOperation(2, sendCommand));
		pushLocalOperationCache(createSendOperation2(2, sendCommand));
		pushLocalOperationCache(createSendOperation_cancel(2, sendCommand));
		pushLocalOperationCache(createSendOperation_undo(2, sendCommand));
		pushLocalOperationCache(createSendOperation4(2, sendCommand));
		pushLocalOperationCache(createSendOperation5(2, sendCommand));
		//pushLocalOperationCache(createSendOperation(0, command, arguments));
		//pushLocalOperationCache(createSendOperation(0, command));
		pushLocalOperationCache(createSendTimeoutOperation(2, sendCommand));
		addRequiredVariablesInScope(sendCommand);
		return buf.get();
	}

	public ModelOperation createSendOperationOLD(int indent, Command command, List<Parameter> arguments) throws Exception {
		Interactor actor = (Interactor) command.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(command.getText());
		String commandTextUncapped = NameUtil.uncapName(command.getText());
		String operationName = "send" + commandTextCapped;
		String serviceName = commandTextCapped;
		String targetName = actor.getTarget();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		//TODO RESOLVE THIS OUT SOON
		if (targetName.endsWith("Group"))
			targetName = targetName.replace("Group", "");
			
		Project project = context.getProject();
		Application application = ProjectUtil.getApplicationByName(project, targetName);
		Service service = ApplicationUtil.getServiceByName(application, serviceName);
		List<Operation> operations = ServiceUtil.getOperations(service);
		Assert.isTrue(operations.size() == 1, "Only one operation per service");
		List<Parameter> parameters = operations.get(0).getParameters();

		//List<Parameter> parameters = operation.getParameters();
		//String messageType = getFieldOwnerFromParameters(parameters, argument);

		Iterator<Parameter> iterator = arguments.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			String argumentCapped = NameUtil.capName(argument);
			String parameterType = null;
			String parameterConstruct = null;
			
			Element parameterElement = context.getElementByName(argument);
			if (parameterElement == null) {
				String messageName = getFieldOwnerFromParameters(parameters, argument);
				parameterElement = context.getElementByName(messageName);
				Field field = ElementUtil.getField(parameterElement, argument);
				Assert.notNull(field, "Field not found: "+argument);
				parameterType = field.getType();
				parameterConstruct = field.getStructure();
			} else {
				parameterType = parameterElement.getType();
				parameterConstruct = "item";
			}
			
			String className = TypeUtil.getClassName(parameterType);
			String packageName = TypeUtil.getPackageName(parameterType);
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(argument);
			modelParameter.setClassName(className);
			modelParameter.setPackageName(packageName);
			modelParameter.setConstruct(parameterConstruct);
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
			if (parameterConstruct.equals("list"))
				modelClass.addImportedClass("java.util.List");
			else if (parameterConstruct.equals("set"))
				modelClass.addImportedClass("java.util.Set");
			else if (parameterConstruct.equals("map"))
				modelClass.addImportedClass("java.util.Map");
		}

		Process process = context.getProcess();
		Namespace namespace = context.getNamespaceByUri(ProcessUtil.getNamespace(process));
		
		Buf buf = new Buf();
		iterator = arguments.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			String messageName = getFieldOwnerFromParameters(parameters, argument);
			String messageType = NameUtil.capName(messageName);
			Element messageElement = context.getElementByName(messageName);
			String packageName = TypeUtil.getPackageName(messageElement.getType());
			String baseName = NameUtil.getBaseName(packageName+".seller");
			//buf.putLine(indent+2, messageType+" "+messageName+" = new "+messageType+"();");
			String objectFactoryName = baseName + "ObjectFactory";
			buf.putLine(indent+2, messageType+" "+messageName+" = "+objectFactoryName+".create"+messageType+"();");
			referenceStack.add(createReference_ObjectFactory(packageName));
		}
		
		Buf argumentString = new Buf();
		iterator = arguments.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String argument = parameter.getName();
			String argumentCapped = NameUtil.capName(argument);
			String messageName = getFieldOwnerFromParameters(parameters, argument);
			buf.putLine(indent, messageName+".set"+argumentCapped+"("+argument+");");
			if (i > 0)
				argumentString.put(", ");
			argumentString.put(messageName);
		}
		
		buf.putLine(indent, operationName+"("+argumentString.get()+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public ModelOperation createSendOperationOLD(int indent, Command command) throws Exception {
		Interactor actor = (Interactor) command.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(command.getText());
		String commandTextUncapped = NameUtil.uncapName(command.getText());
		String operationName = "send" + commandTextCapped;
		String clientOperationName = commandTextUncapped;
		String serviceName = commandTextCapped;
		String targetName = actor.getTarget();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);
		//List<String> arguments = command.getParameters();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		//TODO RESOLVE THIS OUT SOON
		if (targetName.endsWith("Group"))
			targetName = targetName.replace("Group", "");
		
		Project project = context.getProject();
		Application application = ProjectUtil.getApplicationByName(project, targetName);
		Service service = ApplicationUtil.getServiceByName(application, serviceName);
		List<Operation> operations = ServiceUtil.getOperations(service);
		Assert.isTrue(operations.size() == 1, "Only one operation per service");
		List<Parameter> parameters = operations.get(0).getParameters();

		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String messageType = parameter.getName();
			String messageName = NameUtil.uncapName(messageType);
			Element parameterElement = context.getElementByName(messageType);
			String className = TypeUtil.getClassName(parameterElement.getType());
			String packageName = TypeUtil.getPackageName(parameterElement.getType());
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(messageName);
			modelParameter.setClassName(className);
			modelParameter.setPackageName(packageName);
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
		}
		
//		List<String> arguments = command.getParameters();
//		Iterator<String> iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			String parameterType = NameUtil.capName(argument);
//			//if (!parameterType.endsWith("Message"))
//			//	parameterType += "Message";
//			String parameterName = NameUtil.uncapName(parameterType);
//			Element parameterElement = context.getElementByName(parameterType);
//			if (parameterElement == null) {
//				parameterType += "Message";
//				parameterElement = context.getElementByName(parameterType);
//				parameterName = NameUtil.uncapName(parameterType);
//			}
//			
//			String className = TypeUtil.getClassName(parameterElement.getType());
//			String packageName = TypeUtil.getPackageName(parameterElement.getType());
//			ModelParameter modelParameter = new ModelParameter();
//			modelParameter.setName(parameterName);
//			modelParameter.setClassName(className);
//			modelParameter.setPackageName(packageName);
//			modelOperation.addParameter(modelParameter);
//			modelClass.addImportedClass(modelParameter);
//		}

//		String serviceName = invoker.getService();
//		String serviceNameCapped = NameUtil.capName(invoker.getService());
//		String packageName = getPackageNameFromTargetName(invoker.getTarget());
//		String parameters = generateCallerSideNameList(command.getParameters());
//		packageName = getPackageNameFromTargetName(invoker.getTarget());
//		modelClass.addImportedClass(packageName+"."+serviceNameCapped+"Client");
		
//		if (parameterType.endsWith("Message"))
//			parameterType = parameterType.substring(0, parameterType.length()-7);
		
		String packageName = getPackageNameFromTargetName(actor.getTarget());
		//modelClass.addImportedClass(packageName+"."+serviceName+"Client");
		String parameterList = generateCallerSideParameterList2(modelOperation.getParameters());
		
		Buf buf = new Buf();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			buf.putLine(indent, serviceName+" client = getClient("+serviceName+".ID);");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
			
		case POJO:	
			buf.putLine(indent, "ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
			buf.putLine(indent, serviceName+"Client client = proxyLocator.get(\""+serviceName+"\");");
			buf.putLine(indent, "client.setCorrelationId(getCorrelationId());");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
		}
		
		//String generatedSource = CodeUtil.createMethodSource(buf.get());
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public String generateSourceForThrowCommand(int indent, Operation operation, Command command) throws Exception {
		Buf buf = new Buf();
		return buf.get();
	}

	
	
	public Operation createReferencedOperation(String operationName, List<Parameter> arguments, Collection<Result> results) throws Exception {
		Operation referencedOperation = new Operation();
		referencedOperation.setName(operationName);
		
		Iterator<Parameter> argumentIterator = arguments.iterator();
		while (argumentIterator.hasNext()) {
			Parameter parameter1 = argumentIterator.next();
			String parameterName = parameter1.getName();
			ModelVariable localVariable = modelClass.getLocalVariable(parameterName);
//			if (localVariable == null)
//				System.out.println();
			String parameterType = localVariable.getType();
			String parameterConstruct = localVariable.getConstruct();
			
			Parameter parameter = new Parameter();
			parameter.setName(parameterName);
			parameter.setType(parameterType);
			parameter.setConstruct(parameterConstruct);
			referencedOperation.getParameters().add(parameter);
		}

		referencedOperation.addToResults(results);
		return referencedOperation;
	}

	public String getParameterName(String argumentReference) {
		String parameterName = NameUtil.getLastSegmentFromPackageName(argumentReference);
		parameterName = NameUtil.uncapName(parameterName);
		if (parameterName.startsWith("get"))
			parameterName = parameterName.substring(3);
		if (parameterName.endsWith("()"))
			parameterName = parameterName.substring(0, parameterName.length()-2);
		parameterName = NameUtil.uncapName(parameterName);
		return parameterName;
	}

	public ModelOperation createCalledOperation(Command command, List<Parameter> arguments, int indent) throws Exception {
		Interactor actor = (Interactor) command.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(command.getText());
		String commandTextUncapped = NameUtil.uncapName(command.getText());
		String callbackOperationName = "process" + commandTextCapped;
		String localOperationName = "process" + commandTextCapped;
		String serviceName = commandTextCapped;
		String targetName = actor.getTarget();
		
		//if (command.getName() == CommandName.CALL)
			
//		if (commandTextCapped.equalsIgnoreCase("purchasebooks"))
//			System.out.println();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(callbackOperationName);
		modelOperation.setResultType("void");

		//TODO RESOLVE THIS OUT SOON
		if (targetName.endsWith("Group"))
			targetName = targetName.replace("Group", "");
		
		// Lookup the actual method and get the parameters from it
		List<Parameter> parameters = getParametersFromOperation(commandTextUncapped);
//		if (parameters == null)
//			System.out.println();

		Iterator<Parameter> argumentIterator = arguments.iterator();
		while (argumentIterator.hasNext()) {
			Parameter parameter = argumentIterator.next();
			String argument = parameter.getName();
			String argumentCapped = NameUtil.capName(argument);
			
			ModelVariable localVariable = modelClass.getLocalVariable(argument);
			String parameterType = localVariable.getType();
			String parameterConstruct = localVariable.getConstruct();
			String messageName = NameUtil.uncapName(localVariable.getOwner());
			String messageType = NameUtil.capName(messageName);
			String parameterName = argument;
			
			String className = TypeUtil.getClassName(parameterType);
			String packageName = TypeUtil.getPackageName(parameterType);
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(parameterName);
			modelParameter.setClassName(className);
			modelParameter.setPackageName(packageName);
			modelParameter.setConstruct(parameterConstruct);
			modelOperation.addParameter(modelParameter);
			modelClass.addImportedClass(modelParameter);
			if (parameterConstruct.equals("list"))
				modelClass.addImportedClass("java.util.List");
			else if (parameterConstruct.equals("set"))
				modelClass.addImportedClass("java.util.Set");
			else if (parameterConstruct.equals("map"))
				modelClass.addImportedClass("java.util.Map");
			
//			ModelVariable localVariable = modelClass.getLocalVariable(argument);
//			String parameterType = localVariable.getType();
//			String parameterConstruct = localVariable.getConstruct();
//			
//			String className = TypeUtil.getClassName(parameterType);
//			String packageName = TypeUtil.getPackageName(parameterType);
//			ModelParameter modelParameter = new ModelParameter();
//			modelParameter.setName(argument);
//			modelParameter.setClassName(className);
//			modelParameter.setPackageName(packageName);
//			modelParameter.setConstruct(parameterConstruct);
//			modelOperation.addParameter(modelParameter);
//			modelClass.addImportedClass(modelParameter);
//			if (parameterConstruct.equals("list"))
//				modelClass.addImportedClass("java.util.List");
//			else if (parameterConstruct.equals("set"))
//				modelClass.addImportedClass("java.util.Set");
//			else if (parameterConstruct.equals("map"))
//				modelClass.addImportedClass("java.util.Map");
		}

		Buf buf = new Buf();
		Buf argumentString = new Buf();
		if (parameters != null) {
			Iterator<Parameter> iterator = parameters.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Parameter parameter = iterator.next();
				String parameterType = parameter.getType();
				String parameterName = parameter.getName();
				String className = TypeUtil.getClassName(parameterType);
				buf.putLine(indent, className+" "+parameterName+" = new "+className+"();");
				
				Element parameterElement = context.getElementByName(parameterName);
				argumentIterator = arguments.iterator();
				while (argumentIterator.hasNext()) {
					Parameter parameter2 = argumentIterator.next();
					String argument = parameter2.getName();
					String argumentCapped = NameUtil.capName(argument);
				
					Field field = ElementUtil.getField(parameterElement, argument);
					if (field != null) {
						buf.putLine(indent+2, parameterName+".set"+argumentCapped+"("+argument+");");
					}
				}
	
				if (i > 0)
					argumentString.put(", ");
				argumentString.put(parameterName);
			}
		}
		
//		iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			ModelVariable localVariable = modelClass.getLocalVariable(argument);
//			String messageName = NameUtil.uncapName(localVariable.getOwner());
//			String messageType = NameUtil.capName(messageName);
//			buf.putLine(indent+2, messageType+" "+messageName+" = new "+messageType+"();");
//		}
		
//		iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			String parameterType = parameter.getType();
//			String parameterName = parameter.getName();
//			String messageType = NameUtil.capName(messageName);
//			buf.putLine(indent+2, parameterName+".set"+argumentCapped+"("+argument+");");
//			if (i > 0)
//				argumentString.put(", ");
//			argumentString.put(messageName);
//		}
		
//		Iterator<String> argumentIterator = arguments.iterator();
//		for (int i=0; argumentIterator.hasNext(); i++) {
//			String argument = argumentIterator.next();
//			String argumentCapped = NameUtil.capName(argument);
//			
//			
//			ModelVariable localVariable = modelClass.getLocalVariable(argument);
//			String messageName = NameUtil.uncapName(localVariable.getOwner());
//			buf.putLine(indent+2, messageName+".set"+argumentCapped+"("+argument+");");
//			if (i > 0)
//				argumentString.put(", ");
//			argumentString.put(messageName);
//		}
		
		buf.putLine(indent, localOperationName+"("+argumentString.get()+");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	public List<Parameter> getParametersFromOperation(String operationName) {
		Operation operation = getOperationByName(operationName);
		if (operation != null) {
			return operation.getParameters();
		}
		return null;
	}

	public Operation getOperationByName(String operationName) {
		List<Operation> operations = context.getProcess().getOperations();
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (operation.getName().equalsIgnoreCase(operationName)) {
				return operation;
			}
		}
		return null;
	}
	
	
	public String generateCallerSideNameList(List<String> parameters) {
		Buf buf = new Buf();
		Iterator<String> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			if (i > 0)
				buf.put(", ");
			String parameter = iterator.next();
			parameter = NameUtil.uncapName(parameter);
			buf.put(parameter);
		}
		return buf.get();
	}
	
	public String generateCallerSideParameterList(List<Parameter> parameters) {
		Buf buf = new Buf();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			if (i > 0)
				buf.put(", ");
			Parameter parameter = iterator.next();
			String type = parameter.getType();
			String className = TypeUtil.getClassName(type);
			buf.put(NameUtil.uncapName(className));
			modelClass.addImportedClass(type);
		}
		return buf.get();
	}

	public String generateCallerSideParameterList2(List<ModelParameter> parameters) {
		Buf buf = new Buf();
		Iterator<ModelParameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			if (i > 0)
				buf.put(", ");
			ModelParameter parameter = iterator.next();
			String packageName = parameter.getPackageName();
			String className = parameter.getClassName();
			buf.put(NameUtil.uncapName(className));
			modelClass.addImportedClass(packageName+"."+className);
		}
		return buf.get();
	}
	
	public ModelOperation createCalledOperation(Command command, int indent) throws Exception {
		Interactor actor = (Interactor) command.getActor();
		//TODO check access here based on role/channel/role
		//String arguments = generateCallerSideArgumentString(command.getParameters());
		String commandTextCapped = NameUtil.capName(command.getText());
		String commandTextUncapped = NameUtil.uncapName(command.getText());
		String operationName = "send" + commandTextCapped;
		String clientOperationName = commandTextUncapped;
		String serviceName = commandTextCapped;
		String targetName = actor.getTarget();
		
		//String messageType = serviceName;
		//if (!messageType.endsWith("Message"))
		//	messageType += "Message";
		//String messageName = NameUtil.uncapName(messageType);
		//List<String> arguments = command.getParameters();

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName(operationName);
		modelOperation.setResultType("void");

		//TODO RESOLVE THIS OUT SOON
		if (targetName.endsWith("Group"))
			targetName = targetName.replace("Group", "");

//		Iterator<String> iterator = arguments.iterator();
//		while (iterator.hasNext()) {
//			String argument = iterator.next();
//			String argumentCapped = NameUtil.capName(argument);
//			
//			ModelVariable localVariable = modelClass.getLocalVariable(argument);
//			String parameterType = localVariable.getType();
//			String parameterConstruct = localVariable.getConstruct();
//			String messageName = NameUtil.uncapName(localVariable.getOwner());
//			String messageType = NameUtil.capName(messageName);
//
//			String messageType = parameter.getName();
//			String messageName = NameUtil.uncapName(messageType);
//			Element parameterElement = context.getElementByName(messageType);
//			String className = TypeUtil.getClassName(parameterElement.getType());
//			String packageName = TypeUtil.getPackageName(parameterElement.getType());
//			ModelParameter modelParameter = new ModelParameter();
//			modelParameter.setName(messageName);
//			modelParameter.setClassName(className);
//			modelParameter.setPackageName(packageName);
//			modelOperation.addParameter(modelParameter);
//			modelClass.addImportedClass(modelParameter);
//		}
		
		String packageName = getPackageNameFromTargetName(actor.getTarget());
		//modelClass.addImportedClass(packageName+"."+serviceName+"Client");
		String parameterList = generateCallerSideParameterList2(modelOperation.getParameters());
		
		Buf buf = new Buf();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			buf.putLine(indent, serviceName+" client = getClient("+serviceName+".ID);");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
			
		case POJO:	
			buf.putLine(indent, "ProxyLocator proxyLocator = BeanContext.get(\"org.aries.proxyLocator\");");
			buf.putLine(indent, serviceName+"Client client = proxyLocator.get(\""+serviceName+"\");");
			buf.putLine(indent, "client.setCorrelationId(getCorrelationId());");
			buf.putLine(indent, "client."+clientOperationName+"("+parameterList+");");
			break;
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public List<ModelOperation> createOperationsForOptionChecks(ModelClass modelClass, Operation operation) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Command> commands = operation.getCommands();
		Iterator<Command> iterator2 = commands.iterator();
		while (iterator2.hasNext()) {
			Command command = iterator2.next();
			if (command.getName() == CommandName.OPTION) {
				ModelOperation modelOperation = createOperationForOptionCommand(modelClass, operation);
				modelOperations.add(modelOperation);
				optionCount++;
			}
		}
		return modelOperations;
	}

	public ModelOperation createOperationForOptionCommand(ModelClass modelClass, Operation operation) throws Exception {
		String parameterName = operation.getParameters().get(0).getName();
		if (parameterName.endsWith("Message"))
			parameterName = parameterName.substring(0, parameterName.length()-7);
		String operationName = "get"+NameUtil.capName(parameterName);
		//String operationNameCapped = NameUtil.uncapName(operation.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName(operationName+"Option"+optionCount);
		modelOperation.setResultType("boolean");
		Buf buf = new Buf();
		String serviceName = NameUtil.capName(context.getApplication().getName());
		buf.putLine2("boolean result = "+serviceName+"ProcessOptions."+operationName+"Option();");
		buf.putLine2("return result;");
		//String generatedSource = CodeUtil.createMethodSource(buf.get());
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}



	public ModelReference createReference_UnitManager(Unit unit, String namespace) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "Manager";
		String beanName = NameUtil.uncapName(className);

		ModelReference reference = new ModelReference();
		reference.getAnnotations().add(AnnotationUtil.createInjectAnnotation());
		reference.setModifiers(Modifier.PROTECTED);
		reference.setName(beanName);
		reference.setPackageName(packageName);
		reference.setClassName(className);
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		modelClass.addImportedClass(packageName+"."+className);
		return reference;
	}
	
	public ModelReference createReference_CacheManager(Cache cache, String namespace) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "Manager";
		String beanName = NameUtil.uncapName(className);

		ModelReference reference = new ModelReference();
		reference.getAnnotations().add(AnnotationUtil.createInjectAnnotation());
		reference.setModifiers(Modifier.PROTECTED);
		reference.setName(beanName);
		reference.setPackageName(packageName);
		reference.setClassName(className);
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		modelClass.addImportedClass(packageName+"."+className);
		return reference;
	}

	public List<ModelReference> createReferences_ObjectFactory() throws Exception {
		return createReferences_ObjectFactory(false);
	}
	
	//TODO neeed to loop through:
	//modelOperationFactory.getReferenceStack()
	public List<ModelReference> createReferences_ObjectFactory(boolean isStatic) throws Exception {
		List<ModelReference> modelReferences = new ArrayList<ModelReference>();
		Set<Module> modules = ProjectUtil.getProjectModules(context.getProject(), ModuleType.MODEL);
		Iterator<Module> moduleIterator = modules.iterator();
		while (moduleIterator.hasNext()) {
			Module projectModule = moduleIterator.next();
			String namespaceUri = projectModule.getNamespace();
			//String localPart = NameUtil.getLocalPartFromNamespace(namespaceUri);
			String packageName = ProjectLevelHelper.getPackageName(namespaceUri);
			modelReferences.add(createReference_ObjectFactory(packageName, isStatic));
		}
		return modelReferences;
	}

	public ModelReference createReference_ObjectFactory(String packageName) throws Exception {
		return createReference_ObjectFactory(packageName, false);
	}
	
	public ModelReference createReference_ObjectFactory(String packageName, boolean isStatic) throws Exception {
		String factoryPrefix = NameUtil.getSimpleName(packageName);
		ModelReference reference = new ModelReference();
		reference.setModifiers(getModifiers(isStatic));
		reference.setFullyQualified(true);
		reference.setPackageName(packageName);
		reference.setClassName(packageName+".ObjectFactory");
		reference.setName(factoryPrefix+"ObjectFactory");
		//attribute.setValue("new "+className+"();");
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		return reference;
	}
	
	public String getTypeFromXSDType(String xsdType) {
		String prefix = NameUtil.getPrefixFromXSDType(xsdType);
		String localName = NameUtil.getLocalNameFromXSDType(xsdType);
		Namespace namespace = context.getNamespaceByPrefix(prefix);
		Assert.notNull(namespace, "Namespace not found: "+prefix);
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace.getUri(), localName);
		return typeName;
	}
	
	public String getFieldOwnerFromParameters(List<Parameter> parameters, String fieldName) {
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.getElementByType(parameter.getType());
			if (element != null) {
				Field field = ElementUtil.getField(element, fieldName);
				if (field != null) {
					return parameter.getName();
				}
			}
		}
		return null;
	}

	public Field getFieldFromParameters(List<Parameter> parameters, String fieldName) {
		Iterator<Parameter> parameterIterator = parameters.iterator();
		while (parameterIterator.hasNext()) {
			Parameter parameter = parameterIterator.next();
			Element element = context.getElementByType(parameter.getType());
			if (element != null) {
				Field field = ElementUtil.getField(element, fieldName);
				if (field != null) {
					return field;
				}
			}
		}
		return null;
	}
	

	public String getPackageNameFromTargetName(String targetName) {
		Collection<Application> applications = ProjectUtil.getApplications(context.getProject());
		String packageName = getPackageNameFromTargetName(targetName, applications);
		if (packageName == null)
			packageName = getPackageNameFromTargetGroupName(targetName);
		return packageName;
	}
	
	public String getPackageNameFromTargetName(String targetName, Collection<Application> applications) {
		//first look in set of Applications
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			if (application.getName().equalsIgnoreCase(targetName)) {
				String packageName = ProjectLevelHelper.getPackageName(application.getNamespace());
				return packageName;
			}
		}
		return null;
	}
		
	public String getPackageNameFromTargetGroupName(String targetName) {
		//next look in set of Groups
		List<Group> groups = ProjectUtil.getGroups(context.getProject());
		Iterator<Group> groupIterator = groups.iterator();
		while (groupIterator.hasNext()) {
			Group group = groupIterator.next();
			if (group.getName().equalsIgnoreCase(targetName)) {
				targetName = group.getParticipantName();
				String packageName = getPackageNameFromTargetName(targetName, group.getApplications());
				return packageName;
			}
		}
		return null;
	}

	public String createSource_UndoCommands(ModelClass modelClass, Service service, Operation operation) {
		Buf buf = new Buf();
		buf.put(createSource_UndoCommands_FromCommands(modelClass, service, operation.getCommands()));
		Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(operation.getCommands(), InvokeCommand.class);
		Iterator<InvokeCommand> iterator2 = invokeCommands.iterator();
		while (iterator2.hasNext()) {
			InvokeCommand invokeCommand = iterator2.next();
			buf.put(createSource_UndoCommands_FromCommands(modelClass, service, invokeCommand.getCommands()));
			buf.put(createSource_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getResponseBlocks()));
			buf.put(createSource_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getExceptionBlocks()));
			buf.put(createSource_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getTimeoutBlocks()));
		}
		return buf.get();
	}

	public <T extends AbstractBlock> String createSource_UndoCommands_FromCommandBlocks(ModelClass modelClass, Service service, List<T> commandBlocks) {
		Buf buf = new Buf();
		Iterator<T> iterator = commandBlocks.iterator();
		while (iterator.hasNext()) {
			T commandBlock = iterator.next();
			buf.put(createSource_UndoCommands_FromCommands(modelClass, service, commandBlock.getCommands()));
		}
		return buf.get();
	}

	public String createSource_UndoCommands_FromCommands(ModelClass modelClass, Service service, List<Command> commands) {
		Buf buf = new Buf();
		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator = expressionStatements.iterator();
		while (iterator.hasNext()) {
			ExpressionStatement expressionStatement = iterator.next();
			String targetName = expressionStatement.getTargetName();
			String methodName = expressionStatement.getSelector();
			String targetNameCapped = NameUtil.capName(targetName);
			
			Cache cacheUnit = ServiceUtil.getCacheUnitReference(service, targetName);
			if (cacheUnit != null && methodName.startsWith("add") || methodName.startsWith("remove")) {
				//loggedStateUndoOperations.add(createOperation_CacheUnit_UndoAction(service, cacheUnit, expressionStatement));
				buf.putLine2("undo_"+targetNameCapped+"_"+methodName+"(correlationId);");
			}
		}
		return buf.get();
	}
	
	public Set<ModelOperation> createOperation_UndoCommands(ModelClass modelClass, Service service, Operation operation) {
		Set<ModelOperation> modelOperations = new LinkedHashSet<ModelOperation>();
		modelOperations.addAll(createOperation_UndoCommands(modelClass, service, operation.getCommands()));
		Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(operation.getCommands(), InvokeCommand.class);
		Iterator<InvokeCommand> iterator2 = invokeCommands.iterator();
		while (iterator2.hasNext()) {
			InvokeCommand invokeCommand = iterator2.next();
			modelOperations.addAll(createOperation_UndoCommands(modelClass, service, invokeCommand.getCommands()));
			modelOperations.addAll(createOperation_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getResponseBlocks()));
			modelOperations.addAll(createOperation_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getExceptionBlocks()));
			modelOperations.addAll(createOperation_UndoCommands_FromCommandBlocks(modelClass, service, invokeCommand.getTimeoutBlocks()));
		}
		return modelOperations;
	}

	public <T extends AbstractBlock> Set<ModelOperation> createOperation_UndoCommands_FromCommandBlocks(ModelClass modelClass, Service service, List<T> commandBlocks) {
		Set<ModelOperation> modelOperations = new LinkedHashSet<ModelOperation>();
		Iterator<T> iterator = commandBlocks.iterator();
		while (iterator.hasNext()) {
			T commandBlock = iterator.next();
			modelOperations.addAll(createOperation_UndoCommands(modelClass, service, commandBlock.getCommands()));
		}
		return modelOperations;
	}

	public Set<ModelOperation> createOperation_UndoCommands(ModelClass modelClass, Service service, List<Command> commands) {
		Set<ModelOperation> modelOperations = new LinkedHashSet<ModelOperation>();

		Collection<ExpressionStatement> expressionStatements = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator = expressionStatements.iterator();
		while (iterator.hasNext()) {
			ExpressionStatement expressionStatement = iterator.next();
			String targetName = expressionStatement.getTargetName();
			String methodName = expressionStatement.getSelector();
			
			Cache cacheUnit = ServiceUtil.getCacheUnitReference(service, targetName);
			if (cacheUnit != null && methodName.startsWith("add") || methodName.startsWith("remove")) {
				modelOperations.add(createOperation_CacheUnit_UndoAction(service, cacheUnit, expressionStatement));
			}
		}
		return modelOperations;
	}
	
	public ModelOperation createOperation_CacheUnit_UndoAction(Service service, Cache cacheUnit, ExpressionStatement expressionStatement) {
		String targetName = expressionStatement.getTargetName();
		String methodName = expressionStatement.getSelector();
		String targetNameCapped = NameUtil.capName(targetName);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("undo_"+targetNameCapped+"_"+methodName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		
		ModelParameter modelParameter = createParameter("String", "correlationId");
		modelOperation.addParameter(modelParameter);

		String sourceCode = getSource_actionStateRestoration(service, cacheUnit, expressionStatement, "undo");
		modelOperation.addInitialSource(sourceCode);
		return modelOperation;
	}

	public String getSource_actionStateRestoration(Service service, Cache cacheUnit, ExpressionStatement expressionStatement, String operationType) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String messageType = ServiceLayerHelper.getMessageType(serviceOperation);
		//String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
		//String messageBeanName = ServiceLayerHelper.getMessageBeanName(serviceOperation);
		Element messageElement = context.getElementByType(messageType);

		String targetName = expressionStatement.getTargetName();
		String methodName = expressionStatement.getSelector();
		
		String correctiveMethodName = null;
		if (methodName.startsWith("addTo"))
			correctiveMethodName = methodName.replace("addTo", "removeFrom");
		if (methodName.startsWith("removeFrom"))
			correctiveMethodName = methodName.replace("removeFrom", "addTo");
		if (methodName.startsWith("removeAll"))
			correctiveMethodName = methodName.replace("removeAll", "addTo");

		Buf buf = new Buf();
		Process process = context.getProcess();
		String processContextBeanName = ServiceLayerHelper.getProcessContextInstanceName(process);
		buf.putLine2("ActionState actionState = "+processContextBeanName+".getActionState(correlationId, \""+methodName+"\");");
		buf.putLine2("if (actionState != null) {");

		StringBuffer argumentString = new StringBuffer();
		List<Parameter> parameters = expressionStatement.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			String structuredType = TypeUtil.getStructuredType(parameter);
			String structuredParam = TypeUtil.getStructuredParam(parameter);
			buf.putLine3(structuredType+" "+structuredParam+" = actionState.getElement(\""+structuredParam+"\");");
			if (i > 0)
				argumentString.append(", ");
			argumentString.append(structuredParam);
		}
		
		buf.putLine3(targetName+"Manager."+correctiveMethodName+"("+argumentString+");");
		
//		StringBuffer argumentString = new StringBuffer();
//		List<Reference> references = ElementUtil.getReferences(messageElement);
//		Iterator<Reference> iterator = references.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			Reference reference = (Reference) iterator.next();
//			String structuredType = TypeUtil.getStructuredType(reference);
//			String structuredName = TypeUtil.getStructuredName(reference);
//			buf.putLine3(structuredType+" "+structuredName+" = actionState.getElement(\""+structuredName+"\");");
//			if (i > 0)
//				argumentString.append(", ");
//			argumentString.append(structuredName);
//		}
		
		//buf.putLine2("	send_"+serviceInterfaceName+"_request_"+operationType+"("+argumentString+");");
		buf.putLine2("}");
		return buf.get();
	}
	
	public String getSource_actionStateRestoration(Service service, String operationType) {
		String serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceInterfaceNameUncapped = NameUtil.uncapName(serviceInterfaceName);
		String serviceName = ServiceUtil.getExtendedDomainServiceName(service);

		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String messageType = ServiceLayerHelper.getMessageType(serviceOperation);
		//String messageClassName = ServiceLayerHelper.getMessageClassName(serviceOperation);
		//String messageBeanName = ServiceLayerHelper.getMessageBeanName(serviceOperation);
		Element messageElement = context.getElementByType(messageType);
		Process process = context.getProcess();
		
		Buf buf = new Buf();
		String processContextBeanName = ServiceLayerHelper.getProcessContextInstanceName(process);
		buf.putLine2("ActionState actionState = "+processContextBeanName+".getActionState(correlationId, \""+serviceInterfaceNameUncapped+"\");");
		buf.putLine2("if (actionState != null) {");

		if (operationType.equals("cancel")) {
			buf.putLine2("	send_"+serviceName+"_request_"+operationType+"();");
			
		} else if (operationType.equals("undo")) {
			StringBuffer argumentString = new StringBuffer();
			List<Reference> references = ElementUtil.getReferences(messageElement);
			Iterator<Reference> iterator = references.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Reference reference = (Reference) iterator.next();
				String structuredType = TypeUtil.getStructuredType(reference);
				String structuredParam = TypeUtil.getStructuredParam(reference);
				buf.putLine3(structuredType+" "+structuredParam+" = actionState.getElement(\""+structuredParam+"\");");
				if (i > 0)
					argumentString.append(", ");
				argumentString.append(structuredParam);
			}
			buf.putLine2("	send_"+serviceName+"_request_"+operationType+"("+argumentString+");");
		}
		
		buf.putLine2("}");
		return buf.get();
	}
	
	
	/*
	 * Parameter factory methods
	 * -------------------------
	 */
	
	public ModelParameter createParameter(Parameter parameter) {
		return createParameter(parameter.getType(), parameter.getName());
	}
	
	public ModelParameter createParameter(String type, String name) {
		return createParameter(type, name, "item");
	}

	public ModelParameter createParameter(String type, String name, String construct) {
		if (type.contains("{"))
			return createParameter(TypeUtil.getPackageName(type), TypeUtil.getClassName(type), name, construct);
		return createParameter(NameUtil.getPackageName(type), NameUtil.getClassName(type), name, construct);
	}

	public ModelParameter createParameter(String packageName, String className, String name, String construct) {
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName(name);
		modelParameter.setPackageName(packageName);
		modelParameter.setClassName(className);
		//if (StringUtils.isEmpty(packageName))
		//	modelParameter.setClassName(className);
		//else modelParameter.setClassName(packageName+"."+className);
		modelParameter.setConstruct(construct);
		if (construct.equalsIgnoreCase("map")) {
			modelParameter.setKeyPackageName("java.lang");
			modelParameter.setKeyClassName("Object");
		}
//		if (construct == null)
//			System.out.println();
//		if (construct.startsWith("List<"))
//			System.out.println();
//		if (construct.startsWith("Set<"))
//			System.out.println();
//		if (construct.startsWith("Map<"))
//			System.out.println();
		return modelParameter;
	}

//	public ModelParameter createParameter(Parameter parameter) {
//		ModelParameter modelParameter = new ModelParameter();
//		modelParameter.setName(parameter.getName());
//		modelParameter.setConstruct(parameter.getConstruct());
//		modelParameter.setPackageName(TypeUtil.getPackageName(parameter.getType()));
//		modelParameter.setClassName(TypeUtil.getClassName(parameter.getType()));
//		modelParameter.setKeyPackageName(TypeUtil.getPackageName(parameter.getKey()));
//		modelParameter.setKeyClassName(TypeUtil.getClassName(parameter.getKey()));
//		return modelParameter;
//	}

	protected int getModifiers(boolean isStatic) {
		if (isStatic)
			return Modifier.PUBLIC + Modifier.STATIC;
		return Modifier.PRIVATE;
	}

	
	public String getExtendedDomainServiceName(Interactor interactor) {
		String remoteApplicationName = interactor.getName();
		String remoteServiceName = interactor.getService();
		String domainServiceName = getExtendedDomainServiceName(remoteApplicationName, remoteServiceName);
		return domainServiceName;
	}

	public String getExtendedDomainServiceName(String applicationName, String serviceName) {
		List<Project> projects = context.getProjectList();
		Application remoteApplication = ProjectUtil.getApplicationByName(projects, applicationName);
		//Service remoteService = findServiceFromGroupOrApplication(remoteApplicationName, remoteServiceName);
		//Assert.notNull(remoteService, "Remote service not found: "+remoteApplicationName+"["+serviceName+"]");
		//modelClass.addImportedClass(remoteService.getPackageName()+"."+remoteService.getInterfaceName());
		applicationName = remoteApplication.getName(); 

		//String remoteServiceNameCapped = NameUtil.capName(remoteServiceName);
		Service remoteService = context.getServiceByName(applicationName, serviceName);
		serviceName = ServiceUtil.getExtendedDomainServiceName(remoteService);
		return serviceName;
	}

}

