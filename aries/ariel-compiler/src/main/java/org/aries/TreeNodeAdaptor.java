package org.aries;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;
import org.aries.ast.node.NetworkNode;


public class TreeNodeAdaptor extends CommonTreeAdaptor implements TreeAdaptor {

	public Object create(Token token) {
		Object node = null;
		//node = super.create(token);
		//if (token.toString().equals("protocol"))
			node = new NetworkNode(node);
		return node;
	}

}
