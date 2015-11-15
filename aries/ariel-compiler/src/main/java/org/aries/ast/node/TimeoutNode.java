package org.aries.ast.node;


/**
 * <p>Timeout node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String value</pre>
 *  
 * <pre>String unit</pre>
 *  
 */
public class TimeoutNode extends BlockNode {

	private String unit;
	
	
	public TimeoutNode() {
	}
	
	public TimeoutNode(Object node) {
		super(node);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
