package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Information node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>Properties properties</pre>
 *  
 */
public class InformationNode extends AbstractNode {

	private List<ItemNode> itemNodes = new ArrayList<ItemNode>();

	
	public InformationNode(Object node) {
		super(node);
	}


	public List<ItemNode> getItemNodes() {
		return itemNodes;
	}

	public void addItemNode(ItemNode itemNode) {
		itemNodes.add(itemNode);
	}
	
}
