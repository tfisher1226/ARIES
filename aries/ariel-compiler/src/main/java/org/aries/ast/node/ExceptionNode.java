package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Exception node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>List<String> domains</pre>
 * 
 * <pre>Properties properties</pre>
 *  
 */
public class ExceptionNode extends BlockNode {

	private List<ExpressionNode> expressionNodes = new ArrayList<ExpressionNode>();
	
	
	public ExceptionNode() {
	}
	
	public ExceptionNode(Object node) {
		super(node);
	}

	public List<ExpressionNode> getExpressionNodes() {
		return expressionNodes;
	}

	public void addExpressionNode(ExpressionNode expressionNode) {
		expressionNodes.add(expressionNode);
	}

}
