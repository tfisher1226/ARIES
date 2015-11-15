package org.aries.ast;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import nam.model.Channel;
import nam.model.Condition;
import nam.model.Parameter;
import nam.model.Project;
import nam.model.util.MessagingUtil;
import nam.model.util.PlaceholderUtil;
import nam.model.util.TypeUtil;

import org.antlr.runtime.tree.Tree;
import org.aries.Assert;
import org.aries.ast.node.AbstractNode;
import org.aries.ast.node.BlockNode;
import org.aries.ast.node.BranchNode;
import org.aries.ast.node.CacheNode;
import org.aries.ast.node.ChannelNode;
import org.aries.ast.node.CommandNode;
import org.aries.ast.node.ConditionNode;
import org.aries.ast.node.DefinitionNode;
import org.aries.ast.node.DoneNode;
import org.aries.ast.node.EndpointNode;
import org.aries.ast.node.ExceptionNode;
import org.aries.ast.node.ExecuteNode;
import org.aries.ast.node.ExpressionNode;
import org.aries.ast.node.GroupNode;
import org.aries.ast.node.ImportNode;
import org.aries.ast.node.InvokeNode;
import org.aries.ast.node.ItemNode;
import org.aries.ast.node.ListenNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.MethodNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.OperatorNode;
import org.aries.ast.node.OptionNode;
import org.aries.ast.node.ParameterNode;
import org.aries.ast.node.ParticipantNode;
import org.aries.ast.node.PersistanceNode;
import org.aries.ast.node.PostNode;
import org.aries.ast.node.PrimaryNode;
import org.aries.ast.node.PrincipleNode;
import org.aries.ast.node.PropertyNode;
import org.aries.ast.node.PublishNode;
import org.aries.ast.node.ReceiveNode;
import org.aries.ast.node.ReplyNode;
import org.aries.ast.node.ResultNode;
import org.aries.ast.node.RoleNode;
import org.aries.ast.node.SendNode;
import org.aries.ast.node.StatementNode;
import org.aries.ast.node.SubscribeNode;
import org.aries.ast.node.ThrowsNode;
import org.aries.ast.node.TimeoutNode;
import org.aries.ast.node.TypeNode;
import org.aries.ast.node.VariableNode;
import org.aries.ast.node.control.BreakNode;
import org.aries.ast.node.control.ContinueNode;
import org.aries.ast.node.control.DoNode;
import org.aries.ast.node.control.ForNode;
import org.aries.ast.node.control.IfNode;
import org.aries.ast.node.control.ReturnNode;
import org.aries.ast.node.control.ThrowNode;
import org.aries.ast.node.control.UnnamedNode;
import org.aries.ast.node.control.WhileNode;
import org.aries.util.FileUtil;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class AriesASTBuilder {

	private NetworkNode root;

	private GenerationContext context;

	private ReceiveNode receiveNode;

	private InvokeNode invokeNode;

	private MessageNode messageNode;

	//private String processScope;
	
	private Stack<CommandNode> activeCommandStack = new Stack<CommandNode>();

	private Map<String, ParameterNode> activeParametersInScope = new HashMap<String, ParameterNode>();

	private Map<String, TypeNode> activeVariablesInScope = new HashMap<String, TypeNode>();

	
	public AriesASTBuilder(GenerationContext context) {
		this.context = context;
	}

	protected void pushCommand(CommandNode commandNode) {
//		if (activeCommandStack.contains(commandNode))
//			System.out.println();
		activeCommandStack.push(commandNode);
		//System.out.println("pushed: "+commandNode);
	}
	
	protected void popCommand(CommandNode commandNode) {
		CommandNode poppedCommandNode = activeCommandStack.pop();
		//System.out.println("POPPED: "+poppedCommandNode);
//		if (!poppedCommandNode.equals(commandNode))
//			System.out.println();
		Assert.isTrue(poppedCommandNode.equals(commandNode));
	}

	public DoneNode buildDoneNode(Tree source) throws Exception {
		return buildDoneNode(source, false);
	}
	
	public DoneNode buildDoneNode(Tree source, boolean needsReturn) throws Exception {
		DoneNode doneNode = new DoneNode(source);
		doneNode.setNeedsReturn(needsReturn);
		doneNode.setName("DONE");
		doneNode.setText("done");
		//String serviceId = null;
		//if (messageNode != null)
		//	doneNode.addServiceId(NameUtil.getLastSegment(messageNode.getName()));
		//if (needsReturn == true)
		//	System.out.println();
		if (receiveNode != null)
			doneNode.addServiceId(NameUtil.getLastSegment(receiveNode.getName()));
		return doneNode;
	}
	
	public List<ImportNode> buildImportNodes(String homeDirectory, Tree source) throws Exception {
		Children children = new Children(source);
		List<ImportNode> importNodes = new ArrayList<ImportNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			//System.out.println(">>>"+text);
			if (text.equals("import")) {
				ImportNode importNode = buildImportNode(homeDirectory, child);
				importNodes.add(importNode);
			}
		}
		return importNodes;
	}

	public ImportNode buildImportNode(String homeDirectory, Tree source) throws Exception {
		Children children = new Children(source);
		ImportNode importNode = new ImportNode(source);
		String filePath = "";
		String fileType = null;
		int size = children.size();
		
		for (int i=0; i < size; i++) {
			String token1 = children.pop().getText();
			String token2 = children.pop().getText();
			if (token2.equals(";")) {
				fileType = token1;
				break;
			}
			Assert.equals(token2, ".", "Expecting \".\"");
			if (i > 0 && i < size-1)
				filePath += "/";
			else if (i > 0)
				filePath += ".";
			filePath += token1;
		}
		
		String subFolder = null;
		String fileName = null;
		int lastIndexOf = filePath.lastIndexOf("/");
		if (lastIndexOf > -1) {
			fileName = filePath.substring(lastIndexOf+1);
			subFolder = filePath.substring(0, lastIndexOf);
		} else {
			fileName = filePath;
		}
		
		String parentDirectory = null;
		//homeDirectory = NameUtil.normalizePath(homeDirectory);
		if (FileUtil.fileExists(context.getRuntimeFileContext() + "/" + fileName + "." + fileType)) {
			parentDirectory = context.getRuntimeFileContext();
		}

		if (parentDirectory == null) {
			if (!homeDirectory.endsWith("/cache"))
				homeDirectory += "/cache";
			if (!homeDirectory.endsWith("/model"))
				homeDirectory += "/model";
			homeDirectory += "/" + subFolder;
			homeDirectory = NameUtil.normalizePath(homeDirectory);
			if (FileUtil.fileExists(homeDirectory + "/" + fileName + "." + fileType)) {
				parentDirectory = homeDirectory;
			}
		}
		
		if (parentDirectory == null) {
			throw new RuntimeException("Cannot find file: "+ fileName + "." + fileType);
		}
		
		//System.out.println(">>>"+parentDirectory + "/" + fileName + "." + fileType);
		importNode.setParentDirectory(parentDirectory);
		importNode.setName(fileName);
		importNode.setValue(fileType);
		return importNode;
	}

