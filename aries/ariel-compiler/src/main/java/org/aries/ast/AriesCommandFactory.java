package org.aries.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import nam.model.Command;
import nam.model.CommandName;
import nam.model.Condition;
import nam.model.Definition;
import nam.model.Element;
import nam.model.Field;
import nam.model.Interaction;
import nam.model.Interactor;
import nam.model.Invoker;
import nam.model.Listener;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Sender;
import nam.model.Type;
import nam.model.statement.Branch;
import nam.model.statement.ConditionStatement;
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
import nam.model.util.ElementUtil;
import nam.model.util.OperationUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ConditionNode;
import org.aries.ast.node.DefinitionNode;
import org.aries.ast.node.DoneNode;
import org.aries.ast.node.ExceptionNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.InvokeNode;
import org.aries.ast.node.ListenNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.OptionNode;
import org.aries.ast.node.ParameterNode;
import org.aries.ast.node.PostNode;
import org.aries.ast.node.ReplyNode;
import org.aries.ast.node.ResultNode;
import org.aries.ast.node.SendNode;
import org.aries.ast.node.StatementNode;
import org.aries.ast.node.TimeoutNode;
import org.aries.ast.node.TypeNode;
import org.aries.ast.node.control.BreakNode;
import org.aries.ast.node.control.ContinueNode;
import org.aries.ast.node.control.DoNode;
import org.aries.ast.node.control.ForNode;
import org.aries.ast.node.control.IfNode;
import org.aries.ast.node.control.ReturnNode;
import org.aries.ast.node.control.ThrowNode;
import org.aries.ast.node.control.UnnamedNode;
import org.aries.ast.node.control.WhileNode;
import org.aries.util.NameUtil;

import aries.codegen.util.CodeUtil;
import aries.codegen.util.ExpressionUtil;
import aries.generation.engine.GenerationContext;


public class AriesCommandFactory {

	private GenerationContext context;
	
	private String processScope;

	private Map<String, Definition> localDefinitions = new HashMap<String, Definition>();

	private Set<ConditionStatement> localConditions = new LinkedHashSet<ConditionStatement>();

	private ArielASTPrinter printer = new ArielASTPrinter();
	

	
	public AriesCommandFactory(GenerationContext context) {
		this.context = context;
	}

	public String getProcessScope() {
		return processScope;
	}

	public String setProcessScope(String processScope) {
		String oldScope = this.processScope;
		this.processScope = processScope;
		return oldScope;
	}
	
	public void clearLocalDefinitions() {
		localDefinitions.clear();
	}
	
	public Map<String, Definition> getLocalDefinitions() {
		return localDefinitions;
	}

	public void addLocalDefinition(Definition definition) {
		localDefinitions.put(definition.getName(), definition);
	}

	public void clearLocalConditions() {
		localConditions.clear();
	}
	
	public Set<ConditionStatement> getLocalConditions() {
		return localConditions;
	}

	public void addLocalConditions(ConditionStatement conditionStatement) {
		localConditions.add(conditionStatement);
	}
	
	public void resetServiceScope() {
		clearLocalDefinitions();
		clearLocalConditions();
		activeBranches.clear();
	}

	public Command buildCommandFromStatementNode(StatementNode statementNode) throws Exception {
		return buildCommandFromStatementNode(2, statementNode);
	}

	public Command buildCommandFromStatementNode(int indent, CommandNode statementNode) throws Exception {
		String text = statementNode.getText();
		Command command = new Command();
		command.setName(CommandName.EXPRESSION);
		command.setNode(statementNode);
		command.setType(text);
		
		if (text.equals("if")) {
			return buildIfCommandFromIfNode(indent, (IfNode) statementNode);
		} else if (text.equals("while")) {
			command.setText(printer.printWhileNode(0, (WhileNode) statementNode));
		} else if (text.equals("for")) {
			command.setText(printer.printForNode(0, (ForNode) statementNode));
		} else if (text.equals("do")) {
			command.setText(printer.printDoNode(0, (DoNode) statementNode));
		} else if (text.equals("continue")) {
			command.setText(printer.printContinueNode(0, (ContinueNode) statementNode));
		} else if (text.equals("break")) {
			command.setText(printer.printBreakNode(0, (BreakNode) statementNode));
		} else if (text.equals("throw")) {
			command.setText(printer.printThrowNode(0, (ThrowNode) statementNode));
		} else if (text.equals("return")) {
			command.setText(printer.printReturnNode(0, (ReturnNode) statementNode));
		} else if (text.equals("unnamed")) {
			return buildCommandFromUnnamedNode(indent, (UnnamedNode) statementNode);
//			String sourceCode = printer.printUnnamedNode(0, (UnnamedNode) statementNode);
//			command.setText(sourceCode);
		} else if (text.equals("EXPR")) {
			return buildExpressionStatementFromExpressionNode(indent, (ExpressionNode) statementNode);
			//command.setText(printer.printExpressionNode(0, (ExpressionNode) statementNode));
			//command.getSources().addAll(statementNode.getTokens());
		} else if (text.equals("STATEMENT")) {
			//System.out.println(">>>"+printer.printStatementNode(0, statementNode));
			command.addToTokens(statementNode.getTokens());
		}
		return command;
	}

	public Command buildIfCommandFromIfNode(int indent, IfNode ifNode) throws Exception {
		return buildCommandFromIfNode(indent, ifNode, false);
	}

	public Command buildIfCommandFromElseIfNode(int indent, IfNode ifNode) throws Exception {
		return buildCommandFromIfNode(indent, ifNode, true);
	}

	private Stack<Branch> activeBranches = new Stack<Branch>();
	
