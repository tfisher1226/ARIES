package org.aries.ast.node;


/**
 * <p>Branch node class for Ariel Abstract Syntax Tree.
 * 
 * <p>The following attributes are configurable in Ariel Language.
 * 
 * <pre>String name</pre>
 * 
 * <pre>String type</pre>
 *
 * <pre>List<OptionNode> options</pre>
 *  
 * <pre>List<ListenNode> listens</pre>
 *  
 * <pre>List<MethodNode> methods</pre>
 *  
 * <pre>List<CommandNode> commands</pre>
 *  
 */
public class BranchNode extends BlockNode {
	
	public BranchNode(Object node) {
		super(node);
	}

}
