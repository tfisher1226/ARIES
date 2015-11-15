package aries.bpel;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.xpath10.AdditiveExpr;
import org.eclipse.bpel.xpath10.AllNodeStep;
import org.eclipse.bpel.xpath10.CommentNodeStep;
import org.eclipse.bpel.xpath10.EqualityExpr;
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
import org.eclipse.bpel.xpath10.TextNodeStep;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.UnionExpr;
import org.eclipse.bpel.xpath10.VariableReferenceExpr;


public class Walker {

	int offset = 0;

	protected void visit (AdditiveExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());
		out("RHS:");
		visit(arg0.getRHS());
	}

	protected void visit(AllNodeStep arg0) {
		out("{0}: axis={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());
	}

	protected void visit(CommentNodeStep arg0) {
		out("{0}: {1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());			
	}

	protected void visit(EqualityExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());			
		out("RHS:");
		visit(arg0.getRHS());			
	}

	protected void visit(FilterExpr arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("expression:");
		visit(arg0.getExpr());
		out("predicates:");
		visitList(arg0.getPredicates());
	}
	
	protected void visit(FunctionCallExpr arg0) {
		out("{0}: name={1}, prefix={2}, #arguments={3}, text={4}", //$NON-NLS-1$
				arg0.getClass().getSimpleName(),
				arg0.getFunctionName(),
				arg0.getPrefix(),
				arg0.getParameters().size(),
				arg0.getText());	
		out("arguments:");
		visitList(arg0.getParameters());
	}
	
	protected void visit(LiteralExpr arg0) {
		out("{0}: literal={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getLiteral(),
				arg0.getText());			
	}

	protected void visit (LocationPath arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("steps:");			
		visitList(arg0.getSteps());
	}

	protected void visit(LogicalExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());
		out("RHS:");
		visit(arg0.getRHS());			
	}
	
	protected void visit(MultiplicativeExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());
		out("RHS:");
		visit(arg0.getRHS());
	}

	
	protected void visit(NameStep arg0) {
		out("{0}: axis={1}, name={2}, prefix={3}, text={4}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getLocalName(),
				arg0.getPrefix(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());						
	}
	
	protected void visit(NumberExpr arg0) {
		out("{0}: {1}",arg0.getClass().getSimpleName(),arg0.getNumber());			
	}

	protected void visit(PathExpr arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("filterExpr:");
		visit(arg0.getFilterExpr());			
		out("locationPath:");
		visit(arg0.getLocationPath());					
	}

	protected void visit(Predicate arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText());
		out("expression:");
		visit(arg0.getExpr());			
	}

	protected void visit(ProcessingInstructionNodeStep arg0) {
		out("{0}: name={1}, axis={2}, text={3}",arg0.getClass().getSimpleName(),
				arg0.getName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());
	}
	
	protected void visit(RelationalExpr arg0) {
		out("{0}: op={1} text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getOperator(),
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());
		out("RHS:");
		visit(arg0.getRHS());
	}

	protected void visit(TextNodeStep arg0) {
		out("{0}: axis={1}, text={2}",
				arg0.getClass().getSimpleName(),
				arg0.getAxis(),
				arg0.getText());
		out("predicates:");
		visitList(arg0.getPredicates());		
	}

	protected void visit(UnaryExpr arg0) {
		out("{0}: text={1}",arg0.getClass().getSimpleName(),arg0.getText() ) ;
		out("expression:");			
		visit(arg0.getExpr());			
	}

	protected void visit(UnionExpr arg0) {
		out("{0}: text={1}",
				arg0.getClass().getSimpleName(),										
				arg0.getText() );
		out("LHS:");
		visit(arg0.getLHS());
		out("RHS:");
		visit(arg0.getRHS());	
	}

	protected void visit(VariableReferenceExpr arg0) {
		out("{0}: variable={1}, prefix={2}, text={3}",arg0.getClass().getSimpleName(),
				arg0.getVariableName(),
				arg0.getPrefix(),					
				arg0.getText());			
	}
	
	protected void visitList (List<?> list) {	
		offset += 3;
		Iterator<?> it = list.iterator();			
		while (it.hasNext()) {
			visit(it.next());
		}
		offset -= 3;
	}
	
	public void visit (Object expr) {
		offset += 2;
		
		if (expr instanceof VariableReferenceExpr) {
			visit((VariableReferenceExpr) expr);
		} else if (expr instanceof UnionExpr) {
			visit((UnionExpr) expr);
		} else if (expr instanceof UnaryExpr) {
			visit((UnaryExpr) expr);
		} else if (expr instanceof TextNodeStep ) {
			visit((TextNodeStep) expr) ;
		} else if (expr instanceof RelationalExpr) {
			visit((RelationalExpr) expr);
		} else if (expr instanceof ProcessingInstructionNodeStep) {
			visit((ProcessingInstructionNodeStep) expr) ;
		} else if (expr instanceof Predicate) {
			visit((Predicate) expr );
		} else if (expr instanceof PathExpr) {
			visit((PathExpr) expr) ;
		} else if (expr instanceof NumberExpr) {
			visit((NumberExpr) expr);
		} else if (expr instanceof NameStep) {
			visit((NameStep) expr);
		} else if (expr instanceof MultiplicativeExpr) {
			visit((MultiplicativeExpr) expr);
		} else if (expr instanceof LogicalExpr) {
			visit((LogicalExpr) expr);
		} else if (expr instanceof LocationPath) {
			visit((LocationPath) expr);
		} else if (expr instanceof LiteralExpr) {
			visit((LiteralExpr) expr);
		} else if (expr instanceof FunctionCallExpr) {
			visit((FunctionCallExpr) expr);
		} else if (expr instanceof FilterExpr) {
			visit((FilterExpr) expr);
		} else if (expr instanceof EqualityExpr) {
			visit((EqualityExpr) expr);
		} else if (expr instanceof CommentNodeStep) {
			visit((CommentNodeStep)expr);
		} else if (expr instanceof AllNodeStep) {
			visit((AllNodeStep) expr);
		} else if (expr instanceof AdditiveExpr) {
			visit((AdditiveExpr) expr);
		} else if (expr instanceof List) {
			visitList((List) expr);
		} else if (expr != null) {
			out("Panic: Unknown expression kind {0} ",expr.getClass().getName()); //$NON-NLS-1$
		} else {
			out("null - unset");
		}
		
		offset -= 2;
	}		
	
	void out ( String msg, Object ... args ) {
		for(int i=0; i < offset; i++) {
			System.out.print(' ');
		}
		pl (msg,args);
	}

	static void pl (String msg, Object ... args ) {
		p(msg,args);
		System.out.println(""); //$NON-NLS-1$
		System.out.flush();
	}
	
	static void p (String msg, Object ... args ) {
		String text = MessageFormat.format(msg, args);
		System.out.print(text);
		System.out.flush();
	}
	
}
