package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Subscribe node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>RoleNode role</pre>
 * 
 * <pre>ChannelNode channel</pre>
 *
 * <pre>List<PropertyNode> properties</pre>
 *  
 * <pre>List<VariableNode> variables</pre>
 *  
 * <pre>List<MessageNode> messages</pre>
 *  
 * <pre>List<ExceptionNode> exceptions</pre>
 *  
 * <pre>List<TimeoutNode> timeout</pre>
 *  
 */
public class SubscribeNode extends EndpointNode {

	private List<SubscribeNode> roleNodes = new ArrayList<SubscribeNode>();
	
	
	public SubscribeNode(Object node) {
		super(node);
	}


	public List<SubscribeNode> getRoleNodes() {
		return roleNodes;
	}

	public void addRoleNode(SubscribeNode roleNode) {
		roleNodes.add(roleNode);
	}

}
