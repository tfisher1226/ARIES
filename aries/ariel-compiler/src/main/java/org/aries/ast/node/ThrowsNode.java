package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>ThrowsNode class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>List<String> exceptionTypes</pre>
 *  
 */
public class ThrowsNode extends CommandNode {

	private List<String> exceptionTypes = new ArrayList<String>();
	
		
	public ThrowsNode(Object node) {
		super(node);
	}

	public List<String> getExceptionTypes() {
		return exceptionTypes;
	}

	public void addExceptionType(String exceptionType) {
		exceptionTypes.add(exceptionType);
	}

}
