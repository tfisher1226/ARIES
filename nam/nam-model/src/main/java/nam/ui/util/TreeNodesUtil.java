package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.ui.TreeNodes;


public class TreeNodesUtil extends BaseUtil {

	public static boolean isEmpty(TreeNodes treeNodes) {
		if (treeNodes == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<TreeNodes> treeNodesList) {
		if (treeNodesList == null  || treeNodesList.size() == 0)
			return true;
		Iterator<TreeNodes> iterator = treeNodesList.iterator();
		while (iterator.hasNext()) {
			TreeNodes treeNodes = iterator.next();
			if (!isEmpty(treeNodes))
				return false;
		}
		return true;
	}
	
	public static String toString(TreeNodes treeNodes) {
		if (isEmpty(treeNodes))
			return "TreeNodes: [uninitialized] "+treeNodes.toString();
		String text = treeNodes.toString();
		return text;
	}
	
	public static String toString(Collection<TreeNodes> treeNodesList) {
		if (isEmpty(treeNodesList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<TreeNodes> iterator = treeNodesList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			TreeNodes treeNodes = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(treeNodes);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static TreeNodes create() {
		TreeNodes treeNodes = new TreeNodes();
		initialize(treeNodes);
		return treeNodes;
	}
	
	public static void initialize(TreeNodes treeNodes) {
		//nothing for now
	}
	
	public static boolean validate(TreeNodes treeNodes) {
		if (treeNodes == null)
			return false;
		Validator validator = Validator.getValidator();
		TreeNodeUtil.validate(treeNodes.getTreeNodes());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<TreeNodes> treeNodesList) {
		Validator validator = Validator.getValidator();
		Iterator<TreeNodes> iterator = treeNodesList.iterator();
		while (iterator.hasNext()) {
			TreeNodes treeNodes = iterator.next();
			//TODO break or accumulate?
			validate(treeNodes);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}

	public static TreeNodes clone(TreeNodes treeNodes) {
		if (treeNodes == null)
			return null;
		TreeNodes clone = create();
		clone.getTreeNodes().addAll(TreeNodeUtil.clone(treeNodes.getTreeNodes()));
		return clone;
	}
	
}
