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
 * <pre>List<RoleNode> roleNodes</pre>
 * 
 * <pre>Properties properties</pre>
 *  
 */
public class NetworkNode extends AbstractNode {

	private List<RoleNode> roleNodes = new ArrayList<RoleNode>();
	
	private List<ChannelNode> channelNodes = new ArrayList<ChannelNode>();

	private List<GroupNode> groupNodes = new ArrayList<GroupNode>();

	private List<ParticipantNode> participantNodes = new ArrayList<ParticipantNode>();

	private List<CacheNode> cacheNodes = new ArrayList<CacheNode>();

	private List<InformationNode> informationNodes = new ArrayList<InformationNode>();

	private List<PersistanceNode> persistanceNodes = new ArrayList<PersistanceNode>();

	
	public NetworkNode(Object node) {
		super(node);
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

	public List<GroupNode> getGroupNodes() {
		return groupNodes;
	}

	public void addGroupNode(GroupNode groupNode) {
		groupNodes.add(groupNode);
	}

	public List<ParticipantNode> getParticipantNodes() {
		return participantNodes;
	}

	public void addParticipantNode(ParticipantNode participantNode) {
		participantNodes.add(participantNode);
	}

	public List<CacheNode> getCacheNodes() {
		return cacheNodes;
	}

	public void addCacheNode(CacheNode cacheNode) {
		cacheNodes.add(cacheNode);
	}

	public List<InformationNode> getInformationNodes() {
		return informationNodes;
	}

	public void addInformationNode(InformationNode informationNode) {
		informationNodes.add(informationNode);
	}
	
	public List<PersistanceNode> getPersistanceNodes() {
		return persistanceNodes;
	}

	public void addPersistanceNode(PersistanceNode persistanceNode) {
		persistanceNodes.add(persistanceNode);
	}

}
