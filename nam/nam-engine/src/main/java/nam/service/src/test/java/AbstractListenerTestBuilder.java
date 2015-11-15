package nam.service.src.test.java;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.util.OperationUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.generation.engine.GenerationContext;


public abstract class AbstractListenerTestBuilder extends AbstractBeanBuilder {

	public AbstractListenerTestBuilder(GenerationContext context) {
		super(context);
	}

	public static String getMessageType(Operation operation) {
		return "{"+getMessageNamespace(operation)+"}"+getMessageBeanName(operation);
	}
	
	public static String getMessagePackageName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getPackageName(parameter.getType());
	}

	public static String getMessageClassName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		if (parameter == null)
			System.out.println();
		return TypeUtil.getClassName(parameter.getType());
	}

	public static String getMessageQualifiedName(Operation operation) {
		return getMessagePackageName(operation) + "." + getMessageClassName(operation);
	}
	
	public static String getMessageNamespace(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getNamespace(parameter.getType());
	}

	public static String getMessageBeanName(Operation operation) {
		return NameUtil.uncapName(getMessageClassName(operation));
	}

	public static String getMessageBaseName(Operation operation) {
		String messageBaseName = getMessageClassName(operation);
		if (messageBaseName.endsWith("Message"))
			messageBaseName = messageBaseName.replace("Message", "");
		return messageBaseName;
	}
	
	public static String getMessageReceivedFlagName(Operation operation) {
		String messageBaseName = getMessageBaseName(operation);
		return NameUtil.uncapName(messageBaseName) + "Received";
	}
	
	public static String getFailureMessageReason(Operation operation) {
		String messageBaseName = getMessageBaseName(operation);
		return NameUtil.uncapName(messageBaseName) + "Reason";
	}
	
}
