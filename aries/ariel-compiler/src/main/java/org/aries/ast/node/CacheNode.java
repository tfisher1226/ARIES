package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Cache node class for Ariel Abstract Syntax Tree.
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
public class CacheNode extends AbstractNode {

	private List<ItemNode> itemNodes = new ArrayList<ItemNode>();
	
	
	public CacheNode(Object node) {
		super(node);
	}

	public List<ItemNode> getItemNodes() {
		return itemNodes;
	}

	public void addItemNode(ItemNode itemNode) {
		itemNodes.add(itemNode);
	}

}