	public Command buildCommandFromIfNode(int indent, IfNode ifNode, boolean isElse) throws Exception {
		IfStatement ifCommand = buildIfCommand(indent, ifNode.getName());
		ifCommand.setNode(ifNode);

		ConditionNode conditionNode = ifNode.getConditionNode();
		ConditionStatement conditionStatement = buildConditionStatementFromConditionNode(conditionNode);

		ifCommand.setConditionStatement(conditionStatement);
		String conditionText = printer.printConditionNode(conditionNode);
		ifCommand.setConditionText(conditionText);

		Condition condition = conditionNode.getCondition();
		localConditions.add(ifCommand.getConditionStatement());
//		if (conditionNode.toString().equals("bookCache.getAllBookOrders() < 4"))
//			System.out.println();
		
		Branch successBranch = new Branch();
		successBranch.setSuccess(true);
		successBranch.setConditionStatement(conditionStatement);
		
		// build main body of commands here
		activeBranches.push(successBranch);
		ifCommand.getCommands().addAll(buildCommandsFromStatementNodes(indent+1, ifNode.getStatementNodes()));
		Branch poppedBranch = activeBranches.pop();
		Assert.equals(poppedBranch, successBranch);

//		if (conditionNode.toString().contains("available"))
//			System.out.println();
		
		List<IfNode> elseIfNodes = ifNode.getElseIfNodes();
		Iterator<IfNode> iterator = elseIfNodes.iterator();
		while (iterator.hasNext()) {
			IfNode elseIfNode = iterator.next();
			Branch elseIfBranch = new Branch();
			successBranch.setSuccess(true);
			successBranch.setConditionStatement(conditionStatement);
			Command elseIfCommand = buildIfCommandFromElseIfNode(indent, elseIfNode);
			ifCommand.getElseIfCommands().add(elseIfCommand);
			poppedBranch = activeBranches.pop();
			Assert.equals(poppedBranch, elseIfBranch);
		}
		
		UnnamedNode elseNode = ifNode.getElseNode();
		if (elseNode != null) {
			Branch elseBranch = new Branch();
			elseBranch.setSuccess(false);
			elseBranch.setConditionStatement(conditionStatement);
			activeBranches.push(elseBranch);
			ifCommand.getElseCommands().addAll(buildCommandsFromStatementNodes(indent+1, elseNode.getStatementNodes()));
			poppedBranch = activeBranches.pop();
			Assert.equals(poppedBranch, elseBranch);
		}

		Branch avoidanceBranch = new Branch();
		avoidanceBranch.setConditionStatement(conditionStatement);
		activeBranches.push(avoidanceBranch);
		return ifCommand;
	}
	
	public ConditionStatement buildConditionStatementFromConditionNode(ConditionNode conditionNode) throws Exception {
		ConditionStatement conditionStatement = new ConditionStatement();
		conditionStatement.setOperator(conditionNode.getOperatorNode().getOperator());
		conditionStatement.setExpressionStatement1(buildExpressionStatementFromExpressionNode(conditionNode.getFirstOperandNode()));
		conditionStatement.setExpressionStatement2(buildExpressionStatementFromExpressionNode(conditionNode.getSecondOperandNode()));
		conditionStatement.setText(conditionNode.toString());
		return conditionStatement;
	}

	public DoneCommand buildDoneCommandFromDoneNode(DoneNode doneNode) throws Exception {
		return buildDoneCommandFromDoneNode(2, doneNode);
	}
	
	public DoneCommand buildDoneCommandFromDoneNode(int indent, DoneNode doneNode) throws Exception {
		DoneCommand command = new DoneCommand();
		command.setName(CommandName.DONE);
		command.setNode(doneNode);
		command.setText(doneNode.getText());
		command.setType("done");
//		if (doneNode.isNeedsReturn())
//			System.out.println();
		command.setNeedsReturn(doneNode.isNeedsReturn());
		command.setServiceIds(doneNode.getServiceIds());
		command.setIndent(indent);
		return command;
	}
	
	public ExpressionStatement buildExpressionStatementFromExpressionNode(ExpressionNode expressionNode) throws Exception {
		return buildExpressionStatementFromExpressionNode(2, expressionNode);
	}
	
	public ExpressionStatement buildExpressionStatementFromExpressionNode(int indent, ExpressionNode expressionNode) throws Exception {
		ExpressionStatement expressionStatement = new ExpressionStatement();
		String targetName = NameUtil.uncapName(expressionNode.getObjectName());
		Element elementByName = context.getElementByName(targetName);
		
//		if (targetName.contains("bookCache"))
//			System.out.println();
		
		expressionStatement.setTargetName(targetName);
		expressionStatement.setIndent(indent);
		Definition definition = localDefinitions.get(targetName);
		if (definition != null) {
			expressionStatement.setDefinition(definition);
			expressionStatement.setTargetType(definition.getType());
		} else if (!targetName.equals("this")) {
			//System.out.println();
		}
		
		initializeExpressionStatement(expressionStatement, expressionNode);
		
		//TODO - this needs work
		ExpressionNode subExpressionNode = expressionNode.getExpressionNode();
		while (subExpressionNode != null) {
			String objectName = subExpressionNode.getObjectName();
			List<String> selectorChain = subExpressionNode.getSelectorChain();

			ExpressionStatement subExpressionStatement = new ExpressionStatement();
			subExpressionStatement.setTargetName(objectName);
			//subExpressionStatement.addSelector("getOrder()");
			//subExpressionStatement.addSelector("getBooks()");
			expressionStatement.setSubExpression(subExpressionStatement);
			
			Element objectElement = null;
			if (objectName.equals("this")) {
				String subTargetName = selectorChain.get(0);
				subExpressionStatement.setTargetName(subTargetName);
				Element targetElement = context.getElementByName(subTargetName);
				subExpressionStatement.setTargetType(targetElement);
				//objectElement = targetElement;
			}
			
			if (!objectName.equals("this")) {
				objectElement = context.getElementByName(objectName);
				if (localDefinitions.containsKey(objectName)) {
					subExpressionStatement.setTargetType(objectElement);
				}
			}
			
			Field field = null;
			Iterator<String> iterator = selectorChain.iterator();
			while (iterator.hasNext()) {
				String selector = iterator.next();
				
				if (objectElement != null) {
					field = ElementUtil.getField(objectElement, selector);
//					if (field == null)
//						System.out.println();
					objectElement = context.getElementByType(field.getType());
					String fieldName = NameUtil.capName(selector);
					subExpressionStatement.addSelector("get"+fieldName+"()");
				}
				
				if (objectElement == null) {
					definition = localDefinitions.get(selector);
					if (definition != null) {
						break;
					} else if (!targetName.equals("this")) {
						//System.out.println();
					}
				}
			}
			
			if (definition != null) {
				Parameter parameter = new Parameter();
				parameter.setName(definition.getName());
				parameter.setType(definition.getType().getType());
				parameter.setConstruct(definition.getType().getStructure());
				expressionStatement.getParameters().add(parameter);

			} else if (field != null) {
				String parameterName = selectorChain.get(selectorChain.size()-1);
				Parameter parameter = new Parameter();
				parameter.setName(parameterName);
				parameter.setType(field.getType());
				parameter.setConstruct(field.getStructure());
				expressionStatement.getParameters().add(parameter);

			} else {
				String parameterName = selectorChain.get(selectorChain.size()-1);
//				if (expressionNode.toString().equals("invoice.setOrder(context.shipment.order)"))
//					System.out.println();
				TypeNode typeNode = getTypeNodeFromCommandNode(expressionNode, parameterName, "Message");
//				if (typeNode == null)
//					System.out.println();
				Assert.notNull(typeNode, "Variable not found: "+parameterName);
				Parameter parameter = new Parameter();
				parameter.setName(parameterName);
				parameter.setType(typeNode.getType());
				parameter.setConstruct(typeNode.getConstruct());
				expressionStatement.getParameters().add(parameter);
			}
			
			subExpressionNode = subExpressionNode.getExpressionNode();
		}
		
		expressionStatement.addToTokens(expressionNode.getTokens());
		String sourceCode = CodeUtil.createMethodSource(expressionNode.getTokens());
		//System.out.println(">>>"+sourceCode);
		expressionStatement.setText(sourceCode);
		return expressionStatement;
	}

