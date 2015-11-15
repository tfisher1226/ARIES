package org.aries.ast.node;


/**
 * <p>OperatorNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String text</pre>
 * 
 */
public class OperatorNode extends AbstractNode {

	private String operator;

	
	public OperatorNode(Object node) {
		super(node);
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
