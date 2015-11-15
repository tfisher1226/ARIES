package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Persistence node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>Properties properties</pre>
 *  
 */
public class PersistanceNode extends AbstractNode {

	private List<RoleNode> roleNodes = new ArrayList<RoleNode>();

	private List<ItemNode> itemNodes = new ArrayList<ItemNode>();

	
	public PersistanceNode(Object node) {
		super(node);
	}


	public List<RoleNode> getRoleNodes() {
		return roleNodes;
	}

	public void addRoleNode(RoleNode roleNode) {
		roleNodes.add(roleNode);
	}

	public List<ItemNode> getItemNodes() {
		return itemNodes;
	}

	public void addItemNode(ItemNode itemNode) {
		itemNodes.add(itemNode);
	}
	
}