	protected void initializeExpressionStatement(ExpressionStatement expressionStatement, ExpressionNode expressionNode) {
		String targetName = NameUtil.uncapName(expressionNode.getObjectName());
		Element elementByName = context.getElementByName(targetName);
		
		List<String> selectorChain = expressionNode.getSelectorChain();
		int selectorChainSize = selectorChain.size();
		if (selectorChainSize > 0) {
			String selector = selectorChain.get(selectorChainSize - 1);
			expressionStatement.setSelector(selector);
			//expressionStatement.setFieldNames(selectorChain);
			String fieldName = OperationUtil.getTargetTypeFromOperationName(selector);
			if (fieldName != null) {
				if (elementByName == null) {
					Element element = context.getElementByName(fieldName);
					expressionStatement.setTargetType(element);
				} else {
					Field field = ElementUtil.getField(elementByName, fieldName);
					//if (field == null)
					//	System.out.println();
					expressionStatement.setTargetType(field);
				}
			}
		}
	}

	public Command buildCommandFromUnnamedNode(int indent, UnnamedNode unnamedNode) throws Exception {
		Command command = new Command();
		command.setName(CommandName.EXPRESSION);
		command.setNode(unnamedNode);
		command.setType("unnamed");
		command.getCommands().addAll(buildCommandsFromStatementNodes(indent, unnamedNode.getStatementNodes()));
		return command;
	}

	public <T extends CommandNode> List<Command> buildCommandsFromStatementNodes(int indent, List<T> statementNodes) throws Exception {
		List<Command> commands = new ArrayList<Command>();
		Iterator<T> iterator = statementNodes.iterator();
		while (iterator.hasNext()) {
			T statementNode = iterator.next();
			String text = statementNode.getText();
			if (text.equals("invoke")) {
				commands.add(buildInvokeCommandFromInvokeNode(indent+1, (InvokeNode) statementNode));
			} else if (text.equals("send")) {
				commands.add(buildSendCommandFromSendNode(indent+1, (SendNode) statementNode));
			} else if (text.equals("reply")) {
				commands.add(buildReplyCommandFromReplyNode(indent+1, (ReplyNode) statementNode));
			} else if (text.equals("done")) {
				commands.add(buildDoneCommandFromDoneNode(indent+1, (DoneNode) statementNode));
			} else if (text.equals("EXPR")) {
				commands.add(buildExpressionStatementFromExpressionNode((ExpressionNode) statementNode));
			} else if (text.equals("DEFINITION")) {
				commands.add(buildDefinitionCommandFromCommandNode(indent+1, (DefinitionNode) statementNode));
			} else if (text.equals("unnamed")) {
				commands.add(buildCommandFromUnnamedNode(indent, (UnnamedNode) statementNode));
			} else {
				commands.add(buildCommandFromStatementNode(indent+1, statementNode));
			}
		}
		return commands;
	}

//	public Command buildCommandFromExpressionNode(ExpressionNode expressionNode) {
//		return buildCommandFromExpressionNode(0, expressionNode);
//	}
//	
//	public Command buildCommandFromExpressionNode(int indent, ExpressionNode expressionNode) {
//		Command command = buildExpressionCommand(expressionNode.getName(), "expression");
//		command.setNode(expressionNode);
//		command.getSources().addAll(expressionNode.getTokens());
//		String sourceCode = CodeUtil.createMethodSource(expressionNode.getTokens());
//		//System.out.println(">>>"+sourceCode);
//		command.setText(sourceCode);
//		return command;
//	}
	
	public IfStatement buildIfCommand(int indent, String text) {
		//return buildCommand(CommandName.EXPR, text, "if");
		IfStatement command = new IfStatement();
		command.setIndent(indent);
		command.setName(CommandName.EXPRESSION);
		command.setText(text);
		command.setType("if");
		return command;
	}


	public SendCommand buildSendCommandFromSendNode(SendNode sendNode) throws Exception {
		return buildSendCommandFromSendNode(2, sendNode);
	}
	
