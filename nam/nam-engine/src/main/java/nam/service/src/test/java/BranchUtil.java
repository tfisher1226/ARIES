package nam.service.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Definition;
import nam.model.statement.ExpressionStatement;


public class BranchUtil {

	/**
	 * Points at the object that will be acted upon.
	 * This can be an object reference or a method.
	 */
	public static String getObjectName(ExpressionStatement expressionStatement) {
		//String variableName = expressionStatement.getTargetName();
		Definition definition = expressionStatement.getDefinition();
		String variableSource = null;
		if (definition != null)
			variableSource = definition.getTarget();
		if (variableSource == null)
			variableSource = expressionStatement.getTargetName();
		if (variableSource.endsWith("Manager"))
			variableSource = variableSource.replace("Manager",  "");
		return variableSource;
	}
	
	public static String getValue(ExpressionStatement expressionStatement) {
		//String variableName = expressionStatement.getTargetName();
		Definition definition = expressionStatement.getDefinition();
		if (definition != null) {
			return getSource(definition);
		}

		//at this point - we have a literal - check for a constant
		List<String> selectorChain = expressionStatement.getSelectorChain();
		if (selectorChain != null && selectorChain.size() > 0) {
			//TODO
			return null;
		}
		
		//at this point - selector holds a raw value
		String selector = expressionStatement.getSelector();
		return selector;
	}
	
	public static String getSource(Definition definition) {
		StringBuffer buf = new StringBuffer();
		String target = definition.getTarget();
		buf.append(target);
		
		List<String> selectors = definition.getSelectors();
		Iterator<String> iterator = selectors.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String selector = iterator.next();
			buf.append(".");
			buf.append(selector);
		}
		
		buf.append("(");
		List<String> parameters = definition.getParameters();
		Iterator<String> iterator2 = parameters.iterator();
		for (int i=0; iterator2.hasNext(); i++) {
			String parameter = iterator2.next();
			if (i > 0)
				buf.append(", ");
			buf.append(parameter);
		}
		buf.append(")");
		return buf.toString();
	}

}