//	public static List<InformationNode> buildInformationNodesFromImportNodes(List<ImportNode> importNodes) {
//		Map<String, InformationNode> map = new HashMap<String, InformationNode>();
//		//Placeholders placeholders = modelBuilder.buildPlaceholdersFromImportNodes(importNodes);
//		//List<Information> informationPlaceholders = PlaceholderUtil.getInformationPlaceholders(placeholders);
//		Iterator<ImportNode> iterator = importNodes.iterator();
//		while (iterator.hasNext()) {
//			ImportNode importNode = iterator.next();
//			if (map.containsKey("")) {
//				
//			}
//			InformationNode informationNode = buildInformationNodeFromImportNode(importNode);
//			map.put(key, value);
//		}
//		List<InformationNode> informationNode = new ArrayList<InformationNode>();
//		informationNode.addAll(map.values());
//		return informationNode;
//	}
//	
//	public static InformationNode buildInformationNodeFromImportNode(ImportNode importNode) {
//		InformationNode informationNode = new InformationNode(importNode);
//		List<Namespace> namespaces = InformationUtil.getNamespaces(importNode);
//		return null;
//	}
	
	public List<NetworkNode> buildNetworkNodes(Tree source) throws Exception {
		Children children = new Children(source);
		List<NetworkNode> networkNodes = new ArrayList<NetworkNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("network")) {
				NetworkNode networkNode = buildNetworkNode(child);
				networkNodes.add(networkNode);
			}
		}
		return networkNodes;
	}
	
	public NetworkNode buildNetworkNode(Tree source) throws Exception {
		Children children = new Children(source);
		root = new NetworkNode(source);
		root.setName(children.pop().getText());
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("import"))
				addPropertyNode(root, child);
			else if (text.equals("add"))
				addPropertyNode(root, child);
			else if (text.equals("set"))
				setPropertyNode(root, child);
			else if (text.equals("role"))
				root.addRoleNode(buildRoleNode(child));
			else if (text.equals("channel"))
				root.addChannelNode(buildChannelNode(child));
			else if (text.equals("group"))
				root.addGroupNode(buildGroupNode(child));
			else if (text.equals("participant"))
				root.addParticipantNode(buildParticipantNode(child));
			else if (text.equals("cache"))
				root.addCacheNode(buildCacheNode(child));
			else if (text.equals("persistance"))
				root.addPersistanceNode(buildPersistanceNode(child));
		}
		
		
		return root;
	}
	
	public BranchNode buildBranchNode(Tree source) throws Exception {
		Children children = new Children(source);
		BranchNode branchNode = new BranchNode(source);
		Tree child = children.pop();
		String text = child.getText();
		if (!text.equals(":"))
			branchNode.setName(text);
		buildSubNodes(branchNode, children);
		return branchNode;
	}

	public CacheNode buildCacheNode(Tree source) throws Exception {
		Children children = new Children(source);
		CacheNode cacheNode = new CacheNode(source);
		cacheNode.setName(children.pop().getText());
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("import"))
				addPropertyNode(cacheNode, child);
			else if (text.equals("add"))
				addPropertyNode(cacheNode, child);
			else if (text.equals("set"))
				setPropertyNode(cacheNode, child);
			else if (text.equals("items"))
				cacheNode.getItemNodes().addAll(buildItemNodes(child));
		}
		return cacheNode;
	}

	public List<CommandNode> buildCommandNodes(Tree source, Children children, Set<PropertyNode> propertyNodes) throws Exception {
		List<CommandNode> commandNodes = new ArrayList<CommandNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("{"))
				continue;
			if (text.equals("}"))
				break;
			if (text.equals("execute")) {
				commandNodes.add(buildExecuteNode(child));
			} else if (text.equals("branch")) {
				commandNodes.add(buildBranchNode(child));
			} else if (text.equals("done")) {
				commandNodes.add(buildDoneNode(child));
			} else if (text.equals("post")) {
				commandNodes.add(buildPostNode(child));
			} else if (text.equals("reply")) {
				commandNodes.add(buildReplyNode(child));
			} else if (text.equals("send")) {
				commandNodes.add(buildSendNode(child));
			} else if (text.equals("exception")) {
				commandNodes.add(buildExceptionNode(child));
			} else if (text.equals("timeout")) {
				commandNodes.add(buildTimeoutNode(child, propertyNodes));
			} else if (text.equals("EXPR")) {
				commandNodes.add(buildStatementNode(child));
				//commandNodes.add(buildExpressionNode(child));
			} else if (AriesASTUtil.isEndpoint(text)) {
				commandNodes.add(buildEndpointNode(child));
			} else if (AriesASTUtil.isCommand(text)) {
				commandNodes.add(buildCommandNode(child));
			//} else if (AriesASTUtil.isName(text)) {
			//	commandNodes.add(buildCommandNode(child, children));
			}
		}
		return commandNodes;
	}

	public CommandNode buildCommandNode(Tree source) throws Exception {
		Children children = new Children(source);
		CommandNode commandNode = new CommandNode(source);
		pushCommand(commandNode);
		Tree pop = children.pop();
		//if (pop == null)
		//	System.out.println();
		commandNode.setName(pop.getText());
		Tree token = children.pop();
		if (token.getText().equals("(")) {
			List<ParameterNode> parameterNodes = buildParameterNodes(children);
			commandNode.setParameterNodes(parameterNodes);
		} else {
			children.push(token);
		}
		popCommand(commandNode);
		return commandNode;
	}

	public CommandNode buildCommandNode_OLD(Tree source, Children children) throws Exception {
		CommandNode commandNode = new CommandNode(source);
		commandNode.setName(source.getText());
		String name = source.getText();
//		if (name.contains("Book"))
//			System.out.println();
		Tree token = children.pop();

		if (token.getText().equals(":")) {
			String prefix = name;
			token = children.pop(); //type
			String type = token.getText();
			ResultNode resultNode = new ResultNode(token);
			resultNode.setType(prefix+":"+type);
			resultNode.setName(children.pop().getText());
			resultNode.setConstruct("item");
			commandNode.setResultNode(resultNode);
			token = children.pop(); //"="

		} else if (name.equals("List")) {
			children.push(token);
			children = new Children(source);
			token = children.pop(); //"<"
			token = children.pop(); //prefix
			String prefix = token.getText();
			token = children.pop(); //":"
			token = children.pop(); //type
			String type = token.getText();
			token = children.pop(); //">"
			ResultNode resultNode = new ResultNode(token);
			resultNode.setType(prefix+":"+type);
			resultNode.setName(children.pop().getText());
			resultNode.setConstruct("list");
			commandNode.setResultNode(resultNode);
			token = children.pop(); //"="

		} else if (name.equals("Set")) {
			children.push(token);
			children = new Children(source);
			token = children.pop(); //"<"
			token = children.pop(); //prefix
			String prefix = token.getText();
			token = children.pop(); //":"
			token = children.pop(); //type
			String type = token.getText();
			token = children.pop(); //">"
			ResultNode resultNode = new ResultNode(token);
			resultNode.setType(prefix+":"+type);
			resultNode.setName(children.pop().getText());
			resultNode.setConstruct("set");
			commandNode.setResultNode(resultNode);
			token = children.pop(); //"="

		} else if (name.equals("Map")) {
			children.push(token);
			children = new Children(source);
			token = children.pop(); //"<"
			token = children.pop(); //keyPrefix
			String keyPrefix = token.getText();
			token = children.pop(); //":"
			token = children.pop(); //keyType
			String keyType = token.getText();
			token = children.pop(); //","
			token = children.pop(); //keyPrefix
			String prefix = token.getText();
			token = children.pop(); //":"
			token = children.pop(); //keyType
			String type = token.getText();
			token = children.pop(); //">"
			ResultNode resultNode = new ResultNode(token);
			resultNode.setKey(keyPrefix+":"+keyType);
			resultNode.setType(prefix+":"+type);
			resultNode.setName(children.pop().getText());
			resultNode.setConstruct("map");
			commandNode.setResultNode(resultNode);
			token = children.pop(); //"="
		}

		if (commandNode.getResultNode() != null) {
			source = children.pop();
			name = source.getText();
			token = children.pop();
		}
		
		//TODO handle multiple dots
		if (token.getText().equals(".")) {
			token = children.pop();
			name += "."+token.getText();
			token = children.pop();
		}
		commandNode.setName(name);
		String text = token.getText();
		if (text.equals("(")) {
			commandNode.setText("call");
			List<ParameterNode> parameterNodes = buildParameterNodes(children);
			commandNode.setParameterNodes(parameterNodes);

		} else if (AriesASTUtil.isName(text)) {
			commandNode.setText("new");
			commandNode.addToken(name);
			commandNode.addToken(text);
			while (true) {
				Tree nextToken = children.pop();
				if (nextToken == null)
					break;
				String nextText = nextToken.getText();
				commandNode.addToken(nextText);
				if (nextText.equals(";"))
					break;
			}
//			if (nextText.equals("=")) {
//				commandNode.setText("assign");
//				
//				nextToken = children.pop();
//				nextText = nextToken.getText();
//				if (nextText.equals("new")) {
//					commandNode.setText("new");
//
//					nextToken = children.pop();
//					nextText = nextToken.getText();
//					commandNode.setType(nextText);
//				}
//			}
			
		} else {
			children.push(token);
		}
		return commandNode;
	}

	public ChannelNode buildChannelNode(Tree source) throws Exception {
		Children children = new Children(source);
		ChannelNode channelNode = new ChannelNode(source);
		channelNode.setText(source.getText());
		channelNode.setName(children.pop().getText());
		Assert.isTrue(children.pop().getText().equals("{"));

		while (!children.empty()) {
			Tree action = children.pop();
			String actionName = action.getText();
			Tree entity = children.pop();
			String entityName = entity.getText();
			Assert.isTrue(children.pop().getText().equals("("));
			Tree value = children.pop();
			Assert.isTrue(children.pop().getText().equals(")"));
			if (actionName.equals("add") && entityName.equals("client")) {
				channelNode.addSenderRoleNode(buildRoleNode(value));
			} else if (actionName.equals("set") && entityName.equals("client")) {
				channelNode.addSenderRoleNode(buildRoleNode(value));
			} else if (actionName.equals("add") && entityName.equals("service")) {
				channelNode.addReceiverRoleNode(buildRoleNode(value));
			} else if (actionName.equals("set") && entityName.equals("service")) {
				channelNode.addReceiverRoleNode(buildRoleNode(value));
			} else if (actionName.equals("add") && entityName.equals("manager")) {
				channelNode.addManagerRoleNode(buildRoleNode(value));
			} else if (actionName.equals("set") && entityName.equals("manager")) {
				channelNode.addManagerRoleNode(buildRoleNode(value));
			}
			Assert.isTrue(children.pop().getText().equals(";"));
			Tree child = children.pop();
			if (!child.getText().equals("}")) {
				children.push(child);
			} else {
				break;
			}
		}
		return channelNode;
	}

	public EndpointNode buildEndpointNode(Tree source) throws Exception {
		String text = source.getText();
		if (text.equals("invoke"))
			return buildInvokeNode(source);
		if (text.equals("publish"))
			return buildPublishNode(source);
		if (text.equals("receive"))
			return buildReceiveNode(source);
		if (text.equals("post"))
			return buildPostNode(source);
		if (text.equals("reply"))
			return buildReplyNode(source);
		if (text.equals("send"))
			return buildSendNode(source);
		if (text.equals("subscribe"))
			return buildSubscribeNode(source);
		throw new RuntimeException("");
	}

	public ExceptionNode buildExceptionNode() {
		//TODO need to have default timeout value which is configurable
		ExceptionNode exceptionNode = new ExceptionNode();
		return exceptionNode;
	}
	
	public ExceptionNode buildExceptionNode(Tree source) throws Exception {
		Children children = new Children(source);
		ExceptionNode exceptionNode = new ExceptionNode(source);
		pushCommand(exceptionNode);
		buildSubNodes(exceptionNode, children);
		popCommand(exceptionNode);
		return exceptionNode;
	}

	public CommandNode buildExecuteNode(Tree source) throws Exception {
		Children children = new Children(source);
		ExecuteNode executeNode = new ExecuteNode(source);
		pushCommand(executeNode);
		Tree token = children.pop();
		String lookAheadText = token.getText();
		if (lookAheadText.equals("then")) {
			executeNode.setName(children.pop().getText());
			executeNode.setParameterNodes(buildParameterNodes(children));
		} else {
			children.push(token);
		}
		buildPropertyNodes(executeNode, children);
		executeNode.setCommandNodes(buildCommandNodes(source, children, executeNode.getPropertyNodes()));
		popCommand(executeNode);
		return executeNode;
	}

	public GroupNode buildGroupNode(Tree source) throws Exception {
		Children children = new Children(source);
		GroupNode groupNode = new GroupNode(source);
		groupNode.setName(children.pop().getText());
		buildPropertyNodes(groupNode, children);
		return groupNode;
	}

	public InvokeNode buildInvokeNode(Tree source) throws Exception {
		Children children = new Children(source);
		//Assert.isTrue(children.size() >= 5);
		InvokeNode invokeNode = new InvokeNode(source);
		pushCommand(invokeNode);
		String name = children.pop().getText();//name
		name += children.pop().getText();//'.'
		name += children.pop().getText();//name
		invokeNode.setDestination(name);
		invokeNode.setName(name);
		this.invokeNode = invokeNode;
		
//		if (name.contains("shipBooks"))
//			System.out.println();
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("("))
				invokeNode.setParameterNodes(buildParameterNodes(children));
			else if (text.equals("add"))
				addPropertyNode(invokeNode, child);
			else if (text.equals("add"))
				addPropertyNode(invokeNode, child);
			else if (text.equals("set"))
				setPropertyNode(invokeNode, child);
			else if (text.equals("message"))
				invokeNode.addMessageNode(buildMessageNode(child));
			else if (text.equals("exception"))
				invokeNode.addExceptionNode(buildExceptionNode(child));
			else if (text.equals("timeout"))
				invokeNode.addTimeoutNode(buildTimeoutNode(child, invokeNode.getPropertyNodes()));
		}
		
		if (invokeNode.getExceptionNodes().isEmpty()) {
			invokeNode.addExceptionNode(buildExceptionNode());
		}
		
		if (invokeNode.getTimeoutNodes().isEmpty()) {
			invokeNode.addTimeoutNode(buildTimeoutNode());
		}
		
		this.invokeNode = null;
		popCommand(invokeNode);
		return invokeNode;
	}
	
	public List<ItemNode> buildItemNodes(Tree source) throws Exception {
		Children children = new Children(source);
		List<ItemNode> itemNodes = new ArrayList<ItemNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("ITEM")) {
				itemNodes.add(buildItemNode(child));
			}
		}
		return itemNodes;
	}
	
	public ItemNode buildItemNode(Tree source) throws Exception {
		Children children = new Children(source);
		ItemNode itemNode = new ItemNode(source);
		itemNode.setText(source.getText());
		itemNode.setType("ITEM");
		
		TypeNode typeNode = null;
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals(";")) {
				break;
			} else if (text.equals("{")) {
				//TODO deal with indexes here!!!
				break;
			} else if (text.equals("TYPE")) {
				typeNode = buildTypeNode(child);
				itemNode.setTypeNode(typeNode);
			} else if (AriesASTUtil.isName(text)) {
				itemNode.setName(text);
				activeVariablesInScope.put(text, typeNode);
			}
		}
		
