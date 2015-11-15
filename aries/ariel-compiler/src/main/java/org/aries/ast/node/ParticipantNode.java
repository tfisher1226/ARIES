package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Protocol node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>List<RoleNode> roles</pre>
 *  
 * <pre>List<SendNode> sends</pre>
 *  
 * <pre>List<ReceiveNode> receives</pre>
 *  
 * <pre>List<InvokeNode> invokes</pre>
 *  
 * <pre>List<SubscribeNode> subscribes</pre>
 *  
 * <pre>List<PublishNode> publishes</pre>
 *  
 */
public class ParticipantNode extends AbstractNode {

	private String namespace;
	
	private List<RoleNode> roleNodes = new ArrayList<RoleNode>();

	private List<CommandNode> commandNodes = new ArrayList<CommandNode>();

	private List<ChannelNode> channelNodes = new ArrayList<ChannelNode>();

//	private List<ReceiveNode> receiveNodes = new ArrayList<ReceiveNode>();
//
//	private List<InvokeNode> invokeNodes = new ArrayList<InvokeNode>();
//
//	private List<SubscribeNode> subscribeNodes = new ArrayList<SubscribeNode>();
//
//	private List<PublishNode> publishNodes = new ArrayList<PublishNode>();
//
//	private List<EndpointNode> endpointNodes = new ArrayList<EndpointNode>();
//
//	private List<MethodNode> methodNodes = new ArrayList<MethodNode>();

	private List<CacheNode> cacheNodes = new ArrayList<CacheNode>();

	private List<PersistanceNode> persistanceNodes = new ArrayList<PersistanceNode>();

	
	public ParticipantNode(Object node) {
		super(node);
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<RoleNode> getRoleNodes() {
		return roleNodes;
	}

	public void addRoleNode(RoleNode roleNode) {
		roleNodes.add(roleNode);
	}

	public List<ChannelNode> getChannelNodes() {
		return channelNodes;
	}

	public void addChannelNode(ChannelNode channelNode) {
		channelNodes.add(channelNode);
	}

	public List<CommandNode> getCommandNodes() {
		return commandNodes;
	}

	public void addCommandNode(CommandNode commandNode) {
		commandNodes.add(commandNode);
	}

//	public List<EndpointNode> getEndpointNodes() {
//		return endpointNodes;
//	}
//
//	public List<SendNode> getSendNodes() {
//		return sendNodes;
//	}
//
//	public List<ReceiveNode> getReceiveNodes() {
//		return receiveNodes;
//	}
//
//	public List<InvokeNode> getInvokeNodes() {
//		return invokeNodes;
//	}
//
//	public List<SubscribeNode> getSubscribeNodes() {
//		return subscribeNodes;
//	}
//
//	public List<PublishNode> getPublishNodes() {
//		return publishNodes;
//	}
//
//	public List<EndpointNode> getEndpointNodes() {
//		return endpointNodes;
//	}

//	public void addEndpointNode(EndpointNode endpointNode) {
//		endpointNodes.add(endpointNode);
//		if (endpointNode instanceof ReceiveNode)
//			receiveNodes.add((ReceiveNode) endpointNode);
//		if (endpointNode instanceof SendNode)
//			sendNodes.add((SendNode) endpointNode);
//		if (endpointNode instanceof InvokeNode)
//			invokeNodes.add((InvokeNode) endpointNode);
//		if (endpointNode instanceof SubscribeNode)
//			subscribeNodes.add((SubscribeNode) endpointNode);
//		if (endpointNode instanceof PublishNode)
//			publishNodes.add((PublishNode) endpointNode);
//	}

//	public List<MethodNode> getMethodNodes() {
//		return methodNodes;
//	}
//
//	public void addMethodNode(MethodNode methodNode) {
//		methodNodes.add(methodNode);
//	}

	public List<CacheNode> getCacheNodes() {
		return cacheNodes;
	}

	public void addCacheNode(CacheNode cacheNode) {
		cacheNodes.add(cacheNode);
	}

	public List<PersistanceNode> getPersistanceNodes() {
		return persistanceNodes;
	}

	public void addPersistanceNode(PersistanceNode persistanceNode) {
		persistanceNodes.add(persistanceNode);
	}

}
