package org.aries;

import nam.model.Project;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;


public class TreeWalker extends TreeParser {

	public TreeWalker(TreeNodeStream input) {
		super(input);
	}

	public Project visit(CommonTree node) {
		return null;
	}

}
