package org.aries.ast;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aries.ast.node.BlockNode;
import org.aries.ast.node.BranchNode;
import org.aries.ast.node.CacheNode;
import org.aries.ast.node.ChannelNode;
import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ConditionNode;
import org.aries.ast.node.EndpointNode;
import org.aries.ast.node.ExceptionNode;
import org.aries.ast.node.ExecuteNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.GroupNode;
import org.aries.ast.node.InvokeNode;
import org.aries.ast.node.ItemNode;
import org.aries.ast.node.ListenNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.MethodNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.OptionNode;
import org.aries.ast.node.ParameterNode;
import org.aries.ast.node.ParticipantNode;
import org.aries.ast.node.PersistanceNode;
import org.aries.ast.node.PostNode;
import org.aries.ast.node.PrincipleNode;
import org.aries.ast.node.PropertyNode;
import org.aries.ast.node.PublishNode;
import org.aries.ast.node.ReceiveNode;
import org.aries.ast.node.ReplyNode;
import org.aries.ast.node.RoleNode;
import org.aries.ast.node.SendNode;
import org.aries.ast.node.StatementNode;
import org.aries.ast.node.SubscribeNode;
import org.aries.ast.node.ThrowsNode;
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

import aries.codegen.util.Buf;


public class ArielASTPrinter {

	private boolean addNewLines = false;
	
	private boolean useShortCuts = false;
	
	
	public ArielASTPrinter() {
		//nothing for now
	}
	
	public ArielASTPrinter(boolean addNewLines) {
		this.addNewLines = addNewLines;
	}

//	public void enableAddNewLines() {
//		addNewLines = true;
//	}
	
	public String getSource(NetworkNode protocolNode) {
		return getSourceForProtocolNode(protocolNode, 0);
	}

