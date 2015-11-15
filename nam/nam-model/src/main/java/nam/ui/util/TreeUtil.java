package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.ui.Tree;


public class TreeUtil extends BaseUtil {
	
	public static Object getKey(Tree tree) {
		return tree.getName();
	}
	
	public static String getLabel(Tree tree) {
		return tree.getName();
	}
	
	public static boolean isEmpty(Tree tree) {
		if (tree == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(tree.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Tree> treeList) {
		if (treeList == null  || treeList.size() == 0)
			return true;
		Iterator<Tree> iterator = treeList.iterator();
		while (iterator.hasNext()) {
			Tree tree = iterator.next();
			if (!isEmpty(tree))
				return false;
		}
		return true;
	}
	
	public static String toString(Tree tree) {
		if (isEmpty(tree))
			return "Tree: [uninitialized] "+tree.toString();
		String text = tree.toString();
		return text;
	}
	
	public static String toString(Collection<Tree> treeList) {
		if (isEmpty(treeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Tree> iterator = treeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Tree tree = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(tree);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Tree create() {
		Tree tree = new Tree();
		initialize(tree);
		return tree;
	}
	
	public static void initialize(Tree tree) {
		//nothing for now
	}
	
	public static boolean validate(Tree tree) {
		if (tree == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(tree.getName(), "\"Name\" must be specified");
		TreeNodesUtil.validate(tree.getTreeNodes());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Tree> treeList) {
		Validator validator = Validator.getValidator();
		Iterator<Tree> iterator = treeList.iterator();
		while (iterator.hasNext()) {
			Tree tree = iterator.next();
			//TODO break or accumulate?
			validate(tree);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Tree> treeList) {
		Collections.sort(treeList, createTreeComparator());
	}
	
	public static Collection<Tree> sortRecords(Collection<Tree> treeCollection) {
		List<Tree> list = new ArrayList<Tree>(treeCollection);
		Collections.sort(list, createTreeComparator());
		return list;
	}
	
	public static Comparator<Tree> createTreeComparator() {
		return new Comparator<Tree>() {
			public int compare(Tree tree1, Tree tree2) {
				Object key1 = getKey(tree1);
				Object key2 = getKey(tree2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Tree clone(Tree tree) {
		if (tree == null)
			return null;
		Tree clone = create();
		clone.setName(ObjectUtil.clone(tree.getName()));
		clone.setTreeNodes(TreeNodesUtil.clone(tree.getTreeNodes()));
		return clone;
	}
	
	public static List<Tree> clone(List<Tree> treeList) {
		if (treeList == null)
			return null;
		List<Tree> newList = new ArrayList<Tree>();
		Iterator<Tree> iterator = treeList.iterator();
		while (iterator.hasNext()) {
			Tree tree = iterator.next();
			Tree clone = clone(tree);
			newList.add(clone);
		}
		return newList;
	}
	
}