//		if (itemNode.getTypeNode() == null)
//			System.out.println();
		
//		if (text.equalsIgnoreCase("List") || text.equalsIgnoreCase("Set")) {
//			Children children = new Children(source);
//			Assert.isTrue(children.size() >= 5);
//			Assert.equals(children.pop().getText(), "<");
//			String type = getType(children);
//			Assert.equals(children.pop().getText(), ">");
//			String name = children.pop().getText();
//			itemNode.setText(source.getText());
//			itemNode.setType(type);
//			itemNode.setName(name);
//			itemNode.setConstruct(NameUtil.uncapName(text));
//			
//		} else if (text.equalsIgnoreCase("Map")) {
//			Children children = new Children(source);
//			Assert.isTrue(children.size() >= 7);
//			Assert.equals(children.pop().getText(), "<");
//			String key = getType(children);
//			Assert.equals(children.pop().getText(), ",");
//			String type = getType(children);
//			Assert.equals(children.pop().getText(), ">");
//			String name = children.pop().getText();
//			itemNode.setText(source.getText());
//			itemNode.setKey(key);
//			itemNode.setType(type);
//			itemNode.setName(name);
//			itemNode.setConstruct("map");
//			
//		} else {
//			list.push(source);
//			String type = getType(list);
//			String name = list.pop().getText();
//			itemNode.setText("Data");
//			itemNode.setType(type);
//			itemNode.setName(name);
//			itemNode.setConstruct("item");
//		}
		return itemNode;
	}

	public TypeNode buildTypeNode(Tree source) throws Exception {
		Children children = new Children(source);
		TypeNode typeNode = new TypeNode(source);
		typeNode.setName("TYPE");

		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			
			if (text.equals("List")) {
				typeNode.setConstruct("list");
				List<TypeNode> typeArguments = buildTypeArguments(children);
				Assert.equals(typeArguments.size(), 1);
				typeNode.setTypeArguments(typeArguments);
				typeNode.setType(typeArguments.get(0).getType());
			
			} else if (text.equals("Set")) {
				typeNode.setConstruct("set");
				List<TypeNode> typeArguments = buildTypeArguments(children);
				Assert.equals(typeArguments.size(), 1);
				typeNode.setTypeArguments(typeArguments);
				typeNode.setType(typeArguments.get(0).getType());
			
			} else if (text.equals("Map")) {
				typeNode.setConstruct("map");
				List<TypeNode> typeArguments = buildTypeArguments(children);
				Assert.equals(typeArguments.size(), 2);
				typeNode.setTypeArguments(typeArguments);
				typeNode.setKey(typeArguments.get(0).getType());
				typeNode.setType(typeArguments.get(1).getType());

			} else if (AriesASTUtil.isName(text)) {
				typeNode.setType(text);
				typeNode.setConstruct("item");
			}
		}
		
		typeNode.setTokens(buildSourceCodeFromExpressionNode(source));
		return typeNode;
	}

	private List<TypeNode> buildTypeArguments(Children children) throws Exception {
		List<TypeNode> typeArguments = new ArrayList<TypeNode>();
		Assert.equals(children.pop().getText(), "<");
		Tree child = null;
		String text = null;
		while (!children.empty()) {
			child = children.pop();
			text = child.getText();
			if (text.equals(">")) {
				break;
			} else if (text.equals("TYPE")) {
				TypeNode typeNode = buildTypeNode(child);
				typeArguments.add(typeNode);
			}
		}
		Assert.equals(text, ">");
		return typeArguments;
	}

	public ItemNode buildSetItemNode(Tree source) throws Exception {
		return null;
	}

	public ItemNode buildMapItemNode(Tree source) throws Exception {
		return null;
	}

	protected String getType(Children children) throws Exception {
		String token = children.pop().getText();
		Tree nextToken = children.pop();
		String nextText = nextToken.getText();
		if (nextText.equals(":")) {
			String typePrefix = token;
			String typeLocalPart = children.pop().getText();
			String type = typePrefix + ":" + typeLocalPart;
			return type;
			
		} else {
			children.push(nextToken);
			String type = token;
			return type;
		}
	}
	
