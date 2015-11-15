package nam.service.src.test.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nam.model.Cache;
import nam.model.Callback;
import nam.model.Command;
import nam.model.Invoker;
import nam.model.Operation;
import nam.model.Sender;
import nam.model.Service;
import nam.model.statement.AbstractBlock;
import nam.model.statement.DoneCommand;
import nam.model.statement.ExpressionStatement;
import nam.model.statement.InvokeCommand;
import nam.model.statement.ListenCommand;
import nam.model.statement.SendCommand;
import nam.model.util.CommandUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ProcessUtil;
import nam.model.util.ServiceUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;
import org.springframework.util.Assert;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.Pathway;
import aries.generation.engine.GenerationContext;


public class ServiceListenerITBuilderHelper {

	private GenerationContext context;
	
	private Service service;
	
	private Callback callback;
	
	private String event;
	
	private String serviceInterfaceName;
	
	private String serviceNamespaceAlias;
	
	private Set<String> localSuccessCallbackNames;
	
	private Set<String> allSuccessCallbackNames;
	
	private Set<String> otherCallbackNames;
	
	private Set<String> incomingEvents;

	private ListenCommand listenCommand;
	
	private String eventBaseName;
	
	private boolean shouldResultInCompletion;
	
	//private ServiceMessageITBuilderHelper messageHelper;

	private int indent;
	
	
	public ServiceListenerITBuilderHelper(GenerationContext context) {
		this.context = context;
		this.indent = 2;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}
	
	public void initializeForService() {
		//messageHelper = new ServiceMessageITBuilderHelper(context);
		serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		//String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		//Operation processOperation = ProcessUtil.getOperation(service.getProcess(), serviceOperationName);
		shouldResultInCompletion = isRequestShouldResultInCompletion(service, null);

		List<Callback> callbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		otherCallbackNames = getCallbackNames(callbacks);
		//shouldResultInCompletion = true;
	}
	
	public void initializeForCallback(Pathway pathway) {
		//messageHelper = new ServiceMessageITBuilderHelper(context);
		serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		//String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		//Operation processOperation = ProcessUtil.getOperation(service.getProcess(), serviceOperationName);
		shouldResultInCompletion = isRequestShouldResultInCompletion(service, pathway);
		
		localSuccessCallbackNames = new TreeSet<String>();
		localSuccessCallbackNames.add(callback.getInterfaceName());

		allSuccessCallbackNames = new TreeSet<String>();
		allSuccessCallbackNames.add(callback.getInterfaceName());

		List<Callback> callbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		otherCallbackNames = getCallbackNames(callbacks);
		otherCallbackNames.remove(callback.getInterfaceName());
	}

	public void initializeForEvent() {
		serviceNamespaceAlias = ServiceUtil.getNamespaceAlias(service);
		serviceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		Operation serviceOperation = ServiceUtil.getDefaultOperation(service);
		String serviceOperationName = NameUtil.capName(serviceOperation.getName());
		Operation processOperation = ProcessUtil.getOperation(service.getProcess(), serviceOperationName);
		shouldResultInCompletion = isRequestShouldResultInCompletion(service, null);
		eventBaseName = event;

		listenCommand = OperationUtil.getListenCommand(processOperation, event);
		Assert.notNull(listenCommand, "ListenCommand not found for event: "+event);
		localSuccessCallbackNames = CommandUtil.getSuccessCallbackNames(listenCommand);
		allSuccessCallbackNames = OperationUtil.getSuccessCallbackNames(processOperation);
		otherCallbackNames = OperationUtil.getErrorCallbackNames(processOperation);
		//exceptionCallbackNames = CommandUtil.getExceptionCallbackNames(listenCommand);
		//timeoutCallbackNames = CommandUtil.getTimeoutCallbackNames(listenCommand);
		incomingEvents = OperationUtil.getIncomingEvents(processOperation);
		shouldResultInCompletion = true;
	}
	
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public boolean isShouldResultInCompletion() {
		return shouldResultInCompletion;
	}
	
	public void setShouldResultInCompletion(boolean shouldResultInCompletion) {
		this.shouldResultInCompletion = shouldResultInCompletion;
	}
	
