package org.aries.validate.expr;

import java.util.Map;

import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.xpath10.Expr;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.parser.XPath10Factory;


public class ExpressionGenerator {

	protected boolean verbose;
	
	protected Map<String, Variable> variables;

	
	public ExpressionGenerator(Map<String, Variable> variables) {
		this.variables = variables;
		this.verbose = true;
	}

	public String generate(Expression expression) throws Exception {
		String output = processExpression(expression, true);
		return output;
	}

	public String generate(Expression expression, boolean leftHandSide) throws Exception {
		String output = processExpression(expression, leftHandSide);
		return output;
	}

	public String generate(Object expression) throws Exception {
		String output = processExpression(expression, false);
		return output;
	}

	protected String processExpression(Expression expression) throws Exception {
		return processExpression(expression, false);
	}

	protected String processExpression(Expression expression, boolean leftHandSide) throws Exception {
		return processExpression(expression.getBody(), leftHandSide);
	}
	
	protected String processExpression(Object expression, boolean leftHandSide) throws Exception {
		//String expressionLanguage = expression.getExpressionLanguage();
		//Boolean opaque = expression.getOpaque();

		Expr expr1 = XPath10Factory.create(expression.toString());
		if (expr1 instanceof UnaryExpr) {
			expr1 = ((UnaryExpr) expr1).getExpr();
		}

		BPELExpressionParser parser = new BPELExpressionParser();
		parser.setVariablesMap(variables);
		parser.setLeftHandSide(leftHandSide);
		parser.setVerbose(verbose);
		String output = parser.visit(expr1);

		//		String result = resultBuf.get();
		//		if (result.startsWith("$")) {
		//			result = result.substring(1);
		//			String variableName = result;
		//			if (result.contains(".")) {
		//				String[] split = TokenUtil.tokenize(result);
		//				variableName = split[0];
		//				String variableType = split[1];
		//			}
		//
		//			Variable variable = variables.get(variableName);
		//			Assert.notNull(variable, "Variable not found: "+variableName);
		//
		//			//ServiceState state = context.getService().getState();
		//			//ServiceVariable variable = ServiceStateUtil.getVariableByName(state, variableName);
		//			//String localPart = TypeUtil.getLocalPart(variable.getType());
		//			//if (localPart.equals(variableType)) {
		//			//	buf.putLine2(variableName+"."+getSetterName(fieldName)+"(\""+fieldValue+"\");");
		//			//}
		//			
		//			return result;
		//		}

		return output;
	}

}