//	public ItemNode buildDataItemNode(Tree source) {
//		ItemNode itemNode = new ItemNode(source);
//		itemNode.setName(source.getText());
//		int childCount = source.getChildCount();
//		for (int i=0; i < childCount; i++) {
//			Tree child = source.getChild(i);
//			String childText = child.getText();
//			if (childText.equals("index"))
//				itemNode.addIndex(childText);
//			else if (childText.equals("name"))
//				itemNode.setName(childText);
//			else if (childText.equals("type"))
//				itemNode.setType(childText);
//			else if (childText.equals("key"))
//				itemNode.setKey(childText);
//		}
//		return itemNode;
//	}

	public ListenNode buildListenNode(Tree source) throws Exception {
		Children children = new Children(source);
		String name = children.pop().getText();

		ListenNode listenNode = new ListenNode(source);
		pushCommand(listenNode);
		listenNode.setName(name);
		listenNode.setText("listen");
		listenNode.setMessageName(name);
		
//		if (name.equals("BooksUnavailable"))
//			System.out.println();
		
//		if (invokeNode != null) {
//			listenNode.setDestination(invokeNode.getDestination());
//		} else if (receiveNode != null) {
//			listenNode.setDestination(receiveNode.getName());
//		}
		
//		if (name.equals("OrderAccepted"))
//			System.out.println();
		
		buildSubNodes(listenNode, children);
		
		if (listenNode.getTimeoutNodes().isEmpty()) {
			//TODO need to have default timeout value which is configurable
			TimeoutNode timeoutNode = new TimeoutNode();
			timeoutNode.setValue("60");
			timeoutNode.setUnit("s");
			listenNode.addTimeoutNode(timeoutNode);
		}
		
//		while (!children.empty()) {
//			Tree child = children.pop();
//			String text = child.getText();
//			if (text.equals("(")) {
//				listenNode.setParameterNodes(addParameterPrefix(buildParameterNodes(children), ""));
//			} else if (text.equals("add"))
//				listenNode.addPropertyNode(buildPropertyNode(child));
//			else if (text.equals("set"))
//				listenNode.addPropertyNode(buildPropertyNode(child));
//			else if (text.equals("message"))
//				listenNode.addMessageNode(buildMessageNode(child));
//			else if (text.equals("exception"))
//				listenNode.addExceptionNode(buildExceptionNode(child));
//			else if (text.equals("timeout"))
//				listenNode.setTimeoutNode(buildTimeoutNode(child, listenNode.getPropertyNodes()));
//		}
		popCommand(listenNode);
		return listenNode;
		
		
//		Children children = new Children(source);
//		//Assert.isTrue(children.size() >= 5);
//		ListenNode listenNode = new ListenNode(source);
//		pushCommand(listenNode);
//		String name = children.pop().getText();//name
//		name += children.pop().getText();//'.'
//		name += children.pop().getText();//name
//		//listenNode.setDestination(name);
//		listenNode.setName(name);
//		listenNode.setText("listen");
//		//this.listenNode = listenNode;
//		
//		buildSubNodes(listenNode, children);
//		
//		if (listenNode.getTimeoutNode() == null) {
//			//TODO need to have default timeout value which is configurable
//			TimeoutNode timeoutNode = new TimeoutNode();
//			timeoutNode.setValue("60");
//			timeoutNode.setUnit("s");
//			listenNode.setTimeoutNode(timeoutNode);
//		}
//			
//		popCommand(listenNode);
//		return listenNode;
	}

	public MessageNode buildMessageNode(Tree source) throws Exception {
		Children children = new Children(source);
		MessageNode messageNode = new MessageNode(source);
		this.messageNode = messageNode;
		String text = children.pop().getText();
		messageNode.setName(text);
//		Tree child = children.pop();
//		String text = child.getText();
//		if (text.equals("(")) {
//			messageNode.setParameterNodes(buildParameterNodes(children));
//		} else children.push(child);

		//this.processScope = "message";
		buildSubNodes(messageNode, children);
//		if (text.equalsIgnoreCase("shipmentComplete"))
//			System.out.println();
		this.messageNode = null;
		return messageNode;
	}

	public MethodNode buildMethodNode(Tree source) throws Exception {
		Children children = new Children(source);
		MethodNode methodNode = new MethodNode(source);
		methodNode.setName(source.getText());
		methodNode.setText("method");
		Tree child = children.pop();
//		if (methodNode.getName().equals("shipBooks"))
//			System.out.println();
		String text = child.getText();
		if (text.equals("(")) {
			methodNode.setParameterNodes(buildParameterNodes(children));
			//methodNode.setParameterNodes(buildParameterDeclarationNodes(children));
			buildPropertyNodes(methodNode, children);
			methodNode.setCommandNodes(buildCommandNodes(source, children, methodNode.getPropertyNodes()));
		} else {
			children.push(child);
		}
		return methodNode;
	}

	public OptionNode buildOptionNode(Tree source) throws Exception {
		Children children = new Children(source);
		OptionNode optionNode = new OptionNode(source);
		String text = children.pop().getText();
		if (!text.equals(":")) {
			optionNode.setName(text);
//			text = children.pop().getText();
//			if (text.equals("("))
//				optionNode.setParameterNodes(buildParameterNodes(children));
//			text = children.pop().getText();
//			Assert.isTrue(text.equals(":") || text.equals("{"));
		}
		buildSubNodes(optionNode, children);
		return optionNode;
	}

	public List<ParameterNode> buildParameterDeclarationNodes(Children children) throws Exception {
		List<ParameterNode> parameterNodes = new ArrayList<ParameterNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			//String key = null;
			if (text.equals("("))
				continue;
			if (text.equals(","))
				continue;
			if (text.equals(")"))
				break;
			if (AriesASTUtil.isName(text)) {
				ParameterNode parameterNode = buildParameterDeclarationNode(child, children);
				parameterNodes.add(parameterNode);
			}
		}
		return parameterNodes;
	}
	
	//TODO convert variables here? (or later?) 
	public ParameterNode buildParameterDeclarationNode(Tree source, Children children) throws Exception {
		ParameterNode parameterNode = new ParameterNode(source);
		parameterNode.setConstruct("item");
		parameterNode.setText("parameter");
		String text = source.getText();
		
		if (text.equals("List")) {
			Assert.isTrue(children.pop().getText().endsWith("<"));
			String type = buildName(children);
			Assert.isTrue(children.pop().getText().endsWith(">"));
			//parameterNode.setType(type);
			parameterNode.setConstruct("list");
			return parameterNode;
			
		} else if (text.equals("Set")) {
			Assert.isTrue(children.pop().getText().endsWith("<"));
			String type = buildName(children);
			Assert.isTrue(children.pop().getText().endsWith(">"));
			//parameterNode.setType(type);
			parameterNode.setConstruct("set");
			return parameterNode;

		} else if (text.equals("Map")) {
			Assert.isTrue(children.pop().getText().endsWith("<"));
			String key = buildName(children);
			Assert.isTrue(children.pop().getText().endsWith(","));
			String type = buildName(children);
			Assert.isTrue(children.pop().getText().endsWith(">"));
			//parameterNode.setType(type);
			parameterNode.setConstruct("map");
			parameterNode.setKey(key);
			return parameterNode;
			
		} else {
			if (!AriesASTUtil.isName(text))
				throw new RuntimeException("Bad type: "+text);
			//parameterNode.setType(text);
			text = buildName(children);
			if (!AriesASTUtil.isName(text))
				throw new RuntimeException("Bad name: "+text);
			parameterNode.setName(text);
			return parameterNode;
		}


			
//		if (text.equals(",") || text.equals(")")) {
//			//children.push(child);
//			return parameterNode;
//		}
//		if (text.equals("[")) {
//			children.pop();
//			parameterNode.setValue("list");
//			return parameterNode;
//		}
		
//		if (text.equals("."))
//			throw new RuntimeException("Bad value");
	}
	
	public List<ParameterNode> buildParameterNodes(Children children) throws Exception {
		List<ParameterNode> parameterNodes = new ArrayList<ParameterNode>();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("(")) {
				continue;
			} else if (text.equals(",")) {
				continue;
			} else if (text.equals(")")) {
				break;
			} else if (text.equals("EXPR")) {
				return buildParameterNodes(new Children(child));
			} else if (text.equals("PRIMARY")) {
				return buildParameterNodes(new Children(child));
			} else if (text.equals("TYPE")) {
				ParameterNode parameterNode = buildParameterNode(child, children);
				parameterNodes.add(parameterNode);
			} else if (AriesASTUtil.isName(text)) {
				ParameterNode activeParameterInScope = activeParametersInScope.get(text);
				if (activeParameterInScope != null) {
					parameterNodes.add(activeParameterInScope);
					continue;
				}
				TypeNode activeResultInScope = activeVariablesInScope.get(text);
				if (activeResultInScope != null) {
					String type = activeResultInScope.getType();
					String key = activeResultInScope.getKey();
					ParameterNode parameterNode = new ParameterNode();
					parameterNode.setName(text);
					parameterNode.setText("parameter");
					TypeNode typeNode = new TypeNode();
					typeNode.setText("TYPE");
					typeNode.setName(text);
					typeNode.setType(type);
					typeNode.setKey(key);
					typeNode.setConstruct(activeResultInScope.getConstruct());
					//TODO typeNode.getTypeArguments().add(key);
					//TODO typeNode.getTypeArguments().add(type);
					parameterNode.setTypeNode(typeNode);
					//parameterNode.setType(typeNode.getType());
					parameterNodes.add(parameterNode);
					continue;
				}
				
//				ResultNode activeResultInScope = activeVariablesInScope.get(text);
//				if (activeResultInScope != null) {
//					String type = activeResultInScope.getType();
//					String key = activeResultInScope.getKey();
//					ParameterNode parameterNode = new ParameterNode();
//					parameterNode.setName(text);
//					parameterNode.setText("parameter");
//					TypeNode typeNode = new TypeNode();
//					typeNode.setText("TYPE");
//					typeNode.setName(text);
//					typeNode.setType(type);
//					typeNode.setKey(key);
//					typeNode.setConstruct(activeResultInScope.getConstruct());
//					//TODO typeNode.getTypeArguments().add(key);
//					//TODO typeNode.getTypeArguments().add(type);
//					parameterNode.setTypeNode(typeNode);
//					//parameterNode.setType(typeNode.getType());
//					parameterNodes.add(parameterNode);
//					continue;
//				}
				
				ParameterNode parameterNode = new ParameterNode();
				parameterNode.setName(text);
				parameterNode.setText("parameter");
				@SuppressWarnings("unchecked") Stack<CommandNode> commandStack = (Stack<CommandNode>) activeCommandStack.clone();
				parameterNode.setCommandStack(commandStack);
				parameterNodes.add(parameterNode);
//				//Assert.notNull(parameterNode, "Type not found for: "+text);
//				if (parameterNode.getTypeNode() == null) {
			}
		}
		
		return parameterNodes;
	}
	
	//TODO convert variables here? (or later?) 
	public ParameterNode buildParameterNode(Tree source, Children children) throws Exception {
		ParameterNode parameterNode = new ParameterNode(source);
		parameterNode.setText("parameter");
		String text = source.getText();
		
		Assert.equals(text, "TYPE");
		TypeNode typeNode = buildTypeNode(source);
		parameterNode.setTypeNode(typeNode);
//		if (typeNode == null)
//			System.out.println();
		
		while (!children.empty()) {
			Tree child = children.pop();
			text = child.getText();
			if (text.equals(",")) {
				break;
			} else if (text.equals(")")) {
				children.push(child);
				break;
			} else if (text.equals(";")) {
				break;
			} else if (AriesASTUtil.isName(text)) {
				parameterNode.setName(text);
				activeVariablesInScope.put(text, typeNode);
			}
		}

		//parameterNode.setConstruct("item");

		Assert.notNull(parameterNode.getName(), "Name should be set");
		Assert.notNull(parameterNode.getTypeNode(), "Type should be set");
		Assert.notNull(parameterNode.getConstruct(), "Construct should be set");
		activeParametersInScope.put(parameterNode.getName(), parameterNode);
//		if (parameterNode.getTypeNode() == null)
//			System.out.println();
		
		return parameterNode;
		
//		ParameterNode parameterNode = new ParameterNode(source);
//		parameterNode.setConstruct("item");
//		parameterNode.setText("parameter");
//		String text = source.getText();
//		//System.out.println(text);
//		
//		if (text.equals("List")) {
//			Assert.isTrue(children.pop().getText().endsWith("<"));
//			String type = buildName(children);
//			Assert.isTrue(children.pop().getText().endsWith(">"));
//			parameterNode.setName(type);
//			parameterNode.setConstruct("list");
//			return parameterNode;
//			
//		} else if (text.equals("Set")) {
//			Assert.isTrue(children.pop().getText().endsWith("<"));
//			String type = buildName(children);
//			Assert.isTrue(children.pop().getText().endsWith(">"));
//			parameterNode.setName(type);
//			parameterNode.setConstruct("set");
//			return parameterNode;
//
//		} else if (text.equals("Map")) {
//			Assert.isTrue(children.pop().getText().endsWith("<"));
//			String key = buildName(children);
//			Assert.isTrue(children.pop().getText().endsWith(","));
//			String type = buildName(children);
//			Assert.isTrue(children.pop().getText().endsWith(">"));
//			parameterNode.setName(type);
//			parameterNode.setConstruct("map");
//			parameterNode.setKey(key);
//			return parameterNode;
//		}

//		if (!AriesASTUtil.isName(text))
//			throw new RuntimeException("Bad state");

//		text = buildName(text, children);
//		parameterNode.setName(text);
//		return parameterNode;
			
//		if (text.equals(",") || text.equals(")")) {
//			//children.push(child);
//			return parameterNode;
//		}
//		if (text.equals("[")) {
//			children.pop();
//			parameterNode.setValue("list");
//			return parameterNode;
//		}
		
//		if (text.equals("."))
//			throw new RuntimeException("Bad value");
	}

	public ParameterNode buildParameterNode(Parameter parameter) throws Exception {
		//String parameterName = NameUtil.uncapName(messageName);
		//String parameterClass = NameUtil.capName(messageName);
		//Element parameterElement = context.getElementByName(parameterClass);
		//Assert.notNull(parameterElement, "Message element not found: "+parameterClass);
		
		String parameterName = parameter.getName();
		String parameterType = parameter.getType();
		String parameterClass = TypeUtil.getClassName(parameterType);

		ParameterNode parameterNode = new ParameterNode();
		parameterNode.setName(parameterName);
		parameterNode.setText("parameter");
		TypeNode typeNode = new TypeNode();
		typeNode.setText("TYPE");
		typeNode.setName(parameterClass);
		typeNode.setType(parameterType);
		typeNode.setConstruct("item");
		//TODO typeNode.getTypeArguments().add(key);
		//TODO typeNode.getTypeArguments().add(type);
		parameterNode.setTypeNode(typeNode);
		@SuppressWarnings("unchecked") Stack<CommandNode> commandStack = (Stack<CommandNode>) activeCommandStack.clone();
		parameterNode.setCommandStack(commandStack);
		return parameterNode;
	}
	
	
	public String buildName(Children children) {
		return buildName(children.pop().getText(), children);
	}
	
	public String buildName(String text, Children children) {
		Tree nextChild = children.pop();
		String next = nextChild.getText();
		String prefix = null;
		if (next.equals(".")) {
			prefix = text; 
			text = children.pop().getText();
			Assert.isTrue(AriesASTUtil.isName(prefix));
			Assert.isTrue(AriesASTUtil.isName(text));
			text = prefix+"."+text;
		} else {
//			if (!AriesModelUtil.isName(text))
//				System.out.println();
			Assert.isTrue(AriesASTUtil.isName(text));
			children.push(nextChild);
		}
		return text;
	}

	public ParticipantNode buildParticipantNode(Tree source) throws Exception {
		Children children = new Children(source);
		ParticipantNode participantNode = new ParticipantNode(source);
		participantNode.setName(children.pop().getText());
//		if (participantNode.getName().equalsIgnoreCase("shipper"))
//			System.out.println();
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("import"))
				addPropertyNode(participantNode, child);
			else if (text.equals("add"))
				addPropertyNode(participantNode, child);
			else if (text.equals("set"))
				setPropertyNode(participantNode, child);
			else if (text.equals("role"))
				participantNode.addRoleNode(buildRoleNode(child));
			else if (text.equals("channel"))
				participantNode.addChannelNode(buildChannelNode(child));
			else if (text.equals("send"))
				participantNode.addCommandNode(buildSendNode(child));
			else if (text.equals("receive"))
				participantNode.addCommandNode(buildReceiveNode(child));
			else if (text.equals("invoke"))
				participantNode.addCommandNode(buildInvokeNode(child));
			else if (text.equals("subscribe"))
				participantNode.addCommandNode(buildSubscribeNode(child));
			else if (text.equals("publish"))
				participantNode.addCommandNode(buildPublishNode(child));
			else if (text.equals("cache"))
				participantNode.addCacheNode(buildCacheNode(child));
			else if (text.equals("persist"))
				participantNode.addPersistanceNode(buildPersistanceNode(child));
			else if (AriesASTUtil.isName(text))
				participantNode.addCommandNode(buildMethodNode(child));
		}
		populateChannelNodesFromPropertyNodes(participantNode);
		//participantNode.setNamespace(namespace);
		return participantNode;
	}

	public void populateChannelNodesFromPropertyNodes(ParticipantNode participantNode) {
		Set<PropertyNode> propertyNodes = participantNode.getPropertyNodes();
		Iterator<PropertyNode> iterator = propertyNodes.iterator();
		while (iterator.hasNext()) {
			PropertyNode propertyNode = iterator.next();
			String name = propertyNode.getName();
			String value = propertyNode.getValue();
			if (name.equalsIgnoreCase("channel")) {
				ChannelNode channelNode = findChannelNodeFromRoot(value);
				//Assert.notNull(channelNode, "Channel not found: "+value);
				if (channelNode != null) {
					participantNode.getChannelNodes().add(channelNode);
				}
			}
		}
	}
	
	public ChannelNode findChannelNodeFromRoot(String channelName) {
		List<ChannelNode> channelNodes = root.getChannelNodes();
		Iterator<ChannelNode> iterator = channelNodes.iterator();
		while (iterator.hasNext()) {
			ChannelNode channelNode = iterator.next();
			if (channelNode.getName().equals(channelName))
				return channelNode; 
		}
		return null;
	}
	
	public Channel findChannelFromImports(String channelName) {
		List<Project> projects = PlaceholderUtil.getProjectPlaceholders(context.getPlaceholders());
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			Channel channel = MessagingUtil.getChannelByName(project, channelName);
			if (channel != null)
				return channel;
		}
		return null;
	}

	public PersistanceNode buildPersistanceNode(Tree source) throws Exception {
		Children children = new Children(source);
		PersistanceNode persistanceNode = new PersistanceNode(source);
		persistanceNode.setName(children.pop().getText());
		int childCount = source.getChildCount();
		for (int i=0; i < childCount; i++) {
			Tree child = source.getChild(i);
			String childText = child.getText();
			if (childText.equals("role"))
				persistanceNode.addRoleNode(buildRoleNode(child));
		}
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("add"))
				addPropertyNode(persistanceNode, child);
			else if (text.equals("set"))
				setPropertyNode(persistanceNode, child);
			else if (text.equals("include"))
				addPropertyNode(persistanceNode, child);
			else if (text.equals("import"))
				addPropertyNode(persistanceNode, child);
			else if (text.equals("items"))
				persistanceNode.getItemNodes().addAll(buildItemNodes(child));
		}
		return persistanceNode;
	}

	public PrincipleNode buildPrincipleNode(Tree source) {
		return null;
	}

	public void buildPropertyNodes(AbstractNode node, Children children) throws Exception {
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("{"))
				continue;
