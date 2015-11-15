package nam.model.statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.CommandName;
import nam.model.Definition;
import nam.model.Type;

import org.aries.util.NameUtil;


public class ExpressionStatement extends Statement {

	private String targetName;
	
	private Type targetType;
	
	private List<String> selectorChain;

	private String selector;

	private Definition definition;

	private ExpressionStatement subExpression;

	private boolean nameExtensionEnabled = true;

	
	public ExpressionStatement() {
		setName(CommandName.EXPRESSION);
		setType("expression");
	}
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Type getTargetType() {
		return targetType;
	}

	public void setTargetType(Type targetType) {
		this.targetType = targetType;
	}

	public List<String> getSelectorChain() {
		if (selectorChain == null)
			selectorChain = new ArrayList<String>();
		return selectorChain;
	}

	public void setSelectorChain(List<String> selectorChain) {
		this.selectorChain = selectorChain;
	}
	
	public void addSelector(String selector) {
		getSelectorChain().add(selector);
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}
	
	public Definition getDefinition() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	public ExpressionStatement getSubExpression() {
		return subExpression;
	}

	public void setSubExpression(ExpressionStatement subExpression) {
		this.subExpression = subExpression;
	}

	public String getLabel() {
		String label = "";
		if (!targetName.equals("this"))
			label += NameUtil.capName(targetName);
		//String label = objectName;
		if (nameExtensionEnabled) {
			if (selectorChain != null) {
				Iterator<String> iterator = selectorChain.iterator();
				while (iterator.hasNext()) {
					String fieldName = NameUtil.capName(iterator.next());
					label += "_" + fieldName;
				}
			}
			if (selector != null)
				label += "_" + NameUtil.capName(selector);
		}
		return label;
	}

}
