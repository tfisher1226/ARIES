package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Channel node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>Properties properties</pre>
 *  
 */
public class ChannelNode extends AbstractNode {

	private List<RoleNode> senderRoleNodes = new ArrayList<RoleNode>();

	private List<RoleNode> receiverRoleNodes = new ArrayList<RoleNode>();

	private List<RoleNode> managerRoleNodes = new ArrayList<RoleNode>();

	
	public ChannelNode(Object node) {
		super(node);
	}

	public List<RoleNode> getSenderRoleNodes() {
		return senderRoleNodes;
	}

	public void addSenderRoleNode(RoleNode roleNode) {
		senderRoleNodes.add(roleNode);
	}

	public List<RoleNode> getReceiverRoleNodes() {
		return receiverRoleNodes;
	}

	public void addReceiverRoleNode(RoleNode roleNode) {
		receiverRoleNodes.add(roleNode);
	}

	public List<RoleNode> getManagerRoleNodes() {
		return managerRoleNodes;
	}

	public void addManagerRoleNode(RoleNode roleNode) {
		managerRoleNodes.add(roleNode);
	}

}
