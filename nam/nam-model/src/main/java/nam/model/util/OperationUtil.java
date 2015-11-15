package nam.model.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nam.model.Command;
import nam.model.Fault;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Timeout;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ListenCommand;
import nam.model.statement.PostCommand;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.TypeMap;
import org.aries.util.Validator;


public class OperationUtil extends BaseUtil {
	
	public static String getKey(Operation operation) {
		return operation.toString();
	}

	public static String getLabel(Operation operation) {
		return operation.getName();
	}

	public static boolean getLabel(Collection<Operation> operationList) {
		if (operationList == null  || operationList.size() == 0)
			return true;
		Iterator<Operation> iterator = operationList.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (!isEmpty(operation))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Operation operation) {
		if (operation == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(operation.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Operation> operationList) {
		if (operationList == null  || operationList.size() == 0)
			return true;
		Iterator<Operation> iterator = operationList.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			if (!isEmpty(operation))
				return false;
		}
		return true;
	}
	
	public static String toString(Operation operation) {
		if (isEmpty(operation))
			return "Operation: [uninitialized] "+operation.toString();
		String text = operation.toString();
		return text;
	}
	
	public static String toString(Collection<Operation> operationList) {
		if (isEmpty(operationList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Operation> iterator = operationList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Operation operation = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(operation);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Operation create() {
		Operation operation = new Operation();
		initialize(operation);
		return operation;
	}
	
	public static void initialize(Operation operation) {
		//nothing for now
	}
	
	public static boolean validate(Operation operation) {
		if (operation == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(operation.getName(), "\"Name\" must be specified");
		CommandUtil.validate(operation.getCommands());
		FaultUtil.validate(operation.getFaults());
		ParameterUtil.validate(operation.getParameters());
		ResultUtil.validate(operation.getResults());
		TimeoutUtil.validate(operation.getTimeouts());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Operation> operationList) {
		Validator validator = Validator.getValidator();
		Iterator<Operation> iterator = operationList.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			//TODO break or accumulate?
			validate(operation);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Operation> operationList) {
		Collections.sort(operationList, createOperationComparator());
	}
	
	public static Collection<Operation> sortRecords(Collection<Operation> operationCollection) {
		List<Operation> list = new ArrayList<Operation>(operationCollection);
		Collections.sort(list, createOperationComparator());
		return list;
	}
	
	public static Comparator<Operation> createOperationComparator() {
		return new Comparator<Operation>() {
			public int compare(Operation operation1, Operation operation2) {
				Object key1 = getKey(operation1);
				Object key2 = getKey(operation2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Operation clone(Operation operation) {
		if (operation == null)
			return null;
		Operation clone = create();
		clone.setName(ObjectUtil.clone(operation.getName()));
		clone.setRole(ObjectUtil.clone(operation.getRole()));
		clone.setParameters(ParameterUtil.clone(operation.getParameters()));
		clone.setResults(ResultUtil.clone(operation.getResults()));
		clone.setTimeouts(TimeoutUtil.clone(operation.getTimeouts()));
		clone.setFaults(FaultUtil.clone(operation.getFaults()));
		clone.setCommands(CommandUtil.clone(operation.getCommands()));
		return clone;
	}
	
	public static List<Operation> clone(List<Operation> operationList) {
		if (operationList == null)
			return null;
		List<Operation> newList = new ArrayList<Operation>();
		Iterator<Operation> iterator = operationList.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			Operation clone = clone(operation);
			newList.add(clone);
		}
		return newList;
	}



	public static Collection<Command> getCommands(Operation operation) {
		return getCommands(operation.getCommands());
	}
	
	public static Collection<Command> getCommands(List<Command> topLevelCommands) {
		Set<Command> commands = new HashSet<Command>();
		Iterator<Command> iterator = topLevelCommands.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			commands.add(command);
			commands.addAll(command.getCommands());
		}
		return commands;
	}
	
	public static void addCommand(Operation operation, Command command) {
		if (!operation.getCommands().contains(command))
			operation.getCommands().add(command);
	}
	
	public static boolean removeCommand(Operation operation, Command command) {
		if (operation.getCommands().contains(command))
			return operation.getCommands().remove(command);
		return false;
	}
	
	public static Set<String> getIncomingEvents(Operation operation) {
		Set<String> events = new TreeSet<String>();
		List<Command> commands = operation.getCommands();
		Collection<ListenCommand> listenCommands = CommandUtil.getCommands(commands, ListenCommand.class);
		Iterator<ListenCommand> iterator = listenCommands.iterator();
		while (iterator.hasNext()) {
			ListenCommand listenCommand = iterator.next();
			String eventName = listenCommand.getMessageName();
			events.add(eventName);
		}
		return events;
	}
	
	public static Set<String> getOutgoingEvents(Operation operation) {
		Set<String> events = new HashSet<String>();
		List<Command> commands = operation.getCommands();
		Collection<PostCommand> postCommands = CommandUtil.getCommands(commands, PostCommand.class);
		Iterator<PostCommand> iterator = postCommands.iterator();
		while (iterator.hasNext()) {
			PostCommand postCommand = iterator.next();
			String eventName = postCommand.getEventName();
			events.add(eventName);
		}
		return events;
	}
	
	
	public static Operation createOperation(Method method) {
		Operation operation = new Operation();
		operation.setName(method.getName());

		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			Parameter parameter = ParameterUtil.createParameter(parameterType);
			String typeName = TypeMap.INSTANCE.getTypeName(parameterType);
			Assert.notNull(typeName, "ParameterType for method \""+method.getName()+"\" not found: "+parameterType.getCanonicalName());
			operation.getParameters().add(parameter);
		}
		
		Class<?> returnType = method.getReturnType();
		if (returnType != null) {
			Result result = ResultUtil.createResult(returnType);
			String typeName = result.getType();
			Assert.notNull(typeName, "ResultType for method \""+method.getName()+"\" not found: "+returnType.getCanonicalName());
			result.setType(typeName);
			operation.addToResults(result);
		}
		
		return operation;
	}
	
	public static String getUniqueOperationName(Operation operation) {
		String operationName = operation.getName();
		List<Parameter> parameters = operation.getParameters();
		if (parameters.size() == 1) {
			Parameter parameter = parameters.get(0);
			String structure = parameter.getConstruct();
			String name = parameter.getName();
			operationName = getUniqueOperationName(operationName, structure, name);
		}
		return operationName;
	}
	
	public static String getUniqueOperationName(String operationName, String structure, String name) {
//		if (operationName.startsWith("getMatch"))
//			System.out.println();
		if (name.endsWith("Id"))
			return operationName + "ById";
		if (name.endsWith("Ids"))
			return operationName + "ByIds";
		if (name.endsWith("Key"))
			return operationName + "ByKey";
		if (name.endsWith("Keys")) {
			if (operationName.endsWith("AsMap"))
				return operationName.replace("AsMap", "ByKeys");
			return operationName + "ByKeys";
		}
		if (name.endsWith("Criteria"))
			return operationName + "ByCriteria";
		
//		if (structure.equals("item"))
//			operationName += "AsItem";
		if (structure.equals("list") && !operationName.startsWith("getMatching") && !operationName.endsWith("AsMap"))
			operationName += "AsList";
		if (structure.equals("set") && !operationName.endsWith("AsMap"))
			operationName += "AsSet";
		if (structure.equals("map") && !operationName.endsWith("AsMap"))
			operationName += "AsMap";
		return operationName;
	}
	
	public static boolean equals(Operation operation1, Operation operation2) {
		Assert.notNull(operation1, "Operation1 must be specified");
		Assert.notNull(operation2, "Operation2 must be specified");
		Assert.notNull(operation1.getName(), "Operation1 name must be specified");
		Assert.notNull(operation2.getName(), "Operation2 name must be specified");
		if (!operation1.getName().equals(operation2.getName()))
			return false;
		if (!ParameterUtil.equals(operation1.getParameters(), operation2.getParameters()))
			return false;
		if (!FaultUtil.equals(operation1.getFaults(), operation2.getFaults()))
			return false;
		if (!ResultUtil.equals(operation1.getResults(), operation2.getResults()))
			return false;
		return true;
	}

	public static Parameter getParameter(Operation operation) {
		if (operation.getParameters().size() > 0)
			return operation.getParameters().get(0);
		return null;
	}

	public static Collection<Parameter> getParameters(Operation operation) {
		return operation.getParameters();
	}

	public static boolean containsParameter(Operation operation, Parameter parameter) {
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter existingParameter = iterator.next();
			if (ParameterUtil.equals(existingParameter, parameter))
				return true;
		}
		return false;
	}

	public static void addParameter(Operation operation, Parameter parameter) {
		if (!containsParameter(operation, parameter))
			operation.getParameters().add(parameter);
	}
	
	public static boolean removeParameter(Operation operation, Parameter parameter) {
		if (!containsParameter(operation, parameter))
			return operation.getParameters().remove(parameter);
		return false;
	}
	
	public static Collection<Result> getResults(Operation operation) {
		return operation.getResults();
	}

	public static boolean hasResult(Operation operation) {
		List<Result> results = operation.getResults();
		if (results.isEmpty())
			return false;
		Result result = results.get(0);
		if (result == null)
			return false;
		if (result.getType().equals("void"))
			return false;
		return true;
	}

	public static boolean containsResult(Operation operation, Result result) {
		List<Result> results = operation.getResults();
		Iterator<Result> iterator = results.iterator();
		while (iterator.hasNext()) {
			Result existingResult = iterator.next();
			if (ResultUtil.equals(existingResult, result))
				return true;
		}
		return false;
	}
	
	public static void addResult(Operation operation, Result result) {
		if (!containsResult(operation, result))
			operation.getResults().add(result);
	}
	
	public static boolean removeResult(Operation operation, Result result) {
		if (!containsResult(operation, result))
			return operation.getParameters().remove(result);
		return false;
	}
	
	public static Result getFirstResult(Operation operation) {
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		return result;
	}
	
	public static ListenCommand getListenCommand(Operation operation, String event) {
		return CommandUtil.getListenCommand(operation.getCommands(), event);
	}

	public static Set<String> getSuccessCallbackNames(Operation operation) {
		return CommandUtil.getSuccessCallbackNames(operation.getCommands());
	}
	
	public static Set<String> getErrorCallbackNames(Operation operation) {
		Set<String> callbackNames = new TreeSet<String>();
		Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(operation.getCommands(), InvokeCommand.class);
		Collection<ListenCommand> listenCommands = CommandUtil.getCommands(operation.getCommands(), ListenCommand.class);
		callbackNames.addAll(CommandUtil.getTimeoutCallbackNames(invokeCommands));
		callbackNames.addAll(CommandUtil.getTimeoutCallbackNames(listenCommands));
		callbackNames.addAll(CommandUtil.getExceptionCallbackNames(invokeCommands));
		callbackNames.addAll(CommandUtil.getExceptionCallbackNames(listenCommands));
		return callbackNames;
	}

	public static String getTargetTypeFromOperationName(String operationName) {
		if (operationName.contains("By")) {
			int indexOf = operationName.indexOf("By");
			operationName = operationName.substring(0, indexOf);
		}
		
		if (operationName.startsWith("getAll"))
			return operationName.substring(6);
		if (operationName.startsWith("getMatching"))
			return operationName.substring(11);
		if (operationName.startsWith("get"))
			return operationName.substring(3);
		if (operationName.startsWith("set"))
			return operationName.substring(3);
		if (operationName.startsWith("addTo"))
			return operationName.substring(5);
		if (operationName.startsWith("add"))
			return operationName.substring(3);
		if (operationName.startsWith("removeFrom"))
			return operationName.substring(10);
		if (operationName.startsWith("removeAll"))
			return operationName.substring(9);
		if (operationName.startsWith("remove"))
			return operationName.substring(6);
		
		//throw new RuntimeException("Unrecognized operation name: "+operationName);
		return null;
	}

	
	public static Collection<Fault> getFaults(Operation operation) {
		return operation.getFaults();
	}

	public static void addFault(Operation operation, Fault fault) {
		Collection<Fault> faults = operation.getFaults();
		if (!faults.contains(fault))
			faults.add(fault);
	}
	
	public static boolean removeFault(Operation operation, Fault fault) {
		Collection<Fault> faults = operation.getFaults();
		if (faults.contains(fault))
			return faults.remove(fault);
		return false;
	}
	

	public static Collection<Timeout> getTimeouts(Operation operation) {
		Collection<Timeout> timeoutList = operation.getTimeouts();
		return timeoutList;
	}

	public static void addTimeout(Operation operation, Timeout timeout) {
		Collection<Timeout> timeouts = operation.getTimeouts();
		if (!timeouts.contains(timeout))
			timeouts.add(timeout);
	}
	
	public static boolean removeTimeout(Operation operation, Timeout timeout) {
		Collection<Timeout> timeouts = operation.getTimeouts();
		if (timeouts.contains(timeout))
			return timeouts.remove(timeout);
		return false;
	}

}