	protected Set<String> getCallbackNames(List<Callback> callbacks) {
		Set<String> callbackNames = new TreeSet<String>();
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			callbackNames.add(callback.getInterfaceName());
		}
		return callbackNames;
	}

	public String getSuccessCallbackName() {
		//TODO for multiple success callbacks, we need to test each one individually
		return localSuccessCallbackNames.iterator().next();
	}
	
	public String getFailureCallbackName() {
		String failureCallbackName = null;
		//TODO for multiple failure callbacks, we need to test each one individually
		Iterator<String> iterator = otherCallbackNames.iterator();
		while (iterator.hasNext()) {
			String otherCallbackName = iterator.next();
			//TODO we need better way to distinguish a "failure" callback
			if (otherCallbackName.contains("Failed") || otherCallbackName.contains("Failure")) {
				failureCallbackName = otherCallbackName;
				break;
			}
		}
		return failureCallbackName;
	}
	
	protected boolean isRequestShouldResultInCompletion(Service service, Pathway pathway) {
		return CodeUtil.hasDoneCommand(service, pathway);
	}

	public String getValidateRemoteInteractionsStateSource(Pathway pathway) {
		String expectedIncomingCallbackName = pathway.getName();
		
		Buf buf = new Buf();
		buf.putLine(indent, "");
		
		//provide one of these for each external service that is invoked
		//String callbackInterfaceName = ServiceLayerHelper.getServiceInterfaceName(callback);
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		Iterator<Service> iterator = remoteServices.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			if (i > 0)
				buf.putLine(indent, "");

			Service remoteService = iterator.next();
			String remoteServiceNamespaceAlias = ServiceUtil.getNamespaceAlias(remoteService);
			String remoteServiceInterfaceName = ServiceLayerHelper.getServiceInterfaceName(remoteService);
			buf.putLine(indent, "// validate remote "+remoteServiceInterfaceName+" interaction");
			
			//if (serviceInterfaceName.equals("ReserveBooks"))
			//	System.out.println();
			
			boolean isExpectedAbort = pathway.getType().equals("exception") || pathway.getType().equals("timeout");
			Callback remoteCallback = ServiceUtil.getCallbackByName(remoteService, expectedIncomingCallbackName);
			boolean isRemoteServiceInvoked = isRemoteServiceInvoked(remoteService, pathway);
			
			if (isRemoteServiceInvoked)
				buf.putLine(indent, "Assert.isTrue(isFiredRequestSent(\""+remoteServiceNamespaceAlias+"_"+remoteServiceInterfaceName+"\"));");
			else buf.putLine(indent, "Assert.isFalse(isFiredRequestSent(\""+remoteServiceNamespaceAlias+"_"+remoteServiceInterfaceName+"\"));");
			if (remoteCallback != null && !isExpectedAbort)
				buf.putLine(indent, "Assert.isTrue(isFiredResponseDone(\""+remoteServiceNamespaceAlias+"_"+expectedIncomingCallbackName+"\"));");
			
			Operation operation = ServiceUtil.getDefaultOperation(remoteService);
			String messageReceivedFlagName = ServiceLayerHelper.getMessageReceivedFlagName(operation);
			if (isRemoteServiceInvoked)
				buf.putLine(indent, "Assert.isTrue("+messageReceivedFlagName+");");
			else 
				buf.putLine(indent, "Assert.isFalse("+messageReceivedFlagName+");");
		}

		return buf.get();
	}
	
	
	protected boolean isRemoteServiceInvoked(Service service, Pathway pathway) {
//		Command rootCommand = pathway.getRootCommand();
//		Interactor interactor = (Interactor) rootCommand.getActor();
//		if (interactor == null)
//			System.out.println();
//		
//		if (interactor.getService().equalsIgnoreCase(service.getName())) {
//			return true;
//		}

		Collection<Command> commands = getCommandsForPathway(pathway);
		Collection<SendCommand> sendCommands = CommandUtil.getCommands(commands, SendCommand.class);
		Collection<InvokeCommand> invokeCommands = CommandUtil.getCommands(commands, InvokeCommand.class);
		Command rootCommand = pathway.getRootCommand();
		if (rootCommand instanceof SendCommand)
			sendCommands.add((SendCommand) rootCommand);
		if (rootCommand instanceof InvokeCommand)
			invokeCommands.add((InvokeCommand) rootCommand);

		Iterator<SendCommand> iterator = sendCommands.iterator();
		while (iterator.hasNext()) {
			SendCommand sendCommand = iterator.next();
			Object actor = sendCommand.getActor();
			
			if (actor instanceof Sender) {
				Sender sender = (Sender) actor;
				if (sender.getService().equalsIgnoreCase(service.getName())) {
					return true;
				}
				
			} else if (actor instanceof Invoker) {
				Invoker invoker = (Invoker) actor;
				if (invoker.getService().equalsIgnoreCase(service.getName())) {
					return true;
				}
			}
		}
		return false;
	}


	public Collection<Command> getCommandsForPathway(Pathway pathway) {
		Collection<Command> commands = pathway.getCommands();
		if (commands == null || commands.isEmpty()) {
			AbstractBlock commandBlock = pathway.getCommandBlock();
			commands = commandBlock.getCommands();
		}
		return commands;
	}


	public String getValidateSuccessfulCallbackStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate successful callback state");
		Iterator<String> iterator = localSuccessCallbackNames.iterator();
		while (iterator.hasNext()) {
			String callbackName = iterator.next();
			String callbackNameCapped = NameUtil.capName(callbackName);
			String callbackNameUncapped = NameUtil.uncapName(callbackName);
			buf.putLine(indent, "Assert.isTrue(isFiredResponseSent(\""+serviceNamespaceAlias+"_"+callbackNameCapped+"\"));");
			//buf.putLine(indent, "Assert.isTrue(isFiredResponseDone(\""+callbackNameCapped+"\"));");
			buf.putLine(indent, "Assert.isTrue("+callbackNameUncapped+"Received);");
			allSuccessCallbackNames.remove(callbackName);
		}
		return buf.get();
	}

	public String getValidateFailureCallbackStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate failure callback state");
		String failureCallbackName = getFailureCallbackName();
		String callbackNameCapped = NameUtil.capName(failureCallbackName);
		String callbackNameUncapped = NameUtil.uncapName(failureCallbackName);
		buf.putLine(indent, "Assert.isTrue(isFiredResponseSent(\""+serviceNamespaceAlias+"_"+callbackNameCapped+"\"));");
		buf.putLine(indent, "Assert.isTrue("+callbackNameUncapped+"Received);");
		otherCallbackNames.remove(failureCallbackName);
		return buf.get();
	}

	public String getValidateNonExistantCallbackStateSource() {
		otherCallbackNames.addAll(allSuccessCallbackNames);

		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate non-existent callback state");
		Iterator<String> iterator = otherCallbackNames.iterator();
		while (iterator.hasNext()) {
			String callbackName = iterator.next();
			String callbackNameCapped = NameUtil.capName(callbackName);
			//String callbackNameUncapped = NameUtil.uncapName(callbackName);
			buf.putLine(indent, "Assert.isFalse(isFiredResponseSent(\""+serviceNamespaceAlias+"_"+callbackNameCapped+"\"));");
		}
		
		iterator = otherCallbackNames.iterator();
		while (iterator.hasNext()) {
			String callbackName = iterator.next();
			//String callbackNameCapped = NameUtil.capName(callbackName);
			String callbackNameUncapped = NameUtil.uncapName(callbackName);
			buf.putLine(indent, "Assert.isFalse("+callbackNameUncapped+"Received);");
		}
		return buf.get();
	}

	public String getValidateProcessCompletionStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate process completion state");
		Iterator<String> iterator2 = incomingEvents.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			String eventNameCapped = NameUtil.capName(eventName);
			if (eventName.equalsIgnoreCase(eventBaseName))
				buf.putLine(indent, "Assert.isTrue(isFiredProcessComplete(\""+serviceNamespaceAlias+"_"+eventNameCapped+"\"));");
			else buf.putLine(indent, "Assert.isFalse(isFiredProcessComplete(\""+serviceNamespaceAlias+"_"+eventNameCapped+"\"));");
		}
		return buf.get();
	}

	public String getValidateProcessNonCompletionStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate process non-completion state");
		Iterator<String> iterator2 = incomingEvents.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			String eventNameCapped = NameUtil.capName(eventName);
			buf.putLine(indent, "Assert.isFalse(isFiredProcessComplete(\""+serviceNamespaceAlias+"_"+eventNameCapped+"\"));");
		}
		return buf.get();
	}
	
	public String getValidateProcessAbortedStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate process aborted state");
		Iterator<String> iterator2 = incomingEvents.iterator();
		while (iterator2.hasNext()) {
			String eventName = iterator2.next();
			String eventNameCapped = NameUtil.capName(eventName);
			if (eventName.equalsIgnoreCase(eventBaseName))
				buf.putLine(indent, "Assert.isTrue(isFiredProcessAborted(\""+serviceNamespaceAlias+"_"+eventNameCapped+"\"));");
			else buf.putLine(indent, "Assert.isFalse(isFiredProcessAborted(\""+serviceNamespaceAlias+"_"+eventNameCapped+"\"));");
		}
		return buf.get();
	}

	public String getValidateRequestReceivedStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate request initiation state");
		//buf.putLine(indent, "Assert.isTrue(isFiredRequestSent(\""+serviceInterfaceName+"\"));");
		buf.putLine(indent, "Assert.isTrue(isFiredRequestReceived(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}

	public String getValidateRequestCompletionStateSource() {
		return getValidateRequestCompletionStateSource(serviceInterfaceName, true, false);
	}
	
	public String getValidateRequestCompletionStateSource(boolean isHandled) {
		return getValidateRequestCompletionStateSource(serviceInterfaceName, isHandled, false);
	}
	
	public String getValidateRequestCompletionStateSource(String serviceInterfaceName) {
		return getValidateRequestCompletionStateSource(serviceInterfaceName, true, false);
	}
	
	public String getValidateRequestCompletionStateSource(String serviceInterfaceName, boolean isHandled, boolean isAborted) {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate request completion state");
		if (isAborted)
			buf.putLine(indent, "Assert.isTrue(isFiredIncomingRequestAborted(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		if (isHandled)
			buf.putLine(indent, "Assert.isTrue(isFiredRequestHandled(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		else buf.putLine(indent, "Assert.isFalse(isFiredRequestHandled(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		if (shouldResultInCompletion)
			buf.putLine(indent, "Assert.isTrue(isFiredRequestDone(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		else buf.putLine(indent, "Assert.isFalse(isFiredRequestDone(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}
	
	public String getValidateIncomingRequestAbortedStateSource() {
		return getValidateIncomingRequestAbortedStateSource(serviceInterfaceName);
	}

	public String getValidateIncomingRequestAbortedStateSource(String serviceInterfaceName) {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate incoming request aborted state");
		buf.putLine(indent, "Assert.isTrue(isFiredIncomingRequestAborted(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}
	
//	public String getValidateOutgoingRequestAbortedStateSource() {
//		return getValidateOutgoingRequestAbortedStateSource(serviceInterfaceName);
//	}
	
	public String getValidateOutgoingRequestAbortedStateSource(String removeApplicationName, String serviceInterfaceName) {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate outgoing request aborted state");
		buf.putLine(indent, "Assert.isTrue(isFiredOutgoingRequestAborted(\""+removeApplicationName+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}
	
	public String getValidateRequestNonCompletionStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate request non-completion state");
		buf.putLine(indent, "Assert.isFalse(isFiredRequestHandled(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		buf.putLine(indent, "Assert.isFalse(isFiredRequestDone(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}

	public String getValidateRequestCancelledStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate request cancellation state");
		buf.putLine(indent, "Assert.isTrue(isFiredRequestCancelled(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		buf.putLine(indent, "Assert.isTrue(isFiredRequestRolledBack(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}

	public String getValidateRequestRolledBackStateSource() {
		Buf buf = new Buf();
		buf.putLine(indent, "");
		buf.putLine(indent, "// validate request rolledback state");
		buf.putLine(indent, "Assert.isTrue(isFiredRequestRolledBack(\""+serviceNamespaceAlias+"_"+serviceInterfaceName+"\"));");
		return buf.get();
	}


	public String getValidateEmptyCacheStateSource(Pathway pathway) {
		if (pathway.getCommandBlock() != null) {
			List<Command> pathwayCommands = pathway.getCommandBlock().getCommands();
			return getValidateEmptyCacheStateSource(pathwayCommands);
		}
		return "";
	}
	
	public String getValidateEmptyCacheStateSource(List<Command> commands) {
		Buf buf = new Buf();
		Collection<ExpressionStatement> pathwayExpressions = CommandUtil.getCommands(commands, ExpressionStatement.class);
		Iterator<ExpressionStatement> iterator3 = pathwayExpressions.iterator();
		for (int i=0; iterator3.hasNext(); i++) {
			ExpressionStatement expressionStatement = iterator3.next();
			String targetName = expressionStatement.getTargetName();
			String methodName = expressionStatement.getSelector();
			String targetNameCapped = NameUtil.capName(targetName);

			Cache cacheUnit = ServiceUtil.getCacheUnitReference(service, targetName);
			if (cacheUnit != null && methodName.startsWith("addTo")) {
				String fieldName = methodName.replace("addTo", "");
				
				if (i == 0) {
					buf.putLine(indent, "");
					buf.putLine(indent, "// validate empty "+targetNameCapped+" state");
				}
				buf.putLine(indent, targetName+"Helper.verify"+fieldName+"Count(0);");
			}
		}
		return buf.get();
	}

}
