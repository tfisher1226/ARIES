package nam.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Import;
import nam.ui.Relation;
import nam.ui.Relations;
import nam.ui.Tree;
import nam.ui.View;


public class ViewUtil {

	public static View newView() {
		View view = new View();
		return view;
	}

	public static boolean containsImport(View view, Import model) {
		return ProjectUtil.containsImport(getImports(view), model);
	}
	
	public static List<Import> getImports(View view) {
		return view.getImports();
	}
	
	/**
	 * Merges <code>View</code> blocks.
	 * Merges into block1 all Imports from block2.
	 */
	public static void mergeImports(View block1, View block2) {
		List<Import> list = new ArrayList<Import>(); 
		Iterator<Import> iterator = block2.getImports().iterator();
		while (iterator.hasNext()) {
			Import block2Import = iterator.next();
			if (!ImportUtil.isImportExists(block1.getImports(), block2Import)) {
				list.add(block2Import);
			} else {
				//TODO Merge the two importObjects here
			}
		}
		addImports(block1, list);
	}

	public static void addImports(View view, List<Import> imports) {
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import model = iterator.next();
			addImport(view, model);
		}
	}
	
	public static void addImport(View information, Import model) {
		if (!containsImport(information, model))
			information.getImports().add(model);
	}

	public static Tree getTreeByName(View view, String treeName) {
		List<Tree> trees = view.getControls().getTrees();
		Iterator<Tree> iterator = trees.iterator();
		while (iterator.hasNext()) {
			Tree tree = iterator.next();
			if (tree == null)
				continue;
			if (tree.getName() == null)
				continue;
			if (tree.getName().equalsIgnoreCase(treeName)) {
				return tree;
			}
		}
		return null;
	}

	public static Relation getContainedByRelation(View view, String name) {
		return getRelation(view, name, "containedBy");
	}
	
	public static Relation getManagedByRelation(View view, String name) {
		return getRelation(view, name, "managedBy");
	}
	
	public static Relation getMemberOfRelation(View view, String name) {
		return getRelation(view, name, "memberOf");
	}
	
	public static Relation getManagedSectionRelation(View view, String name) {
		return getRelation(view, name, "managedSection");
	}
	
	public static Relation getRelation(View view, String name, String pattern) {
		if (view == null)
			return null;
		Relations relations = view.getRelations();
		if (relations == null)
			return null;
		List<Relation> relationList = relations.getRelations();
		Iterator<Relation> iterator = relationList.iterator();
		while (iterator.hasNext()) {
			Relation relation = iterator.next();
			if (!relation.getName().equals(name))
				continue;
			if (!relation.getPattern().equals(pattern))
				continue;
			return relation;
		}
		return null;
	}
	
}
