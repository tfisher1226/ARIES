package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nam.model.Command;
import nam.model.statement.AbstractBlock;
import nam.model.statement.BlockStatement;
import nam.model.statement.ExceptionBlock;
import nam.model.statement.ListenCommand;
import nam.model.statement.ReplyCommand;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class CommandUtil extends BaseUtil {
	
	public static String getKey(Command command) {
		return command.toString();
	}

	public static String getLabel(Command command) {
		return command.toString();
	}

	public static boolean getLabel(Collection<Command> commandList) {
		if (commandList == null  || commandList.size() == 0)
			return true;
		Iterator<Command> iterator = commandList.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (!isEmpty(command))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Command command) {
		if (command == null)
			return true;
		boolean status = false;
		status |= command.getName() != null;
		status |= StringUtils.isEmpty(command.getType());
		return status;
	}
	
	public static boolean isEmpty(Collection<Command> commandList) {
		if (commandList == null  || commandList.size() == 0)
			return true;
		Iterator<Command> iterator = commandList.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			if (!isEmpty(command))
				return false;
		}
		return true;
	}
	
	public static String toString(Command command) {
		if (isEmpty(command))
			return "Command: [uninitialized] "+command.toString();
		String text = command.toString();
		return text;
	}
	
	public static String toString(Collection<Command> commandList) {
		if (isEmpty(commandList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Command> iterator = commandList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Command command = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(command);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Command create() {
		Command command = new Command();
		initialize(command);
		return command;
	}
	
	public static void initialize(Command command) {
		//nothing for now
	}
	
	public static boolean validate(Command command) {
		if (command == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notNull(command.getName(), "\"Name\" must be specified");
		validator.notEmpty(command.getType(), "\"Type\" must be specified");
		AttributeUtil.validate(command.getAttributes());
		CommandUtil.validate(command.getCommands());
		ParameterUtil.validate(command.getParameters());
		ReferenceUtil.validate(command.getReferences());
		ResultUtil.validate(command.getResults());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Command> commandList) {
		Validator validator = Validator.getValidator();
		Iterator<Command> iterator = commandList.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			//TODO break or accumulate?
			validate(command);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Command> commandList) {
		Collections.sort(commandList, createCommandComparator());
	}
	
	public static Collection<Command> sortRecords(Collection<Command> commandCollection) {
		List<Command> list = new ArrayList<Command>(commandCollection);
		Collections.sort(list, createCommandComparator());
		return list;
	}
	
	public static Comparator<Command> createCommandComparator() {
		return new Comparator<Command>() {
			public int compare(Command command1, Command command2) {
				Object key1 = getKey(command1);
				Object key2 = getKey(command2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Command clone(Command command) {
		if (command == null)
			return null;
		Command clone = create();
		clone.setName(command.getName());
		clone.setType(ObjectUtil.clone(command.getType()));
		//TODO clone these somehow...
		clone.setNode(command.getNode());
		clone.setActor(command.getActor());
		//clone.setNode(ObjectUtil.clone(command.getNode()));
		//clone.setActor(ObjectUtil.clone(command.getActor()));
		clone.setTokens(ObjectUtil.clone(command.getTokens(), String.class));
		clone.setAttributes(AttributeUtil.clone(command.getAttributes()));
		clone.setReferences(ReferenceUtil.clone(command.getReferences()));
		clone.setParameters(ParameterUtil.clone(command.getParameters()));
		clone.setResults(ResultUtil.clone(command.getResults()));
		clone.setCommands(CommandUtil.clone(command.getCommands()));
		clone.setText(ObjectUtil.clone(command.getText()));
		clone.setIndent(ObjectUtil.clone(command.getIndent()));
		return clone;
	}
	
	public static List<Command> clone(List<Command> commandList) {
		if (commandList == null)
			return null;
		List<Command> newList = new ArrayList<Command>();
		Iterator<Command> iterator = commandList.iterator();
		while (iterator.hasNext()) {
			Command command = iterator.next();
			Command clone = clone(command);
			newList.add(clone);
		}
		return newList;
	}



	public static ListenCommand getListenCommand(Collection<Command> commands, String event) {
		Collection<ListenCommand> listenCommands = getCommands(commands, ListenCommand.class);
		Iterator<ListenCommand> iterator = listenCommands.iterator();
		while (iterator.hasNext()) {
			ListenCommand listenCommand = iterator.next();
			if (listenCommand.getMessageName().equalsIgnoreCase(event)) {
				return listenCommand;
			}
		}
		return null;
	}

	public static <T extends Command, X extends Command> Collection<X> getCommands(Collection<T> commands, Class<X> commandClass) {
		List<X> targetCommands = new ArrayList<X>();
		Iterator<T> iterator = commands.iterator();
		while (iterator.hasNext()) {
			T command = iterator.next();
			if (command.getClass().equals(commandClass)) {
				@SuppressWarnings("unchecked") X targetCommand = (X) command;
				targetCommands.add(targetCommand);
			}
		}
		iterator = commands.iterator();
		while (iterator.hasNext()) {
			T command = iterator.next();
			targetCommands.addAll(getCommands(command.getCommands(), commandClass));
		}
		return targetCommands;
	}
	
	public static <T extends Command> Set<String> getSuccessCallbackNames(T command) {
		return getCallbackNames(command.getCommands());
	}
	
	public static <T extends Command> Set<String> getSuccessCallbackNames(Collection<T> commands) {
		Set<String> callbackNames = new TreeSet<String>();
		Iterator<T> iterator = commands.iterator();
		while (iterator.hasNext()) {
			T command = iterator.next();
			callbackNames.addAll(getSuccessCallbackNames(command));
		}
		return callbackNames;
	}
	
	public static Set<String> getErrorCallbackNames(BlockStatement command) {
		Set<String> callbackNames = new TreeSet<String>();
		callbackNames.addAll(getTimeoutCallbackNames(command));
		callbackNames.addAll(getExceptionCallbackNames(command));
		return callbackNames;
	}
	
	public static <T extends Command> Set<String> getExceptionCallbackNames(Collection<T> commands) {
		Set<String> callbackNames = new TreeSet<String>();
		Iterator<T> iterator = commands.iterator();
		while (iterator.hasNext()) {
			T command = iterator.next();
			if (command instanceof BlockStatement) {
				BlockStatement blockStatement = (BlockStatement) command;
				callbackNames.addAll(getExceptionCallbackNames(blockStatement));
			}
		}
		return callbackNames;
	}
	
	public static Set<String> getExceptionCallbackNames(BlockStatement command) {
		Set<String> callbackNames = new TreeSet<String>();
		List<ExceptionBlock> exceptionBlocks = command.getExceptionBlocks();
		Iterator<ExceptionBlock> iterator = exceptionBlocks.iterator();
		while (iterator.hasNext()) {
			ExceptionBlock exceptionBlock = iterator.next();
			callbackNames.addAll(getCallbackNames(exceptionBlock.getCommands()));
		}
		return callbackNames;
	}

	public static <T extends Command> Set<String> getTimeoutCallbackNames(Collection<T> commands) {
		Set<String> callbackNames = new TreeSet<String>();
		Iterator<T> iterator = commands.iterator();
		while (iterator.hasNext()) {
			T command = iterator.next();
			if (command instanceof BlockStatement) {
				BlockStatement blockStatement = (BlockStatement) command;
				callbackNames.addAll(getTimeoutCallbackNames(blockStatement));
			}
		}
		return callbackNames;
	}
	
	public static Set<String> getTimeoutCallbackNames(BlockStatement command) {
		return getCallbackNamesFromBlocks(command.getTimeoutBlocks());
	}

	public static <T extends AbstractBlock> Set<String> getCallbackNamesFromBlocks(Collection<T> commandBlocks) {
		Set<String> callbackNames = new TreeSet<String>();
		Iterator<T> iterator = commandBlocks.iterator();
		while (iterator.hasNext()) {
			T commandBlock = iterator.next();
			callbackNames.addAll(getCallbackNames(commandBlock.getCommands()));
		}
		return callbackNames;
	}
	
	public static Set<String> getCallbackNamesFromBlock(AbstractBlock commandBlock) {
		return getCallbackNames(commandBlock.getCommands());
	}
	
	public static <T extends Command> Set<String> getCallbackNames(Collection<T> commands) {		
		Set<String> callbackNames = new TreeSet<String>();
		Collection<ReplyCommand> replyCommands = getCommands(commands, ReplyCommand.class);
		Iterator<ReplyCommand> iterator = replyCommands.iterator();
		while (iterator.hasNext()) {
			ReplyCommand replyCommand = (ReplyCommand) iterator.next();
			String callbackName = NameUtil.capName(replyCommand.getMessageName());
			callbackNames.add(callbackName);
		}
		return callbackNames;
	}
	
}