	public String getSourceForProtocolNode(NetworkNode protocolNode, int indent) {
		Buf buf = new Buf();
		buf.putLine(indent, "protocol "+protocolNode.getName()+" {");
		buf.put(printPropertyNodes(indent, protocolNode.getPropertyNodes()));
		buf.put(printRoleNodes(indent, protocolNode.getRoleNodes()));
		buf.put(printGroupNodes(indent, protocolNode.getGroupNodes()));
		buf.put(printChannelNodes(indent, protocolNode.getChannelNodes()));
		buf.put(printParticipantNodes(indent, protocolNode.getParticipantNodes()));
		buf.put(printCacheNodes(indent, protocolNode.getCacheNodes()));
		buf.put(printPersistanceNodes(indent, protocolNode.getPersistanceNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printBlockNode(int indent, BlockNode blockNode) {
		Buf buf = new Buf();
		List<CommandNode> commandNodes = blockNode.getCommandNodes();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			buf.put(printCommandNode(indent, commandNode));
//			String value = commandNode.getValue();
//			if (!value.endsWith(";"))
//				value += ";";
//			buf.putLine(indent, value);
		}
		return buf.get();
	}

	public String printBranchNode(int indent, BranchNode branchNode) {
		Buf buf = new Buf();
		String name = branchNode.getName();
		if (name == null)
			buf.put(indent, "branch:");
		else
			buf.put(indent, "branch "+branchNode.getName()+":");
		boolean oneLineStyle = useShortCuts && branchNode.getCommandNodes().size() == 1;
		if (oneLineStyle)
			indent = 0;
		if (!oneLineStyle)
			buf.putLine(" {");
		//buf.putLine(printParameterNodes(branchNode.getParameterNodes(), indent)+" {");
		buf.put(printPropertyNodes(indent, branchNode.getPropertyNodes()));
		buf.put(printCommandNodes(indent, branchNode.getCommandNodes()));
		if (!oneLineStyle)
			buf.putLine(indent, "}");
		return buf.get();
	}

	public String printCacheNodes(int indent, List<CacheNode> cacheNodes) {
		Buf buf = new Buf();
		Iterator<CacheNode> iterator = cacheNodes.iterator();
		while (iterator.hasNext()) {
			CacheNode cacheNode = iterator.next();
			buf.put(printCacheNode(indent+1, cacheNode));
		}
		return buf.get();
	}

	public String printCacheNode(int indent, CacheNode cacheNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.putLine(indent, "cache "+cacheNode.getName()+" {");
		buf.put(printPropertyNodes(indent, cacheNode.getPropertyNodes()));
		buf.put(printItemNodes(indent+1, cacheNode.getItemNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printChannelNodes(int indent, List<ChannelNode> channelNodes) {
		Buf buf = new Buf();
		Iterator<ChannelNode> iterator = channelNodes.iterator();
		while (iterator.hasNext()) {
			ChannelNode channelNode = iterator.next();
			buf.put(printChannelNode(indent+1, channelNode));
		}
		return buf.get();
	}

	public String printChannelNode(int indent, ChannelNode channelNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "channel "+channelNode.getName());
		return buf.get();
	}

	public String printCommandNodes(int indent, List<CommandNode> commandNodes) {
		Buf buf = new Buf();
		int count = commandNodes.size();
		boolean oneLineStyle = useShortCuts && count == 1 && commandNodes.get(0) instanceof EndpointNode == false;
		if (oneLineStyle)
			indent = -1;
			//buf.put(printCommandNode(commandNodes.get(0), 0));
		if (oneLineStyle)
			buf.put(" ");
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			buf.put(printCommandNode(indent+1, commandNode));
		}
		return buf.get();
	}

	public String printCommandNode(int indent, CommandNode commandNode) {
		Buf buf = new Buf();
		String text = commandNode.getText();
		//System.out.println(">>>"+text);
		if (text.equals("branch")) {
			buf.put(printBranchNode(indent, (BranchNode) commandNode));
		} else if (text.equals("execute")) {
			buf.put(printExecuteNode(indent, (ExecuteNode) commandNode));
		} else if (text.equals("method")) {
			buf.put(printMethodNode(indent, (MethodNode) commandNode));
		} else if (text.equals("listen")) {
			buf.put(printListenNode(indent, (ListenNode) commandNode));
		} else if (text.equals("receive")) {
			buf.put(printReceiveNode(indent, (ReceiveNode) commandNode));
		} else if (text.equals("post")) {
			buf.put(printPostNode(indent, (PostNode) commandNode));
		} else if (text.equals("send")) {
			buf.put(printSendNode(indent, (SendNode) commandNode));
		} else if (text.equals("reply")) {
			buf.put(printReplyNode(indent, (ReplyNode) commandNode));
		} else if (text.equals("invoke")) {
			buf.put(printInvokeNode(indent, (InvokeNode) commandNode));
		} else if (text.equals("subscribe")) {
			buf.put(printSubscribeNode(indent, (SubscribeNode) commandNode));
		} else if (text.equals("publish")) {
			buf.put(printPublishNode(indent, (PublishNode) commandNode));
		} else if (text.equals("STATEMENT")) {
			buf.put(printStatementNode(indent, (StatementNode) commandNode));
		} else if (text.equals("EXPR")) {
			buf.put(printStatementNode(indent, (StatementNode) commandNode));
		} else if (text.equals("BLOCK")) {
			buf.put(printBlockNode(indent, (BlockNode) commandNode));
		} else if (AriesASTUtil.isStatement(text)) {
			buf.put(printStatementNode(indent, (StatementNode) commandNode));
		} else if (text.equals("exception")) {
			buf.put(printExceptionNode(indent, (ExceptionNode) commandNode));
		} else if (text.equals("timeout")) {
			buf.put(printTimeoutNode(indent, (TimeoutNode) commandNode));
		} else {
			String name = commandNode.getName();
			if (AriesASTUtil.isCommand(text))
				buf.put(indent, text+" "+name);
			else buf.put(indent, name);
			List<ParameterNode> parameterNodes = commandNode.getParameterNodes();
			buf.put(printParameterNodes(indent, parameterNodes));
			buf.putLine(";");
		}
		return buf.get();
	}

	public String printConditionNode(ConditionNode conditionNode) {
		return printConditionNode(0, conditionNode);
	}
	
	public String printConditionNode(int indent, ConditionNode conditionNode) {
		Buf buf = new Buf();
		String source = AriesASTUtil.getNodeSource(conditionNode);
		buf.put(indent, source);
		return buf.get();
	}

	public String printExceptionNodes(int indent, List<ExceptionNode> exceptionNodes) {
		Buf buf = new Buf();
		Iterator<ExceptionNode> iterator = exceptionNodes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ExceptionNode exceptionNode = iterator.next();
			if (i > 0)
				buf.putLine(indent);
			buf.put(printExceptionNode(indent, exceptionNode));
		}
		return buf.get();
	}

	public String printExceptionNode(int indent, ExceptionNode exceptionNode) {
		Buf buf = new Buf();
		buf.put(indent, "exception:");
		boolean oneLineStyle = useShortCuts && exceptionNode.getCommandNodes().size() == 1;
		if (oneLineStyle)
			indent = 0;
		if (!oneLineStyle)
			buf.putLine(" {");
		//buf.putLine(printParameterNodes(branchNode.getParameterNodes(), indent)+" {");
		buf.put(printPropertyNodes(indent, exceptionNode.getPropertyNodes()));
		buf.put(printCommandNodes(indent, exceptionNode.getCommandNodes()));
		if (!oneLineStyle)	
			buf.putLine(indent, "}");
		return buf.get();
	}
	
	public String printExecuteNode(int indent, ExecuteNode executeNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		String name = executeNode.getName();
		if (name == null) {
			buf.putLine(indent, "execute {");
		} else {
			buf.put(indent, "execute then "+executeNode.getName());
			buf.putLine(printParameterNodes(indent, executeNode.getParameterNodes())+" {");
		}
		buf.put(printPropertyNodes(indent, executeNode.getPropertyNodes()));
		//TODO put a newline here
		buf.put(printCommandNodes(indent, executeNode.getCommandNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printExpressionNode(ExpressionNode expressionNode) {
		return printExpressionNode(0, expressionNode);
	}
	
	public String printExpressionNode(int indent, ExpressionNode expressionNode) {
		Buf buf = new Buf();
		String source = AriesASTUtil.getNodeSource(expressionNode);
		buf.put(indent, source);
		return buf.get();
	}

	public String printGroupNodes(int indent, List<GroupNode> groupNodes) {
		Buf buf = new Buf();
		buf.putLine(indent);
		Iterator<GroupNode> iterator = groupNodes.iterator();
		while (iterator.hasNext()) {
			GroupNode groupNode = iterator.next();
			buf.put(printGroupNode(indent+1, groupNode));
		}
		return buf.get();
	}

	public String printGroupNode(int indent, GroupNode groupNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "group "+groupNode.getName());
		return buf.get();
	}
	
	public String printInvokeNode(int indent, InvokeNode invokeNode) {
		Buf buf = new Buf();
		buf.put(indent, "invoke "+invokeNode.getName());
		List<ParameterNode> parameterNodes = invokeNode.getParameterNodes();
		buf.putLine(printParameterNodes(indent, parameterNodes)+" {");
		buf.put(printPropertyNodes(indent, invokeNode.getPropertyNodes()));
		buf.put(printSpacingIfNeeded(indent, invokeNode.getPropertyNodes().size() > 0));
		buf.put(printMessageNodes(indent+1, invokeNode.getMessageNodes()));
		buf.put(printSpacingIfNeeded(indent, invokeNode.getMessageNodes().size() > 0));
		buf.put(printExceptionNodes(indent+1, invokeNode.getExceptionNodes()));
		buf.put(printSpacingIfNeeded(indent, invokeNode.getExceptionNodes().size() > 0));
		buf.put(printTimeoutNodes(indent+1, invokeNode.getTimeoutNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}
	
	public String printItemNodes(int indent, List<ItemNode> itemNodes) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.putLine(indent, "items {");
		Iterator<ItemNode> iterator = itemNodes.iterator();
		while (iterator.hasNext()) {
			ItemNode itemNode = iterator.next();
			buf.put(printItemNode(indent+1, itemNode));
		}
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printItemNode(int indent, ItemNode itemNode) {
		Buf buf = new Buf();
		String construct = itemNode.getConstruct();
		TypeNode typeNode = itemNode.getTypeNode();
		if (construct.equals("item")) {
			buf.putLine(indent, typeNode.getType()+" "+itemNode.getName()+";");
		} else if (construct.equals("list")) {
			buf.putLine(indent, "List<"+typeNode.getType()+"> "+itemNode.getName()+";");
		} else if (construct.equals("set")) {
			buf.putLine(indent, "Set<"+typeNode.getType()+"> "+itemNode.getName()+";");
		} else if (construct.equals("map")) {
			buf.putLine(indent, "Map<"+typeNode.getKey()+", "+typeNode.getType()+"> "+itemNode.getName()+";");
		}
		return buf.get();
	}

	public String printListenNode(int indent, ListenNode listenNode) {
		Buf buf = new Buf();
		buf.put(indent, "listen "+listenNode.getName());
		buf.putLine(printParameterNodes(indent, listenNode.getParameterNodes())+" {");
		buf.put(printPropertyNodes(indent, listenNode.getPropertyNodes()));
		//TODO put a newline here
		buf.put(printCommandNodes(indent, listenNode.getCommandNodes()));
		//buf.put(printOptionNodes(listenNode.getOptionNodes(), indent));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printMessageNodes(int indent, List<MessageNode> messageNodes) {
		Buf buf = new Buf();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			MessageNode messageNode = iterator.next();
			if (i > 0)
				buf.putLine(indent);
			buf.put(printMessageNode(indent, messageNode));
		}
		return buf.get();
	}

	public String printMessageNode(int indent, MessageNode messageNode) {
		Buf buf = new Buf();
		buf.put(indent, "message "+messageNode.getName());
		buf.put(printParameterNodes(indent, messageNode.getParameterNodes())+":");
		boolean oneLineStyle = useShortCuts && messageNode.getCommandNodes().size() == 1;
		if (oneLineStyle)
			indent = 0;
		if (!oneLineStyle)
			buf.putLine(" {");
		buf.put(printCommandNodes(indent, messageNode.getCommandNodes()));
		if (!oneLineStyle)
			buf.putLine(indent, "}");
		return buf.get();
	}

	public String printMethodNodes(int indent, List<MethodNode> methodNodes) {
		Buf buf = new Buf();
		Iterator<MethodNode> iterator = methodNodes.iterator();
		while (iterator.hasNext()) {
			MethodNode methodNode = iterator.next();
			buf.put(printMethodNode(indent+1, methodNode));
		}
		return buf.get();
	}

	public String printMethodNode(int indent, MethodNode methodNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.put(indent, methodNode.getName());
		buf.putLine(printParameterNodes(indent, methodNode.getParameterNodes(), true)+" {");
		buf.put(printPropertyNodes(indent, methodNode.getPropertyNodes()));
		buf.put(printCommandNodes(indent, methodNode.getCommandNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printOptionNodes(int indent, List<OptionNode> optionNodes) {
		Buf buf = new Buf();
		Iterator<OptionNode> iterator = optionNodes.iterator();
		while (iterator.hasNext()) {
			OptionNode optionNode = iterator.next();
			buf.put(printOptionNode(indent+1, optionNode));
		}
		return buf.get();
	}

	public String printOptionNode(int indent, OptionNode optionNode) {
		Buf buf = new Buf();
		buf.put(indent, "option");
		if (optionNode.getName() != null)
			buf.put(" "+optionNode.getName());
		buf.put(printParameterNodes(indent, optionNode.getParameterNodes())+":");
		boolean oneLineStyle = useShortCuts && optionNode.getCommandNodes().size() == 1;
		if (oneLineStyle)
			indent = 0;
		if (!oneLineStyle)
			buf.putLine(" {");
		buf.put(printCommandNodes(indent, optionNode.getCommandNodes()));
		if (!oneLineStyle)
			buf.putLine(indent, "}");
		return buf.get();
	}

	public String printParameterNodes(int indent, List<ParameterNode> parameterNodes) {
		return printParameterNodes(indent, parameterNodes, false);
	}
	
	public String printParameterNodes(int indent, List<ParameterNode> parameterNodes, boolean forceParens) {
		Buf buf = new Buf();
		forceParens |= parameterNodes.size() > 0;
		if (forceParens)
			buf.put("(");
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			if (i > 0)
				buf.put(", ");
			ParameterNode parameterNode = iterator.next();
			buf.put(printParameterNode(parameterNode));
		}
		if (forceParens)
			buf.put(")");
		return buf.get();
	}

	public String printParameterNode(ParameterNode parameterNode) {
		Buf buf = new Buf();
		buf.put(parameterNode.getName());
		return buf.get();
	}

	public String printParticipantNodes(int indent, List<ParticipantNode> participantNodes) {
		Buf buf = new Buf();
		Iterator<ParticipantNode> iterator = participantNodes.iterator();
		while (iterator.hasNext()) {
			ParticipantNode participantNode = iterator.next();
			buf.put(printParticipantNode(indent+1, participantNode));
		}
		return buf.get();
	}

	public String printParticipantNode(int indent, ParticipantNode participantNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.putLine(indent, "participant "+participantNode.getName()+" {");
		buf.put(printPropertyNodes(indent, participantNode.getPropertyNodes()));
		buf.put(printCommandNodes(indent, participantNode.getCommandNodes()));
		buf.put(printCacheNodes(indent, participantNode.getCacheNodes()));
		buf.put(printPersistanceNodes(indent, participantNode.getPersistanceNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printPersistanceNodes(int indent, List<PersistanceNode> persistanceNodes) {
		Buf buf = new Buf();
		Iterator<PersistanceNode> iterator = persistanceNodes.iterator();
		while (iterator.hasNext()) {
			PersistanceNode persistanceNode = iterator.next();
			buf.put(printPersistanceNode(indent+1, persistanceNode));
		}
		return buf.get();
	}

	public String printPersistanceNode(int indent, PersistanceNode persistanceNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.putLine(indent, "persistance "+persistanceNode.getName()+" {");
		buf.put(printPropertyNodes(indent, persistanceNode.getPropertyNodes()));
		buf.put(printItemNodes(indent+1, persistanceNode.getItemNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printPostNode(int indent, PostNode postNode) {
		Buf buf = new Buf();
		buf.put(indent, "post "+postNode.getName());
		buf.putLine(printParameterNodes(indent, postNode.getParameterNodes())+";");
		return buf.get();
	}

	public String printPrincipleNodes(int indent, List<PrincipleNode> principleNodes) {
		Buf buf = new Buf();
		Iterator<PrincipleNode> iterator = principleNodes.iterator();
		while (iterator.hasNext()) {
			PrincipleNode principleNode = iterator.next();
			buf.put(printPrincipleNode(indent+1, principleNode));
		}
		return buf.get();
	}
	
	public String printPrincipleNode(int indent, PrincipleNode principleNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "principle "+principleNode.getName());
		return buf.get();
	}

	public String printPropertyNodes(int indent, Set<PropertyNode> propertyNodes) {
		Buf buf = new Buf();
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			buf.put(printPropertyNode(indent+1, propertyNode));
		}
		return buf.get();
	}

	public String printPropertyNode(int indent, PropertyNode propertyNode) {
		Buf buf = new Buf();
		buf.putLine(indent, propertyNode.getText()+" "+propertyNode.getName()+"(\""+propertyNode.getValue()+"\");");
		return buf.get();
	}
	
	public String printPublishNode(int indent, PublishNode publishNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "publish "+publishNode.getName());
		return buf.get();
	}

	public String printReceiveNode(int indent, ReceiveNode receiveNode) {
		Buf buf = new Buf();
		buf.putLine(indent);
		buf.put(indent, "receive "+receiveNode.getName());
		buf.put(printParameterNodes(indent, receiveNode.getParameterNodes()));
		buf.put(printThrowsNode(receiveNode.getThrowsNode()));
		buf.putLine(" {");
		buf.put(printPropertyNodes(indent, receiveNode.getPropertyNodes()));
		buf.put(printSpacingIfNeeded(indent, receiveNode.getPropertyNodes().size() > 0));
		buf.put(printCommandNodes(indent, receiveNode.getCommandNodes()));
		buf.put(printOptionNodes(indent, receiveNode.getOptionNodes()));
		//buf.put(printEndpointNodes(receiveNode.getEndpointNodes(), indent));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printReplyNode(int indent, ReplyNode replyNode) {
		Buf buf = new Buf();
		buf.put(indent, "reply "+replyNode.getMessageName());
		buf.putLine(printParameterNodes(indent, replyNode.getParameterNodes())+";");
		return buf.get();
	}

	public String printRoleNodes(int indent, List<RoleNode> roleNodes) {
		StringBuffer buf = new StringBuffer();
		Iterator<RoleNode> iterator = roleNodes.iterator();
		while (iterator.hasNext()) {
			RoleNode roleNode = iterator.next();
			printRoleNode(indent+1, roleNode);
		}
		return buf.toString();
	}

	public String printRoleNode(int indent, RoleNode roleNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "role "+roleNode.getName()+" {");
		buf.put(printPrincipleNodes(indent, roleNode.getPrincipleNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printSendNode(int indent, SendNode sendNode) {
		Buf buf = new Buf();
		buf.put(indent, "send "+sendNode.getDestination());
		buf.putLine(printParameterNodes(indent, sendNode.getParameterNodes())+";");
		return buf.get();
	}

	public <T extends CommandNode> String printStatementNode(int indent, T statementNode) {
		Buf buf = new Buf();
		String text = statementNode.getText();
		//String val = statementNode.getValue();
		//System.out.println(">>>"+val);
		if (text.equals("if")) {
			buf.put(printIfNode(indent, (IfNode) statementNode));
		} else if (text.equals("while")) {
			buf.put(printWhileNode(indent, (WhileNode) statementNode));
		} else if (text.equals("for")) {
			buf.put(printForNode(indent, (ForNode) statementNode));
		} else if (text.equals("do")) {
			buf.put(printDoNode(indent, (DoNode) statementNode));
		} else if (text.equals("continue")) {
			buf.put(printContinueNode(indent, (ContinueNode) statementNode));
		} else if (text.equals("break")) {
			buf.put(printBreakNode(indent, (BreakNode) statementNode));
		} else if (text.equals("throw")) {
			buf.put(printThrowNode(indent, (ThrowNode) statementNode));
		} else if (text.equals("return")) {
			buf.put(printReturnNode(indent, (ReturnNode) statementNode));
		} else if (text.equals("unnamed")) {
			buf.put(printUnnamedNode(indent, (UnnamedNode) statementNode));
		} else {
			String source = AriesASTUtil.getNodeSource(statementNode);
			if (!source.endsWith(";"))
				source += ";";
			buf.put(indent, source);
			if (addNewLines)
				buf.putLine("");
		}
		return buf.get();
	}
	
	public String printIfNode(int indent, IfNode ifNode) {
		return printIfNode(indent, ifNode, false);
	}

	public String printElseIfNode(int indent, IfNode ifNode) {
		return printIfNode(indent, ifNode, true);
	}

	public String printIfNode(int indent, IfNode ifNode, boolean isElse) {
		Buf buf = new Buf();
		if (!isElse)
			buf.putLine(indent, "if ("+printConditionNode(ifNode.getConditionNode())+") {");
		else buf.putLine(indent, "} else if ("+printConditionNode(ifNode.getConditionNode())+") {");
		buf.put(printStatementNodes(indent+1, ifNode.getStatementNodes()));
		
		List<IfNode> elseIfNodes = ifNode.getElseIfNodes();
		Iterator<IfNode> iterator = elseIfNodes.iterator();
		while (iterator.hasNext()) {
			IfNode elseIfNode = iterator.next();
			buf.put(printElseIfNode(indent, elseIfNode));
		}
		UnnamedNode elseNode = ifNode.getElseNode();
		if (elseNode != null) {
			buf.putLine(indent, "} else {");
			buf.put(printStatementNodes(indent+1, elseNode.getStatementNodes()));
			buf.putLine(indent, "}");
		} else
			buf.putLine(indent, "}");
		return buf.get();
	}


	public String printUnnamedNode(int indent, UnnamedNode elseNode) {
		return printStatementNodes(indent, elseNode.getStatementNodes());
	}
	
	public <T extends CommandNode> String printStatementNodes(int indent, List<T> statementNodes) {
		Buf buf = new Buf();
		Iterator<T> iterator = statementNodes.iterator();
		while (iterator.hasNext()) {
			T statementNode = iterator.next();
			String text = printStatementNode(indent, statementNode);
			buf.put(text);
		}
		return buf.get();
	}

	public String printWhileNode(int indent, WhileNode whileNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "while ("+printExpressionNode(whileNode.getExpressionNode())+") {");
		buf.put(printCommandNodes(indent, whileNode.getCommandNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printForNode(int indent, ForNode forNode) {
		Buf buf = new Buf();
		String startExpression = printExpressionNode(forNode.getStartExpressionNode());
		String conditionExpression = printExpressionNode(forNode.getConditionExpressionNode());
		String updateExpression = printExpressionNode(forNode.getUpdateExpressionNode());
		buf.putLine(indent, "for ("+startExpression+"; "+conditionExpression+"; "+updateExpression+") {");
		buf.put(printCommandNodes(indent, forNode.getCommandNodes()));
		buf.putLine(indent, "}");
		return buf.get();
	}

	public String printDoNode(int indent, DoNode statementNode) {
		return null;
	}

	public String printContinueNode(int indent, ContinueNode statementNode) {
		return null;
	}

	public String printBreakNode(int indent, BreakNode statementNode) {
		return null;
	}

	public String printThrowNode(int indent, ThrowNode statementNode) {
		return null;
	}

	public String printReturnNode(int indent, ReturnNode statementNode) {
		return null;
	}
	
	public String printSubscribeNode(int indent, SubscribeNode subscribeNode) {
		Buf buf = new Buf();
		buf.putLine(indent, "subscribe "+subscribeNode.getName());
		return buf.get();
	}

	public String printThrowsNode(ThrowsNode throwsNode) {
		if (throwsNode == null)
			return "";
		List<String> exceptionTypes = throwsNode.getExceptionTypes();
		if (exceptionTypes.size() == 0)
			return "";
		Buf buf = new Buf();
		buf.put(" throws ");
		Iterator<String> iterator = exceptionTypes.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String exceptionType = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(exceptionType);
		}
		return buf.get();
	}
	
	public String printTimeoutNodes(int indent, List<TimeoutNode> timeoutNodes) {
		Buf buf = new Buf();
		Iterator<TimeoutNode> iterator = timeoutNodes.iterator();
		while (iterator.hasNext()) {
			TimeoutNode timeoutNode = iterator.next();
			buf.put(printTimeoutNode(indent, timeoutNode));
		}
		return buf.get();
	}
	
	public String printTimeoutNode(int indent, TimeoutNode timeoutNode) {
		Buf buf = new Buf();
		if (timeoutNode != null) {
			buf.put(indent, "timeout:");
			boolean oneLineStyle = useShortCuts && timeoutNode.getCommandNodes().size() == 1;
			if (oneLineStyle)
				indent = 0;
			if (!oneLineStyle)
				buf.putLine(" {");
			//buf.putLine(printParameterNodes(branchNode.getParameterNodes(), indent)+" {");
			buf.put(printPropertyNodes(indent, timeoutNode.getPropertyNodes()));
			buf.put(printCommandNodes(indent, timeoutNode.getCommandNodes()));
			if (!oneLineStyle)
				buf.putLine(indent, "}");
		}
		return buf.get();
	}

	
	protected String printSpacingIfNeeded(int indent, boolean needed) {
		Buf buf = new Buf();
		if (needed)
			buf.putLine(indent);
		return buf.get();
	}


}
