package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Publish node class for Ariel Abstract Syntax Tree.
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
public class PublishNode extends EndpointNode {

	private List<PublishNode> roleNodes = new ArrayList<PublishNode>();
	
	
	public PublishNode(Object node) {
		super(node);
	}


	public List<PublishNode> getRoleNodes() {
		return roleNodes;
	}

	public void addRoleNode(PublishNode roleNode) {
		roleNodes.add(roleNode);
	}

}