	public SendCommand buildSendCommandFromSendNode(int indent, SendNode sendNode) throws Exception {
		SendCommand sendCommand = new SendCommand();
		sendCommand.setName(CommandName.SEND);
		sendCommand.setNode(sendNode);
		sendCommand.setType("send");
		sendCommand.setText(NameUtil.capName(sendNode.getName()));
		//String messageName = command.getParameters().get(0)+"Message";
		//command.setText(resolveVariableReference(messageName));
		sendCommand.setIndent(indent);
		
		List<Parameter> parameters = buildParametersFromParameterNodes(sendNode);
		sendCommand.getParameters().addAll(parameters);

		List<String> messageNames = getMessageNames(sendNode.getMessageNodes());
		sendCommand.setReturnMessages(messageNames);
		
		//first get the channel
		String channelId = sendNode.getDestination();
		//ChannelNode channel = getChannelForTargetId(targetId);
		//Assert.notNull(channel, "Channel not found for: "+targetId);

		Sender interactor = new Sender();
		interactor.setChannel(channelId);
		interactor.setInteraction(Interaction.SEND);
		String target = sendNode.getDestination();
		int indexOfDot = target.indexOf(".");
		Assert.isTrue(indexOfDot > 0, "Incomplete destination: "+target);
		String participantName = target.substring(0, indexOfDot);
		String serviceName = target.substring(indexOfDot+1);
		initializeInteractor(interactor, sendNode, participantName, serviceName);
		sendCommand.setActor(interactor);
		
		//context.getServiceByName(serviceNamespace, serviceName);
		
//		String target = sendNode.getName();
//		int indexOfDot = target.indexOf(".");
//		if (indexOfDot != -1) {
//			String participantName = target.substring(0, indexOfDot);
//			String serviceName = target.substring(indexOfDot+1);
//			initializeInteractor(interactor, sendNode, participantName, serviceName);
//			command.setActor(interactor);
//		}
		return sendCommand;
	}

	public ReplyCommand buildReplyCommandFromReplyNode(ReplyNode replyNode) throws Exception {
		return buildReplyCommandFromReplyNode(2, replyNode);
	}
	
	public ReplyCommand buildReplyCommandFromReplyNode(int indent, ReplyNode replyNode) throws Exception {
//		if (replyNode.getMessageName().equalsIgnoreCase("BooksUnavailable"))
//			System.out.println();
		
		ReplyCommand replyCommand = new ReplyCommand();
		replyCommand.setName(CommandName.REPLY);
		replyCommand.setNode(replyNode);
		replyCommand.setType("reply");
		replyCommand.getParameters().addAll(buildParametersFromParameterNodes(replyNode, "Message"));
		replyCommand.setMessageName(replyNode.getMessageName());
		replyCommand.setOperationName(replyNode.getOperationName());
		replyCommand.setProcessScope(processScope);
		replyCommand.setIndent(indent);
		
//		if (processScope == null)
//			System.out.println();
		
		String participantName = NameUtil.uncapName(AriesASTContext.participantNode.getName());
		String serviceName = NameUtil.uncapName(AriesASTContext.receiveNode.getName());
		replyCommand.setServiceName(serviceName);
		
		//first get the channel
		String channelId = replyNode.getDestination();
		//ChannelNode channel = getChannelForTargetId(targetId);
		//Assert.notNull(channel, "Channel not found for: "+targetId);
		
		//List<Channel> channels = ApplicationUtil.getChannels(application);
		//Iterator<Channel> iterator = channels.iterator();
		//while (iterator.hasNext()) {
		//	Channel channel = iterator.next();
		//}
		
		Sender interactor = new Sender();
		interactor.setName(channelId);
		interactor.setChannel(channelId);
		interactor.setInteraction(Interaction.REPLY);
		initializeInteractor(interactor, replyNode, participantName, serviceName);
		//initializeInteractor(interactor, replyNode);
		//String messageName = replyNode.getMessageName();
		//command.setText(resolveVariableReference(messageName));
		replyCommand.setActor(interactor);
		//service.getInteractors().add(interactor);
		return replyCommand;
	}
	

	public Command buildExpressionCommand(String text, String type) {
		return buildCommand(CommandName.EXPRESSION, text, type);
	}

	public Command buildCommand(CommandName commandName, String text, String type) {
		Command command = new Command();
		command.setName(commandName);
		command.setText(text);
		command.setType(type);
		return command;
	}
	

	
	public Command buildCommandFromCommandNode(CommandNode commandNode) throws Exception {
		String label = commandNode.getText();
		//System.out.println(">>>"+label);
		if (label.equals("new")) {
			return buildNewCommandFromCommandNode(commandNode);
		} else if (label.equals("call")) {
			return buildCallCommandFromCommandNode(commandNode);
		} else if (label.equals("option")) {
			return buildOptionCommandFromOptionNode((OptionNode) commandNode);
		} else if (label.equals("listen")) {
			return buildListenCommandFromListenNode((ListenNode) commandNode);
		} else if (label.equals("invoke")) {
			return buildInvokeCommandFromInvokeNode((InvokeNode) commandNode);
		} else if (label.equals("post")) {
			return buildPostCommandFromPostNode((PostNode) commandNode);
		} else if (label.equals("reply")) {
			return buildReplyCommandFromReplyNode((ReplyNode) commandNode);
		} else if (label.equals("send")) {
			return buildSendCommandFromSendNode((SendNode) commandNode);
		} else if (label.equals("done")) {
			return buildDoneCommandFromDoneNode((DoneNode) commandNode);
		} else if (label.equals("EXPR")) {
			//AriesASTUtil.printCommandSource(commandNode);
			return buildExpressionStatementFromExpressionNode((ExpressionNode) commandNode);
		} else if (label.equals("STATEMENT") || label.equals("DEFINITION")) {
			//AriesASTUtil.printCommandSource(commandNode);
			return buildCommandFromStatementNode((StatementNode) commandNode);
		} else if (AriesASTUtil.isStatement(label)) {
			//AriesASTUtil.printCommandSource(commandNode);
			return buildCommandFromStatementNode((StatementNode) commandNode);
		} else {
			//throw new RuntimeException("Unrecognized command: "+label);
			System.out.println("Unrecognized command: "+label);
			return null;
		}
	}
	
