package org.aries.ast.node;


/**
 * <p>Result node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *  
 */
public class ResultNode extends AbstractNode {
	
	private String construct;

	private String key;


	public ResultNode(Object node) {
		super(node);
	}

	public String getConstruct() {
		return construct;
	}

	public void setConstruct(String struct) {
		this.construct = struct;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String toString() {
		String type = super.toString();
		if (construct.equalsIgnoreCase("list"))
			return "List<"+type+">";
		else if (construct.equalsIgnoreCase("set"))
			return "Set<"+type+">";
		else if (construct.equalsIgnoreCase("map"))
			return "Map<"+key+", "+type+">";
		return super.toString();
	}

}
