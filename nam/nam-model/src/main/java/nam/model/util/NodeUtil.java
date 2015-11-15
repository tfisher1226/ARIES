package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Node;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class NodeUtil extends BaseUtil {

	public static Object getKey(Node node) {
		return node.getName();
	}
	
	public static String getLabel(Node node) {
		return node.getName();
	}
	
	public static boolean getLabel(Collection<Node> nodeList) {
		if (nodeList == null  || nodeList.size() == 0)
			return true;
		Iterator<Node> iterator = nodeList.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (!isEmpty(node))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Node node) {
		if (node == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Node> nodeList) {
		if (nodeList == null  || nodeList.size() == 0)
			return true;
		Iterator<Node> iterator = nodeList.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (!isEmpty(node))
				return false;
		}
		return true;
	}
	
	public static String toString(Node node) {
		if (isEmpty(node))
			return "Node: [uninitialized] "+node.toString();
		String text = node.toString();
		return text;
	}
	
	public static String toString(Collection<Node> nodeList) {
		if (isEmpty(nodeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Node> iterator = nodeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Node node = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(node);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Node create() {
		Node node = new Node();
		initialize(node);
		return node;
	}
	
	public static void initialize(Node node) {
		//nothing for now
	}
	
	public static boolean validate(Node node) {
		if (node == null)
			return false;
		Validator validator = Validator.getValidator();
		PropertyUtil.validate(node.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Node> nodeList) {
		Validator validator = Validator.getValidator();
		Iterator<Node> iterator = nodeList.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			//TODO break or accumulate?
			validate(node);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Node> nodeList) {
		Collections.sort(nodeList, createNodeComparator());
	}
	
	public static Collection<Node> sortRecords(Collection<Node> nodeCollection) {
		List<Node> list = new ArrayList<Node>(nodeCollection);
		Collections.sort(list, createNodeComparator());
		return list;
	}
	
	public static Comparator<Node> createNodeComparator() {
		return new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				Object key1 = getKey(node1);
				Object key2 = getKey(node2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Node clone(Node node) {
		if (node == null)
			return null;
		Node clone = create();
		clone.setName(ObjectUtil.clone(node.getName()));
		clone.setNodeIP(ObjectUtil.clone(node.getNodeIP()));
		clone.setProperties(PropertyUtil.clone(node.getProperties()));
		return clone;
	}
	
}
