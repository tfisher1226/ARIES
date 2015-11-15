package aries.bpel;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Part;

import org.aries.Assert;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.xpath10.AdditiveExpr;
import org.eclipse.bpel.xpath10.AllNodeStep;
import org.eclipse.bpel.xpath10.BinaryExpr;
import org.eclipse.bpel.xpath10.CommentNodeStep;
import org.eclipse.bpel.xpath10.EqualityExpr;
import org.eclipse.bpel.xpath10.Expr;
import org.eclipse.bpel.xpath10.FilterExpr;
import org.eclipse.bpel.xpath10.FunctionCallExpr;
import org.eclipse.bpel.xpath10.LiteralExpr;
import org.eclipse.bpel.xpath10.LocationPath;
import org.eclipse.bpel.xpath10.LogicalExpr;
import org.eclipse.bpel.xpath10.MultiplicativeExpr;
import org.eclipse.bpel.xpath10.NameStep;
import org.eclipse.bpel.xpath10.NumberExpr;
import org.eclipse.bpel.xpath10.PathExpr;
import org.eclipse.bpel.xpath10.Predicate;
import org.eclipse.bpel.xpath10.ProcessingInstructionNodeStep;
import org.eclipse.bpel.xpath10.RelationalExpr;
import org.eclipse.bpel.xpath10.Step;
import org.eclipse.bpel.xpath10.TextNodeStep;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.UnionExpr;
import org.eclipse.bpel.xpath10.VariableReferenceExpr;
import org.eclipse.wst.wsdl.Message;

import aries.codegen.util.TokenUtil;


public class BPELExpressionParser {

	private int offset = 0;

	private boolean verbose;
	
	private boolean leftHandSide;

	private Map<String, Variable> variables;

	
	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public boolean isLeftHandSide() {
		return leftHandSide;
	}

	public void setLeftHandSide(boolean leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public Map<String, Variable> getVariablesMap() {
		if (variables == null)
			variables = new HashMap<String, Variable>();
		return variables;
	}

	public void setVariablesMap(Map<String, Variable> variables) {
		this.variables = variables;
	}

	protected String visit(AdditiveExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText());
		String output = generate(arg0);
		return output;
	}

	protected String visit(AllNodeStep arg0) {
		out("{0}: axis={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());
		return null;
	}

	protected String visit(CommentNodeStep arg0) {
		out("{0}: {1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());			
		return null;
	}

	protected String visit(EqualityExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText());
		String output = generate(arg0);
		return output;
	}

	protected String visit(FilterExpr arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("expression:");
		visit(arg0.getExpr());
		out("predicates:");
		visitList(arg0.getPredicates());
		return null;
	}
	
	protected String visit(FunctionCallExpr arg0) {
		out("{0}: name={1}, prefix={2}, #arguments={3}, text={4}", //$NON-NLS-1$
				arg0.getClass().getSimpleName(),
				arg0.getFunctionName(),
				arg0.getPrefix(),
				arg0.getParameters().size(),
				arg0.getText());
		out("arguments:");
		//visitList(parameters);
		
		//functionCallGeneratorMap.get(arg0.getFunctionName());
		
		String output = null;
		String function = arg0.getFunctionName();
		if (function.equals("abs"))
			output = generateABS(arg0);
		else if (function.equals("concat"))
			output = generateCONCAT(arg0);
		else if (function.equals("count"))
			output = generateCOUNT(arg0);
		else if (function.equals("number"))
			output = generateNUMBER(arg0);
		else if (function.equals("true"))
			output = "Boolean.TRUE";
		return output;
	}
	
	protected String visit(LiteralExpr arg0) {
		out("{0}: literal={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getLiteral(),
				arg0.getText());
		String literal = arg0.getLiteral();
		if (literal.toLowerCase().equals("true") || literal.toLowerCase().equals("false")) {
			return literal.toLowerCase();
		}
		String output = "\""+literal+"\"";
		return output;
	}

	protected String visit(LocationPath arg0) {
		out("{0}: text={1}", 
				arg0.getClass().getSimpleName(),
				arg0.getText());
		out("steps:");			
		List<Step> steps = arg0.getSteps();
		StringBuffer output = new StringBuffer();
		Iterator<Step> iterator = steps.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Step step = iterator.next();
			String s0 = visit(step);
			if (i > 0)
				output.append(".");
			output.append(s0);
		}
		return output.toString();
	}

