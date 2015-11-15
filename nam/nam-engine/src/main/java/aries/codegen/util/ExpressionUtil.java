package aries.codegen.util;

import java.util.StringTokenizer;

import org.aries.util.NameUtil;


public class ExpressionUtil {

	public static String extractFieldFromExpression(String expression) {
		int lastIndexOf = expression.lastIndexOf(".");
		if (lastIndexOf == -1)
			return expression;
		expression = expression.substring(lastIndexOf+1);
		if (expression.startsWith("get"))
			expression = expression.substring(3);
		expression = NameUtil.uncapName(expression);
		if (expression.endsWith("()"))
			expression = expression.replace("()", "");
		return expression;
	}
	
	public static String parseExpression(String expression) {
		StringBuffer buf = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(expression, ".");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String nextToken = tokenizer.nextToken();
			if (i == 0) {
				buf.append(nextToken);
			} else {
				buf.append(".");
				buf.append("get"+NameUtil.capName(nextToken)+"()");
			}
		}
		return buf.toString();
	}

//	public static String parseExpression(String expression) {
//		BPELExpressionParser parser = new BPELExpressionParser();
//		//parser.setVariablesMap(variables);
//		//parser.setLeftHandSide(leftHandSide);
//		parser.setVerbose(false);
//		Expr expr = buildExpression(expression);
//		String output = parser.visit(expr);
//		return output;
//	}
//
//	public static Expr buildExpression(String inputText) {
//		Expr expr1 = XPath10Factory.create(inputText);
//		if (expr1 instanceof UnaryExpr) {
//			UnaryExpr unaryExpr = (UnaryExpr) expr1;
//			expr1 = unaryExpr.getExpr();
//		}
//		return expr1;
//	}


}