	public Command buildNewCommandFromCommandNode(CommandNode commandNode) throws Exception {
		Command command = new Command();
		command.setName(CommandName.NEW);
		command.setNode(commandNode);
		command.setText(commandNode.getName());
		command.setType("new");
		
		String suffix = "Message";
		List<String> tokens = commandNode.getTokens();
		Iterator<String> iterator = tokens.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String token = iterator.next();
			Parameter newParameter = new Parameter();
			newParameter.setConstruct("item");
			newParameter.setType("Object");
			if (AriesASTUtil.isName(token) && !token.equals("new"))
				newParameter.setName(token+suffix);
			else newParameter.setName(token);
			command.getParameters().add(newParameter);
		}
		return command;
	}
	
	public Command buildCallCommandFromCommandNode(CommandNode commandNode) throws Exception {
		Command command = new Command();
		command.setName(CommandName.CALL);
		command.setNode(commandNode);
		command.setText(commandNode.getName());
		command.setType("call");
		
//		if (commandNode.getName().startsWith("purchaseBooks"))
//			System.out.println();
		
		ResultNode resultNode = commandNode.getResultNode();
		if (resultNode != null) {
			Result result = new Result();
			result.setName(resultNode.getName());
			result.setType(resultNode.getType());
			result.setKey(resultNode.getKey());
			result.setConstruct(resultNode.getConstruct());
			command.addToResults(result);
			AriesASTContext.addActiveVariableInScope(result);
		}
		command.getParameters().addAll(buildParametersFromParameterNodes(commandNode, "Message"));
		Interactor interactor = new Interactor();
		interactor.setInteraction(Interaction.CALL);
		initializeInteractor(interactor, commandNode);
		command.setActor(interactor);
		return command;
	}

	public InvokeCommand buildInvokeCommandFromInvokeNode(InvokeNode invokeNode) throws Exception {
		return buildInvokeCommandFromInvokeNode(2, invokeNode);
	}
	
	public InvokeCommand buildInvokeCommandFromInvokeNode(int indent, InvokeNode invokeNode) throws Exception {
		InvokeCommand invokeCommand = new InvokeCommand();
		invokeCommand.setName(CommandName.INVOKE);
		invokeCommand.setNode(invokeNode);
		invokeCommand.setText(invokeNode.getName());
		invokeCommand.setType("invoke");
		invokeCommand.setIndent(indent);
		
		//System.out.println(">>>"+invokeNode.getName());
		invokeCommand.addRequiredBranches(activeBranches);
		invokeCommand.addRequiredConditions(localConditions);
//		if (invokeNode.getName().contains("Supplier.shipBooks"))
//			System.out.println();
		
		List<Parameter> arguments = buildParametersFromParameterNodes(invokeNode, "Message");
		invokeCommand.getParameters().addAll(arguments);

		List<String> messageNames = getMessageNames(invokeNode.getMessageNodes());
		invokeCommand.setReturnMessages(messageNames);

		invokeCommand.addResponseBlocks(buildResponseBlocksFromMessageNodes(indent, invokeNode.getMessageNodes()));
		invokeCommand.addTimeoutBlocks(buildTimeoutBlocksFromTimeoutNodes(indent, invokeNode.getTimeoutNodes()));
		invokeCommand.addExceptionBlocks(buildExceptionBlocksFromExceptionNodes(indent, invokeNode.getExceptionNodes()));

		//first get the channel
		String channelId = invokeNode.getDestination();
		//ChannelNode channel = getChannelForTargetId(targetId);
		//Assert.notNull(channel, "Channel not found for: "+targetId);

		Invoker invoker = new Invoker();
		invoker.setChannel(channelId);
		invoker.setInteraction(Interaction.INVOKE);
		String target = invokeNode.getDestination();
		int indexOfDot = target.indexOf(".");
		Assert.isTrue(indexOfDot > 0, "Incomplete destination: "+target);
		String participantName = target.substring(0, indexOfDot);
		String serviceName = target.substring(indexOfDot+1);
		initializeInteractor(invoker, invokeNode, participantName, serviceName);
		invokeCommand.setActor(invoker);
		return invokeCommand;
	}

	public List<String> getMessageNames(List<MessageNode> messageNodes) {
		List<String> messageNames = new ArrayList<String>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			MessageNode messageNode = iterator.next();
			String messageName = messageNode.getName();
			if (!messageName.endsWith("Message"))
				messageName += "Message";
			messageNames.add(messageName);
		}
		return messageNames;
	}