//			if (text.equals(")"))
//				break;
			if (text.equals("import")) {
				addPropertyNode(node, child);
				continue;
			} else if (text.equals("add")) {
				addPropertyNode(node, child);
				continue;
			} else if (text.equals("set")) {
				setPropertyNode(node, child);
				continue;
			}
//			if (text.equals("branch")) {
//				PropertyNode propertyNode = buildPropertyNode(child);
//				propertyNodes.add(propertyNode);
//			}
			children.push(child);
			break;
		}
	}

	protected void setPropertyNode(AbstractNode node, Tree source) throws Exception {
		Children children = new Children(source);
		String name = children.pop().getText();
		List<PropertyNode> propertyNodes = node.getPropertyNodes(name);
		propertyNodes.add(0, buildPropertyNode(source));
	}

	protected void addPropertyNode(AbstractNode node, Tree source) throws Exception {
		Children children = new Children(source);
		String name = children.pop().getText();
		List<PropertyNode> propertyNodes = node.getPropertyNodes(name);
		propertyNodes.add(buildPropertyNode(source));
	}

	public PropertyNode buildPropertyNode(Tree source) throws Exception {
		Children children = new Children(source);
		PropertyNode propertyNode = new PropertyNode(source);
		propertyNode.setName(children.pop().getText());
		Assert.equals(children.pop().getText(), "(");
		String value = children.pop().getText();
		value = value.replace("\"", "");
		Tree nextChild = children.pop();
		if (nextChild.getText().equals(".")) {
			value += nextChild.getText(); 
			value += children.pop().getText();
		} else {
			children.push(nextChild);
		}
		propertyNode.setValue(value);
		Assert.equals(children.pop().getText(), ")");
		Assert.equals(children.pop().getText(), ";");
		return propertyNode;
	}

	public PublishNode buildPublishNode(Tree source) throws Exception {
		PublishNode publishNode = new PublishNode(source);
		publishNode.setName(source.getText());
		int childCount = source.getChildCount();
		for (int i=0; i < childCount; i++) {
			Tree child = source.getChild(i);
			String childText = child.getText();
			if (childText.equals("message"))
				publishNode.addMessageNode(buildMessageNode(child));
			else if (childText.equals("property"))
				addPropertyNode(publishNode, child);
			else if (childText.equals("variable"))
				publishNode.addVariableNode(buildVariableNode(child));
		}
		return publishNode;
	}

	public ReceiveNode buildReceiveNode(Tree source) throws Exception {
		Children children = new Children(source);
		ReceiveNode receiveNode = new ReceiveNode(source);
		receiveNode.setName(children.pop().getText());
		//System.out.println(">>>"+receiveNode.getName());
		this.receiveNode = receiveNode;
		activeParametersInScope.clear();
		
//		if (receiveNode.getName().equals("purchaseBooks"))
//			System.out.println();
		
		while (!children.empty()) {
			Tree child = buildSubNode(receiveNode, children);
			if (child == null)
				break;
			String text = child.getText();
			if (text.equals("throws")) {
				receiveNode.addThrowsNode(buildThrowsNode(child));
			}
		}
		
		buildSubNodes(receiveNode, children);
		this.receiveNode = null;
		return receiveNode;
	}

	public RoleNode buildRoleNode(Tree source) throws Exception {
		RoleNode roleNode = new RoleNode(source);
		roleNode.setName(source.getText());
		int childCount = source.getChildCount();
		for (int i=0; i < childCount; i++) {
			Tree child = source.getChild(i);
			String childText = child.getText();
			if (childText.equals("principle"))
				roleNode.addPrincipleNode(buildPrincipleNode(child));
		}
		return roleNode;
	}

	public PostNode buildPostNode(Tree source) throws Exception {
		Children children = new Children(source);
		PostNode postNode = new PostNode(source);
		pushCommand(postNode);
		Tree token = children.pop();
		String name = token.getText();
		postNode.setName(name);
		postNode.setText("post");
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("("))
				postNode.addParameterNodes(buildParameterNodes(children));
			else if (text.equals("import"))
				addPropertyNode(postNode, child);
			else if (text.equals("add"))
				addPropertyNode(postNode, child);
			else if (text.equals("set"))
				setPropertyNode(postNode, child);
			else if (text.equals("message"))
				postNode.addMessageNode(buildMessageNode(child));
			else if (text.equals("exception"))
				postNode.addExceptionNode(buildExceptionNode(child));
			else if (text.equals("timeout"))
				postNode.addTimeoutNode(buildTimeoutNode(child, postNode.getPropertyNodes()));
		}
		popCommand(postNode);
		return postNode;
	}

	public ReplyNode buildReplyNode(Tree source) throws Exception {
		Children children = new Children(source);
		String name = children.pop().getText();

		ReplyNode replyNode = new ReplyNode(source);
		pushCommand(replyNode);
		replyNode.setName("reply");
		replyNode.setText("reply");
		replyNode.setMessageName(name);
		//replyNode.setProcessScope(processScope);

//		if (name.equals("BooksUnavailable"))
//			System.out.println();
		if (invokeNode != null) {
			replyNode.setDestination(invokeNode.getDestination());
		} else if (receiveNode != null) {
			replyNode.setDestination(receiveNode.getName());
		}
		
//		if (name.equals("OrderAccepted"))
//			System.out.println();
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("("))
				replyNode.setParameterNodes(addParameterPrefix(buildParameterNodes(children), ""));
			else if (text.equals("import"))
				addPropertyNode(replyNode, child);
			else if (text.equals("add"))
				addPropertyNode(replyNode, child);
			else if (text.equals("set"))
				setPropertyNode(replyNode, child);
			else if (text.equals("message"))
				replyNode.addMessageNode(buildMessageNode(child));
			else if (text.equals("exception"))
				replyNode.addExceptionNode(buildExceptionNode(child));
			else if (text.equals("timeout"))
				replyNode.addTimeoutNode(buildTimeoutNode(child, replyNode.getPropertyNodes()));
		}
		popCommand(replyNode);
		return replyNode;
	}

	public ReplyNode buildReplyNode2(Tree source) throws Exception {
		Children children = new Children(source);
		Tree child = children.pop();
		String name = child.getText();
		Assert.equals(name, "PRIMARY");
		source = child;
		
		ReplyNode replyNode = new ReplyNode(source);
		pushCommand(replyNode);
		replyNode.setName("reply");
		replyNode.setText("reply");
		if (invokeNode != null) {
			replyNode.setDestination(invokeNode.getDestination());
		} else if (receiveNode != null) {
			replyNode.setDestination(receiveNode.getName());
		}
		
//		if (name.equals("OrderAccepted"))
//			System.out.println();
		
		children = new Children(source);
		while (!children.empty()) {
			child = children.pop();
			String text = child.getText();
			if (text.equals("(")) {
				replyNode.setParameterNodes(addParameterPrefix(buildParameterNodes(children), ""));
			} else if (text.equals(")")) {
				break;
			} else if (AriesASTUtil.isName(text)) {
				replyNode.setOperationName(text);
				replyNode.setMessageName(text.substring(5));
//				if (replyNode.getMessageName().equals("BooksUnavailable"))
//					System.out.println();
			}
		}
		popCommand(replyNode);
		return replyNode;
	}
	
	public List<ParameterNode> addParameterPrefix(List<ParameterNode> parameterNodes) {
		return addParameterPrefix(parameterNodes, "$message.");
	}
	
	public List<ParameterNode> addParameterPrefix(List<ParameterNode> parameterNodes, String prefix) {
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			ParameterNode parameterNode = iterator.next();
			String name = parameterNode.getName();
			parameterNode.setName(prefix+name);
		}
		return parameterNodes;
	}

	public SendNode buildSendNode(Tree source) throws Exception {
		Children children = new Children(source);
		SendNode sendNode = new SendNode(source);
		pushCommand(sendNode);
		//sendNode.setName(children.pop().getText());
		//String name = children.pop().getText();//name
		//name += children.pop().getText();//'.'
		//name += children.pop().getText();//name
		String destination = children.pop().getText();//destination
		children.pop().getText();//'.'
		String name = children.pop().getText();//name
		sendNode.setDestination(destination + "." + name);
		sendNode.setName(name);
		sendNode.setText("send");

//		Service service = context.getServiceByName(name);
//		Assert.notNull(service, "Service not found: "+name);
//		Operation operation = ServiceUtil.getOperation(service, name);
//		List<Parameter> parameters = operation.getParameters();
//		Iterator<Parameter> iterator = parameters.iterator();
//		while (iterator.hasNext()) {
//			Parameter parameter = iterator.next();
//			ParameterNode parameterNode = buildParameterNode(parameter);
//			sendNode.addParameterNode(parameterNode);
//		}
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("("))
				sendNode.setParameterNodes(buildParameterNodes(children));
			else if (text.equals("import"))
				addPropertyNode(sendNode, child);
			else if (text.equals("add"))
				addPropertyNode(sendNode, child);
			else if (text.equals("set"))
				setPropertyNode(sendNode, child);
			else if (text.equals("message"))
				sendNode.addMessageNode(buildMessageNode(child));
			else if (text.equals("exception"))
				sendNode.addExceptionNode(buildExceptionNode(child));
			else if (text.equals("timeout"))
				sendNode.addTimeoutNode(buildTimeoutNode(child, sendNode.getPropertyNodes()));
		}

		popCommand(sendNode);
		//outgoingNode = null;
		return sendNode;
	}

	public SubscribeNode buildSubscribeNode(Tree source) throws Exception {
		Children children = new Children(source);
		SubscribeNode subscribeNode = new SubscribeNode(source);
		subscribeNode.setName(children.pop().getText());
		return subscribeNode;
	}

	public ThrowsNode buildThrowsNode(Tree source) throws Exception {
		Children children = new Children(source);
		ThrowsNode throwsNode = new ThrowsNode(source);
		throwsNode.setName(source.getText());
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("{"))
				break;
			throwsNode.addExceptionType(text);
		}
	
		return throwsNode;
	}
	
	public StatementNode buildStatementNode(Tree source) throws Exception {
		String text = source.getText();
		if (text.equals("if")) {
			return buildIfNode(source);
		} else if (text.equals("while")) {
			return buildWhileNode(source);
		} else if (text.equals("for")) {
			return buildForNode(source);
		} else if (text.equals("do")) {
			return buildDoNode(source);
		} else if (text.equals("continue")) {
			return buildContinueNode(source);
		} else if (text.equals("break")) {
			return buildBreakNode(source);
		} else if (text.equals("throw")) {
			return buildThrowNode(source);
		} else if (text.equals("return")) {
			return buildReturnNode(source);
		} else if (text.equals("EXPR")) {
			//List<String> tokens = buildSourceCodeFromExpressionNode(source);
			
			int childCount = source.getChildCount();
//			System.out.println("--------");
//			System.out.println(">>>"+childCount);
//			for (int i=0; i < childCount; i++) {
//				Tree child = source.getChild(i);
//				System.out.println(">>>"+child);
//			}

			if (childCount == 1) {
				ExpressionNode expressionNode = buildExpressionNode(source.getChild(0));
				return expressionNode;
				
			} else if (childCount == 3) {
				DefinitionNode definitionNode = buildDefinitionNode(source);
				return definitionNode;
				
			} else {
				//System.out.println();
			}
			
		} else {
			//System.out.println();
		}
		return null;
	}

	public DefinitionNode buildDefinitionNode(Tree source) throws Exception {
		Assert.isTrue(source.getChildCount() == 3);
		Tree declarationChild = source.getChild(0);
		Tree assignmentChild = source.getChild(1);
		Tree expressionChild = source.getChild(2);
		Assert.isTrue(declarationChild.toString().equals("PRIMARY"));
		Assert.isTrue(assignmentChild.toString().equals("="));
		Assert.isTrue(expressionChild.toString().equals("EXPR"));
		
		PrimaryNode primaryNode = buildDeclarationNode(declarationChild);
		//CallNode methodCallNode = buildMethodCallNode(source.getChild(0));
		ExpressionNode expressionNode = buildExpressionNode(expressionChild);

		DefinitionNode definitionNode = new DefinitionNode(source);
		definitionNode.setName("DEFINITION");
		definitionNode.setText("DEFINITION");
		definitionNode.setPrimaryNode(primaryNode);
		definitionNode.setExpressionNode(expressionNode);
		definitionNode.getTokens().addAll(buildSourceCodeFromExpressionNode(source));
		return definitionNode;
	}
	
	public PrimaryNode buildDeclarationNode(Tree source) throws Exception {
		if (source.getChildCount() == 1) {
			Assert.isTrue(source.getChildCount() == 1);
			Tree child = source.getChild(0);
			String name = child.toString();
			
			TypeNode typeNode = activeVariablesInScope.get(name);
			//if (typeNode == null)
			//	System.out.println("BREAK");
			Assert.notNull(typeNode, "Active variable not found: "+name);
			
			PrimaryNode primaryNode = new PrimaryNode(source);
			primaryNode.setName(source.getText());
			primaryNode.setText("PRIMARY");
			primaryNode.setTypeNode(typeNode);
			primaryNode.setName(name);
			primaryNode.setTokens(buildSourceCodeFromExpressionNode(source));
			return primaryNode;

		} else if (source.getChildCount() == 2) {
			Assert.isTrue(source.getChildCount() == 2);
			Tree child1 = source.getChild(0);
			Tree child2 = source.getChild(1);

			TypeNode typeNode = buildTypeNode(child1);
			String name = child2.toString();
			activeVariablesInScope.put(name, typeNode);

			PrimaryNode primaryNode = new PrimaryNode(source);
			primaryNode.setName(source.getText());
			primaryNode.setText("PRIMARY");
			primaryNode.setTypeNode(typeNode);
			primaryNode.setName(name);
			primaryNode.setTokens(buildSourceCodeFromExpressionNode(source));
			return primaryNode;
		}
		return null;
	}
	
	public ResultNode buildResultNode(Tree source) throws Exception {
		Children children = new Children(source);
		boolean assignmentFound = false;
		Tree primaryChild = null;
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals(";")) {
				break;
			} else if (text.equals("=")) {
				assignmentFound = true;
				break;
			} else if (text.equals("PRIMARY")) {
				primaryChild = child;
			}
		}
		if (assignmentFound && primaryChild != null) {
			return buildResultNodeFromPrimary(primaryChild);
		}
		return null;
	}

	public ResultNode buildResultNodeFromPrimary(Tree source) throws Exception {
		Assert.equals(source.getText(), "PRIMARY");
		Children children = new Children(source);
		Tree child = children.pop();
		Assert.equals(child.getText(), "TYPE");
		
		TypeNode typeNode = buildTypeNode(child);
		String name = children.pop().getText();
		activeVariablesInScope.put(name, typeNode);
		
		ResultNode resultNode = new ResultNode(child);
		//resultNode.setType(AriesASTUtil.getFieldTypeFromTypeNode(typeNode));
		resultNode.setType(typeNode.getType());
		resultNode.setName(name);
		resultNode.setConstruct(typeNode.getConstruct());
		return resultNode;
	}
	
	public IfNode buildIfNode(Tree source) throws Exception {
		Children children = new Children(source);
		IfNode ifNode = new IfNode(source);
		ifNode.setName(source.getText());
		ifNode.setText("if");
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			//System.out.println(">>>"+text);
			
			if (text.equals(";")) {
				break;
				
			} else if (text.equals("(")) {
				Tree expressionTree = children.pop();
				ConditionNode conditionNode = buildConditionNode(expressionTree);
				ifNode.setConditionNode(conditionNode);
				child = children.pop();
				text = child.getText();
				Assert.isTrue(text.equals(")"));
				continue;
				
			} else if (text.equals("EXPR")) {
				StatementNode statementNode = buildStatementNode(child);
				ifNode.addStatementNode(statementNode);
				
			} else if (text.equals("BLOCK")) {
				UnnamedNode executeNode = buildUnnamedNode(child, true);
				ifNode.addStatementNode(executeNode);
				
			} else if (text.equals("else")) {
				child = children.pop();
				text = child.getText();
				
				if (text.equals("if")) {
					IfNode elseIfNode = buildIfNode(child);
					ifNode.addElseIfNode(elseIfNode);
					
				} else if (text.equals("BLOCK")) {
					UnnamedNode elseNode = buildUnnamedNode(child, true);
					ifNode.setElseNode(elseNode);
				} else {
					//System.out.println();
				}
			
			} else {
				//System.out.println();
			}
		}
		return ifNode;
	}

	private ConditionNode buildConditionNode(Tree source) throws Exception {
		//if (source.getChildCount() > 3)
		//	System.out.println();
		Assert.isTrue(source.getChildCount() == 3);
		Tree operandChild1 = source.getChild(0);
		Tree operatorChild = source.getChild(1);
		Tree operandChild2 = source.getChild(2);
		Assert.isTrue(operandChild1.toString().equals("PRIMARY"));
		//Assert.isTrue(operatorChild.toString().equals("="));
		Assert.isTrue(operandChild2.toString().equals("PRIMARY"));
		
		ExpressionNode operandNode1 = buildExpressionNode(operandChild1);
		ExpressionNode operandNode2 = buildExpressionNode(operandChild2);
		OperatorNode operatorNode = buildOperatorNode(operatorChild);
		//PrimaryNode operandNode1 = buildPrimaryNode(operandChild1);
		//PrimaryNode operandNode2 = buildPrimaryNode(operandChild2);

		ConditionNode conditionNode = new ConditionNode(source);
		conditionNode.setName("CONDITION");
		conditionNode.setText("CONDITION");
		conditionNode.setFirstOperandNode(operandNode1);
		conditionNode.setSecondOperandNode(operandNode2);
		conditionNode.setOperatorNode(operatorNode);
		conditionNode.getTokens().addAll(buildSourceCodeFromExpressionNode(source));

		Condition condition = new Condition();
		conditionNode.setCondition(condition);

//		if (conditionNode.toString().contains("available"))
//			System.out.println();
		return conditionNode;
	}

	public OperatorNode buildOperatorNode(Tree source) {
		OperatorNode operatorNode = new OperatorNode(source);
		operatorNode.setName("OPERATOR");
		operatorNode.setText("OPERATOR");
		operatorNode.setOperator(source.getText());
		return operatorNode;
	}

	public UnnamedNode buildUnnamedNode(Tree source) throws Exception {
		return buildUnnamedNode(source);
	}
	
	public UnnamedNode buildUnnamedNode(Tree source, boolean isSecondary) throws Exception {
		Children children = new Children(source);
		UnnamedNode newNode = new UnnamedNode(source);
		newNode.setName(source.getText());
		newNode.setText("unnamed");
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("{")) {
				continue;
			} else if (text.equals("}")) {
				break;
			} else if (text.equals(";")) {
				continue;
			} else if (text.equals("execute")) {
				newNode.addStatementNode(buildExecuteNode(child));
			} else if (text.equals("invoke")) {
				newNode.addStatementNode(buildInvokeNode(child));
			} else if (text.equals("listen")) {
				newNode.addStatementNode(buildListenNode(child));
			} else if (text.equals("post")) {
				newNode.addStatementNode(buildPostNode(child));
			} else if (text.equals("reply")) {
				newNode.addStatementNode(buildReplyNode(child));
			} else if (text.equals("send")) {
				newNode.addStatementNode(buildSendNode(child));
			} else if (text.equals("EXPR")) {
				List<String> tokens = buildSourceCodeFromExpressionNode(child);
				if (tokens.get(0).startsWith("reply")) {
					ReplyNode replyNode = buildReplyNode2(child);
					replyNode.getTokens().addAll(tokens);
					newNode.addStatementNode(replyNode);
				} else {
					newNode.addStatementNode(buildStatementNode(child));
				}
			} else if (text.equals("done")) {
				newNode.addStatementNode(buildDoneNode(child, isSecondary));
			}
		}
		return newNode;
	}
	
	public List<String> getArgumentsFromEXPR(Tree source) throws Exception {
		Children children = new Children(source);
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("{")) {
				continue;
			} else if (text.equals("}")) {
				break;
			} else if (text.equals(";")) {
				break;
			} else if (text.equals("PRIMARY")) {
				
			} else if (text.equals("EXPR")) {
				List<String> tokens = buildSourceCodeFromExpressionNode(child);
				if (tokens.get(0).startsWith("reply")) {
					ReplyNode replyNode = buildReplyNode(child);
					replyNode.getTokens().addAll(tokens);
					//elseNode.addStatementNode(replyNode);
				} else {
					//elseNode.addStatementNode(buildStatementNode(child));
				}
			}
		}
		return null;
	}
	
	public BlockNode buildBlockNode(Tree source) throws Exception {
		Children children = new Children(source);
		BlockNode executeNode = new BlockNode(source);
		executeNode.setName(source.getText());
		executeNode.setText("BLOCK");
		buildSubNodes(executeNode, children);
		return executeNode;
	}

	public WhileNode buildWhileNode(Tree source) throws Exception {
		Children children = new Children(source);
		WhileNode whileNode = new WhileNode(source);
		whileNode.setName(source.getText());
		whileNode.setText("while");
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			//System.out.println(">>>"+text);
			
			if (text.equals("(")) {
				Tree expressionTree = children.pop();
				whileNode.setExpressionNode(buildExpressionNode(expressionTree));
				child = children.pop();
				text = child.getText();
				Assert.isTrue(text.equals(")"));
				continue;
				
			} else if (text.equals("EXPR")) {
				StatementNode statementNode = buildStatementNode(child);
				whileNode.addCommandNode(statementNode);
				
			} else if (text.equals("BLOCK")) {
				BlockNode executeNode = buildBlockNode(child);
				whileNode.addCommandNode(executeNode);
				
			} else if (text.equals("{")) {
				StatementNode statementNode = null;
				while (true) {
					child = children.pop();
					text = child.getText();
					if (text.equals("}")) {
						break;
					} else if (text.equals(";")) {
						if (statementNode != null)
							statementNode.appendText(text);
					} else {
						statementNode = buildStatementNode(child);
						whileNode.addCommandNode(statementNode);
					}
				}
			}
		}
		
		return whileNode;
	}

	public ExpressionNode buildExpressionNodeOLD(Tree source) throws Exception {
		ExpressionNode expressionNode = new ExpressionNode(source);
		expressionNode.setName(source.getText());
		expressionNode.setText("EXPR");
		expressionNode.getTokens().addAll(buildSourceCodeFromExpressionNode(source));
		return expressionNode;
	}
	
	//TODO make this handle more complex call chains i.e. selectors that are call themselves
	private ExpressionNode buildExpressionNode(Tree source) throws Exception {
		//Assert.isTrue(source.toString().equals("EXPR"));
		Children children = new Children(source);
		//System.out.println(">>>"+children);

		Tree firstChild = children.pop();
		String objectName = firstChild.getText();
		
		if (objectName.equals("PRIMARY"))
			return buildExpressionNode(firstChild);
		if (objectName.equals("EXPR"))
			return buildExpressionNode(firstChild);
		
//		if (firstChild.getText().equals("SupplierOrderCache"))
//			System.out.println();
		
		ExpressionNode expressionNode = new ExpressionNode(source);
		expressionNode.setName("EXPR");
		expressionNode.setText("EXPR");
		List<String> tokens = buildSourceCodeFromExpressionNode(source);
		expressionNode.getTokens().addAll(tokens);

		boolean isNewExpression = false;
		if (objectName.equals("new")) {
			firstChild = children.pop();
			objectName = firstChild.getText();
			//expressionNode.setNewExpression(true);
			isNewExpression = true;
		}
		
		expressionNode.setObjectName(objectName);
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals(";")) {
				break;
			} else if (text.equals("(")) {
				continue;
			} else if (text.equals(".")) {
				Tree selectorChild = children.pop();
				expressionNode.addSelector(selectorChild.getText());
			} else if (text.equals("new")) {
				ExpressionNode subExpressionNode = buildExpressionNode(child);
				expressionNode.setExpressionNode(subExpressionNode);
			} else if (text.equals("EXPR")) {
				ExpressionNode subExpressionNode = buildExpressionNode(child);
				expressionNode.setExpressionNode(subExpressionNode);
			}
		}
		
		if (expressionNode.getSelectorChain().isEmpty() && !isNewExpression) {
			expressionNode.addSelector(objectName);
			expressionNode.setObjectName("this");
		}
		return expressionNode;
	}	

