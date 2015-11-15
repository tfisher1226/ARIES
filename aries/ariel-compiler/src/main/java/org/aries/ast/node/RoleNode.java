package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;



/**
 * <p>Role node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *
 * <pre>List<PrincipleNode> credentials</pre>
 *  
 */
public class RoleNode extends AbstractNode {

	private List<PrincipleNode> principleNodes = new ArrayList<PrincipleNode>();
	
	
	public RoleNode(Object node) {
		super(node);
	}

	public List<PrincipleNode> getPrincipleNodes() {
		return principleNodes;
	}

	public void addPrincipleNode(PrincipleNode principleNode) {
		principleNodes.add(principleNode);
	}

}