//	public Channel getChannelForTargetId(String targetId) {
//		context.getChannelByName(channelName);
//		return null;
//	}

	public Command buildOptionCommandFromOptionNode(OptionNode optionNode) throws Exception {
		Command command = new Command();
		command.setName(CommandName.OPTION);
		command.setNode(optionNode);
		command.setType("option");
		command.setText(optionNode.getName());
		List<CommandNode> commandNodes = optionNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			command.getCommands().add(buildCommandFromCommandNode(commandNode));
		}
		return command;
	}

	public ListenCommand buildListenCommandFromListenNode(ListenNode listenNode) throws Exception {
		return buildListenCommandFromListenNode(2, listenNode);
	}
	
	public ListenCommand buildListenCommandFromListenNode(int indent, ListenNode listenNode) throws Exception {
//		if (replyNode.getMessageName().equalsIgnoreCase("BooksUnavailable"))
//		System.out.println();
	
		String name = listenNode.getName();
		String messageName = listenNode.getMessageName();
		String operationName = NameUtil.uncapName(messageName);

		ListenCommand listenCommand = new ListenCommand();
		listenCommand.setName(CommandName.LISTEN);
		listenCommand.setNode(listenNode);
		listenCommand.setType("listen");
		listenCommand.getParameters().addAll(buildParametersFromParameterNodes(listenNode, "Message"));
		listenCommand.getCommands().addAll(buildCommandsFromStatementNodes(indent, listenNode.getCommandNodes()));
		listenCommand.setText(name);
		listenCommand.setMessageName(messageName);
		listenCommand.setOperationName(operationName);
		listenCommand.setIndent(indent);
		
		String participantName = NameUtil.uncapName(AriesASTContext.participantNode.getName());
		String serviceName = NameUtil.uncapName(AriesASTContext.receiveNode.getName());
		listenCommand.setServiceName(serviceName);
		
		listenCommand.addTimeoutBlocks(buildTimeoutBlocksFromTimeoutNodes(indent, listenNode.getTimeoutNodes()));
		listenCommand.addExceptionBlocks(buildExceptionBlocksFromExceptionNodes(indent, listenNode.getExceptionNodes()));
		
		//first get the channel
		String channelId = operationName;
		//String channelId = listenNode.getDestination();
		//ChannelNode channel = getChannelForTargetId(targetId);
		//Assert.notNull(channel, "Channel not found for: "+targetId);
		
		//List<Channel> channels = ApplicationUtil.getChannels(application);
		//Iterator<Channel> iterator = channels.iterator();
		//while (iterator.hasNext()) {
		//	Channel channel = iterator.next();
		//}
		
		Listener interactor = new Listener();
		interactor.setName(channelId);
		interactor.setChannel(channelId);
		interactor.setInteraction(Interaction.LISTEN);
		initializeInteractor(interactor, listenNode, participantName, serviceName);
		//initializeInteractor(interactor, replyNode);
		//String messageName = replyNode.getMessageName();
		//command.setText(resolveVariableReference(messageName));
		listenCommand.setActor(interactor);
		//service.getInteractors().add(interactor);
		return listenCommand;
	}
	
	public Command buildPostCommandFromPostNode(PostNode postNode) throws Exception {
		PostCommand command = new PostCommand();
		command.setName(CommandName.POST);
		command.setNode(postNode);
		command.setType("post");
		command.setEventName(postNode.getName());
		command.getParameters().addAll(buildParametersFromParameterNodes(postNode));
		Sender interactor = new Sender();
		interactor.setInteraction(Interaction.POST);
		initializeInteractor(interactor, postNode);
		command.setActor(interactor);
		return command;
	}
	
	public List<ResponseBlock> buildResponseBlocksFromMessageNodes(List<MessageNode> messageNodes) throws Exception {
		return buildResponseBlocksFromMessageNodes(0, messageNodes);
	}
	
	public List<ResponseBlock> buildResponseBlocksFromMessageNodes(int indent, List<MessageNode> messageNodes) throws Exception {
		List<ResponseBlock> responseBlocks = new ArrayList<ResponseBlock>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		String oldScope = setProcessScope("message");
		while (iterator.hasNext()) {
			MessageNode messageNode = iterator.next();
			ResponseBlock responseBlock = new ResponseBlock();
			responseBlock.setName(messageNode.getName());
			responseBlock.getCommands().addAll(buildCommandsFromStatementNodes(indent, messageNode.getCommandNodes()));
			responseBlocks.add(responseBlock);
		}
		setProcessScope(oldScope);
		return responseBlocks;
	}
	
	public List<TimeoutBlock> buildTimeoutBlocksFromTimeoutNodes(List<TimeoutNode> timeoutNodes) throws Exception {
		return buildTimeoutBlocksFromTimeoutNodes(0, timeoutNodes);
	}
	
	public List<TimeoutBlock> buildTimeoutBlocksFromTimeoutNodes(int indent, List<TimeoutNode> timeoutNodes) throws Exception {
		List<TimeoutBlock> timeoutBlocks = new ArrayList<TimeoutBlock>();
		Iterator<TimeoutNode> iterator = timeoutNodes.iterator();
		String oldScope = setProcessScope("timeout");
		while (iterator.hasNext()) {
			TimeoutNode timeoutNode = iterator.next();
			TimeoutBlock timeoutBlock = new TimeoutBlock();
			//timeoutBlock.setName(timeoutNode.getName());
			timeoutBlock.getCommands().addAll(buildCommandsFromStatementNodes(indent, timeoutNode.getCommandNodes()));
			timeoutBlocks.add(timeoutBlock);
		}
		setProcessScope(oldScope);
		return timeoutBlocks;
	}
	

	public List<ExceptionBlock> buildExceptionBlocksFromExceptionNodes(List<ExceptionNode> exceptionNodes) throws Exception {
		return buildExceptionBlocksFromExceptionNodes(0, exceptionNodes);
	}
	
	public List<ExceptionBlock> buildExceptionBlocksFromExceptionNodes(int indent, List<ExceptionNode> exceptionNodes) throws Exception {
		List<ExceptionBlock> exceptionBlocks = new ArrayList<ExceptionBlock>();
		Iterator<ExceptionNode> iterator2 = exceptionNodes.iterator();
		String oldScope = setProcessScope("exception");
		while (iterator2.hasNext()) {
			ExceptionNode exceptionNode = iterator2.next();
			ExceptionBlock exceptionBlock = new ExceptionBlock();
			//exceptionBlock.setName(exceptionNode.getName());
			exceptionBlock.getCommands().addAll(buildCommandsFromStatementNodes(indent, exceptionNode.getCommandNodes()));
			exceptionBlocks.add(exceptionBlock);
		}
		setProcessScope(oldScope);
		return exceptionBlocks;
	}

	public void initializeInteractor(Interactor interactor, CommandNode commandNode) throws Exception {
		String participantName = NameUtil.uncapName(AriesASTContext.participantNode.getName());
		String serviceName = NameUtil.uncapName(AriesASTContext.receiveNode.getName());
		initializeInteractor(interactor, commandNode, participantName, serviceName);
	}
	
	public void initializeInteractor(Interactor interactor, CommandNode commandNode, String participantName, String serviceName) {
		interactor.setRole(participantName);
		interactor.setTarget(participantName);
		interactor.setService(serviceName);
		interactor.setChannel(serviceName);
		interactor.setName(participantName);
		interactor.setLink(participantName);
		interactor.setTransacted(true);
		interactor.setTimeToLive(1000L);
		
//		TimeoutNode timeoutNode = commandNode.getTimeoutNode();
//		if (timeoutNode != null) {
//			String textValue = timeoutNode.getValue();
//			long value = Long.parseLong(textValue);
//			String unit = timeoutNode.getUnit();
//			if (unit.equalsIgnoreCase("s"))
//				value *= 1000;
//			else if (unit.equalsIgnoreCase("m"))
//				value *= 1000 * 60;
//			else if (unit.equalsIgnoreCase("h"))
//				value *= 1000 * 60 * 60;
//			interactor.setTimeout(value);
//		}
	}

	public List<Parameter> buildParametersFromParameterNodes(CommandNode commandNode) throws Exception {
		return buildParametersFromParameterNodes(commandNode, null);
	}
	
	public List<Parameter> buildParametersFromParameterNodes(CommandNode commandNode, String suffix) throws Exception {
		List<Parameter> arguments = new ArrayList<Parameter>();
		List<ParameterNode> parameterNodes = commandNode.getParameterNodes();
		if (parameterNodes.size() == 0)
			return arguments;
		
//		ParameterNode parameterNode = parameterNodes.get(0);
////		parameterName = resolveVariableReference(parameterName);
//		String variableReference = resolveVariableReference(parameterNode.getName());
//		String variableReferenceFields = null;
//		int indexOfDot = variableReference.indexOf(".");
//		if (indexOfDot > -1) {
//			variableReferenceFields = variableReference.substring(indexOfDot+1);
//			variableReference = variableReference.substring(0, indexOfDot);
//		}

//		Type parameterElement = context.findTypeByName(variableReference+suffix);
//		
//		if (suffix != null && 
//			parameterNodes.size() == 1 && 
//			!parameterName.endsWith(suffix) && 
//			parameterElement != null &&
//			(messageNode == null || parameterName.equals(messageNode.getName()))) {
//				arguments.add(parameterName+suffix);
//				
//		} else {
		
			Iterator<ParameterNode> iterator = parameterNodes.iterator();
			while (iterator.hasNext()) {
				ParameterNode parameterNode = iterator.next();

				String parameterName = parameterNode.getName();
				String variableReference = resolveVariableReference(parameterName);
				String variableReferenceFields = null;
				int indexOfDot = variableReference.indexOf(".");
				if (indexOfDot > -1) {
					variableReferenceFields = variableReference.substring(indexOfDot+1);
					variableReference = variableReference.substring(0, indexOfDot);
				}
				
				Type parameterElement = context.findTypeByName(variableReference + suffix);
				if (parameterElement == null)
					parameterElement = context.getElementByName(variableReference + suffix);
				if (!StringUtils.isEmpty(suffix) && parameterElement != null && !variableReference.endsWith(suffix))
					variableReference += suffix;
				if (variableReferenceFields != null)
					variableReference += "." + variableReferenceFields;
				String qualifiedName = ExpressionUtil.parseExpression(variableReference);

				if (parameterNode.getTypeNode() == null && parameterName.equals("cause")) {
					TypeNode typeNode = new TypeNode();
					typeNode.setName("cause");
					typeNode.setType("Throwable");
					typeNode.setConstruct("item");
					typeNode.setText("TYPE");
					parameterNode.setTypeNode(typeNode);
				}

				if (parameterNode.getTypeNode() == null && parameterName.equals("reason")) {
					TypeNode typeNode = new TypeNode();
					typeNode.setName("reason");
					typeNode.setType("String");
					typeNode.setConstruct("item");
					typeNode.setText("TYPE");
					parameterNode.setTypeNode(typeNode);
				}

				if (parameterNode.getTypeNode() == null) {
					TypeNode typeNode = getTypeNodeFromCommandStack(parameterNode.getCommandStack(), qualifiedName, "Message");
//					if (typeNode == null)
//						typeNode = getTypeNodeFromCommandStack(parameterNode.getCommandStack(), qualifiedName, "Message");
					Assert.notNull(typeNode, "Variable not found: "+parameterName);
					parameterNode.setTypeNode(typeNode);
				}

				if (parameterElement == null)
					parameterElement = context.getElementByName(parameterNode.getType());
				if (parameterElement == null)
					parameterElement = context.getElementByName(parameterNode.getType() + "Message");
				
//				if (parameterElement == null)
//					System.out.println();
//				if (parameterNode.getTypeNode() == null)
//					System.out.println();
//				if (parameterNode.getType().endsWith("int"))
//					System.out.println();
				
				String parameterType = null;
				if (parameterElement != null) {
					Assert.notNull(parameterElement, "Element not found: "+parameterName);
					parameterType = parameterElement.getType();
				} else {
					parameterType = TypeUtil.getDefaultType(parameterNode.getType());
				}

				Parameter newParameter = new Parameter();
				newParameter.setName(qualifiedName);
				newParameter.setType(parameterType);
				newParameter.setConstruct(parameterNode.getConstruct());
				arguments.add(newParameter);
			}
//		}
		return arguments;
	}
	
	public String getTypeFromCommandStack(String parameterName, Stack<CommandNode> commandStack) {
		Iterator<CommandNode> iterator = commandStack.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			String type = getTypeFromCommandNode(parameterName, commandNode);
			if (type != null)
				return type;
		}
		return null;
	}

	public String getTypeFromCommandNode(String parameterName, CommandNode commandNode) {
		List<ParameterNode> parameterNodes = commandNode.getParameterNodes();
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = (ParameterNode) iterator.next();
			TypeNode typeNode = parameterNode.getTypeNode();
			if (parameterNode.getName().equals(parameterName) && typeNode != null) {
				String type = TypeUtil.getTypeFromClass(typeNode.getType());
				return type;
			}
		}
		iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = (ParameterNode) iterator.next();
			TypeNode typeNode = parameterNode.getTypeNode();
			if (typeNode != null) {
				String typeName = typeNode.getType();
				Element element = context.getElementByName(typeName);
				if (element != null) {
					Field field = ElementUtil.getField(element, parameterName);
					if (field != null) {
						return field.getType();
					}
				}
				element = context.getElementByName(typeName + "Message");
				if (element != null) {
					Field field = ElementUtil.getField(element, parameterName);
					if (field != null) {
						return field.getType();
					}
				}
				return typeName;
			}
		}
		return null;
	}

	public TypeNode getTypeNodeFromCommandStack(Stack<CommandNode> commandStack, String parameterName, String suffix) {
		Iterator<CommandNode> iterator = commandStack.iterator();
		//TODO get correct order here 
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			TypeNode typeNode = getTypeNodeFromCommandNode(commandNode, parameterName, suffix);
			if (typeNode != null)
				return typeNode;
		}
		return null;
	}

	public TypeNode getTypeNodeFromCommandNode(CommandNode commandNode, String variableName, String suffix) {
		TypeNode typeNode = getTypeNodeFromParameterNodes(commandNode.getParameterNodes(), variableName, suffix);
		if (typeNode != null)
			return typeNode;
		List<ParameterNode> parameterNodes = AriesASTContext.receiveNode.getParameterNodes();
		typeNode = getTypeNodeFromParameterNodes(parameterNodes, variableName, suffix);
		if (typeNode != null)
			return typeNode;
		if (commandNode instanceof ExpressionNode) {
			ExpressionNode expressionNode = (ExpressionNode) commandNode;
			if (expressionNode.getExpressionNode() != null) {
				expressionNode = expressionNode.getExpressionNode();
				typeNode = getTypeNodeFromCommandNode(expressionNode, variableName, suffix);
				return typeNode;
			}
		}
		return null;
	}
	
	public TypeNode getTypeNodeFromParameterNodes(List<ParameterNode> parameterNodes, String parameterName, String suffix) {
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = (ParameterNode) iterator.next();
			TypeNode typeNode = parameterNode.getTypeNode();
			if (parameterNode.getName().equals(parameterName) && typeNode != null) {
				return typeNode;
			}
		}
		iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = (ParameterNode) iterator.next();
			TypeNode typeNode = parameterNode.getTypeNode();
			if (typeNode != null) {
				String typeName = typeNode.getType();
				
				//Taking this part out - it is irrelevant
				//Element element = context.getElementByName(typeName);
				//if (element != null) {
				//	Field field = ElementUtil.getField(element, parameterName);
				//	if (field != null) {
				//		TypeNode newTypeNode = new TypeNode();
				//		newTypeNode.setName(parameterName);
				//		newTypeNode.setType(TypeUtil.getClassName(field.getType()));
				//		newTypeNode.setConstruct(field.getStructure());
				//		newTypeNode.setText("TYPE");
				//		return newTypeNode;
				//	}
				//}
				
				Element element = context.getElementByName(typeName + suffix);
				if (element != null) {
					Field field = ElementUtil.getField(element, parameterName);
					if (field != null) {
						TypeNode newTypeNode = new TypeNode();
						newTypeNode.setName(parameterName);
						newTypeNode.setType(TypeUtil.getClassName(field.getType()));
						newTypeNode.setConstruct(field.getStructure());
						newTypeNode.setText("TYPE");
						//TODO newTypeNode.getTypeArguments().add();
						return newTypeNode;
					}
				}
				//return typeNode;
			}
		}
		return null;
	}

	public String getTypeFromField(String parameterName, Element element) {
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (field.getName().equalsIgnoreCase(parameterName)) {
				return field.getType();
			}
		}
		return null;
	}

	public String resolveVariableReference(String referenceText) throws Exception {
		if (referenceText.startsWith("$message") || referenceText.startsWith("$request") || referenceText.startsWith("$response")) {
			String incomingType = referenceText; 
			//this is only temporary til we learn more...
			if (referenceText.startsWith("$request")) {
				incomingType = AriesASTContext.receiveNode.getParameterNodes().get(0).getName();
				//incomingType = "message";
				if (!incomingType.endsWith("Message"))
					incomingType += "Message";

			} else if (referenceText.startsWith("$response")) {
				incomingType = AriesASTContext.messageNode.getName();
				//incomingType = "message";
				if (!incomingType.endsWith("Message"))
					incomingType += "Message";
				
				boolean isParameter = false;
				//TODO fix this context state (if needed)... now is not set anymore
				List<Parameter> parameters = AriesASTContext.operation.getParameters();
				Iterator<Parameter> iterator = parameters.iterator();
				while (iterator.hasNext()) {
					Parameter parameter = iterator.next();
					//System.out.println(parameter.getType());
					String parameterType = parameter.getType();
					if (parameterType.contains("{"))
						parameterType = TypeUtil.getClassName(parameter.getType());
					if (parameterType.equalsIgnoreCase(incomingType)) {
						isParameter = true;
						break;
					}
				}
			
				//if (isParameter)
				//	incomingType = "message";

			} else if (referenceText.startsWith("$message")) {
				if (AriesASTContext.messageNode != null)
					incomingType = AriesASTContext.messageNode.getName();
				if (AriesASTContext.methodNode != null)
					incomingType = AriesASTContext.methodNode.getParameterNodes().get(0).getName();
				else incomingType = AriesASTContext.receiveNode.getParameterNodes().get(0).getName();
				//incomingType = "message";
				if (!incomingType.endsWith("Message"))
					incomingType += "Message";
			}
			
			String variableText = NameUtil.uncapName(incomingType);
			int indexOfDot = referenceText.indexOf(".");
			if (indexOfDot != -1) {
				referenceText = referenceText.substring(indexOfDot+1);
				referenceText = variableText+"."+referenceText;
			} else referenceText = variableText;
		}
		return referenceText;
	}

	public DefinitionCommand buildDefinitionCommandFromCommandNode(CommandNode commandNode) throws Exception {
		return buildDefinitionCommandFromCommandNode(2, commandNode);
	}
	
	public DefinitionCommand buildDefinitionCommandFromCommandNode(int indent, CommandNode commandNode) {
		DefinitionNode definitionNode = (DefinitionNode) commandNode;
		
		String typeName = definitionNode.getPrimaryNode().getTypeNode().getType();
		Element elementByName = context.getElementByName(typeName);
//		if (elementByName == null)
//			System.out.println();
		
		Definition definition = new Definition();
		Type type = new Type();
		type.setName(typeName);
		type.setType(typeName);
		type.setStructure(definitionNode.getPrimaryNode().getTypeNode().getConstruct());
		//type.setKey(value);
		definition.setType(type);
		definition.setName(definitionNode.getPrimaryNode().getName());

		String sourceCode = CodeUtil.getSourceFromTokens(definitionNode.getTokens());
		//System.out.println(">>> "+sourceCode);

		ExpressionNode expressionNode = definitionNode.getExpressionNode();
		List<String> tokens = expressionNode.getTokens();
		if (tokens.size() > 0 && tokens.get(0).equals("new"))
			definition.setIsNew(true);
		definition.setTarget(expressionNode.getObjectName());
		definition.getSelectors().addAll(expressionNode.getSelectorChain());
		ExpressionNode subExpressionNode = expressionNode.getExpressionNode();
		if (subExpressionNode != null)
			definition.getParameters().addAll(subExpressionNode.getTokens());
		
		DefinitionCommand definitionCommand = new DefinitionCommand();
		definitionCommand.setDefinition(definition);
		definitionCommand.setName(CommandName.EXPRESSION);
		definitionCommand.setType("DEFINITION");
		definitionCommand.setIndent(indent);
		
		addLocalDefinition(definition);
		return definitionCommand;
	}

}
