package nam.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import nam.model.CommandName;


public class CommandNameUtil extends BaseUtil {
	
	public static final CommandName[] VALUES_ARRAY = new CommandName[] {
		CommandName.NEW,
		CommandName.CALL,
		CommandName.SEND,
		CommandName.INVOKE,
		CommandName.LISTEN,
		CommandName.RECEIVE,
		CommandName.REPLY,
		CommandName.POST,
		CommandName.THROW,
		CommandName.EXECUTE,
		CommandName.EXPRESSION,
		CommandName.OPTION,
		CommandName.BRANCH,
		CommandName.DONE
	};
	
	public static final List<CommandName> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static CommandName getCommandName(int ordinal) {
		if (ordinal == CommandName.NEW.ordinal())
			return CommandName.NEW;
		if (ordinal == CommandName.CALL.ordinal())
			return CommandName.CALL;
		if (ordinal == CommandName.SEND.ordinal())
			return CommandName.SEND;
		if (ordinal == CommandName.INVOKE.ordinal())
			return CommandName.INVOKE;
		if (ordinal == CommandName.LISTEN.ordinal())
			return CommandName.LISTEN;
		if (ordinal == CommandName.RECEIVE.ordinal())
			return CommandName.RECEIVE;
		if (ordinal == CommandName.REPLY.ordinal())
			return CommandName.REPLY;
		if (ordinal == CommandName.POST.ordinal())
			return CommandName.POST;
		if (ordinal == CommandName.THROW.ordinal())
			return CommandName.THROW;
		if (ordinal == CommandName.EXECUTE.ordinal())
			return CommandName.EXECUTE;
		if (ordinal == CommandName.EXPRESSION.ordinal())
			return CommandName.EXPRESSION;
		if (ordinal == CommandName.OPTION.ordinal())
			return CommandName.OPTION;
		if (ordinal == CommandName.BRANCH.ordinal())
			return CommandName.BRANCH;
		if (ordinal == CommandName.DONE.ordinal())
			return CommandName.DONE;
		return null;
	}
	
	public static String toString(CommandName commandName) {
		return commandName.name();
	}
	
	public static String toString(Collection<CommandName> commandNameList) {
		StringBuffer buf = new StringBuffer();
		Iterator<CommandName> iterator = commandNameList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			CommandName commandName = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(commandName);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<CommandName> commandNameList) {
		Collections.sort(commandNameList, createCommandNameComparator());
	}
	
	public static Collection<CommandName> sortRecords(Collection<CommandName> commandNameCollection) {
		List<CommandName> list = new ArrayList<CommandName>(commandNameCollection);
		Collections.sort(list, createCommandNameComparator());
		return list;
	}
	
	public static Comparator<CommandName> createCommandNameComparator() {
		return new Comparator<CommandName>() {
			public int compare(CommandName commandName1, CommandName commandName2) {
				String text1 = commandName1.value();
				String text2 = commandName2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