	protected String visit(LogicalExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText());
		String output = generate(arg0);
		return output;
	}
	
	protected String visit(MultiplicativeExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText());
		String output = generate(arg0);
		return output;
	}
	
	protected String visit(NameStep arg0) {
		out("{0}: axis={1}, name={2}, prefix={3}, text={4}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getLocalName(),
				arg0.getPrefix(),
				arg0.getText());
		out("predicates:");
		String localName = arg0.getLocalName();
		if (localName.equals("null"))
			return localName;
		//String output = arg0.getLocalName();
		String output = null;
		if (leftHandSide)
			output = JavaLanguageProvider.generateSetter(localName);
		else output = JavaLanguageProvider.generateGetter(localName);
		String predicates = visitList(arg0.getPredicates());
		if (!leftHandSide && predicates.length() > 0)
			output += ".get("+predicates+")";
		return output;
	}
	
	protected String visit(NumberExpr arg0) {
		out("{0}: {1}",
				arg0.getClass().getSimpleName(),
				arg0.getNumber());			
		return arg0.getNumber().toString();
	}

	protected String visit(PathExpr arg0) {
		out("{0}: text={1}",
				arg0.getClass().getSimpleName(),
				arg0.getText());
		out("filterExpr:");
		String s0 = visit(arg0.getFilterExpr());			
		out("locationPath:");
		String s1 = visit(arg0.getLocationPath());
		if (s1.indexOf(".") > -1) 
			return s0+"."+s1;
		//if (isLeftHandSide())
		//	return s0+"."+s1;
		//return s0+"."+JavaLanguageProvider.generateGetter(s1);
		return s0+"."+s1;
	}

	protected String visit(Predicate arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("expression:");
		String output = visit(arg0.getExpr());			
		return output;
	}

	protected String visit(ProcessingInstructionNodeStep arg0) {
		out("{0}: name={1}, axis={2}, text={3}",arg0.getClass().getSimpleName(),
				arg0.getName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());
		return null;
	}
	
	protected String visit(RelationalExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		String output = generate(arg0);
		return output;
	}

	protected String visit(TextNodeStep arg0) {
		out("{0}: axis={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());		
		return null;
	}

	protected String visit(UnaryExpr arg0) {
		out("{0}: text={1}", arg0.getClass().getSimpleName(),arg0.getText());
		out("expression:");			
		String output = visit(arg0.getExpr());			
		return output;
	}

	protected String visit(UnionExpr arg0) {
		out("{0}: text={1}",
				arg0.getClass().getSimpleName(),										
				arg0.getText());
		String output = generate(arg0);
		return output;
	}

	protected String visit(VariableReferenceExpr arg0) {
		out("{0}: variable={1}, prefix={2}, text={3}", 
				arg0.getClass().getSimpleName(),
				arg0.getVariableName(),
				arg0.getPrefix(),					
				arg0.getText());			
		String variableName = arg0.getVariableName();
		int indexOfDot = variableName.indexOf(".");
		if (indexOfDot != -1) {
			String[] segments = TokenUtil.tokenize(variableName);
			Assert.isTrue(segments.length == 2, "Unexpected variable format: "+variableName);
			Variable variable = getVariablesMap().get(segments[0]);
			Assert.notNull(variable, "Variable not found: "+segments[0]);
			Message messageType = variable.getMessageType();
			if (messageType != null) {
				//message type variable, take part name to be the variable name
				@SuppressWarnings("unchecked") Iterator<Part> iterator = messageType.getParts().values().iterator();
				while (iterator.hasNext()) {
					Part part = iterator.next();
					if (part.getName().equals(segments[1])) {
						variableName = "this."+variable.getName()+"_"+segments[1];
						break;
					}
				}
			}
		}
		return variableName;
	}
	
	protected String visitList(List<?> list) {	
		offset += 3;
		StringBuffer output = new StringBuffer();
		Iterator<?> it = list.iterator();	
		for (int i=0; it.hasNext(); i++) {
			if (i > 0)
				output.append(", ");
			output.append(visit(it.next()));
		}
		offset -= 3;
		return output.toString();
	}
	
	public String visit(Object expr) {
		offset += 2;
		String output = null;
		if (expr instanceof VariableReferenceExpr) {
			output = visit((VariableReferenceExpr) expr);
		} else if (expr instanceof UnionExpr) {
			output = visit((UnionExpr) expr);
		} else if (expr instanceof UnaryExpr) {
			output = visit((UnaryExpr) expr);
		} else if (expr instanceof TextNodeStep ) {
			output = visit((TextNodeStep) expr) ;
		} else if (expr instanceof RelationalExpr) {
			output = visit((RelationalExpr) expr);
		} else if (expr instanceof ProcessingInstructionNodeStep) {
			output = visit((ProcessingInstructionNodeStep) expr) ;
		} else if (expr instanceof Predicate) {
			output = visit((Predicate) expr );
		} else if (expr instanceof PathExpr) {
			output = visit((PathExpr) expr) ;
		} else if (expr instanceof NumberExpr) {
			output = visit((NumberExpr) expr);
		} else if (expr instanceof NameStep) {
			output = visit((NameStep) expr);
		} else if (expr instanceof MultiplicativeExpr) {
			output = visit((MultiplicativeExpr) expr);
		} else if (expr instanceof LogicalExpr) {
			output = visit((LogicalExpr) expr);
		} else if (expr instanceof LocationPath) {
			output = visit((LocationPath) expr);
		} else if (expr instanceof LiteralExpr) {
			output = visit((LiteralExpr) expr);
		} else if (expr instanceof FunctionCallExpr) {
			output = visit((FunctionCallExpr) expr);
		} else if (expr instanceof FilterExpr) {
			output = visit((FilterExpr) expr);
		} else if (expr instanceof EqualityExpr) {
			output = visit((EqualityExpr) expr);
		} else if (expr instanceof CommentNodeStep) {
			output = visit((CommentNodeStep)expr);
		} else if (expr instanceof AllNodeStep) {
			output = visit((AllNodeStep) expr);
		} else if (expr instanceof AdditiveExpr) {
			output = visit((AdditiveExpr) expr);
		} else if (expr instanceof List) {
			output = visitList((List<?>) expr);
		} else if (expr != null) {
			out("Error: Unknown expression: {0} ", expr.getClass().getName()); //$NON-NLS-1$
		} else {
			out("null - unset");
		}
		
		offset -= 2;
		if (output != null)
			out(output);
		return output;
	}		
	
	

	private String generate(BinaryExpr arg0) {
		out("LHS:");
		String lhs = visit(arg0.getLHS());			
		out("RHS:");
		String rhs = visit(arg0.getRHS());
		String operator = arg0.getOperator();
		if (operator.equals("="))
			operator = "==";
		//String output = "("+lhs+" "+operator+" "+rhs+")";
		String output = lhs+" "+operator+" "+rhs;
		return output;
	}
	
	
	
	void out(String msg, Object ... args) {
		if (!verbose)
			return;
		for(int i=0; i < offset; i++)
			System.out.print(' ');
		println(msg, args);
	}

	void println(String msg, Object ... args ) {
		print(msg, args);
		System.out.println(""); //$NON-NLS-1$
		System.out.flush();
	}
	
	void print(String msg, Object ... args ) {
		String text = MessageFormat.format(msg, args);
		System.out.print(text);
		System.out.flush();
	}

	
	protected String generateABS(FunctionCallExpr arg0) {
		List<Expr> parameters = arg0.getParameters();
		StringBuffer output = new StringBuffer();
		output.append("Math.abs");
		output.append("(");
		Iterator<Expr> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Expr expr = (Expr) iterator.next();
			if (i > 0)
				output.append(", ");
			output.append(visit(expr));
		}
		output.append(")");
		return output.toString();
	}

	protected String generateCONCAT(FunctionCallExpr arg0) {
		List<Expr> parameters = arg0.getParameters();
		StringBuffer output = new StringBuffer();
		Iterator<Expr> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Expr expr = (Expr) iterator.next();
			if (i > 0)
				output.append(" + ");
			output.append(visit(expr));
		}
		return output.toString();
	}

	protected String generateCOUNT(FunctionCallExpr arg0) {
		List<Expr> parameters = arg0.getParameters();
		StringBuffer output = new StringBuffer();
		Assert.isTrue(parameters.size() == 1);
		output.append(visit(parameters.get(0)));
		output.append(".size()");
		return output.toString();
	}

	protected String generateNUMBER(FunctionCallExpr arg0) {
		List<Expr> parameters = arg0.getParameters();
		StringBuffer output = new StringBuffer();
		output.append("Integer.parseInt");
		output.append("(");
		Assert.isTrue(parameters.size() == 1);
		output.append(visit(parameters.get(0)));
		output.append(")");
		return output.toString();
	}

}