//	private CallNode buildMethodCallNode(Tree source) throws Exception {
//		Assert.isTrue(source.toString().equals("PRIMARY"));
//		Children children = new Children(source);
//		Tree nameChild = children.pop();
//		
//		CallNode callNode = new CallNode(source);
//		callNode.setName(nameChild.getText());
//		callNode.setText("STATEMENT");
//		callNode.getTokens().addAll(buildSourceCodeFromExpressionNode(source));
//		
//		while (!children.empty()) {
//			Tree child = children.pop();
//			String text = child.getText();
//			if (text.equals(";")) {
//				break;
//			} else if (text.equals("(")) {
//				continue;
//			} else if (text.equals(".")) {
//				Tree selectorChild = children.pop();
//				callNode.addSelector(selectorChild.getText());
//			} else if (text.equals("EXPR")) {
//				callNode.setExpressionNode(buildExpressionNode(child));
//			}
//		}
//		return callNode;
//	}
	

//	public StatementNode buildStatementNode(Tree source) {
//		StatementNode statementNode = new StatementNode(source);
//		statementNode.setName(source.getText());
//		statementNode.setText("STATEMENT");
//		statementNode.setValue(buildSourceCodeFromStatementNode(source));
//		return statementNode;
//	}

//	public List<CommandNode> buildStatementNodes(Tree source) {
//		return null;
//	}

	public ExpressionNode buildExpressionNode(Children children) throws Exception {
		ExpressionNode expressionNode = null;
		while (true) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("}")) {
				break;
			} else if (text.equals(";")) {
				if (expressionNode != null)
					expressionNode.appendText(text);
			} else {
				expressionNode = buildExpressionNode(child);
			}
		}
		return expressionNode;
	}

	public ForNode buildForNode(Tree source) throws Exception {
		Children children = new Children(source);
		ForNode forNode = new ForNode(source);
		forNode.setName(source.getText());
		forNode.setText("for");
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			//System.out.println(">>>"+text);
			
			if (text.equals("(")) {
				Tree expressionTree = children.pop();
				forNode.setStartExpressionNode(buildExpressionNode(expressionTree));
				child = children.pop();
				text = child.getText();
				Assert.isTrue(text.equals(";"));
				
				expressionTree = children.pop();
				forNode.setConditionExpressionNode(buildExpressionNode(expressionTree));
				child = children.pop();
				text = child.getText();
				Assert.isTrue(text.equals(";"));
				
				expressionTree = children.pop();
				forNode.setUpdateExpressionNode(buildExpressionNode(expressionTree));
				child = children.pop();
				text = child.getText();
				Assert.isTrue(text.equals(")"));
				continue;
				
			} else if (text.equals("EXPR")) {
				StatementNode statementNode = buildStatementNode(child);
				forNode.addCommandNode(statementNode);
				
			} else if (text.equals("BLOCK")) {
				BlockNode executeNode = buildBlockNode(child);
				forNode.addCommandNode(executeNode);
			}
		}
		
		return forNode;
	}

	public DoNode buildDoNode(Tree source) throws Exception {
		Children children = new Children(source);
		String text = source.getText();
		DoNode doNode = new DoNode(source);
		return doNode;
	}

	public ContinueNode buildContinueNode(Tree source) throws Exception {
		Children children = new Children(source);
		String text = source.getText();
		ContinueNode continueNode = new ContinueNode(source);
		return continueNode;
	}

	public BreakNode buildBreakNode(Tree source) throws Exception {
		Children children = new Children(source);
		String text = source.getText();
		BreakNode breakNode = new BreakNode(source);
		return breakNode;
	}

	public ThrowNode buildThrowNode(Tree source) throws Exception {
		Children children = new Children(source);
		String text = source.getText();
		ThrowNode throwNode = new ThrowNode(source);
		return throwNode;
	}

	public ReturnNode buildReturnNode(Tree source) throws Exception {
		Children children = new Children(source);
		String text = source.getText();
		ReturnNode returnNode = new ReturnNode(source);
		return returnNode;
	}

	public TimeoutNode buildTimeoutNode() {
		//TODO need to have default timeout value which is configurable
		TimeoutNode timeoutNode = new TimeoutNode();
		timeoutNode.setValue("60");
		timeoutNode.setUnit("s");
		return timeoutNode;
	}

	public TimeoutNode buildTimeoutNode(Tree source, Set<PropertyNode> propertyNodes) throws Exception {
		Children children = new Children(source);
		TimeoutNode timeoutNode = new TimeoutNode(source);
		pushCommand(timeoutNode);
		
		//timeoutNode.setName(children.pop().getText());
		Tree token = children.pop();
		String lookAheadText = token.getText();
		if (lookAheadText.equals("[")) {
			timeoutNode.setParameterNodes(buildParameterNodes(children));
			children.pop();//remove the "]"
		} else
			children.push(token);
		children.pop();//remove the ":"
		
		if (propertyNodes.size() > 0) {
			Iterator<PropertyNode> iterator = propertyNodes.iterator();
			while (iterator.hasNext()) {
				PropertyNode propertyNode = iterator.next();
				if (propertyNode.getName().equals("timeout")) {
					String value = propertyNode.getValue();
					int length = value.length();
					long timeout = 0L;
					String unit = "s";
					if (value.endsWith("sec")) {
						value = value.substring(0, length-3);
						timeout = Long.parseLong(value);
						timeout *= 1000;
						unit = "s";
					} else if (value.endsWith("min")) {
						value = value.substring(0, length-3);
						timeout = Long.parseLong(value);
						timeout *= 1000 * 60;
						unit = "m";
					} else if (value.endsWith("hr")) {
						value = value.substring(0, length-2);
						timeout = Long.parseLong(value);
						timeout *= 1000 * 60 * 60;
						unit = "h";
					} else if (value.endsWith("day")) {
						value = value.substring(0, length-3);
						timeout = Long.parseLong(value);
						timeout *= 1000 * 60 * 60 * 24;
						unit = "d";
					} else {
						timeout = Long.parseLong(value);
						timeout *= 1000;
						unit = "s";
					}
					timeoutNode.setValue(Long.toString(timeout));
					timeoutNode.setUnit(unit);
					break;
				}
			}
		}
		buildPropertyNodes(timeoutNode, children);
		timeoutNode.setCommandNodes(buildCommandNodes(source, children, timeoutNode.getPropertyNodes()));
		popCommand(timeoutNode);
		return timeoutNode;
	}
	
