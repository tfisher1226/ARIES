package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.ui.TreeNode;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.Validator;


public class TreeNodeUtil extends BaseUtil {
	
	public static Object getKey(TreeNode treeNode) {
		return treeNode.getType();
	}
	
	public static String getLabel(TreeNode treeNode) {
		return treeNode.getLabel();
	}
	
	public static boolean isEmpty(TreeNode treeNode) {
		if (treeNode == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<TreeNode> treeNodeList) {
		if (treeNodeList == null  || treeNodeList.size() == 0)
			return true;
		Iterator<TreeNode> iterator = treeNodeList.iterator();
		while (iterator.hasNext()) {
			TreeNode treeNode = iterator.next();
			if (!isEmpty(treeNode))
				return false;
		}
		return true;
	}
	
	public static String toString(TreeNode treeNode) {
		if (isEmpty(treeNode))
			return "TreeNode: [uninitialized] "+treeNode.toString();
		String text = treeNode.toString();
		return text;
	}
	
	public static String toString(Collection<TreeNode> treeNodeList) {
		if (isEmpty(treeNodeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<TreeNode> iterator = treeNodeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TreeNode treeNode = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(treeNode);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static TreeNode create() {
		TreeNode treeNode = new TreeNode();
		initialize(treeNode);
		return treeNode;
	}
	
	public static void initialize(TreeNode treeNode) {
		//nothing for now
	}
	
	public static boolean validate(TreeNode treeNode) {
		if (treeNode == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<TreeNode> treeNodeList) {
		Validator validator = Validator.getValidator();
		Iterator<TreeNode> iterator = treeNodeList.iterator();
		while (iterator.hasNext()) {
			TreeNode treeNode = iterator.next();
			//TODO break or accumulate?
			validate(treeNode);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<TreeNode> treeNodeList) {
		Collections.sort(treeNodeList, createTreeNodeComparator());
	}
	
	public static Collection<TreeNode> sortRecords(Collection<TreeNode> treeNodeCollection) {
		List<TreeNode> list = new ArrayList<TreeNode>(treeNodeCollection);
		Collections.sort(list, createTreeNodeComparator());
		return list;
	}
	
	public static Comparator<TreeNode> createTreeNodeComparator() {
		return new Comparator<TreeNode>() {
			public int compare(TreeNode treeNode1, TreeNode treeNode2) {
				Object key1 = getKey(treeNode1);
				Object key2 = getKey(treeNode2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static TreeNode clone(TreeNode treeNode) {
		if (treeNode == null)
			return null;
		TreeNode clone = create();
		return clone;
	}
	
	public static List<TreeNode> clone(List<TreeNode> treeNodeList) {
		if (treeNodeList == null)
			return null;
		List<TreeNode> newList = new ArrayList<TreeNode>();
		Iterator<TreeNode> iterator = treeNodeList.iterator();
		while (iterator.hasNext()) {
			TreeNode treeNode = iterator.next();
			TreeNode clone = clone(treeNode);
			newList.add(clone);
		}
		return newList;
	}
	
}
