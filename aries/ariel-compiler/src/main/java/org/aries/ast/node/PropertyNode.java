package org.aries.ast.node;


/**
 * <p>Property node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>Object value</pre>
 * 
 */
public class PropertyNode extends AbstractNode {
	
	public PropertyNode(Object node) {
		super(node);
	}
	
	public String toString() {
		return super.toString() + " = " + getValue();
	}

}