//	public ExceptionNode buildExceptionNode(Tree source, List<PropertyNode> propertyNodes) throws Exception {
//		Children children = new Children(source);
//		ExceptionNode exceptionNode = new ExceptionNode(source);
//		pushCommand(exceptionNode);
//		
//		//timeoutNode.setName(children.pop().getText());
//		Tree token = children.pop();
//		String lookAheadText = token.getText();
//		if (lookAheadText.equals("[")) {
//			exceptionNode.setParameterNodes(buildParameterNodes(children));
//			children.pop();//remove the "]"
//		} else
//			children.push(token);
//		children.pop();//remove the ":"
//		
//		exceptionNode.setPropertyNodes(buildPropertyNodes(children));
//		exceptionNode.setCommandNodes(buildCommandNodes(source, children, exceptionNode.getPropertyNodes()));
//		popCommand(exceptionNode);
//		return exceptionNode;
//	}
	

	public VariableNode buildVariableNode(Tree source) throws Exception {
		return null;
	}

	
	protected void buildSubNodes(BlockNode blockNode, Children children) throws Exception {
		while (!children.empty()) {
			Tree child = buildSubNode(blockNode, children);
			if (child == null)
				break;
		}
	}
	
	protected Tree buildSubNode(BlockNode blockNode, Children children) throws Exception {
		Tree child = children.pop();
		String text = child.getText();
		if (text.equals("{"))
			return child;
		if (text.equals("}"))
			return null;
		if (text.equals("throws"))
			return child;
		if (text.equals("(")) {
			blockNode.setParameterNodes(buildParameterNodes(children));
		} else if (text.equals("import")) {
			addPropertyNode(blockNode, child);
		} else if (text.equals("add")) {
			addPropertyNode(blockNode, child);
		} else if (text.equals("set")) {
			setPropertyNode(blockNode, child);
		} else if (text.equals("join")) {
			addPropertyNode(blockNode, child);
		//} else if (text.equals("option")) {
		//	blockNode.addOptionNode(buildOptionNode(child));
		} else if (text.equals("execute")) {
			blockNode.addCommandNode(buildExecuteNode(child));
		} else if (text.equals("invoke")) {
			blockNode.addCommandNode(buildInvokeNode(child));
		} else if (text.equals("listen")) {
			blockNode.addCommandNode(buildListenNode(child));
		} else if (text.equals("post")) {
			blockNode.addCommandNode(buildPostNode(child));
		} else if (text.equals("reply")) {
			blockNode.addCommandNode(buildReplyNode(child));
		} else if (text.equals("send")) {
			blockNode.addCommandNode(buildSendNode(child));
		} else if (text.equals("done")) {
			blockNode.addCommandNode(buildDoneNode(child));
		} else if (text.equals("EXPR")) {
			blockNode.addCommandNode(buildStatementNode(child));
		} else if (AriesASTUtil.isStatement(text)) {
			blockNode.addCommandNode(buildStatementNode(child));
		} else if (AriesASTUtil.isEndpoint(text)) {
			blockNode.addCommandNode(buildEndpointNode(child));
		} else if (AriesASTUtil.isCommand(text)) {
			blockNode.addCommandNode(buildCommandNode(child));
		//} else if (AriesASTUtil.isName(text)) {
		//	blockNode.addCommandNode(buildCommandNode(child, children));
		} else if (text.equals("exception")) {
			blockNode.addExceptionNode(buildExceptionNode(child));
		} else if (text.equals("timeout")) {
			blockNode.addTimeoutNode(buildTimeoutNode(child, blockNode.getPropertyNodes()));
		} else if (text.equals("TYPE")) {
			//System.out.println("TYPE");
		} else if (text.equals("PRIMARY")) {
			//System.out.println("PRIMARY");
		}
		
		return child;
	}

	

	@SuppressWarnings("serial")
	class Children extends Stack<Tree> {
		
		private List<Tree> children = new ArrayList<Tree>();
		
		
		public Children(Tree source) {
			initialize(source);
		}
		
		protected void initialize(Tree source) {
			int count = source.getChildCount();
			for (int i = 0; i < count; i++) {
				Tree child = source.getChild(i);
				children.add(child);
				//push(child);
			}
			for (int i = count; i > 0; i--) {
				Tree child = source.getChild(i-1);
				push(child);
			}
		}

		public Tree pop() {
			try {
				Tree tree = super.pop();
				return tree;
			} catch (EmptyStackException e) {
				return null;
			}
		}
		
		public String toString() {
			return children.toString();
		}
	}
	
	public List<String> buildSourceCodeFromExpressionNode(Tree source) {
		List<String> buf = new ArrayList<String>();
		Children children = new Children(source);
		//boolean paddingNeeded = false;
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();

			if (text.equals("TYPE")) {
				buf.addAll(buildSourceCodeFromTypeNode(child));
				buf.add(" ");
				
			} else if (text.equals("PRIMARY")) {
				buf.addAll(buildSourceCodeFromPrimaryNode(child));
				String nextToken = peekNextTokenText(children);
				if (nextToken.length() > 0 && !nextToken.equals("++") && !nextToken.equals("--")) {
					buf.add(" ");
				}

			} else if (text.equals("EXPR")) {
				buf.addAll(buildSourceCodeFromExpressionNode(child));
				buf.add(" ");

			} else if (text.equals("(") || text.equals(")")) {
				AriesASTUtil.trimCommandSource(buf);
				buf.add(text.trim());
//				paddingNeeded = false;
				
			} else if (text.equals(".")) {
				buf.add(text.trim());
//				paddingNeeded = false;
				
			} else {
//				if (paddingNeeded)
//					buf.append(" ");
				buf.add(text);
				if (AriesASTUtil.isRelationalOp(text)) {
					buf.add(" ");
				} else {
					//buf.append(text);
				}
//				paddingNeeded = true;
			}
		}
		AriesASTUtil.trimCommandSource(buf);
		return buf;
	}

	public List<String> buildSourceCodeFromTypeNode(Tree source) {
		List<String> buf = new ArrayList<String>();
		Children children = new Children(source);
		
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();
			if (text.equals("TYPE")) {
				buf.addAll(buildSourceCodeFromTypeNode(child));
			} else {
				buf.add(text);
			}
		}
		AriesASTUtil.trimCommandSource(buf);
		return buf;
	}

	public List<String> buildSourceCodeFromPrimaryNode(Tree source) {
		List<String> buf = new ArrayList<String>();
		Children children = new Children(source);
		while (!children.empty()) {
			Tree child = children.pop();
			String text = child.getText();

			if (text.equals("TYPE")) {
				buf.addAll(buildSourceCodeFromTypeNode(child));
				buf.add(" ");

			} else if (text.equals("EXPR")) {
				buf.addAll(buildSourceCodeFromExpressionNode(child));
				buf.add(" ");

			} else {
				if (text.equals(")"))
					AriesASTUtil.trimCommandSource(buf);
				buf.add(text);
			}
		}
		AriesASTUtil.trimCommandSource(buf);
		return buf;
	}

	protected String peekNextTokenText(Children children) {
		Tree child = children.pop();
		if (child == null)
			return "";
		String text = child.getText();
		children.push(child);
		return text;
	}

}

